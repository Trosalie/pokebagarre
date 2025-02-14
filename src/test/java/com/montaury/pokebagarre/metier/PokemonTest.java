package com.montaury.pokebagarre.metier;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.montaury.pokebagarre.fixtures.ConstructeurDePokemon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PokemonTest {
    @Test
    void pokemon_1_devrait_gagner_avec_attaque_superieure() {
        // GIVEN
        Pokemon pokemon_1 = new ConstructeurDePokemon().avecAttaque(2).avecDefense(2).construire();
        Pokemon pokemon_2 = new ConstructeurDePokemon().avecAttaque(1).avecDefense(1).construire();

        // WHEN
        boolean resultat = pokemon_1.estVainqueurContre(pokemon_2);

        // THEN
        assertThat(resultat).isTrue();
    }

    @Test
    void pokemon_1_devrait_perdre_avec_attaque_inferieure() {
        // GIVEN
        Pokemon pokemon_1 = new ConstructeurDePokemon().avecAttaque(1).avecDefense(2).construire();
        Pokemon pokemon_2 = new ConstructeurDePokemon().avecAttaque(2).avecDefense(1).construire();

        // WHEN
        boolean resultat = pokemon_1.estVainqueurContre(pokemon_2);

        // THEN
        assertThat(resultat).isFalse();
    }

    @Test
    void pokemon_1_devrait_gagner_avec_attaque_egale_et_defense_superieure() {
        // GIVEN
        Pokemon pokemon_1 = new ConstructeurDePokemon().avecAttaque(1).avecDefense(2).construire();
        Pokemon pokemon_2 = new ConstructeurDePokemon().avecAttaque(1).avecDefense(1).construire();

        // WHEN
        boolean resultat = pokemon_1.estVainqueurContre(pokemon_2);

        // THEN
        assertThat(resultat).isTrue();
    }

    @Test
    void pokemon_1_devrait_perdre_avec_attaque_egale_et_defense_inferieure() {
        // GIVEN
        Pokemon pokemon_1 = new ConstructeurDePokemon().avecAttaque(1).avecDefense(1).construire();
        Pokemon pokemon_2 = new ConstructeurDePokemon().avecAttaque(1).avecDefense(2).construire();

        // WHEN
        boolean resultat = pokemon_1.estVainqueurContre(pokemon_2);

        // THEN
        assertThat(resultat).isFalse();
    }

    @Test
    void pokemon_1_devrait_gagner_avec_attaque_egale_et_defense_egale() {
        // GIVEN
        Pokemon pokemon_1 = new ConstructeurDePokemon().avecAttaque(2).avecDefense(2).construire();
        Pokemon pokemon_2 = new ConstructeurDePokemon().avecAttaque(2).avecDefense(2).construire();

        // WHEN
        boolean resultat = pokemon_1.estVainqueurContre(pokemon_2);

        // THEN
        assertThat(resultat).isTrue();
    }
}