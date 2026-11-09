package boutique.fabrique;

/** Cree les trois moyens de paiement par leur code et affiche la commission. */
public final class DemoFabrique {

    private DemoFabrique() {
    }

    public static void main(String[] args) {
        String[][] demandes = {
            {"CB", "**** 4242"},
            {"PAYPAL", "alice@exemple.fr"},
            {"VIREMENT", "FR76 **** 0189"},
        };
        for (String[] d : demandes) {
            MoyenPaiement m = FabriquePaiement.creer(d[0], d[1]);
            System.out.printf("%-8s -> %-40s commission sur 79,00 : %d%n",
                    d[0], m, FraisPaiement.commission(m, 7900));
        }
        try {
            FabriquePaiement.creer("CHEQUE", "123");
        } catch (IllegalArgumentException e) {
            System.out.println("CHEQUE   -> " + e.getMessage());
        }
    }
}
