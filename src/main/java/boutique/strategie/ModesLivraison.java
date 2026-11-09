package boutique.strategie;

import java.util.Map;

/**
 * Le meme pattern en Java moderne : l'interface est fonctionnelle,
 * chaque strategie tient donc dans une lambda, sans classe a ecrire.
 */
public final class ModesLivraison {

    private ModesLivraison() {
    }

    public static final Map<String, StrategieLivraison> MODES = Map.of(
            "STANDARD", p -> p.montantCentimes() >= 5000 ? 0 : 490,
            "EXPRESS", p -> 990 + 100 * (p.poidsGrammes() / 1000),
            "RETRAIT", p -> 0,
            "RELAIS", p -> 290);

    public static int frais(String mode, Panier panier) {
        StrategieLivraison strategie = MODES.get(mode);
        if (strategie == null) {
            throw new IllegalArgumentException("Mode inconnu : " + mode);
        }
        return strategie.frais(panier);
    }
}
