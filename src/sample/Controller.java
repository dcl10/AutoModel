package sample;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class Controller {

    private Search search =  new Search();
    private int program;
    @FXML
    private TextArea terminal;
    private String message = "";

    public void begin() {
        search.getFiles();
        try {
            program = search.runSearch();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        message += program;
        terminal.setText(message);

    }

    public void cancel() {
        // TODO: add code to terminate the the searches
    }
}
