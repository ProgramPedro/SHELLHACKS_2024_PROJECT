package mainfolder;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.scene.image.ImageView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.FileReader;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //Path to JSON file
        String aiOutputFile = "SHELLHACKS_2024_PROJECT/output.json";

        //VBox to hold content
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        try (FileReader reader = new FileReader(aiOutputFile)) {
            //Parse JSON file into a Json Object
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

            //Create a JsonObject of the response thorugh the Response key found in the Json AI Output File
            JsonObject response = jsonObject.getAsJsonObject("Response");

            //Extract values from "response" through keys from the Json file
            String explanation = response.get("Explanation").getAsString();
            String question = response.get("Question").getAsString();
            String answer = response.get("Answer").getAsString();

            //Split values extracted into tokens separated by two dollars signs
            //$$ is a delimeter in LaTeX (typesetting system used to create mathematical expressions, equations, etc.)
            String[] explanationBig = explanation.split("\\$\\$");//makes explanation tokens
            String[] questionBig = question.split("\\$\\$");
            String[] answerBig = answer.split("\\$\\$");
            
            //Iterate through tokens to see what is a math formula or not
            //Convert to math image, then add to vbox
            //If not formula, add normal text to vbox
            int i = 0;
            for (String explanationToken : explanationBig) {
                if (i % 2 == 1) {
                    ImageView mathImage = printImage(explanationToken);
                    vbox.getChildren().add(mathImage);
                } else {
                    explanationToken = explanationToken.replace("$", ""); //Remove single dollar signs for simple math equations
                    Text normalText = new Text(explanationToken);
                    vbox.getChildren().add(normalText);
                }
                i++;//Iterate i to the next
            }

            int j = 0;
            for (String questionToken : questionBig) {

                if (j % 2 == 1) {
                    ImageView mathImage = printImage(questionToken);
                    vbox.getChildren().add(mathImage);
                } else {
                    questionToken = questionToken.replace("$", "");
                    Text normalText = new Text(questionToken);
                    vbox.getChildren().add(normalText);
                }
                j++;//Iterate j to the next
            }

            int e = 0;
            for (String answerToken : answerBig) {

                if (e % 2 == 1) {
                    ImageView mathImage = printImage(answerToken);
                    vbox.getChildren().add(mathImage);
                } else {
                    answerToken = answerToken.replace("$", "");
                    Text normalText = new Text(answerToken);
                    vbox.getChildren().add(normalText);
                }
                e++;//Iterate e to the next
            }

        } catch (IOException e){
            System.out.println(e);
        }

        //Set the scrollbar
        ScrollPane scroll = new ScrollPane();
        scroll.setPrefSize(500, 500);
        scroll.setContent(vbox);

        //Add scroll pane to scene and set the stage
        Scene scene = new Scene(scroll, 600, 400);
        stage.setTitle("uHackEDU");
        stage.setScene(scene);
        //Display the window
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }//end of main method

    public static ImageView printImage(String mathExpression) {
        TeXFormula formula = new TeXFormula(mathExpression);//Convert mathexpression into LaTeX Formula
        TeXIcon formulaIcon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20); //Create icon for conversion into buffered image

        //Convert formula into a buffered image (BufferedImage -> FX Image)
        BufferedImage bufferedImage = new BufferedImage(formulaIcon.getIconWidth(), formulaIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D iconGraphics = bufferedImage.createGraphics();
        iconGraphics.setColor(Color.WHITE);
        iconGraphics.fillRect(0,0, bufferedImage.getWidth(), bufferedImage.getHeight());
        formulaIcon.paintIcon(null, iconGraphics, 0, 0);
        iconGraphics.dispose();//Toss out graphics context

        Image FXImage = SwingFXUtils.toFXImage(bufferedImage, null);

        return new ImageView(FXImage); //return the FXImage of the LaTeX formula to place in the VBox
    }//end of printImage method

}//end of Main
