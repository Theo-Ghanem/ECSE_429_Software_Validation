import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // path to feature files
        glue = {"stepdefinitions"}, // package with step definitions
        plugin = {
                "pretty", // readable output
                "html:target/cucumber-reports/cucumber.html", // html report
                "json:target/cucumber-reports/cucumber.json" // json report
        },
        monochrome = true, // readable console output
        tags = "" // you can add tags to run specific scenarios
)
public class TestRunner {
    // This class should be empty
}