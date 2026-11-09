package boutique.strategie;

/** Livraison express : 9,90 euros, plus 1 euro par kilo entier. */
public final class LivraisonExpress implements StrategieLivraison {

    @Override
    public int frais(Panier panier) {
        return 990 + 100 * (panier.poidsGrammes() / 1000);
    }
}
