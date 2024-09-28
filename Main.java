import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import org.json.JSONObject;

public class Main {

    public void reader() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter key-value pairs (e.g., name:John age:30):");

            JSONObject jsonObject = new JSONObject();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) {
                    break;
                }

                String[] parts = line.split(":");
                if (parts.length == 2) {
                    jsonObject.put(parts[0].trim(), parts[1].trim());
                } else {
                    System.out.println("Invalid input format. Please use key:value format.");
                }
            }

            try (FileWriter fileWriter = new FileWriter("input.json")) {
                fileWriter.write(jsonObject.toString(4)); // 4 is for pretty printing
                System.out.println("JSON file created successfully.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}