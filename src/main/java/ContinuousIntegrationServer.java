import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.opencsv.CSVReader;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

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

        // here you do all the continuous integration tasks
        // for example
        // 1st clone your repository
        // 2nd compile the code

        response.getWriter().println("CI job done");
    }

    // Method for P1 below this line


    // Method for P2 below this line
    /*the CI server supports executing the automated tests of the group project.
    Testing is triggered as webhook, on the branch where the change has been made,
    as specified in the HTTP payload.*/
    private void executeTests(String repoBaseDirPath) throws IOException {
        final String CLASS_NAME_KEY = "CLASS";
        final String METHODS_MISSED_KEY = "METHOD_MISSED";
        final String METHODS_COVERED_KEY = "METHOD_COVERED";

        // Branch coverage
        // https://www.baeldung.com/java-csv-file-array
        List<List<String>> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(repoBaseDirPath + "/target/site/jacoco/jacoco.csv"))) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Could not find file for branch coverage. Supplied path:\n" +
                repoBaseDirPath + "/target/site/jacoco/jacoco.csv");
        }
        catch (IOException e) {
            System.out.println("IOException when trying to read file for branch coverage. Supplied path:\n" +
                repoBaseDirPath + "/target/site/jacoco/jacoco.csv");
        }
        if (records.size() != 2) {
            throw new IOException("Unexpected input from branch coverage report. Expected a CSV file with " +
                "one line of identifiers and one line of values. Received " + records.size() + " lines.");
        }
        HashMap<String, String> reportMap = new HashMap<>();
        for (int i = 0; i < records.get(0).size(); i++) {
            reportMap.put(records.get(0).get(i), records.get(1).get(i));
        }
        System.out.println("In the class " + reportMap.get(CLASS_NAME_KEY) + " the number of methods covered " +
            "by tests is: " + reportMap.get(METHODS_COVERED_KEY) + ",\n" +
            "and the number of methods not covered by tests is: " + reportMap.get(METHODS_MISSED_KEY));

    }


    // Method for P3 below this line


    // Method for handling history below


    // used to start the CI server in command line
    public static void main(String[] args) throws Exception {
        /*Server server = new Server(8080);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();*/
        ContinuousIntegrationServer ciServer = new ContinuousIntegrationServer();
        ciServer.executeTests(".");
    }
}

