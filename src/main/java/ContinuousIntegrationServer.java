import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.BufferedReader;
import java.io.IOException;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.*;


import org.json.JSONArray;
import org.json.JSONObject;

/**
 Skeleton of a ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
 */
public class ContinuousIntegrationServer extends AbstractHandler {
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        System.out.println(target);

        ParseInput(request);

        // here you do all the continuous integration tasks
        // for example
        // 1st clone your repository
        // 2nd compile the code

        response.getWriter().println("CI job done");
    }

    // Method for P1 below this line


    // Method for P2 below this line


    // Method for P3 below this line
    public void ParseInput(HttpServletRequest request) throws java.io.IOException {

        String rawJson = request.getReader().readLine();
        System.out.println("Raw JSON: " + rawJson);
        JSONObject reqJson = new JSONObject(rawJson);

        String ref = reqJson.getString("ref");
        String commitId = reqJson.getJSONObject("head_commit").getString("id");

        //Check that there are tests

        //Builds the build

        //Tests the test

        //Comment the commit


    }



    // Method for handling history below


    // used to start the CI server in command line
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }
}
