package com.bernardispa.blackjack.Model;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author bernardi.nicola
 */
public class Partita {
    private int pDealer = 0;
    private int pPlayer = 0;
    private ArrayList<Carta> cPlayer = new ArrayList();
    private ArrayList<Carta> cDealer = new ArrayList();
    private Carta[] mazzo;
    private int vittoria = 0;       // 0: nessuno, 1: dealer, 2: player, 3: pareggio
    private boolean finito;

    public Partita() {
        creaMazzo();
        estraiCarte();
    }
    
    /**
     * è davvero necessario questo commento? Cosa faccia si evince dal nome del metodo.
     */
    public void creaMazzo(){
        mazzo = new Carta[52];
        String[] semi = new String[4];
        semi[0] = "P";
        semi[1] = "F";
        semi[2] = "C";
        semi[3] = "Q";
        int c = 0;
        for(int i=0; i<4; i++){
            for(int j=1; j<=13; j++){
                mazzo[c] = new Carta(j+"", semi[i]);
                c++;
            }
        }
    }
    
    public void estraiCarte(){
        finito = false;
        Random random = new Random();
        int n;
        for(int i=0; i<2; i++){
            do{
                n = random.nextInt(52);
            }while(mazzo[n]==null);           
            cDealer.add(mazzo[n]);
            pDealer += mazzo[n].getValore();
            mazzo[n] = null;
        }
        for(int i=0; i<2; i++){
            do{
                n = random.nextInt(52);
            }while(mazzo[n]==null);           
            cPlayer.add(mazzo[n]);
            pPlayer += mazzo[n].getValore();
            mazzo[n] = null;
        }
    }
    
    /**
     * Non serve spiegare cosa faccia.
     */
    public void chiamaCarta(){
        if(finito)
            return;
        Random random = new Random();
        int n;
        do{
            n = random.nextInt(52);
        }while(mazzo[n]==null);           
        cPlayer.add(mazzo[n]);
        pPlayer += mazzo[n].getValore();
        mazzo[n] = null;
        controlli(2);
        if(pPlayer>21){
            vittoria = 1;
            finito = true;
            return;
        }
    }
    
    /**
     * Controlla lo stato della partita e se ci sono assi che valgono 11 e necessitano di valere 10.
     * @param p 1: controllo mazzo dealer, n!=1 controllo carte player.
     * @return false se sfora in ogni caso
     */
    public boolean controlli(int p){
        if(p==1){
            for(Object c: cDealer){
                    if(((Carta)c).getValore()==11){
                        Carta y = (Carta)c;     // per maggiore comodità, così non devo fare il casting
                        if(pDealer > 21 && pDealer - 10 < 21){
                            y.setValore(1);
                            pDealer -= 10;
                            return true;
                        }else if(pDealer < 22){
                            return true;
                        }
                    }
            }
            if(pDealer > 21){
                vittoria = 2;
                return false;
            }
            return true;
        }else{
            for(Object c: cPlayer){
                    if(((Carta)c).getValore()==11){
                        Carta y = (Carta)c;     // per maggiore comodità, così non devo fare il casting
                        if(pPlayer > 21 && pPlayer - 10 < 22){
                            y.setValore(1);
                            pPlayer -= 10;
                            return true;
                        }else if(pPlayer < 22){
                            return true;
                        }
                    }
            }
            if(pDealer > 21){
                vittoria = 1;
                return false;
            }
            return true;
        }
    }
    

    
    public void stai(){
        finito = true;
        dealerPesca();
    }
    
    /**
     * Gestisce la pesca del dealer.
     */
    public void dealerPesca(){
        while(pDealer < 17){
            Random random = new Random();
            int n;
            do{
                n = random.nextInt(52);
            }while(mazzo[n]==null);           
            cDealer.add(mazzo[n]);
            pDealer += mazzo[n].getValore();
            mazzo[n] = null;
        }
        finito = true;
        if(!controlli(1)){
                vittoria = 2;
                return;
        }
        if(pDealer > pPlayer)
            vittoria = 1;
        else if(pDealer < pPlayer)
            vittoria = 2;
        else
            vittoria = 3;       // pareggio
    }

    
    
    
    /**
     * Restituisce l'ultima carta pescata
     * @param i 1: del dealer; 2: del player
     * @return l'ultima carta pescata
     */
    public Carta getLastCarta(int i){
        if(i == 1)
            return cDealer.get(cDealer.size()-1);
        else
            return cPlayer.get(cPlayer.size()-1);
    }
    
    /**
     * Getter e Setter.
     */
    
    /**
     * Imposta lo stato di finito
     * @param state valore da impostare
     */
    public void setFinito(boolean state){
        finito = state;
    }
    
    public int getpDealer() {
        return pDealer;
    }

    public int getpPlayer() {
        return pPlayer;
    }

    public ArrayList<Carta> getcPlayer() {
        return cPlayer;
    }

    public ArrayList<Carta> getcDealer() {
        return cDealer;
    }

    public Carta[] getMazzo() {
        return mazzo;
    }

    public int getVittoria() {
        return vittoria;
    }

    public boolean isFinito() {
        return finito;
    }
    
    /**
     * Funzione per giocare senza grafica
     * @param hide se deve stampare la carta nascosta
     */
    public void stampaMazzo(boolean hide){
        System.out.println("CARTE PLAYER:");
        for(Carta c: cPlayer){
            System.out.println(c);
        }
        System.out.println("");
        if(hide){
            System.out.println("CARTE DEALER:");
            int i=0;
            for(Carta c: cDealer){
                if(i==1)
                    System.out.println("[--]");
                else
                    System.out.println(c);
                i++;
            }
            System.out.println("");
        }else{
            System.out.println("CARTE DEALER:");
            for(Carta c: cDealer){
                System.out.println(c);
            }
            System.out.println("");
        }
    }
}
