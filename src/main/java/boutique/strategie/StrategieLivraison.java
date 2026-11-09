package boutique.strategie;

/**
 * Le pattern Strategy : une famille d'algorithmes interchangeables
 * derriere une seule interface. Ici, le calcul des frais de livraison.
 */
@FunctionalInterface
public interface StrategieLivraison {

    /** Frais de livraison du panier, en centimes. */
    int frais(Panier panier);
}
