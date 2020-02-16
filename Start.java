package sample;
import javafx.application.Application;

public class Start {

    public static void main(String[] args) {

        //must be set before launching the Main class
        System.setProperty("quantum.multithreaded", "false");

        Application.launch(Game.class, args);
    }
}