package com.example.app;

import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class JsonCreation {
    public void jsonCreate(String schoolInput, String courseInput, String topicInput) {
        JSONObject jsonObject = new JSONObject();// New JSON object to take in the response from user
        //jsonObject.put("Input", text);
        // First input from user will be these three keys and values
        // then their response as a whole string
        jsonObject.put("School", schoolInput);
        jsonObject.put("Course", courseInput);
        jsonObject.put("Topic", topicInput);

        try {
            try (FileWriter fileWriter = new FileWriter("input.json")) { // input.json will override w new data every user input
                fileWriter.write(jsonObject.toString(4)); // 4 is for pretty printing
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage()); // If there's ever an error, this will display on console.
        }
    }

    public void genJson(String genInput) {
        JSONObject jsonObject = new JSONObject();// New JSON object to take in the response from user
        //jsonObject.put("Input", text);
        // All inputs from user will start with the key being "Input",
        // then their response as a whole string
        jsonObject.put("Input", genInput);

        try {
            try (FileWriter fileWriter = new FileWriter("input.json")) { // input.json will override w new data every user input
                fileWriter.write(jsonObject.toString(4)); // 4 is for pretty printing
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage()); // If there's ever an error, this will display on console.
        }
    }
}
