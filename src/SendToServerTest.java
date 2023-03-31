import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class SendToServerTest {
    @BeforeEach
    void setUp() {
        System.out.println("before test!");
    }

    @AfterEach
    void tearDown() {
        System.out.println("after test");
    }
    @Test
    void TestPOSTMethod() {
        String testInput = "John\nblue\n30\n";
        System.out.println(testInput);
        InputStream inputStream = new ByteArrayInputStream(testInput.getBytes());
        Scanner mockScanner = new Scanner(inputStream);

        //values that will be sent to server
        JSONObject result = Main.POSTMethod(mockScanner);
        JSONObject expected = new JSONObject();
        expected.put("name", "John");
        expected.put("favoriteColor", "blue");
        expected.put("age", 30);
        assertEquals(expected.toString(), result.toString());



        // reset System.in to original state
        System.setIn(System.in);
    }
    @Test
    void TestGetMethod() throws IOException {
        String testInput = "{\"httpStatusCode\": 200, \"users\": " +
                "[{\"name\": \"John\", \"age\": 30, \"favoriteColor\": \"blue\"}," +
                " {\"name\": \"Jane\", \"age\": 25, \"favoriteColor\": \"green\"}]}\n";

        InputStream inputStream = new ByteArrayInputStream(testInput.getBytes());
        BufferedReader mockReader = new BufferedReader(new InputStreamReader(inputStream));

        // call the method and assert the result
        Main.GETMethod(mockReader);
        // add your own assertions as needed
    }

}