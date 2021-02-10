import java.io.File;
import org.apache.commons.io.FileUtils;

public class RepositoryCloner {


    public static void deleteRepo(File repo){
        try{
            FileUtils.deleteDirectory(repo);
        }catch(Exception e) {
            System.out.println("The repo could not be deleted");
        }
        System.out.println("The repo is deleted");
        return;
    }

}
