package boutique.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommandeTest {

    private static final String DOMICILE = "12 rue des Lilas, Lyon";
    private static final String BUREAU = "3 place Bellecour, Lyon";

    @Test
    @DisplayName("Le constructeur accepte deux adresses inversees sans erreur")
    void constructeurAccepteLesAdressesInversees() {
        // GIVEN : livraison voulue au bureau, mais les arguments sont inverses
        CommandeAvant commande = new CommandeAvant("Alice", DOMICILE, BUREAU,
                "EXPRESS", null, false);

        // WHEN
        String livraison = commande.adresseLivraison();

        // THEN
        assertEquals(DOMICILE, livraison);
    }

    @Test
    @DisplayName("Avec le Builder, chaque adresse est nommee")
    void builderNommeLesAdresses() {
        // GIVEN
        Commande.Builder builder = Commande.builder();

        // WHEN
        Commande commande = builder
                .client("Alice")
                .livraison(BUREAU)
                .facturation(DOMICILE)
                .build();

        // THEN
        assertEquals(BUREAU, commande.adresseLivraison());
        assertEquals(DOMICILE, commande.adresseFacturation());
        assertEquals("STANDARD", commande.modeLivraison());
    }

    @Test
    @DisplayName("Sans facturation, on facture a l'adresse de livraison")
    void facturationParDefaut() {
        // GIVEN
        Commande.Builder builder = Commande.builder();

        // WHEN
        Commande commande = builder
                .client("Alice")
                .livraison(BUREAU)
                .build();

        // THEN
        assertEquals(BUREAU, commande.adresseFacturation());
    }

    @Test
    @DisplayName("Sans client, build() refuse de creer la commande")
    void sansClientRefuse() {
        // GIVEN
        Commande.Builder builder = Commande.builder().livraison(BUREAU);

        // WHEN / THEN
        IllegalStateException e = assertThrows(IllegalStateException.class,
                builder::build);
        assertEquals("client obligatoire", e.getMessage());
    }

    @Test
    @DisplayName("Le constructeur du record ne contourne pas les verifications")
    void constructeurDuRecordVerifieAussi() {
        // WHEN / THEN
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> new Commande(null, BUREAU, DOMICILE, "EXPRESS", null,
                        false));
        assertEquals("client obligatoire", e.getMessage());
    }
}
