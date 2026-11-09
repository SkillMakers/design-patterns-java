#!/bin/sh
# Compile, teste et execute tout ; sorties dans preuves/.
# Lance dans le conteneur maven:3.9-eclipse-temurin-25 (voir le README), ou avec Java 25 et Maven installes.
# -Xlint:all -Werror dans le pom : un seul avertissement fait echouer la compilation.
set -eu
cd "$(dirname "$0")"
P=preuves
mkdir -p "$P"

{
  echo "\$ java -version"; java -version 2>&1
  echo "\$ mvn -version | head -1"; mvn -version | head -1
} > "$P/versions.txt"

mvn -q -B clean test > "$P/tests.txt" 2>&1 || { cat "$P/tests.txt"; exit 1; }
# Resume des tests (surefire) : une ligne par classe
grep -h "Tests run" target/surefire-reports/*.txt >> "$P/tests.txt"

for d in strategie.DemoStrategie fabrique.DemoFabrique builder.DemoBuilder \
         observateur.DemoObservateur singleton.DemoSingleton; do
  nom=$(echo "$d" | cut -d. -f2)
  echo "\$ java boutique.$d" > "$P/$nom.txt"
  java -cp target/classes "boutique.$d" >> "$P/$nom.txt"
done

# Le singleton naif sous 8 threads : 10 JVM neuves, une ligne par execution
: > "$P/singleton-threads-10-executions.txt"
for i in 1 2 3 4 5 6 7 8 9 10; do
  java -cp target/classes boutique.singleton.DemoSingleton \
    | grep "instances differentes :" | head -1 \
    >> "$P/singleton-threads-10-executions.txt"
done

# Factory : on ajoute Crypto a l'interface scellee sans le traiter dans le switch
T=$(mktemp -d)
cp -r src/main/java/. "$T/"
F="$T/boutique/fabrique/MoyenPaiement.java"
sed -i 's/MoyenPaiement.Virement {$/MoyenPaiement.Virement, MoyenPaiement.Crypto {/' "$F"
sed -i '$i\
    record Crypto(String portefeuille) implements MoyenPaiement {\
    }' "$F"
# Les deux sed doivent avoir agi, sinon l'erreur montree ne serait pas la bonne
grep -q 'MoyenPaiement.Virement, MoyenPaiement.Crypto {' "$F"
grep -q 'record Crypto(String portefeuille)' "$F"
{
  echo "\$ (MoyenPaiement avec Crypto ajoute, FraisPaiement inchange)"
  echo "\$ javac boutique/fabrique/*.java"
  (cd "$T" && javac -d "$T/out" boutique/fabrique/*.java 2>&1) || echo "(compilation refusee, code $?)"
} > "$P/fabrique-crypto-compilation.txt"
rm -rf "$T"
grep -q 'does not cover all possible input values' "$P/fabrique-crypto-compilation.txt" \
  || { echo "Erreur attendue absente"; cat "$P/fabrique-crypto-compilation.txt"; exit 1; }

# Nombre de lignes de la strategie : chaine de if, classes, lambdas
wc -l src/main/java/boutique/strategie/*.java > "$P/strategie-lignes.txt"

echo "OK : preuves dans $P"
