import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class Compiler {

    static String mavenHomeAdress = "/usr/local/Cellar/maven/3.6.3_1/libexec";

    /**
     * This function sets the Maven home address later, the maven home address is used later in the compileMavenProject method.
     * */
    public static void setMavenHomeAdress(String address){
        mavenHomeAdress = address;
    }
    /**
     * This functions compile a specified maven project. It return a string whether the build was successful or failed.
     * For the method to work the HomeAddress to maven must be specified. This can be specified using Compiler.setMavenHomeAdress method.
     *
     * @param pomPath String path to were the pom-file for the maven project exists.
     * @return String containing "Build Success" or "Build Failed" if the build failed succeeded and failed respectively
     * */
    public static String compileMavenProject(String pomPath) throws MavenInvocationException, IOException {

        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile( new File(pomPath));
        request.setGoals( Collections.singletonList( "compile" ) );

        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File(mavenHomeAdress));
        InvocationResult result = invoker.execute(request);

        return result.getExitCode() == 0 ? "Build Success" : "Build Failed";

    }
}
