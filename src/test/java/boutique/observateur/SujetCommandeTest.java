package boutique.observateur;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SujetCommandeTest {

    private final List<String> journal = new ArrayList<>();
    private final ObservateurCommande email = (n, s) -> journal.add("email");
    private final ObservateurCommande stock = (n, s) -> journal.add("stock");
    private final ObservateurCommande facture =
            (n, s) -> journal.add("facture");

    @Test
    @DisplayName("Tous les abonnes sont prevenus")
    void tousLesAbonnesSontPrevenus() {
        // GIVEN
        SujetCommande sujet = new SujetCommande(new ArrayList<>());
        sujet.abonner(email);
        sujet.abonner(stock);
        sujet.abonner(facture);

        // WHEN
        sujet.valider("5001");

        // THEN
        assertEquals(List.of("email", "stock", "facture"), journal);
    }

    @Test
    @DisplayName("ArrayList, desabonnement en 1er : ConcurrentModificationException")
    void desabonnementEnPremierLeveUneException() {
        // GIVEN
        SujetCommande sujet = new SujetCommande(new ArrayList<>());
        sujet.abonner(new OffreBienvenue(journal));
        sujet.abonner(email);
        sujet.abonner(facture);

        // WHEN / THEN
        assertThrows(ConcurrentModificationException.class,
                () -> sujet.valider("5001"));
        assertEquals(List.of("bienvenue 5001"), journal);
    }

    @Test
    @DisplayName("ArrayList, desabonnement en avant-dernier : la facture saute sans erreur")
    void desabonnementEnAvantDernierSauteLeDernier() {
        // GIVEN
        SujetCommande sujet = new SujetCommande(new ArrayList<>());
        sujet.abonner(email);
        sujet.abonner(stock);
        sujet.abonner(new OffreBienvenue(journal));
        sujet.abonner(facture);

        // WHEN
        assertDoesNotThrow(() -> sujet.valider("5001"));

        // THEN
        assertEquals(List.of("email", "stock", "bienvenue 5001"), journal);
    }

    @Test
    @DisplayName("CopyOnWriteArrayList : les quatre abonnes sont prevenus")
    void copyOnWritePrevientTout() {
        // GIVEN
        SujetCommande sujet = new SujetCommande(new CopyOnWriteArrayList<>());
        sujet.abonner(email);
        sujet.abonner(stock);
        sujet.abonner(new OffreBienvenue(journal));
        sujet.abonner(facture);

        // WHEN
        sujet.valider("5001");
        sujet.valider("5002");

        // THEN : l'offre de bienvenue n'est envoyee qu'une fois
        assertEquals(List.of("email", "stock", "bienvenue 5001", "facture",
                "email", "stock", "facture"), journal);
    }
}
