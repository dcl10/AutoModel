package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;


/**
This is the controller class for AutoModel. The methods determine the functionality of each button
in the GUI.
 */
public class Controller {

    private Search search = new Search();
    private int program;
    @FXML
    private TextArea terminal;
    private String message = "";
    private Thread thread;

    /**
     * On clicking the "Begin" button on the GUI, the system file chooser will appear.
     * Once the user has selected his/her files to be modelled the workflow will be initiated.
     * As the program continues, updates will be posted to the "terminal" TextArea.
     */
    public void begin() {
        search.getFiles();
        thread = new Thread(search);
        thread.start();

        /*
        This is wrong. The exit codes from the actual Perl script
        were not returned. Instead they are from the Process object in Search
        */
        /*switch (program) {
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
        }*/
        //message += program + "\n";
        //terminal.setText(message);

    }

    /**
     * Upon clicking the "Cancel" button in the GUI, this method will terminate the thread
     * generated in begin()
     */
    public void cancel() {
        // TODO: work out how to terminate the thread generated in begin
        search.stopSearch();
    }
}
