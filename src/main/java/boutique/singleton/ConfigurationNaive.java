package boutique.singleton;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Le singleton tel qu'on l'ecrit souvent : constructeur prive, instance
 * creee au premier appel. Il a trois failles : les threads, la
 * serialisation et la reflexion. DemoSingleton les montre toutes.
 */
public final class ConfigurationNaive implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** Nombre de fois ou le constructeur a tourne : un singleton doit rester a 1. */
    public static final AtomicInteger CREATIONS = new AtomicInteger();

    private static ConfigurationNaive instance;

    private final int tauxTva;

    private ConfigurationNaive() {
        CREATIONS.incrementAndGet();
        this.tauxTva = FichierConfiguration.lireTauxTva();
    }

    public static ConfigurationNaive getInstance() {
        if (instance == null) {
            instance = new ConfigurationNaive();
        }
        return instance;
    }

    public int tauxTva() {
        return tauxTva;
    }
}
