package sample;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by dcl10 on 05/06/17.
 */
public class Retriever implements Runnable {

    Process process = null;
    File program = new File("additinal/result_parse.pl");
    String command = "perl " + program.getAbsolutePath() + " /Working/*_results.bls";
    boolean keepRunning;

    public void retrieve() throws IOException {
        process = Runtime.getRuntime().exec(command);
    }

    public void stop() {
        if (process.isAlive()) {
            int exit = JOptionPane.showConfirmDialog(null, "WARNING: All subsequent "
                    + "processes will be terminated as well.", "End process", JOptionPane.OK_CANCEL_OPTION);
            if (exit == JOptionPane.OK_OPTION){
                keepRunning = false;
                process.destroy();
            }
        }
    }
    @Override
    public void run() {
        keepRunning = true;
        while (keepRunning) {
            try {
                retrieve();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
