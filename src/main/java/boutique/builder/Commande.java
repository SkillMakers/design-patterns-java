package boutique.builder;

import java.util.Objects;

/**
 * Le pattern Builder : chaque champ est nomme a l'appel, les champs
 * facultatifs ont une valeur par defaut, et build() verifie les champs
 * obligatoires avant de creer un objet immuable.
 */
public record Commande(String client, String adresseLivraison,
        String adresseFacturation, String modeLivraison,
        String codePromo, boolean emballageCadeau) {

    /**
     * Les verifications sont ici, et pas seulement dans build() : le
     * constructeur d'un record reste public, il ne doit pas les contourner.
     */
    public Commande {
        if (client == null) {
            throw new IllegalStateException("client obligatoire");
        }
        if (adresseLivraison == null) {
            throw new IllegalStateException("adresse de livraison obligatoire");
        }
        // Sans adresse de facturation, on facture a l'adresse de livraison.
        adresseFacturation = Objects.requireNonNullElse(
                adresseFacturation, adresseLivraison);
        modeLivraison = Objects.requireNonNullElse(modeLivraison, "STANDARD");
    }

    public static Builder builder() {
        return new Builder();
    }

    /** Construit une commande pas a pas ; l'appel se lit comme une phrase. */
    public static final class Builder {

        private String client;
        private String adresseLivraison;
        private String adresseFacturation;
        private String modeLivraison = "STANDARD";
        private String codePromo;
        private boolean emballageCadeau;

        private Builder() {
        }

        public Builder client(String client) {
            this.client = client;
            return this;
        }

        public Builder livraison(String adresse) {
            this.adresseLivraison = adresse;
            return this;
        }

        public Builder facturation(String adresse) {
            this.adresseFacturation = adresse;
            return this;
        }

        public Builder mode(String mode) {
            this.modeLivraison = mode;
            return this;
        }

        public Builder codePromo(String code) {
            this.codePromo = code;
            return this;
        }

        public Builder emballageCadeau() {
            this.emballageCadeau = true;
            return this;
        }

        /** Cree la commande ; le constructeur du record verifie les champs obligatoires. */
        public Commande build() {
            return new Commande(client, adresseLivraison, adresseFacturation,
                    modeLivraison, codePromo, emballageCadeau);
        }
    }
}
