package boutique.singleton;

/** Simule la lecture du fichier de configuration, commune aux deux singletons. */
final class FichierConfiguration {

    private FichierConfiguration() {
    }

    /** 50 ms pendant lesquelles un autre thread a le temps de passer. */
    static int lireTauxTva() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return 20;
    }
}
