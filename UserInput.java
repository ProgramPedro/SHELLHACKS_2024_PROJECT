package com.example.app;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class UserInput {

    JsonCreation reader = new JsonCreation();
    @FXML
    private TextField textBox1;
    private TextField textBox2;
    private TextField textBox3;
    private TextField gentText;


    @FXML
    private Button myButton; // Optional, if you have a button to trigger the action

    @FXML
    private void handleButtonClick() {
        // Get the text from the TextField
        if (textBox1.getText() != null && textBox2.getText() != null && textBox3.getText() != null) {
            String inputText1 = textBox1.getText();
            String inputText2 = textBox2.getText();
            String inputText3 = textBox3.getText();
            // Do something with the inputText, like printing it
            reader.jsonCreate(inputText1, inputText2, inputText3);
        } else if(gentText != null) {
            String genInput = gentText.getText();
            reader.genJson(genInput);
        }
    }
}