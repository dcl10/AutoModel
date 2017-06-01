package sample;

import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by dcl10 on 01/06/17.
 */
public class Search {

    private List<File> files;
    private String searchPerl;
    private Process process;
    private boolean keepRunning;

    public void Search() {
        files = null;
        searchPerl = new File("web_blast.pl").getAbsolutePath();
        process = null;
        keepRunning = true;
    }

    public void getFiles() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open");
        files = fc.showOpenMultipleDialog(null);
    }

    public int runSearch() throws IOException, InterruptedException {
        int exitCode = 0;
        while (keepRunning){
            for (File query : files) {
                process = Runtime.getRuntime().exec("perl " + searchPerl + " " + query);
                process.waitFor();
                exitCode = process.exitValue();
            }
        }
        return exitCode;
    }

    public boolean stopSearch() {
        if (process.isAlive()) {
            int exit = JOptionPane.showConfirmDialog(null, "Cancel all subsequent " +
            "searches?", "End process", JOptionPane.YES_NO_OPTION);
            if (exit == JOptionPane.YES_OPTION){
                keepRunning = false;
            }
        }
        return keepRunning;
    }
}
