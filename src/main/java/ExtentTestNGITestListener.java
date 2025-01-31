import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestNGITestListener implements ITestListener {
    private static ExtentReports extent = new ExtentReports();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    static {
        extent.attachReporter(new ExtentSparkReporter("extentReports.html"));
    }

    public static ExtentTest getTest() {
        return test.get();
    }
    @Override
    public void onStart(ITestContext context) {
        System.out.println("Start of Execution(TEST): " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("End of Execution(TEST): " + context.getName());
        extent.flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test Started->" + result.getName());
        test.set(extent.createTest(result.getMethod().getMethodName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test Pass->" + result.getName());
        test.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test Failed->" + result.getName());
        test.get().log(Status.FAIL, "Test Failed");
    }
    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("Test Failed but within success percentage->" + result.getName());
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }
}