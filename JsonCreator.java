import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class JsonCreator {
    public void jsonCreate(String text) {
        JSONObject jsonObject = new JSONObject(); // New JSON object to take in the response from user
        jsonObject.put("Input", text); // All inputs from user will start with the key being "Input",
                                       // then their response as a whole string

        try {
            try (FileWriter fileWriter = new FileWriter("input.json")) { // input.json will override w new data every user input
                fileWriter.write(jsonObject.toString(4)); // 4 is for pretty printing
                System.out.println("JSON file created successfully.");// Printed to
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage()); // If there's ever an error, this will display on console.
        }
    }
}
