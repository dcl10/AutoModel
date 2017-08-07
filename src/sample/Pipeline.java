package sample;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * This class is responsible for executing web BLAST searches against the PDB and generating results files
 * to be parsed later.
 * This class enables the user to select the FASTA files to be modelled graphically via the JFileChooser
 * because the JavaFX FileChooser wouldn't close after the files were chosen.
 * The class also provides the ability to run the search via the runSearch() method. Sadly it doesn't work yet.
 * The user can terminate the searches at any point. When the "Cancel" button is clicked in the GUI the controller
 * calls the stopPipeline() method in this class which sets the boolean variable keepRunning to false, breaking the
 * while loop in runSearch().
 */
public class Pipeline implements Runnable {

    private File[] files = null;
    private File dir = new File("./Working");
    private File program1 = new File("./Additional/web_blast.pl");
    private File program2 = new File("./Additional/result_parse.pl");
    private String searchPerl = "perl " + program1 + " ";
    private String parsePerl = "perl " + program2 + " ";
    private Process process = null;
    private boolean keepRunning;
    private String message = "";


    /**
     * This method calls the JFileChooser and adds the user's choice of files to the File [] variable called
     * files.
     */
    public void getFiles() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(dir);
        fc.setMultiSelectionEnabled(true);
        fc.showOpenDialog(null);
        files = fc.getSelectedFiles();
    }

    /**
     * This method makes a call to web_blast.pl to search the PDB database for the protein whose sequence
     * matches that in the input file given as the method parameter.
     * @param file
     * @throws IOException
     * @throws InterruptedException
     */
    public void runSearch(File file) throws IOException, InterruptedException {
        setMessage("Starting: " + file.getName() + System.lineSeparator());
        process = Runtime.getRuntime().exec(searchPerl + "blastp pdb " + file);
        process.waitFor();
        if (process.exitValue() == 0 ) setMessage("Completed: " + file.getName() + System.lineSeparator());
        else setMessage("Failed: " + file.getName() + System.lineSeparator());
    }

    /*
    Even though I can get the String in the processs...exec(#command#) to match what I would write in the terminal,
    the program doesn't execute.
    I independently tested the perl script with the right arguments and that goes just fine.
    The String should look something like :
        perl Additional/result_parse.pl /home/dcl10/IdeaProjects/ProteinModel/Working/P11802.bls (wrapped around)
        /home/dcl10/IdeaProjects/ProteinModel/Working/P11802.fasta
     */
    public void runParse(File file1) throws IOException, InterruptedException {
        String bls = file1.getAbsolutePath().replace(".fasta", ".bls");
        File file2 = new File(bls);
        setMessage("Parsing: " + file1.getName() + System.lineSeparator());
        process = Runtime.getRuntime().exec(parsePerl);
        process.waitFor();
        if (process.exitValue() == 0 ) setMessage("Completed: " + file1.getName() + System.lineSeparator());
        else setMessage("Failed: " + file1.getName() + System.lineSeparator());
    }

    /**
     * This method is called when the user clicks the "Cancel" button in the GUI. This sets the boolean variable
     * keepRunning to false, thereby breaking the while loop in the run() method. The current process is also
     * destroyed.
     */
    public void stopPipeline() {
        if (process.isAlive()) {
            int exit = JOptionPane.showConfirmDialog(null, "WARNING: All subsequent "
                    + "processes will be terminated as well.", "End process", JOptionPane.OK_CANCEL_OPTION);
            if (exit == JOptionPane.OK_OPTION){
                keepRunning = false;
                process.destroy();
            }
        }
    }

    /**
     *
     * @param s
     */
    public void setMessage(String s) {
        this.message += s;
    }

    public String getMessage() {
        return message;
    }

    /**
     * This method is called when the user clicks "Begin" on the GUI. The while loop will execute runSearch()
     * so long is keepRunning is true and counter is less than length of files[]. The if statement exit the loop if
     * keepRunning is set to false by calling the stopPipeline() method. The current process will also be stopped.
     */
    @Override
    public void run() {
        keepRunning = true;
        int counter = 0;
        while (keepRunning && counter < files.length) {
            try {
                runSearch(files[counter]);
                runParse(files[counter]);
                counter++;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                System.out.println("The thread was interrupted" + System.lineSeparator());
                setMessage("The thread was interrupted" + System.lineSeparator());
            }
        }
        setMessage("Workflow completed.");
    }
}
