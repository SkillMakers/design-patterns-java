package boutique.observateur;

/**
 * Le pattern Observer : un objet prevenu a chaque commande validee.
 * Le sujet est passe en parametre pour qu'un observateur puisse se desabonner.
 */
@FunctionalInterface
public interface ObservateurCommande {

    void commandeValidee(String numero, SujetCommande sujet);
}
