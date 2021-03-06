import com.opencsv.CSVReader;

import java.io.*;
import java.util.*;

public class ExecuteTests {
    /**
     * Executes the test cases of a Maven project with root at the specified directory. Returns a report of tests
     * results. If all tests pass the report additionally show method coverage of the tests.
     * @param repoBaseDirPath The root of the Maven project to execute test for.
     * @return report of test results.
     */
    public static String executeTests(String repoBaseDirPath) {
        final String TEST_REPORT_FOLDER = repoBaseDirPath + "target/surefire-reports/";
        final String CSV_COVERAGE_REPORT_PATH = repoBaseDirPath + "target/site/jacoco/jacoco.csv";
        final String CLASS_NAME_KEY = "CLASS";
        final String METHODS_MISSED_KEY = "METHOD_MISSED";
        final String METHODS_COVERED_KEY = "METHOD_COVERED";
        System.out.println();
        printPWD(repoBaseDirPath);
        System.out.println();
        StringBuilder finalReportSB = new StringBuilder();

        // Execute tests

        // https://mkyong.com/java/how-to-execute-shell-command-from-java/
        // https://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html
        File baseDir = new File(repoBaseDirPath);
        File[] baseDirFiles = baseDir.listFiles();
        boolean foundPOM = false;
        if (baseDirFiles == null) {
            System.out.println("The path of " + repoBaseDirPath + " is wrong or the repo is empty! Aborting testing.");
            return "The path of " + repoBaseDirPath + " is wrong or the repo is empty! No tests were ran.";
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
            return "Repo at " + repoBaseDirPath + " contains no pom.xml! No tests were ran.";
        }
        ProcessBuilder pb = new ProcessBuilder();
        pb.directory(new File(repoBaseDirPath));
        pb.command("bash", "-c", "mvn test");

        try {
            Process process = pb.start();
            int exitVal = process.waitFor();
            System.out.println("Exited ProcessBuilder process 'mvn test' with value: " + exitVal);
        }
        catch (IOException e) {
            System.out.println("IO exception when trying to execute ProcessBuilder 'mvn test' process");
            return "IO exception when trying to execute ProcessBuilder 'mvn test' process";
        }
        catch (InterruptedException e) {
            System.out.println("InterruptedException when trying to execute ProcessBuilder 'mvn test' process");
            return "InterruptedException when trying to execute ProcessBuilder 'mvn test' process";
        }
        ArrayList<String> testResults = new ArrayList<>();
        try {
            File dir = new File(TEST_REPORT_FOLDER);
            File[] files = dir.listFiles();
            if (files == null) {
                System.out.println("No test reports found! Expected path for reports: " + TEST_REPORT_FOLDER);
                return "No test reports found! Expected path for reports: " + TEST_REPORT_FOLDER;
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
        }
        catch (FileNotFoundException e) {
            System.out.println("Error when trying to find test report files. Supplied path:\n" +
                TEST_REPORT_FOLDER);
            return "Error when trying to find test report files. Supplied path:\n" + TEST_REPORT_FOLDER;
        }
        if (!testResults.isEmpty()) {
            finalReportSB.append("\n----------------------------------Test results---------------------------------\n");
            for (String s: testResults) {
                finalReportSB.append(s);
                finalReportSB.append("\n");
            }
        }
        else {
            System.out.println("No test results to present. Perhaps the test report folder had no .txt files.");
            return "No test results to present. Perhaps the test report folder had no .txt files.";
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
            finalReportSB.append("Could not find file for method coverage. Supplied path:\n");
            finalReportSB.append(CSV_COVERAGE_REPORT_PATH);
            return finalReportSB.toString();
        }
        catch (IOException e) {
            System.out.println("IOException when trying to read file for branch coverage. Supplied path:\n" +
                CSV_COVERAGE_REPORT_PATH);
            finalReportSB.append("IOException when trying to read file for branch coverage. Supplied path:\n");
            finalReportSB.append(CSV_COVERAGE_REPORT_PATH);
            return finalReportSB.toString();
        }
        if (records.size() < 2) {
            throw new IllegalArgumentException("Unexpected input from method coverage report. Expected a CSV file with " +
                "one line of identifiers and at least one line of values. Received " + records.size() + " lines.");
        }
        List<HashMap<String, String>> reportMaps = new ArrayList<>();
        for (int i = 1; i < records.size(); i++) {
            reportMaps.add(new HashMap<>());
            for (int j = 0; j < records.get(0).size(); j++) {
                reportMaps.get(i-1).put(records.get(0).get(j), records.get(i).get(j));
            }
        }
        for (int i = 0; i < reportMaps.size(); i++) {
            finalReportSB.append("\nIn the class ").append(reportMaps.get(i).get(CLASS_NAME_KEY)).append(" the number of methods covered ")
                .append("by tests is: ").append(reportMaps.get(i).get(METHODS_COVERED_KEY)).append(",\n")
                .append("and the number of methods not covered by tests is: ").append(reportMaps.get(i).get(METHODS_MISSED_KEY)).append("\n");
        }

        return finalReportSB.toString();
    }

    /**
     * Prints the absolute path to the supplied directory, like the bash command PWD.
     * @param path what folder to execute a PWD-like command from.
     */
    private static void printPWD(String path) {
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
            System.out.print(output);
        }
        catch (Exception e) {
            System.out.println("Error in printing PWD. Perhaps the path was non-existent.");
        }
    }

}
