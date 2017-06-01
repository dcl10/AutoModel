package sample;



import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;

/**
 This program is used to produce computer models of proteins whose structures have not yet been solved
 using X-ray crystallography or NMR spectroscopy using the MODELLER program <insert citation>.
 Upon pressing the "Begin" button, the user will select the FASTA file(s) for which (s)he wishes to produce models.
 The program will execute a system call to "web_blast.pl" (download: (insert NCBI link)) using the selected files
 as arguments. This will find solved structures in the PDB database with similar amino acid sequences.
 [retrieve.pl] will parse the results files of the searches and retrieve the sequence of the best BLAST hit for each
 search. [alignment.pl] will perform a sequence alignment between the query selected at the start and the sequence
 retrieved by [retrieve.pl] and generate an alignment file to be used by MODELLER. [prepare.pl] will prepare the
 instruction files to by used by MODELLER to build the models.
 */

public class Main extends Application {

    /**
     * Launches the AutoModel program and displays the main screen.
     * <i>terminal.fxml</i> is the resource to build the scene.
     * The default WIDTH is 600 pixels and the default HEIGHT is 400 pixels.
     * The title of the window is "AutoModel" after the program.
     * The exit route is controlled by exit().
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("terminal.fxml"));
        primaryStage.setTitle("AutoModel");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            exit();
        });
    }

    /**
     * The main method.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method is called on exit request and will exit the program if the user clicks <b>YES</b>.
     */
    public void exit() {
        int close = JOptionPane.showConfirmDialog(null, "Close AutoModel?", "Close",
                JOptionPane.YES_NO_OPTION);
        if (close == JOptionPane.YES_OPTION){
            Platform.exit();
        }
    }
}
