import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryClonerTest {


    /**
     * Tests whether the method RepositoryCloner.cloneRepo() works by checking
     * if the directory of the repo exists.
     * @throws IOException
     * @throws GitAPIException
     */
    @Test
    void cloneRepoTest() throws IOException, GitAPIException {
        File dir = new File("clonedRepo");
        RepositoryCloner.cloneRepo("https://github.com/snissy/Sandbox.git","", "clonedRepo1");
        Assertions.assertTrue(dir.isDirectory());
    }
    /**
     * Tests whether the method RepositoryCloner.cloneRepo() works by checking
     * if the directory of the repo does not exist when trying to clone a non-existent repo.
     * @throws IOException
     * @throws GitAPIException
     */
    @Test
    void cloneRepoFalse() throws IOException, GitAPIException {
        File dir = new File("clonedRepo2");
        RepositoryCloner.cloneRepo("https://github.com/snisy/Sandbox.git","", "clonedRepo2");
        Assertions.assertFalse(dir.isDirectory());
    }

    /**
     * Tests whether the method RepositoryCloner.deleteRepo() works by checking
     * if the directory of the repo does not exist.
     * @throws IOException
     * @throws GitAPIException
     */
    @Test
    void deleteRepoTest() throws IOException, GitAPIException {
        File dir = new File("clonedRepo");
        RepositoryCloner.deleteRepo(dir);
        Assertions.assertFalse(dir.isDirectory());
    }

    /**
     * Tests whether the method RepositoryCloner.deleteRepo() throws exception when
     * the dir does not exist.
     */
    @Test
    void deleteIllegalArgument() throws IOException, GitAPIException {
        File dir = new File("clonedRepo2");
        Assertions.assertThrows(IllegalArgumentException.class, () -> RepositoryCloner.deleteRepo(dir));
    }
}

