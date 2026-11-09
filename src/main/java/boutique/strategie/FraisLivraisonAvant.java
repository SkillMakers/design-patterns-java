package boutique.strategie;

/**
 * AVANT le pattern : tous les modes de livraison dans une seule methode.
 * Chaque nouveau mode oblige a rouvrir ce fichier et a rallonger la chaine de if.
 */
public final class FraisLivraisonAvant {

    private FraisLivraisonAvant() {
    }

    public static int calculer(String mode, Panier panier) {
        if (mode.equals("STANDARD")) {
            return panier.montantCentimes() >= 5000 ? 0 : 490;
        } else if (mode.equals("EXPRESS")) {
            return 990 + 100 * (panier.poidsGrammes() / 1000);
        } else if (mode.equals("RETRAIT")) {
            return 0;
        }
        throw new IllegalArgumentException("Mode inconnu : " + mode);
    }
}
