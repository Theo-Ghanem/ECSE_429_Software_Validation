package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.*;

public class ServerSetup {
    public static final String BASE_URL = "http://localhost:4567";
    private static Process serverProcess;


    public static void startServer() {
        final ExecutorService service = Executors.newSingleThreadExecutor();
        try {
            Future<Boolean> future = service.submit(() -> {
                startServerUntimed();
                return true;
            });
            if (!future.get(5000, TimeUnit.MILLISECONDS)) {
                handleServerStartFailure();
            }
        } catch (Exception e) {
            handleServerStartFailure();
        } finally {
            service.shutdown();
        }
    }

    public static void startServerUntimed() {
        String jarPath = "../Application_Being_Tested/runTodoManagerRestAPI-1.5.5.jar";
        if (!Files.exists(Paths.get(jarPath))) {
            System.exit(-1);
        }

        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-jar", jarPath);
            destroyProcess();
            serverProcess = pb.start();
            try (BufferedReader output = new BufferedReader(new InputStreamReader(serverProcess.getInputStream()))) {
                String line;
                while ((line = output.readLine()) != null) {
                    if (line.contains("Running on 4567")) {
                        waitUntilOnline();
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleServerStartFailure() {
        destroyProcess();
        System.out.println("Server failed to start");
        System.exit(-1);
    }

    private static void destroyProcess() {
        if (serverProcess != null) {
            serverProcess.destroy();
        }
    }

    public static void waitUntilOnline() {
        int tries = 0;
        while (!isOnline()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {}
            if (++tries > 100) {
                startServer();
                tries = 0;
            }
        }
    }

    public static boolean isOnline() {
        try {
            Response response = RestAssured.get("/");
            return response.getStatusCode() == 200;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static void stopServer() {
        try {
            RestAssured.post(BASE_URL + "/shutdown");
            System.out.println("Shuting down server");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            destroyProcess();
        }
    }

}