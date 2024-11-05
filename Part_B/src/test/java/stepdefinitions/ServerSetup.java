package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class ServerSetup {

    private String baseUrl = "http://localhost:4567";
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private Process serverProcess;


    @Before
    public void startServer() throws IOException {
        // Implement the logic to start the server if it's not running
        HttpGet request = new HttpGet(baseUrl + "/todos");
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            if (response.getStatusLine().getStatusCode() != 200) {
                // Code to start the server
                serverProcess = Runtime.getRuntime().exec("java -jar /Users/theoghanem/Dev/ECSE_429_Software_Validation/Application_Being_Tested/runTodoManagerRestAPI-1.5.5.jar");
                // Add a delay to give the server time to start
                Thread.sleep(5000);
                System.out.println("Server started successfully.");
            } else {
                System.out.println("Server is already running.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void stopServer() throws IOException {
        if (serverProcess != null) {
            serverProcess.destroy();
            System.out.println("Server stopped successfully.");
        }
    }
}

