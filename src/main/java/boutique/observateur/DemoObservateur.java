package boutique.observateur;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Trois essais avec quatre abonnes : email, stock, offre de bienvenue,
 * facture. L'offre de bienvenue se desabonne pendant la notification.
 */
public final class DemoObservateur {

    private DemoObservateur() {
    }

    public static void main(String[] args) {
        essai("A. ArrayList, bienvenue en 1er",
                new ArrayList<>(), true);
        essai("B. ArrayList, bienvenue en 3e",
                new ArrayList<>(), false);
        essai("C. CopyOnWriteArrayList, bienvenue en 3e",
                new CopyOnWriteArrayList<>(), false);
    }

    private static void essai(String titre, List<ObservateurCommande> liste,
            boolean bienvenueEnPremier) {
        List<String> journal = new ArrayList<>();
        SujetCommande sujet = new SujetCommande(liste);
        ObservateurCommande email = (n, s) -> journal.add("email " + n);
        ObservateurCommande stock = (n, s) -> journal.add("stock " + n);
        ObservateurCommande facture = (n, s) -> journal.add("facture " + n);
        ObservateurCommande bienvenue = new OffreBienvenue(journal);
        if (bienvenueEnPremier) {
            sujet.abonner(bienvenue);
        }
        sujet.abonner(email);
        sujet.abonner(stock);
        if (!bienvenueEnPremier) {
            sujet.abonner(bienvenue);
        }
        sujet.abonner(facture);

        System.out.println(titre);
        try {
            sujet.valider("5001");
            System.out.println("  aucune exception");
        } catch (RuntimeException e) {
            System.out.println("  exception : " + e.getClass().getName());
        }
        System.out.println("  appeles : " + journal);
        System.out.println("  facture 5001 emise : "
                + journal.contains("facture 5001"));
    }
}
