package boutique.fabrique;

/**
 * Le pattern Factory : un seul endroit sait quelle classe creer.
 * Le reste de l'application demande un moyen de paiement par son code
 * et ne connait jamais les classes concretes.
 */
public final class FabriquePaiement {

    private FabriquePaiement() {
    }

    public static MoyenPaiement creer(String code, String detail) {
        return switch (code) {
            case "CB" -> new MoyenPaiement.Carte(detail);
            case "PAYPAL" -> new MoyenPaiement.PayPal(detail);
            case "VIREMENT" -> new MoyenPaiement.Virement(detail);
            default -> throw new IllegalArgumentException(
                    "Moyen de paiement inconnu : " + code);
        };
    }
}
