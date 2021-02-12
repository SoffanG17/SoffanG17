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

    public static void addBuild(String buildInfo, LocalDateTime timeStamp) {
        String yearDirectory = BUILD_BASE_DIRECTORY + timeStamp.getYear() + "/";
        String monthDirectory = timeStamp.getMonthValue() + "/";
        String dayDirectory = timeStamp.getDayOfMonth() + "/";
        String timeFileName = timeStamp.getHour() + "_" + timeStamp.getMinute() + "_" + timeStamp.getSecond() + ".txt";
        String filePath = yearDirectory + monthDirectory + dayDirectory + timeFileName;
        File dir = new File(yearDirectory + monthDirectory + dayDirectory);
        Path dirPath = Paths.get(yearDirectory + monthDirectory + dayDirectory);
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
