import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class BuildHistory {
    public final static String BUILD_BASE_DIRECTORY = "./history/";

    /**
     * Creates a .txt file for a build, containing the specified buildInfo and naming it
     * after the specified timestamp on the format yyyy_mm_dd_hh_mm_ss.txt , without padding zeros
     * (i.e. the moth January is 1 and not 01). The files are put into the folder specified in
     * BUILD_BASE_DIRECTORY, and such a directory is create if it does not already exist.
     * @param buildInfo The contents of the file.
     * @param timeStamp The time of the build.
     */
    public static void addBuild(String buildInfo, LocalDateTime timeStamp) {
        String year= timeStamp.getYear() + "_";
        String month = timeStamp.getMonthValue() + "_";
        String day = timeStamp.getDayOfMonth() + "_";
        String timeFileName = timeStamp.getHour() + "_" + timeStamp.getMinute() + "_" + timeStamp.getSecond() + ".txt";
        String filePath = BUILD_BASE_DIRECTORY + year + month + day + timeFileName;
        File dir = new File(BUILD_BASE_DIRECTORY);
        Path dirPath = Paths.get(BUILD_BASE_DIRECTORY);
        try {
            Files.createDirectories(dirPath);
        }
        catch (IOException e) {
            System.out.println("Something went wrong with directory creation. Exiting.");
            return;
        }
        if (!dir.exists()) {
            System.out.println("Something went wrong with directory creation. Exiting.");
            return;
        }

        BufferedWriter writer = null;
        try {
            File buildFile = new File(filePath);
            if (buildFile.createNewFile()) {
                System.out.println("File created: " + buildFile.getName());
            }
            else {
                System.out.println("File already exists. Exiting");
                return;
            }
            FileWriter fw = new FileWriter(buildFile);
            writer = new BufferedWriter(fw);
            writer.write(buildInfo);
        }
        catch (IOException e) {
            System.out.println("An error occurred while creating file: " + filePath + ". Exiting.");
            return;
        }
        finally {
            try{
                if(writer != null)
                    writer.close();
            }
            catch(Exception ex){
                System.out.println("Error in closing the BufferedWriter: " + ex);
            }
        }
    }
}
