package sample;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class Controller {

    private Search search = new Search();
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
        switch (program) {
            case -1 : message += "Failed to execute BLAST search.\n";
            break;
            case 0 : message += "Search completed.\n";
            break;
            case 1 : message += "web_blast.pl improperly executed.\n";
            break;
            case 2 : message += "No results found.\n";
            break;
            case 3 : message += "rid expired.\n";
            break;
            case 4 : message += "Search failed.\n";
            break;
            case 5 : message += "An unknown error occurred.\n";
            break;
            default : message += "A totally unforeseen error occurred.\n";
            break;
        }
        terminal.setText(message);

    }

    public void cancel() {
       search.stopSearch();
    }
}
