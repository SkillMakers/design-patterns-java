package boutique.strategie;

import java.util.List;

/** Affiche les frais des quatre modes sur deux paniers. */
public final class DemoStrategie {

    private DemoStrategie() {
    }

    public static void main(String[] args) {
        List<Panier> paniers = List.of(new Panier(3200, 2500),
                new Panier(7900, 2500));
        for (Panier p : paniers) {
            System.out.println("Panier " + p);
            for (String mode : List.of("STANDARD", "EXPRESS", "RETRAIT")) {
                System.out.printf("  %-8s avant=%4d  classe=%4d  lambda=%4d%n",
                        mode, FraisLivraisonAvant.calculer(mode, p),
                        classe(mode).frais(p), ModesLivraison.frais(mode, p));
            }
            System.out.printf("  %-8s lambda=%4d (nouveau mode, rien d'autre"
                    + " n'a change)%n", "RELAIS",
                    ModesLivraison.frais("RELAIS", p));
        }
    }

    private static StrategieLivraison classe(String mode) {
        return switch (mode) {
            case "STANDARD" -> new LivraisonStandard();
            case "EXPRESS" -> new LivraisonExpress();
            case "RETRAIT" -> new RetraitMagasin();
            default -> throw new IllegalArgumentException(
                    "Mode inconnu : " + mode);
        };
    }
}
