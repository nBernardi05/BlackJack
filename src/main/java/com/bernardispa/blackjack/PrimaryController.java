package com.bernardispa.blackjack;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import com.bernardispa.blackjack.Model.Partita;
import com.bernardispa.blackjack.Model.Carta;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PrimaryController implements Initializable{
    Partita p;
    private int pvPlayer = 0;   // partite vinte player
    private int pvDealer = 0;   // partite vinte dealer
    private double w;
    private double h;
    
    @FXML
    private Canvas cvTavolo;
    private GraphicsContext gcTavolo;
    Image backCard;
    Image imgCarte;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gcTavolo = cvTavolo.getGraphicsContext2D();
        backCard = new Image("com/bernardispa/images/cardBack.png");
        imgCarte = new Image("com/bernardispa/images/cardsDeck.png");
        w = imgCarte.getWidth()/13;
        h = imgCarte.getHeight()/4;
        newGame();
    }
    
    @FXML
    private void chiamaCarta() throws IOException {
        p.chiamaCarta();
        int nCarte = p.getcPlayer().size();
        Carta c = p.getLastCarta(2);
        int numero = Integer.parseInt(c.getCarta().substring(0, c.getCarta().length()-1))-1;
        String sem = c.getCarta().substring(c.getCarta().length()-1, c.getCarta().length());
        int seme = getnSeme(sem)-1;
        
        gcTavolo.drawImage(imgCarte, numero*w, seme*h, w, h, w*nCarte, 0, w, h);
        aggiornaPunteggi();
        aggiornaPunteggi();
        check();
    }
    
    @FXML
    private void stai() throws IOException {
        p.stai();
        drawDealerCards();
        aggiornaPunteggi();
        check();
        aggiornaPartiteVinte();
    }
    
    /**
     * Controlla lo stato della partita.
     */
    public void check() {
        switch (p.getVittoria()) {
            case 1:
                pvDealer++;
                display.setText("Dealer Vince!");
                newGame.setDisable(false);
                staii.setDisable(true);
                chiamaC.setDisable(true);
                break;
            case 2:
                pvPlayer++;
                display.setText("Player Vince!");
                newGame.setDisable(false);
                staii.setDisable(true);
                chiamaC.setDisable(true);
                break;
            case 3:
                display.setText("Pareggio");
                newGame.setDisable(false);
                staii.setDisable(true);
                chiamaC.setDisable(true);
                break;
            default:
                break;
        }
        aggiornaPartiteVinte();
    }
    
    public void newGame() {
        p = new Partita();
        p.setFinito(false);
        newGame.setDisable(true);
        staii.setDisable(false);
        chiamaC.setDisable(false);
        gcTavolo.clearRect(0, 0, cvTavolo.getWidth(), cvTavolo.getHeight());
        aggiornaPunteggi();
        int nCarte = 1;
        int numero;
        for(Carta c: p.getcPlayer()){
            numero = Integer.parseInt(c.getCarta().substring(0, c.getCarta().length()-1))-1;        // prendo il numero senza il seme e lo converto in int
            String sem = c.getCarta().substring(c.getCarta().length()-1, c.getCarta().length());
            int seme = getnSeme(sem)-1;
            gcTavolo.drawImage(imgCarte, numero*w, seme*h, w, h, w*nCarte, 0, w, h);
            aggiornaPunteggi();
            nCarte++;
        }
        nCarte = 1;
        for(Carta c: p.getcDealer()){
            numero = Integer.parseInt(c.getCarta().substring(0, c.getCarta().length()-1))-1;
            String sem = c.getCarta().substring(c.getCarta().length()-1, c.getCarta().length());
            int seme = getnSeme(sem)-1;
            gcTavolo.drawImage(imgCarte, numero*w, seme*h, w, h, w*nCarte, h*3, w, h);
            if(nCarte==2)
                gcTavolo.drawImage(backCard, 0, 0, backCard.getWidth(), backCard.getHeight(), w*2, h*3, w, h);
            aggiornaPunteggi();
            nCarte++;
        }
    }
     @FXML
    private Label dealer;

    @FXML
    private Label player;
    
    @FXML
    private Label display;
    
    @FXML
    private Button newGame;
     @FXML
    private Button chiamaC;
    @FXML
    private Button staii;
    /**
     * Aggiorna i punti del dealer/player nel display.
     */
    public void aggiornaPunteggi(){
        if(p.isFinito())
            display.setText("Player " + p.getpPlayer() + " - " + p.getpDealer() + " Dealer");
        else
            display.setText("Player " + p.getpPlayer() + " - ? Dealer");
    }
    /**
     * Aggiorna il numero di partite vinte.
     */
    public void aggiornaPartiteVinte(){
        dealer.setText("Dealer: " + pvDealer);
        player.setText("Player: " + pvPlayer);
    }
    /**
     * Trasforma la lettera associata a un seme al valore di altezza che corrisponde
     * @param seme la lettera del seme
     * @return il numero di altezza per cui si trova tale seme
     */
    public int getnSeme(String seme){
        switch (seme){
            case "P":
                return 2;
            case "F":
                return 1;
            case "C":
                return 3;
            case "Q":
                return 4;
            default:
                break;
        }
        return -1;
    }
    
    /**
     * Disegna le carte che pesca il dealer dopo che il player ha premuto "stai".
     */
    public void drawDealerCards(){
        int nCarte = 1;
        for(Object c: p.getcDealer()){
            Carta x = (Carta)c;
            int numero = Integer.parseInt(x.getCarta().substring(0, x.getCarta().length()-1))-1;
            String sem = x.getCarta().substring(x.getCarta().length()-1, x.getCarta().length());
            int seme = getnSeme(sem)-1;
            gcTavolo.drawImage(imgCarte, numero*w, seme*h, w, h, w*nCarte, h*3, w, h);
            aggiornaPunteggi();
            nCarte++;
        }
    }
    
    
}
