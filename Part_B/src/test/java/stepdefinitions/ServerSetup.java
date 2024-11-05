package stepdefinitions;

import io.restassured.RestAssured;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.*;

public class ServerSetup {
    public static final String BASE_URL = "http://localhost:4567";
    private static Process serverProcess;

    @BeforeClass
    public static void beforeAllTests() {
        System.out.println("Setting up server for tests");
        RestAssured.baseURI = BASE_URL;
        startServer();
    }

    @AfterClass
    public static void killAllTests() {
        System.out.println("Tearing down server after tests");
        if (serverProcess != null) {
            serverProcess.destroy();
        }
    }

    private static void startServer() {
        boolean success = false;
        final ExecutorService service = Executors.newSingleThreadExecutor();
        try {
            final Future<Boolean> future = service.submit(ServerSetup::startServerProcess);
            success = future.get(5000, TimeUnit.MILLISECONDS);
        } catch (Exception ignored) {
        } finally {
            if (!success) {
                handleServerStartFailure();
            }
        }
    }

    private static boolean startServerProcess() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "../runTodoManagerRestAPI-1.5.5.jar");
            if (serverProcess != null) {
                serverProcess.destroy();
            }
            serverProcess = processBuilder.start();
            return waitForServerToStart();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean waitForServerToStart() throws Exception {
        try (InputStream is = serverProcess.getInputStream();
             BufferedReader output = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = output.readLine()) != null) {
                if (line.contains("Running on 4567")) {
                    waitUntilOnline();
                    return true;
                }
            }
        }
        return false;
    }

    private static void waitUntilOnline() {
        int tries = 0;
        while (!isOnline()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }
            if (++tries > 100) {
                startServer();
                tries = 0;
            }
        }
    }

    private static boolean isOnline() {
        try {
            return RestAssured.get("/").getStatusCode() == 200;
        } catch (Exception ignored) {
            return false;
        }
    }

    private static void handleServerStartFailure() {
        if (serverProcess != null) {
            serverProcess.destroy();
        }
        System.out.println("Failed to start server -- exiting program");
        System.exit(-1);
    }


}