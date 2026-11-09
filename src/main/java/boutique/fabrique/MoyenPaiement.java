package boutique.fabrique;

/**
 * Les moyens de paiement de la boutique. L'interface est scellee :
 * la liste des implementations est fermee et connue du compilateur.
 */
public sealed interface MoyenPaiement
        permits MoyenPaiement.Carte, MoyenPaiement.PayPal,
                MoyenPaiement.Virement {

    record Carte(String numeroMasque) implements MoyenPaiement {
    }

    record PayPal(String email) implements MoyenPaiement {
    }

    record Virement(String iban) implements MoyenPaiement {
    }
}
