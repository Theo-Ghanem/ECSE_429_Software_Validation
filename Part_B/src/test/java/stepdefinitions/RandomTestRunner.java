package stepdefinitions;

import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

import java.util.Collections;
import java.util.List;

@RunWith(RandomTestRunner.RandomSuite.class)
@CucumberOptions(features = "src/test/resources/features", glue = "stepdefinitions")
public class RandomTestRunner {

    public static class RandomSuite extends Suite {

        public RandomSuite(Class<?> klass, RunnerBuilder builder) throws InitializationError {
            super(klass, getSortedRunners(builder, klass));
        }

        private static List<Runner> getSortedRunners(RunnerBuilder builder, Class<?> klass) throws InitializationError {
            List<Runner> runners = builder.runners(klass, new Class<?>[0]);
            Collections.shuffle(runners);
            return runners;
        }
    }
}