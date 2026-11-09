package boutique.singleton;

/**
 * Le singleton recommande : une enum a une seule valeur.
 * La JVM garantit une seule instance, meme avec plusieurs threads,
 * apres une serialisation, et elle refuse la reflexion.
 */
public enum Configuration {

    INSTANCE;

    private final int tauxTva;

    /** La meme lecture de 50 ms que ConfigurationNaive : la comparaison est equitable. */
    Configuration() {
        this.tauxTva = FichierConfiguration.lireTauxTva();
    }

    public int tauxTva() {
        return tauxTva;
    }
}
