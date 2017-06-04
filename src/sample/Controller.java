package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
This is the controller class for AutoModel. The methods determine the functionality of each button
in the GUI.
 */
public class Controller {

    private Search search = new Search();
    private Thread thread;
    @FXML
    private TextArea terminal;

    /**
     * On clicking the "Begin" button on the GUI, the system file chooser will appear.
     * Once the user has selected his/her files to be modelled the workflow will be initiated.
     * As the program continues, updates will be posted to the "terminal" TextArea.
     */
    public void begin() throws InterruptedException {
        search.getFiles();
        thread = new Thread(search);
        thread.start();
        refresh();
    }

    /**
     * This method updates the terminal window with information on the current run. The refresh rate is
     * every 0.5 seconds.
     */
    public void refresh() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                terminal.setText(search.getMessage());
                System.out.println(thread.getState().toString());
                if (thread.getState() == Thread.State.TERMINATED) timer.cancel();
            }
        }, 500, 500);
    }

    /**
     * Upon clicking the "Cancel" button in the GUI, this method will terminate the thread
     * generated in begin()
     */
    public void cancel() {
        search.stopSearch();
    }
}
