import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window {
    public static void main(String[] args) {
        // Create a new JFrame
        JsonCreator reader = new JsonCreator();
        JFrame frame = new JFrame("Simple Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        // Create a label to display a message
        JLabel label = new JLabel("Type something:");

        // Create a text field for user input
        JTextField textField = new JTextField(15);

        // Create a button to print the text
        JButton button = new JButton("Print");

        // Create a panel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Add components to the panel
        panel.add(label);
        panel.add(textField);
        panel.add(button);

        // Add the panel to the frame
        frame.getContentPane().add(panel);

        // Add an action listener to the button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText(); // Initializes the string the user inputs to "text"
                textField.setText(""); // Clear the text field so the user can continue with responses
                reader.jsonCreate(text); // Takes the text written and creates a input.json file for the AI
            }
        });

        // Set the frame visibility
        frame.setVisible(true);
    }
}
