import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class RepositoryCloner {
    
    public static final String tempDirectoryPath = "clonedRepo";

    static String cloneRepo(String repoHttpsAdress , String branchName, String repoName) throws IOException, GitAPIException {

        try {
            File tempDirectory  = new File(repoName);
            boolean okCloning = true;

            Git git = Git.cloneRepository().
                    setURI(repoHttpsAdress).
                    setBranch(branchName).
                    setDirectory(new File(tempDirectoryPath)).
                    call();

            System.out.println("Successful cloning of repo: " + repoHttpsAdress);
            return tempDirectoryPath;
        }catch (Exception e){
            System.out.println("Repo could not be cloned");
            return "";
        }

    }

    public static void deleteRepo(File repo){
        if(!repo.isDirectory()){
            throw new IllegalArgumentException("Repo does not exist on this directory ");
        }
        try{
            FileUtils.deleteDirectory(repo);
        }catch(Exception e) {
            System.out.println("The repo could not be deleted");
        }
        System.out.println("The repo is deleted");
        return;
    }


}
