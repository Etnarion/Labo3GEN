/****************************************************************
 * Auteur:	    Eric Lefrançois                                 *
 * Groupe:	    HES_SO      Informatique & Télécommunications   *
 * Fichier:     Emetteur.java                                   *
 * Date :	      1er Octobre 2016    - Départ             		    *
 * Projet:	    Horloges synchronisées                          *
 ****************************************************************
 */

package models;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;


public class Emetteur extends Observable implements Runnable {
    private int dureeSeconde ;                    // Durée sec. en msec.

    private int secondes = 0;						// Compteur de secondes

    private LinkedList<Observer> observers;

    Thread thread;
    // Constructeur
    public Emetteur (int dureeSeconde) {
        thread = new Thread(this);
        observers = new LinkedList<>();
        this.dureeSeconde = dureeSeconde;
        thread.start();
    }

    @Override
    public synchronized void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void run() {
        while(true) {
            try {
                thread.sleep(dureeSeconde);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            heureMettreAJour();
        }
    }

    private void heureMettreAJour () {
        secondes = ++secondes % 60;
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(this, secondes);
        }
    }
}