
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.maven.RepositoryUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class CiServer {

    private Server server;


    CiServer(int port) throws Exception {

        // Create and configure a ThreadPool.
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setName("server");
        // Create a Server instance.
        server = new Server(threadPool);
        // Create a ServerConnector to accept connections from clients.
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        // Add the Connector to the Server
        server.addConnector(connector);
        // Set a simple Handler to handle requests/responses.
        server.setHandler(new CiHandler());
        // Start the Server so it starts accepting connections from clients.
        server.start();
    }
    public void join() throws InterruptedException {
        server.join();
    }

    private class CiHandler extends AbstractHandler{

        @Override
        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

            baseRequest.setHandled(true);
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);


            System.out.println(target);

            try {
                ParseInput(request);
            }catch (Exception e){
                System.out.println("Json wasn't as expected");
                System.out.println(e);
            }


            response.getWriter().println("CI job done");
        }

        public void ParseInput(HttpServletRequest request) throws IOException, InterruptedException, ExecutionException, TimeoutException, GitAPIException {

            String rawJson = request.getReader().readLine();
            System.out.println("Raw JSON: " + rawJson);
            JSONObject reqJson = new JSONObject(rawJson);

            String ref = reqJson.getString("ref");
            String cloneURL = reqJson.getJSONObject("repository").getString("clone_url");
            String repoName = reqJson.getJSONObject("repository").getString("name");
            //String commitId = reqJson.getJSONObject("head_commit").getString("id");
            String branch = RepoUtils.getBranch(ref);
            RepositoryCloner.cloneRepo(cloneURL , branch, repoName);

            //Check that there are tests

            //Builds the build

            //Tests the test

            //Comment the commit


        }

    }


}
