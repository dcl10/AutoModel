package sample;

import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This class is responsible for executing web BLAST searches against the PDB and generating results files
 * to be parsed later.
 * This class enables the user to select the FASTA files to be modelled graphically via the JFileChooser
 * because the JavaFX FileChooser wouldn't close after the files were choosen.
 * The class also provides the ability to run the search via the runSearch() method. Sadly it doesn't work yet.
 * The user can terminate the searches at any point. When the "Cancel" button is clicked in the GUI the controller
 * calls the stopSearch() method in this class which sets the boolean variable keepRunning to false, breaking the
 * while loop in runSearch().
 */
public class Search implements Runnable{

    private File [] files = null;
    private String searchPerl = new File("web_blast.pl").getAbsolutePath();
    private Process process = null;
    private boolean keepRunning;

    /**
     * This method calls the system's file chooser and adds the user's choice of files to the File [] variable called
     * files.
     */
    public void getFiles() {
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(true);
        fc.showOpenDialog(null);
        files = fc.getSelectedFiles();
    }

    /**
     * This method executes system calls on the Perl script "web_blast.pl" (insert URL to NCBI).
     * By default the exitCode is -1 which occurs if the while loop is never activated.
     * If there are no files in the List files, then the while loop never executes.
     * Provided that there is at least 1 file in the List files, the for loop will iteratively execute BLAST searches
     * using web_blast.pl and the file at the current index as the argument.
     * When the last File in the List files is reached, the boolean variable keepRunning is set to false,
     * terminating the while loop.
     * @return the exit code of each process to the method caller.
     * @throws IOException
     * @throws InterruptedException
     */
    public int runSearch() throws IOException, InterruptedException {
        int exitCode = -1;
        keepRunning = true;
        while (keepRunning && files != null){
            for (int i = 0; i < files.length; i++) {
                String query = files[i].getAbsolutePath();
                process = Runtime.getRuntime().exec("espeak -f " + query);
                process.waitFor();
                exitCode = process.exitValue();
                if (i == files.length-1) {
                    keepRunning = false;
                }
            }
        }
        return exitCode;
    }

    /**
     * This method is called when the user clicks the "Cancel" button in the GUI. This sets the boolean variable
     * keepRunning to false, thereby breaking the while loop in the runSearch() method.
     */
    public void stopSearch() {
        if (process.isAlive()) {
            int exit = JOptionPane.showConfirmDialog(null, "Cancel all subsequent " +
            "searches?", "End process", JOptionPane.YES_NO_OPTION);
            if (exit == JOptionPane.YES_OPTION){
                keepRunning = false;
                process.destroy();
            }
        }
    }

    @Override
    public void run() {
        getFiles();
        try {
            runSearch();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
