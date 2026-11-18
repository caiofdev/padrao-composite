package com.exemplo.composite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("ItemSistemaArquivos")
class ItemSistemaArquivosTest {

    @Nested
    @DisplayName("construtor - complexidade ciclomática 3")
    class Construtor {

        @Test
        @DisplayName("Caminho 1: nome nulo lança exceção")
        void nomeNuloLancaExcecao() {
            assertThrows(IllegalArgumentException.class, () -> new Pasta(null));
        }

        @Test
        @DisplayName("Caminho 2: nome em branco lança exceção")
        void nomeEmBrancoLancaExcecao() {
            assertThrows(IllegalArgumentException.class, () -> new Pasta("   "));
        }

        @Test
        @DisplayName("Caminho 3: nome válido é atribuído")
        void nomeValidoEhAtribuido() {
            ItemSistemaArquivos item = new Pasta("documentos");

            assertEquals("documentos", item.getNome());
        }
    }
}
