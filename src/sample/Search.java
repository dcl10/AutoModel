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

    private List<File> files = null;
    private String searchPerl = new File("web_blast.pl").getAbsolutePath();;
    private Process process = null;
    private boolean keepRunning;



    public void getFiles() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open");
        files = fc.showOpenMultipleDialog(null);
    }

    public int runSearch() throws IOException, InterruptedException {
        int exitCode = -1;
        keepRunning = true;
        while (keepRunning && files != null){
            for (int i = 0; i < files.size(); i++) {
                process = Runtime.getRuntime().exec("perl web_blast.pl blastp nr " + files.get(i));
                process.waitFor();
                exitCode = process.exitValue();
                if (i == files.size()-1) {keepRunning = false;}
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
