package boutique.singleton;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

/** Les trois failles du singleton naif, puis les memes essais sur l'enum. */
public final class DemoSingleton {

    private static final int THREADS = 8;

    private DemoSingleton() {
    }

    public static void main(String[] args) throws Exception {
        System.out.println("1. Threads : " + THREADS
                + " threads appellent getInstance() en meme temps");
        Set<Object> vues = instancesVuesParThreads(
                ConfigurationNaive::getInstance);
        System.out.println("   instances differentes : " + vues.size()
                + " ; constructeur appele "
                + ConfigurationNaive.CREATIONS.get() + " fois");

        ConfigurationNaive a = ConfigurationNaive.getInstance();
        ConfigurationNaive b = copieParSerialisation(a);
        System.out.println("2. Serialisation : a == b -> " + (a == b));

        Constructor<ConfigurationNaive> c =
                ConfigurationNaive.class.getDeclaredConstructor();
        c.setAccessible(true);
        ConfigurationNaive d = c.newInstance();
        System.out.println("3. Reflexion : a == d -> " + (a == d));

        System.out.println("--- enum ---");
        Set<Object> vuesEnum = instancesVuesParThreads(
                () -> Configuration.INSTANCE);
        System.out.println("1. Threads : instances differentes : "
                + vuesEnum.size());
        Configuration e = Configuration.INSTANCE;
        Configuration f = copieParSerialisation(e);
        System.out.println("2. Serialisation : e == f -> " + (e == f));
        try {
            Constructor<Configuration> ce = Configuration.class
                    .getDeclaredConstructor(String.class, int.class);
            ce.setAccessible(true);
            ce.newInstance("COPIE", 1);
        } catch (IllegalArgumentException ex) {
            System.out.println("3. Reflexion : IllegalArgumentException: "
                    + ex.getMessage());
        }
    }

    /** Lance 8 threads au meme instant et compte les instances distinctes obtenues. */
    static Set<Object> instancesVuesParThreads(Supplier<Object> acces)
            throws InterruptedException {
        Set<Object> vues = Collections.synchronizedSet(
                Collections.newSetFromMap(new IdentityHashMap<>()));
        CountDownLatch depart = new CountDownLatch(1);
        Thread[] threads = new Thread[THREADS];
        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                try {
                    depart.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                vues.add(acces.get());
            });
            threads[i].start();
        }
        depart.countDown();
        for (Thread t : threads) {
            t.join();
        }
        return vues;
    }

    @SuppressWarnings("unchecked")
    static <T> T copieParSerialisation(T objet)
            throws IOException, ClassNotFoundException {
        ByteArrayOutputStream octets = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(octets)) {
            out.writeObject(objet);
        }
        try (ObjectInputStream in = new ObjectInputStream(
                new ByteArrayInputStream(octets.toByteArray()))) {
            return (T) in.readObject();
        }
    }
}
