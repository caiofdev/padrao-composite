package com.exemplo.composite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Arquivo (folha)")
class ArquivoTest {

    @Nested
    @DisplayName("construtor - complexidade ciclomática 2")
    class Construtor {

        @Test
        @DisplayName("Caminho 1: tamanho negativo lança exceção")
        void tamanhoNegativoLancaExcecao() {
            assertThrows(IllegalArgumentException.class, () -> new Arquivo("foto.png", -1));
        }

        @Test
        @DisplayName("Caminho 2: tamanho válido é atribuído")
        void tamanhoValidoEhAtribuido() {
            Arquivo arquivo = new Arquivo("foto.png", 0);

            assertEquals(0, arquivo.getTamanho());
        }
    }

    @Nested
    @DisplayName("getTamanho() - complexidade ciclomática 1")
    class GetTamanho {

        @Test
        @DisplayName("Caminho 1: retorna o tamanho informado")
        void retornaTamanhoInformado() {
            Arquivo arquivo = new Arquivo("video.mp4", 2048);

            assertEquals(2048, arquivo.getTamanho());
        }
    }

    @Nested
    @DisplayName("contarArquivos() - complexidade ciclomática 1")
    class ContarArquivos {

        @Test
        @DisplayName("Caminho 1: um arquivo conta como um")
        void retornaUm() {
            Arquivo arquivo = new Arquivo("notas.txt", 10);

            assertEquals(1, arquivo.contarArquivos());
        }
    }

    @Nested
    @DisplayName("buscar() - complexidade ciclomática 2")
    class Buscar {

        @Test
        @DisplayName("Caminho 1: nome igual retorna o próprio arquivo")
        void nomeIgualRetornaProprioArquivo() {
            Arquivo arquivo = new Arquivo("notas.txt", 10);

            Optional<ItemSistemaArquivos> resultado = arquivo.buscar("notas.txt");

            assertTrue(resultado.isPresent());
            assertSame(arquivo, resultado.get());
        }

        @Test
        @DisplayName("Caminho 2: nome diferente retorna vazio")
        void nomeDiferenteRetornaVazio() {
            Arquivo arquivo = new Arquivo("notas.txt", 10);

            assertTrue(arquivo.buscar("outro.txt").isEmpty());
        }
    }
}
