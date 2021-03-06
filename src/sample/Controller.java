package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
This is the controller class for AutoModel. The methods determine the functionality of each button
in the GUI.
 */
public class Controller implements Initializable{

    private Pipeline pipeline = new Pipeline();
    private ShowProtein showProtein = new ShowProtein();
    private Thread thread;
    @FXML
    private TextArea terminal;
    @FXML
    private ListView<File> listView;

    /**
     * On clicking the "Begin" button on the GUI, the system file chooser will appear.
     * Once the user has selected his/her files to be modelled the workflow will be initiated.
     * As the program continues, updates will be posted to the "terminal" TextArea.
     */
    public void begin() throws InterruptedException {
        pipeline.getFiles();
        thread = new Thread(pipeline);
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
                terminal.setText(pipeline.getMessage());
                System.out.println(thread.getState().toString());
                if (thread.getState() == Thread.State.TERMINATED) timer.cancel();
                listView.setItems(showProtein.getPDBFiles());
            }
        }, 500, 500);
    }

    /**
     * Upon clicking the "Cancel" button in the GUI, this method will terminate the thread
     * generated in begin()
     */
    public void cancel() {
        pipeline.stopPipeline();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(showProtein.getPDBFiles());
        listView.setOnMouseClicked(event -> {
            try {
                Process p = Runtime.getRuntime().exec("pymol " +
                        listView.getSelectionModel().getSelectedItem());
                p.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
