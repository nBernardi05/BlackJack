package com.bernardispa.blackjack;

import com.bernardispa.blackjack.Model.Partita;
import com.bernardispa.blackjack.Model.Carta;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
        /*Scanner sc = new Scanner(System.in);
        Partita p = new Partita();
        while(!p.isFinito()){
            p.stampaMazzo(true);
            System.out.println("1: pesca; 2: stai");
            int ris = sc.nextInt();
            switch (ris) {
                case 1:
                    p.chiamaCarta();
                    break;
                case 2:
                    p.dealerPesca();
                    break;
                default:
                    break;
            }
        }
        p.stampaMazzo(false);
        System.out.println(p.getVittoria());*/
    }

}