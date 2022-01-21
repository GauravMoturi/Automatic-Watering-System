package sample;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListenerWithExceptions;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.nio.ByteBuffer;

public class DataController implements SerialPortMessageListenerWithExceptions {
    private static final byte[] DELIMITER = new byte[]{'\n'};
    private final ObservableList<XYChart.Data<Number, Number>> dataPoints;

    public DataController() {
        this.dataPoints = FXCollections.observableArrayList();
    }

    public ObservableList<XYChart.Data<Number, Number>> getDataPoints() {
        return dataPoints;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if(serialPortEvent.getEventType() != SerialPort.LISTENING_EVENT_DATA_RECEIVED){
            return;
        }

        byte[] data =serialPortEvent.getReceivedData();
        int rawbyte = ByteBuffer.wrap(data).getInt();
        long time = System.currentTimeMillis();
        var dataPoint = new XYChart.Data<Number,Number>(time,rawbyte);
        Platform.runLater(()->this.dataPoints.add(dataPoint));


        // TODO: Implement this method. Refer to the documentation for more details.
    }

    @Override
    public void catchException(Exception e) {
        e.printStackTrace();
    }

    @Override
    public byte[] getMessageDelimiter() {
        return DELIMITER;
    }

    @Override
    public boolean delimiterIndicatesEndOfMessage() {
        return true;
    }
}