package boutique.fabrique;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FabriquePaiementTest {

    @Test
    @DisplayName("Le code CB donne une carte")
    void codeCbDonneUneCarte() {
        // GIVEN
        String code = "CB";

        // WHEN
        MoyenPaiement moyen = FabriquePaiement.creer(code, "**** 4242");

        // THEN
        assertInstanceOf(MoyenPaiement.Carte.class, moyen);
    }

    @Test
    @DisplayName("La commission depend du moyen de paiement")
    void commissionParMoyen() {
        // GIVEN
        MoyenPaiement carte = FabriquePaiement.creer("CB", "**** 4242");
        MoyenPaiement paypal = FabriquePaiement.creer("PAYPAL", "a@b.fr");
        MoyenPaiement virement = FabriquePaiement.creer("VIREMENT", "FR76");

        // WHEN
        long surCarte = FraisPaiement.commission(carte, 7900);
        long surPaypal = FraisPaiement.commission(paypal, 7900);
        long surVirement = FraisPaiement.commission(virement, 7900);

        // THEN : 1,2 % de 79,00 = 94,8 centimes, arrondis a 95
        assertEquals(95, surCarte);
        assertEquals(264, surPaypal);
        assertEquals(0, surVirement);
    }

    @Test
    @DisplayName("Un code inconnu est refuse avec son nom dans le message")
    void codeInconnuRefuse() {
        // WHEN / THEN
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> FabriquePaiement.creer("CHEQUE", "123"));
        assertEquals("Moyen de paiement inconnu : CHEQUE", e.getMessage());
    }
}
