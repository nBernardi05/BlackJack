package com.bernardispa.blackjack.Model;

/**
 *
 * @author bernardi.nicola
 */
public class Carta {
    private int valore;
    private String carta;

    // P = picche, F = fiori, C = cuori, Q = quadri
    public Carta(String numero, String seme) {
        carta = numero + seme;
        if(numero.equals("11") || numero.equals("12") || numero.equals("13"))       // J, Q, K
            valore = 10;
        else if(numero.equals("1"))
            valore = 11;
        else
            valore = Integer.parseInt(numero);
    }

    public int getValore() {
        return valore;
    }

    public String getCarta() {
        return carta;
    }

    public void setValore(int valore) {
        this.valore = valore;
    }

    @Override
    public String toString() {
        return "[" + carta + "]";
    }
    
    
}
