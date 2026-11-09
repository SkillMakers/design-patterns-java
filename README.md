# Design patterns en Java : les 5 essentiels (et leurs pièges)

Code de la vidéo **« Design patterns en Java : les 5 essentiels (et leurs pièges) »** de la chaîne
[IT Skills Maker](https://www.youtube.com/@ItSkillsMaker), playlist
[Java pour développeurs](https://www.youtube.com/playlist?list=PLFmYDsjERZrU).

Les cinq patterns sont appliqués à la même boutique en ligne, en **Java 25** :

| Pattern | Paquet | Le problème | Le piège montré |
|---|---|---|---|
| Strategy | [`boutique.strategie`](src/main/java/boutique/strategie/) | les frais de livraison (une chaîne de `if`) | la version lambda : un nouveau mode sans toucher aux autres |
| Factory | [`boutique.fabrique`](src/main/java/boutique/fabrique/) | les moyens de paiement | interface scellée + `switch` sans `default` : un oubli ne compile pas |
| Builder | [`boutique.builder`](src/main/java/boutique/builder/) | une commande à six champs | deux adresses inversées qui compilent ; vérifications dans le `record` |
| Observer | [`boutique.observateur`](src/main/java/boutique/observateur/) | la commande validée | un abonné qui se désabonne pendant la notification : avec une `ArrayList`, la facture saute **sans aucune erreur** |
| Singleton | [`boutique.singleton`](src/main/java/boutique/singleton/) | la configuration | 8 threads = 8 appels du constructeur ; sérialisation et réflexion ; l'`enum` résiste aux trois |

## Lancer

Avec Java 25 et Maven : `sh executer.sh`. Ou sans rien installer, avec Docker :

```
docker run --rm -v "$PWD:/w" -w /w maven:3.9-eclipse-temurin-25 sh executer.sh
```

Le script lance les 27 tests JUnit (compilation avec `-Xlint:all -Werror`), les 5 démos, le singleton naïf dans
10 JVM neuves, et la compilation de la variante « Crypto » qui doit échouer. Les sorties réelles
(Temurin 25.0.4.1) sont dans [`preuves/`](preuves/).
