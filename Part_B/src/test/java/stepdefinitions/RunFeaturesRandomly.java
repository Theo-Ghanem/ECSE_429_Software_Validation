package stepdefinitions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RunFeaturesRandomly {

    public static void main(String[] args) {
        String featuresPath = "src/test/resources/features";

        try {
            List<Path> featureFiles = getListOfFeatureFiles(featuresPath);

            Collections.shuffle(featureFiles);

            for (Path file : featureFiles) {
                runFeatureFile(file);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static List<Path> getListOfFeatureFiles(String directoryPath) throws IOException {
        try (var paths = Files.walk(Paths.get(directoryPath))) {
            return paths.filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".feature"))
                        .collect(Collectors.toList());
        }
    }

    private static void runFeatureFile(Path featureFilePath) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("mvn", "test", "-Dcucumber.features=" + featureFilePath.toString());
        processBuilder.inheritIO();
        Process process = processBuilder.start();
        process.waitFor();
    }
}