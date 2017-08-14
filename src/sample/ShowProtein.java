package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public class ShowProtein {

    public ObservableList getPDBFiles() {
        File dir = new File("./");
        File[] files = dir.listFiles();
        ObservableList ol = FXCollections.observableArrayList();
        if (files != null) {
            for (File f : files) {
                if (f.getAbsolutePath().endsWith(".pdb")) {
                    ol.add(f.getName());
                }
            }
        }
        return ol;
    }
}
