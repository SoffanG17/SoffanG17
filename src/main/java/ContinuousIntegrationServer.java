import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.*;
import java.util.*;

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
    public void executeTests(String repoBaseDirPath) throws IOException {
        final String TEST_REPORT_FOLDER = repoBaseDirPath + "target/surefire-reports/";
        final String CSV_COVERAGE_REPORT_PATH = repoBaseDirPath + "target/site/jacoco/jacoco.csv";
        final String CLASS_NAME_KEY = "CLASS";
        final String METHODS_MISSED_KEY = "METHOD_MISSED";
        final String METHODS_COVERED_KEY = "METHOD_COVERED";
        printPWD(repoBaseDirPath);

        // Execute tests

        // https://mkyong.com/java/how-to-execute-shell-command-from-java/
        // https://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html
        File baseDir = new File(repoBaseDirPath);
        File[] baseDirFiles = baseDir.listFiles();
        boolean foundPOM = false;
        if (baseDirFiles == null) {
            System.out.println("The path of " + repoBaseDirPath + " is wrong or the repo is empty! Aborting testing.");
            return;
        }
        else {
            for (File f : baseDirFiles) {
                if (f.getName().equals("pom.xml")) {
                    foundPOM = true;
                    break;
                }
            }
        }
        if (!foundPOM) {
            System.out.println("Repo at " + repoBaseDirPath + " contains no pom.xml! Aborting testing.");
            return;
        }
        ProcessBuilder pb = new ProcessBuilder();
        pb.directory(new File(repoBaseDirPath));
        pb.command("bash", "-c", "mvn test");

        try {
            Process process = pb.start();
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success for ProcessBuilder process!");
            } else {
                System.out.println("Exited ProcessBuilder process abnormally. Exit val: " + exitVal);
            }
        } catch (IOException e) {
            System.out.println("IO exception when trying to execute ProcessBuilder process");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Interrupted exception when trying to execute ProcessBuilder process");
            e.printStackTrace();
        }
        ArrayList<String> testResults = new ArrayList<>();
        try {
            File dir = new File(TEST_REPORT_FOLDER);
            File[] files = dir.listFiles();
            if (files == null) {
                System.out.println("No test reports found!");
            }
            else {
                for (File f:files) {
                    // Only read the plain text reports
                    if (!f.getName().toLowerCase().endsWith(".txt")) continue;
                    StringBuilder sb = new StringBuilder();
                    Scanner scanner = new Scanner(f);
                    while (scanner.hasNextLine()) {
                        sb.append(scanner.nextLine());
                        sb.append("\n");
                    }
                    scanner.close();
                    testResults.add(sb.toString());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error when trying to find test report files. Supplied path:\n" +
                TEST_REPORT_FOLDER);
            e.printStackTrace();
        }
        if (!testResults.isEmpty()) {
            System.out.println("-------------------------------------Test results-------------------------------------");
            for (String s: testResults) {
                System.out.println(s);
            }
        }
        else {
            System.out.println("-------------------------------------No test results to present-------------------------------------");
        }

        // Branch coverage
        // https://www.baeldung.com/java-csv-file-array
        List<List<String>> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(CSV_COVERAGE_REPORT_PATH))) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Could not find file for method coverage. Supplied path:\n" +
                CSV_COVERAGE_REPORT_PATH);
        }
        catch (IOException e) {
            System.out.println("IOException when trying to read file for branch coverage. Supplied path:\n" +
                CSV_COVERAGE_REPORT_PATH);
        }
        if (records.size() != 2) {
            throw new IOException("Unexpected input from method coverage report. Expected a CSV file with " +
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

    private void printPWD(String path) {
        try {
            ProcessBuilder pb = new ProcessBuilder();
            pb.directory(new File(path));
            pb.command("bash", "-c", "pwd");
            Process process = pb.start();
            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
                output.append("\n");
            }
            System.out.println("PWD of the supplied path is:");
            System.out.println(output);
        }
        catch (Exception e) {
            System.out.println("Error in printing PWD. Perhaps the path was non-existent.");
        }
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
        ciServer.executeTests("./");
    }
}

