import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import java.io.File;
import java.io.IOException;


public class RepositoryCloner {
    
    private static final String tempDirectoryPath = "clonedRepo";

    static String cloneRepo(String repoHttpsAdress , String branchName, String repoName) throws IOException, GitAPIException {

        try {
            File tempDirectory  = new File(repoName);
            boolean okCloning = true;

            Git git = Git.cloneRepository().
                    setURI(repoHttpsAdress).
                    setBranch(branchName).
                    setDirectory(new File(tempDirectoryPath)).
                    call();

            System.out.println((okCloning ? "Successful " : "Unsuccessful ") + "cloning of repo: " + repoHttpsAdress);
            return tempDirectoryPath;
        }catch (Exception e){
            System.out.println("Repo could not be cloned");
            return null;
        }

    }


}
