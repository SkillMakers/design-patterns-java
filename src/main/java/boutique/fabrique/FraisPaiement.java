package boutique.fabrique;

/**
 * Commission de la banque pour chaque moyen de paiement, en centimes.
 * Le switch n'a pas de default : si un moyen de paiement est ajoute a
 * l'interface scellee sans etre traite ici, la compilation echoue.
 */
public final class FraisPaiement {

    private FraisPaiement() {
    }

    public static long commission(MoyenPaiement moyen, long montantCentimes) {
        return switch (moyen) {
            case MoyenPaiement.Carte c -> pourMille(montantCentimes, 12);
            case MoyenPaiement.PayPal p -> 35 + pourMille(montantCentimes, 29);
            case MoyenPaiement.Virement v -> 0;
        };
    }

    /** Taux en pour mille, arrondi au centime le plus proche (pas tronque). */
    private static long pourMille(long montantCentimes, int taux) {
        return Math.round(montantCentimes * taux / 1000.0);
    }
}
