/****************************************************************
 * Auteur:	    Eric Lefrançois                                 *
 * Groupe:	    HES_SO  Informatique & Télécommunications       *
 * Fichier:     1er Octobre 2015-  DEPART		                *
 * Projet:	    Horloges synchronisées                          *
 ****************************************************************
 */
import models.*;
import views.*;

public class Amorce {
    public static void main(String argv[]) {
        Emetteur e = new Emetteur(100);
        e.addObserver(new VueEmetteur());

        Pendule p1 = new Pendule(130, e);
        Pendule p2 = new Pendule(30, e);
        Pendule p3 = new Pendule(40, e);
        p1.addObserver(new VuePendule("H1", 100, 0, p1));
        p2.addObserver(new VuePendule("H2", 300, 0, p2));
        p3.addObserver(new VuePendule("H3", 500, 0, p3));

        e.addObserver(p1);
        e.addObserver(p2);
        e.addObserver(p3);
    }
}