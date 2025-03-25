package com.montaury.pokebagarre.metier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;

import com.montaury.pokebagarre.erreurs.ErreurBagarre;
import com.montaury.pokebagarre.erreurs.ErreurMemePokemon;
import com.montaury.pokebagarre.erreurs.ErreurPokemonNonRenseigne;
import com.montaury.pokebagarre.erreurs.ErreurRecuperationPokemon;
import com.montaury.pokebagarre.fixtures.ConstructeurDePokemon;
import com.montaury.pokebagarre.webapi.PokeBuildApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class BagarreTest {

    @Test
    void pokemon_1_vide_devrait_donner_exception() {
        // GIVEN
        Bagarre bagarre = new Bagarre();
        Pokemon pokemon1 = new Pokemon(null, null, null);
        Pokemon pokemon2 = new Pokemon("Pikachu", null, null);

        // WHEN
        Throwable thrown = catchThrowable(()-> bagarre.validerNomPokemons(pokemon1.getNom(), pokemon2.getNom()));

        // THEN
        assertThat(thrown).isInstanceOf(ErreurPokemonNonRenseigne.class).hasMessage("Le premier pokemon n'est pas renseigne");
    }

    @Test
    void pokemon_2_vide_devrait_donner_exception() {
        // GIVEN
        Bagarre bagarre = new Bagarre();
        Pokemon pokemon1 = new Pokemon("Pikachu", null, null);
        Pokemon pokemon2 = new Pokemon(null, null, null);

        // WHEN
        Throwable thrown = catchThrowable(()-> bagarre.validerNomPokemons(pokemon1.getNom(), pokemon2.getNom()));

        // THEN
        assertThat(thrown).isInstanceOf(ErreurPokemonNonRenseigne.class).hasMessage("Le second pokemon n'est pas renseigne");
    }

    @Test
    void pokemon_1_chaine_vide_devrait_donner_exception() {
        // GIVEN
        Bagarre bagarre = new Bagarre();
        Pokemon pokemon1 = new Pokemon("", null, null);
        Pokemon pokemon2 = new Pokemon("Pikachu", null, null);

        // WHEN
        Throwable thrown = catchThrowable(()-> bagarre.validerNomPokemons(pokemon1.getNom(), pokemon2.getNom()));

        // THEN
        assertThat(thrown).isInstanceOf(ErreurPokemonNonRenseigne.class).hasMessage("Le premier pokemon n'est pas renseigne");
    }

    @Test
    void pokemon_2_chaine_vide_devrait_donner_exception() {
        // GIVEN
        Bagarre bagarre = new Bagarre();
        Pokemon pokemon1 = new Pokemon("Pikachu", null, null);
        Pokemon pokemon2 = new Pokemon("", null, null);

        // WHEN
        Throwable thrown = catchThrowable(()-> bagarre.validerNomPokemons(pokemon1.getNom(), pokemon2.getNom()));

        // THEN
        assertThat(thrown).isInstanceOf(ErreurPokemonNonRenseigne.class).hasMessage("Le second pokemon n'est pas renseigne");
    }

    @Test
    void pokemon_1_et_2_identique_devrait_donner_exception() {
        // GIVEN
        Bagarre bagarre = new Bagarre();
        Pokemon pokemon1 = new Pokemon("Pikachu", null, null);
        Pokemon pokemon2 = pokemon1;

        // WHEN
        Throwable thrown = catchThrowable(()-> bagarre.validerNomPokemons(pokemon1.getNom(), pokemon2.getNom()));

        // THEN
        assertThat(thrown).isInstanceOf(ErreurMemePokemon.class).hasMessage("Impossible de faire se bagarrer un pokemon avec lui-meme");
    }

    @Test
    void pokemon_1_non_trouve_devrait_donner_exception() {
        // GIVEN
        var fausseApi = Mockito.mock(PokeBuildApi.class);
        Bagarre bagarre = new Bagarre(fausseApi);

        // WHEN
        Mockito.when(fausseApi.recupererParNom("pikachu")).thenReturn(CompletableFuture.failedFuture(new ErreurRecuperationPokemon("pikachu")));
        Mockito.when(fausseApi.recupererParNom("pikachette")).thenReturn(CompletableFuture.completedFuture(new Pokemon("pikachette", null, null)));
        CompletableFuture<Pokemon> result = bagarre.demarrer("pikachette", "pikachu");

        // THEN
        assertThat(result)
                .failsWithin(Duration.ofSeconds(2))
                .withThrowableOfType(ExecutionException.class)
                .havingCause()
                .isInstanceOf(ErreurRecuperationPokemon.class)
                .withMessage("Impossible de recuperer les details sur 'pikachu'");
    }

    @Test
    void pokemon_2_non_trouve_devrait_donner_exception() {
        // GIVEN
        var fausseApi = Mockito.mock(PokeBuildApi.class);
        Bagarre bagarre = new Bagarre(fausseApi);

        // WHEN
        Mockito.when(fausseApi.recupererParNom("pikachu")).thenReturn(CompletableFuture.failedFuture(new ErreurRecuperationPokemon("pikachu")));
        Mockito.when(fausseApi.recupererParNom("pikachette")).thenReturn(CompletableFuture.completedFuture(new Pokemon("pikachette", null, null)));
        CompletableFuture<Pokemon> result = bagarre.demarrer("pikachu", "pikachette");

        // THEN
        assertThat(result)
                .failsWithin(Duration.ofSeconds(2))
                .withThrowableOfType(ExecutionException.class)
                .havingCause()
                .isInstanceOf(ErreurRecuperationPokemon.class)
                .withMessage("Impossible de recuperer les details sur 'pikachu'");
    }
}