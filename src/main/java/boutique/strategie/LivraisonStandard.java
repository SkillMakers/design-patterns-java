package boutique.strategie;

/** Livraison standard : 4,90 euros, offerte des 50 euros d'achat. */
public final class LivraisonStandard implements StrategieLivraison {

    @Override
    public int frais(Panier panier) {
        return panier.montantCentimes() >= 5000 ? 0 : 490;
    }
}
