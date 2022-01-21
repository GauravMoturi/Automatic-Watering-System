package sample;

import SerialPortService.SerialPortService;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        var sp = SerialPortService.getSerialPort("COM3");
        var outputStream = sp.getOutputStream();

        var pane = new BorderPane();
        var button = new Button();
        var slider = new Slider();
        var label=new Label();
        slider.setMin(0.0);
        slider.setMax(100.0);

        button.setOnMouseReleased(value -> {
            try {
                outputStream.write(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        button.setOnMousePressed(value -> {
            try {
                outputStream.write(255);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            label.textProperty().bind(slider.valueProperty().asString("%.0f"));
            try {
                outputStream.write(newValue.byteValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        // TODO: Add a 'listener' to the {@code valueProperty} of the slider. The listener
        //  should write the {@code byteValue()} of the new slider value to the output stream.


        pane.setCenter(slider);
        pane.setTop(button);
        pane.setBottom(label);
        pane.setPadding(new Insets(0, 20, 0, 20));

        var scene = new Scene(pane, 400, 200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}