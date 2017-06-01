package sample;


import java.io.IOException;

public class Controller {

    Search search =  new Search();

    public void begin() {
        search.getFiles();
        try {
            int program = search.runSearch();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void cancel() {
        // TODO: add code to terminate the the searches
    }
}
