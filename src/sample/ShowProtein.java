package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public class ShowProtein implements Runnable{

    public ObservableList getPDBFiles() {
        File dir = new File("./");
        File[] files = dir.listFiles();
        ObservableList ol = FXCollections.observableArrayList();
        for (File f : files) {
            if (f.getAbsolutePath().endsWith(".pdb")) {
                ol.add(f.getName());
            }
        }
        return ol;
    }

    @Override
    public void run() {

    }
}
