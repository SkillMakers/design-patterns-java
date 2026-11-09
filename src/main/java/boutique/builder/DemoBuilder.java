package boutique.builder;

/** Le constructeur a six parametres face au Builder. */
public final class DemoBuilder {

    private DemoBuilder() {
    }

    public static void main(String[] args) {
        String domicile = "12 rue des Lilas, Lyon";
        String bureau = "3 place Bellecour, Lyon";

        // Alice veut etre livree au bureau et facturee au domicile.
        // Les deux adresses sont inversees : le compilateur ne dit rien.
        CommandeAvant avant = new CommandeAvant("Alice", domicile, bureau,
                "EXPRESS", null, false);
        System.out.println("Constructeur : colis envoye a "
                + avant.adresseLivraison());

        Commande commande = Commande.builder()
                .client("Alice")
                .livraison(bureau)
                .facturation(domicile)
                .mode("EXPRESS")
                .build();
        System.out.println("Builder      : colis envoye a "
                + commande.adresseLivraison());
        System.out.println(commande);

        try {
            Commande.builder().livraison(bureau).build();
        } catch (IllegalStateException e) {
            System.out.println("Sans client  : IllegalStateException: "
                    + e.getMessage());
        }
    }
}
