package boutique.strategie;

/**
 * Le panier d'une commande, tel que le voit le calcul des frais de livraison.
 * Les montants sont en centimes : pas de double pour de l'argent.
 *
 * @param montantCentimes total des articles, en centimes
 * @param poidsGrammes    poids du colis, en grammes
 */
public record Panier(int montantCentimes, int poidsGrammes) {
}
