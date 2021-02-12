import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.AuthenticationStore;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.util.BasicAuthentication;
import org.eclipse.jetty.client.util.StringRequestContent;
import org.eclipse.jetty.http.HttpMethod;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class ApiClient {

    private HttpClient client;
    private final String outhToken;

    ApiClient() throws Exception {
        // Instantiate HttpClient.
        client = new HttpClient();
        // Configure HttpClient, for example:
        client.setName("CI-server");
        // Start HttpClient.
        client.start();

        File tokenFile = new File("src/main/java/token.txt");
        Scanner myReader = new Scanner(tokenFile);
        outhToken = myReader.nextLine();
    }
    public void stop(){
        try {
            client.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void comment(String id, String comment) throws InterruptedException, ExecutionException, TimeoutException, IOException {

        org.eclipse.jetty.client.api.Request clientReq = client.newRequest("https://api.github.com/repos/SoffanG17/SoffanG17/commits/" + id + "/comments");
        clientReq.method(HttpMethod.POST)
                .header("Authorization", "Bearer " + outhToken)
                .header("Accept", "application/vnd.github.v3+json")
                .body(new StringRequestContent("{\"body\":\"" + comment + "\"}"))
        ;

        ContentResponse contResp = clientReq.send();
        System.out.println(contResp.getContentAsString());


    }

}
