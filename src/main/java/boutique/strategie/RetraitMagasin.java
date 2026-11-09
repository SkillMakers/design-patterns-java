package boutique.strategie;

/** Retrait en magasin : gratuit. */
public final class RetraitMagasin implements StrategieLivraison {

    @Override
    public int frais(Panier panier) {
        return 0;
    }
}
