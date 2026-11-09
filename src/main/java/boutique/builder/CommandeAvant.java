package boutique.builder;

/**
 * AVANT le pattern : un constructeur a six parametres, dont cinq String.
 * Inverser deux adresses compile sans erreur.
 */
public final class CommandeAvant {

    private final String client;
    private final String adresseLivraison;
    private final String adresseFacturation;
    private final String modeLivraison;
    private final String codePromo;
    private final boolean emballageCadeau;

    public CommandeAvant(String client, String adresseLivraison,
            String adresseFacturation, String modeLivraison,
            String codePromo, boolean emballageCadeau) {
        this.client = client;
        this.adresseLivraison = adresseLivraison;
        this.adresseFacturation = adresseFacturation;
        this.modeLivraison = modeLivraison;
        this.codePromo = codePromo;
        this.emballageCadeau = emballageCadeau;
    }

    public String adresseLivraison() {
        return adresseLivraison;
    }

    @Override
    public String toString() {
        return client + " | livraison=" + adresseLivraison + " | facturation="
                + adresseFacturation + " | " + modeLivraison + " | promo="
                + codePromo + " | cadeau=" + emballageCadeau;
    }
}
