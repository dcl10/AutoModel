package sample;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by dcl10 on 01/06/17.
 */
public class Search {

    List<File> files;
    String searchPerl;
    Process process;

    public void Search() {
        files = null;
        searchPerl = new File("web_blast.pl").getAbsolutePath();
        process = null;
    }

    public void getFiles() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open");
        files = fc.showOpenMultipleDialog(null);
    }

    public void runSearch() throws IOException {
       process = Runtime.getRuntime().exec("perl ");

    }
}
