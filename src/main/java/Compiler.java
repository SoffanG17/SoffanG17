import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class Compiler {

    static String mavenHomeAdress = "/usr/local/Cellar/maven/3.6.3_1/libexec";

    public static void setMavenHomeAdress(String address){
        mavenHomeAdress = address;
    }

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
