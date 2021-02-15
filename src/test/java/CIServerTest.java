import org.eclipse.jetty.client.HttpClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CIServerTest{

    // Tests for P1 below (remember to make multiple methods for multiple test cases)

    /**
     * DOC
     */
    @Test
    void testEntireProgram() {
        String headers = "";
        String payload = "";

        try {
            File headerFile = new File("src/test/java/testHeaders.txt");
            Scanner myReader = new Scanner(headerFile).useDelimiter("\\Z");
            headers = myReader.next();
        } catch (FileNotFoundException e) {
            Assertions.assertTrue(false, "testHeaders.txt for the test is missing");
        }
        try {
            File payloadFile = new File("src/test/java/testPayload.json");
            Scanner myReader = new Scanner(payloadFile).useDelimiter("\\Z");
            payload = myReader.next();
        } catch (FileNotFoundException e) {
            Assertions.assertTrue(false, "testPayload.json for the test is missing");
        }

        try {
            HttpClient client = new HttpClient();
            client.setName("CI-server");
            client.start();
        }catch(Exception e){
            Assertions.assertTrue(false, "Setting up http-client for test failed");
        }

        try{
            CiServer server = new CiServer(666);
        }catch(Exception e){
            Assertions.assertTrue(false, "Server constructor threw exception: " + e.getMessage());
        }



    }


}
