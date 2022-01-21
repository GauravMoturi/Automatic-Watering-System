package sample;


import javafx.application.Application;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Main extends Application {
    private final static int MAX_POTENTIOMETER_VALUE = 1 << 10;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        var controller = new DataController(); // create the controller
        var serialPort = SerialPortService.getSerialPort("COM3");
        serialPort.addDataListener(controller);

        stage.setTitle("My Equation");

        var now = System.currentTimeMillis();


        var xAxis = new NumberAxis("time (ms since Jan 1, 1970)", now, now + 50000, 10000); // creates the x-axis (which automatically updates)
        var yAxis = new NumberAxis("y", 0, MAX_POTENTIOMETER_VALUE, 10); // creates the y-axis

        var series = new XYChart.Series<>(controller.getDataPoints()); // creates the series (all the data)
        var lineChart = new LineChart<>(xAxis, yAxis, FXCollections.singletonObservableList(series)); // creates the chart
        lineChart.setTitle("Potentiometer");

        Scene scene = new Scene(lineChart,800,600); // creates the JavaFX window

        stage.setScene(scene);
        stage.show();
    }
}