package boutique.strategie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StrategieLivraisonTest {

    private final Panier petitPanier = new Panier(3200, 2500);
    private final Panier grosPanier = new Panier(7900, 2500);

    @ParameterizedTest(name = "{0}, panier de {1} centimes et {2} g")
    @CsvSource({
        "STANDARD, 3200, 2500", "STANDARD, 7900, 2500",
        "EXPRESS, 3200, 2500", "EXPRESS, 7900, 2500",
        "RETRAIT, 3200, 2500", "RETRAIT, 7900, 2500",
    })
    @DisplayName("Chaine de if, classes et lambdas donnent les memes frais")
    void troisVersionsIdentiques(String mode, int montant, int poids) {
        // GIVEN
        Panier panier = new Panier(montant, poids);
        StrategieLivraison classe = switch (mode) {
            case "STANDARD" -> new LivraisonStandard();
            case "EXPRESS" -> new LivraisonExpress();
            default -> new RetraitMagasin();
        };

        // WHEN
        int avant = FraisLivraisonAvant.calculer(mode, panier);
        int parClasse = classe.frais(panier);
        int parLambda = ModesLivraison.frais(mode, panier);

        // THEN
        assertEquals(avant, parClasse);
        assertEquals(avant, parLambda);
    }

    @Test
    @DisplayName("Express : 9,90 euros plus 1 euro par kilo entier")
    void expressAuPoids() {
        // GIVEN
        StrategieLivraison express = new LivraisonExpress();

        // WHEN
        int frais = express.frais(petitPanier);

        // THEN
        assertEquals(1190, frais);
    }

    @Test
    @DisplayName("La livraison standard est offerte des 50 euros")
    void standardOfferteDes50Euros() {
        // GIVEN
        StrategieLivraison standard = new LivraisonStandard();

        // WHEN
        int petit = standard.frais(petitPanier);
        int gros = standard.frais(grosPanier);

        // THEN
        assertEquals(490, petit);
        assertEquals(0, gros);
    }

    @Test
    @DisplayName("Un nouveau mode s'ajoute sans toucher aux autres")
    void nouveauModeRelais() {
        // GIVEN
        String mode = "RELAIS";

        // WHEN
        int frais = ModesLivraison.frais(mode, petitPanier);

        // THEN
        assertEquals(290, frais);
    }

    @Test
    @DisplayName("Un mode inconnu est refuse")
    void modeInconnuRefuse() {
        // WHEN / THEN
        assertThrows(IllegalArgumentException.class,
                () -> ModesLivraison.frais("DRONE", petitPanier));
    }
}
