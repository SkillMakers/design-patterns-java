package boutique.observateur;

import java.util.List;

/**
 * Le sujet observe : il garde la liste des abonnes et les previent tous
 * quand une commande est validee. Il ne connait aucun abonne en particulier.
 * La liste est fournie par l'appelant : ArrayList ou CopyOnWriteArrayList,
 * et toute la difference est la.
 */
public final class SujetCommande {

    private final List<ObservateurCommande> abonnes;

    public SujetCommande(List<ObservateurCommande> abonnes) {
        this.abonnes = abonnes;
    }

    public void abonner(ObservateurCommande o) {
        abonnes.add(o);
    }

    public void desabonner(ObservateurCommande o) {
        abonnes.remove(o);
    }

    public void valider(String numero) {
        for (ObservateurCommande o : abonnes) {
            o.commandeValidee(numero, this);
        }
    }
}
