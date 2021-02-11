import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import java.io.File;
import java.io.IOException;


public class RepositoryCloner {
    
    private static final String tempDirectoryPath = "clonedRepo";

    public static void main (String[] args) throws IOException, GitAPIException {

        cloneRepo("https://github.com/snissy/Sandbox.git","");
        System.out.print("asd");
    }

    static String cloneRepo(String repoHttpsAdress , String branchName) throws IOException, GitAPIException {

        createTempDirectory();
        boolean okCloning = true;

        Git git = Git.cloneRepository().
                setURI(repoHttpsAdress).
                setBranch(branchName).
                setDirectory(new File(tempDirectoryPath)).
                call();

        System.out.println((okCloning ? "Successful ":"Unsuccessful ") + "cloning of repo: " + repoHttpsAdress);
        return tempDirectoryPath;

    }

    static void createTempDirectory() throws IOException {

        File tempDirectory  = new File(tempDirectoryPath);
    }

    static void removeTempDirectory(){

    }

}
