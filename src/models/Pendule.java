/********************************************************************
 * Auteur:	    Eric Lefrançois                                     *
 * Groupe:	    HES_SO/EIG  Informatique & Télécommunication        *
 * Fichier:     Pendule.java                                        *
 * Date :	    1er Octobre 2015-  DEPART                 		    *
 * Projet:	    Horloges synchronisées                              *
 ********************************************************************
 */

package models;

import java.sql.Time;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class Pendule extends Observable implements Runnable, Observer {
    private LinkedList<Observer> observers;
    private int dureeSeconde;       // Durée de la seconde en msec.
    private int minutes = 0;       	// Compteurs de la pendule
    private int secondes = 0;
    private int heures = 0;
    private Emetteur emetteur;

    private Thread thread;

    //------------------------------------------------------------------------
    public Pendule (int valSeconde, Emetteur emetteur) {
        this.emetteur = emetteur;
        dureeSeconde = valSeconde;
        observers = new LinkedList<>();
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void incrementerSecondes(){
        secondes ++;
        if (secondes == 60) {
            synchronized (emetteur) {
                try {
                    emetteur.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            secondes = 0;
            incrementerMinutes();
        }
        notifyObservers();
    }

    public void incrementerMinutes() {
        minutes = ++minutes % 60 ;
        if (minutes == 0) {
            heures = ++heures % 24;
        }
    }

    @Override
    public synchronized void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(this, o);
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                thread.sleep(dureeSeconde);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            incrementerSecondes();

        }
    }

    @Override
    public void update(Observable observable, Object o) {
    }

    public int getSecondes() {
        return secondes;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getHeures() {
        return heures;
    }
}