package boutique.observateur;

import java.util.List;

/**
 * Envoie un code de bienvenue a la premiere commande, puis se desabonne.
 * Se desabonner PENDANT la notification est exactement le piege.
 */
public final class OffreBienvenue implements ObservateurCommande {

    private final List<String> journal;

    public OffreBienvenue(List<String> journal) {
        this.journal = journal;
    }

    @Override
    public void commandeValidee(String numero, SujetCommande sujet) {
        journal.add("bienvenue " + numero);
        sujet.desabonner(this);
    }
}
