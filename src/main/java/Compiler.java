import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.util.Collections;

public class Compiler {

    static String mavenHomeAdress = "/usr/local/Cellar/maven/3.6.3_1/libexec";

    public static void main (String[] args) throws MavenInvocationException {
        Compiler.compileMavenProject("/Users/nils_merkel/IdeaProjects/DD2480/SoffanG17/pom.xml");
    }

    public static void setMavenHomeAdress(String address){
        mavenHomeAdress = address;
    }

    public static void compileMavenProject(String pomPath) throws MavenInvocationException {

        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile( new File(pomPath));
        request.setGoals( Collections.singletonList( "install" ) );

        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File(mavenHomeAdress));
        InvocationResult result = invoker.execute(request);
        if ( result.getExitCode() != 0 )
        {
            System.out.print("BUILD FAILED");
        }

    }
}
