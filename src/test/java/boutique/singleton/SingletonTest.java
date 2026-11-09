package boutique.singleton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Constructor;
import java.util.function.Supplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SingletonTest {

    @Test
    @DisplayName("Singleton naif : la serialisation cree une deuxieme instance")
    void naifCasseParSerialisation() throws Exception {
        // GIVEN
        ConfigurationNaive a = ConfigurationNaive.getInstance();

        // WHEN
        ConfigurationNaive b = DemoSingleton.copieParSerialisation(a);

        // THEN
        assertNotSame(a, b);
    }

    @Test
    @DisplayName("Singleton naif : la reflexion appelle le constructeur prive")
    void naifCasseParReflexion() throws Exception {
        // GIVEN
        ConfigurationNaive a = ConfigurationNaive.getInstance();
        Constructor<ConfigurationNaive> c =
                ConfigurationNaive.class.getDeclaredConstructor();
        c.setAccessible(true);

        // WHEN
        ConfigurationNaive d = c.newInstance();

        // THEN
        assertNotSame(a, d);
    }

    @Test
    @DisplayName("Enum : la serialisation rend la meme instance")
    void enumResisteALaSerialisation() throws Exception {
        // GIVEN
        Configuration e = Configuration.INSTANCE;

        // WHEN
        Configuration f = DemoSingleton.copieParSerialisation(e);

        // THEN
        assertSame(e, f);
    }

    @Test
    @DisplayName("Enum : la reflexion est refusee")
    void enumRefuseLaReflexion() throws Exception {
        // GIVEN
        Constructor<Configuration> c = Configuration.class
                .getDeclaredConstructor(String.class, int.class);
        c.setAccessible(true);

        // WHEN / THEN
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> c.newInstance("COPIE", 1));
        assertEquals("Cannot reflectively create enum objects",
                ex.getMessage());
    }

    @Test
    @DisplayName("Enum : huit threads obtiennent la meme instance")
    void enumUneSeuleInstanceAvecHuitThreads() throws Exception {
        // GIVEN : le constructeur de l'enum lit le meme fichier en 50 ms
        Supplier<Object> acces = () -> Configuration.INSTANCE;

        // WHEN
        int instances = DemoSingleton.instancesVuesParThreads(acces).size();

        // THEN
        assertEquals(1, instances);
    }
}
