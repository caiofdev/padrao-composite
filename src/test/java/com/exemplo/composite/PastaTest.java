package com.exemplo.composite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Pasta (composto)")
class PastaTest {

    private Pasta raiz;

    @BeforeEach
    void setUp() {
        raiz = new Pasta("raiz");
    }

    @Nested
    @DisplayName("adicionar() - complexidade ciclomática 4")
    class Adicionar {

        @Test
        @DisplayName("Caminho 1: item nulo lança exceção")
        void itemNuloLancaExcecao() {
            assertThrows(IllegalArgumentException.class, () -> raiz.adicionar(null));
        }

        @Test
        @DisplayName("Caminho 2: adicionar a própria pasta lança exceção")
        void propriaPastaLancaExcecao() {
            assertThrows(IllegalArgumentException.class, () -> raiz.adicionar(raiz));
        }

        @Test
        @DisplayName("Caminho 3: nome duplicado lança exceção")
        void nomeDuplicadoLancaExcecao() {
            raiz.adicionar(new Arquivo("a.txt", 10));

            assertThrows(IllegalStateException.class, () -> raiz.adicionar(new Arquivo("a.txt", 20)));
        }

        @Test
        @DisplayName("Caminho 4: item válido é adicionado")
        void itemValidoEhAdicionado() {
            Arquivo arquivo = new Arquivo("a.txt", 10);

            raiz.adicionar(arquivo);

            assertEquals(List.of(arquivo), raiz.getItens());
        }
    }

    @Nested
    @DisplayName("possuiItemComNome() via adicionar() - complexidade ciclomática 3")
    class PossuiItemComNome {

        @Test
        @DisplayName("Caminho 1: pasta vazia não entra no laço e permite adicionar")
        void pastaVaziaPermiteAdicionar() {
            raiz.adicionar(new Arquivo("a.txt", 10));

            assertEquals(1, raiz.getItens().size());
        }

        @Test
        @DisplayName("Caminho 2: laço percorrido sem encontrar o nome permite adicionar")
        void nomeNaoEncontradoPermiteAdicionar() {
            raiz.adicionar(new Arquivo("a.txt", 10));
            raiz.adicionar(new Arquivo("b.txt", 20));

            assertEquals(2, raiz.getItens().size());
        }

        @Test
        @DisplayName("Caminho 3: nome encontrado no laço impede adicionar")
        void nomeEncontradoImpedeAdicionar() {
            raiz.adicionar(new Arquivo("a.txt", 10));
            raiz.adicionar(new Arquivo("b.txt", 20));

            assertThrows(IllegalStateException.class, () -> raiz.adicionar(new Pasta("b.txt")));
            assertEquals(2, raiz.getItens().size());
        }
    }

    @Nested
    @DisplayName("remover() - complexidade ciclomática 1")
    class Remover {

        @Test
        @DisplayName("Caminho 1: remove item existente")
        void removeItemExistente() {
            raiz.adicionar(new Arquivo("a.txt", 10));

            assertTrue(raiz.remover("a.txt"));
            assertTrue(raiz.getItens().isEmpty());
        }

        @Test
        @DisplayName("Caminho 1: item inexistente não altera a pasta")
        void itemInexistenteNaoAlteraPasta() {
            raiz.adicionar(new Arquivo("a.txt", 10));

            assertFalse(raiz.remover("b.txt"));
            assertEquals(1, raiz.getItens().size());
        }
    }

    @Nested
    @DisplayName("getItens() - complexidade ciclomática 1")
    class GetItens {

        @Test
        @DisplayName("Caminho 1: retorna lista imutável")
        void retornaListaImutavel() {
            List<ItemSistemaArquivos> itens = raiz.getItens();

            assertThrows(UnsupportedOperationException.class, () -> itens.add(new Arquivo("a.txt", 1)));
        }
    }

    @Nested
    @DisplayName("getTamanho() - complexidade ciclomática 2")
    class GetTamanho {

        @Test
        @DisplayName("Caminho 1: pasta vazia não entra no laço e retorna zero")
        void pastaVaziaRetornaZero() {
            assertEquals(0, raiz.getTamanho());
        }

        @Test
        @DisplayName("Caminho 2: soma recursivamente arquivos e subpastas")
        void somaRecursivamente() {
            Pasta subpasta = new Pasta("sub");
            subpasta.adicionar(new Arquivo("b.txt", 30));
            raiz.adicionar(new Arquivo("a.txt", 10));
            raiz.adicionar(subpasta);

            assertEquals(40, raiz.getTamanho());
        }
    }

    @Nested
    @DisplayName("contarArquivos() - complexidade ciclomática 2")
    class ContarArquivos {

        @Test
        @DisplayName("Caminho 1: pasta vazia não entra no laço e retorna zero")
        void pastaVaziaRetornaZero() {
            assertEquals(0, raiz.contarArquivos());
        }

        @Test
        @DisplayName("Caminho 2: conta arquivos recursivamente ignorando pastas")
        void contaRecursivamente() {
            Pasta subpasta = new Pasta("sub");
            subpasta.adicionar(new Arquivo("b.txt", 30));
            subpasta.adicionar(new Arquivo("c.txt", 5));
            raiz.adicionar(new Arquivo("a.txt", 10));
            raiz.adicionar(subpasta);
            raiz.adicionar(new Pasta("vazia"));

            assertEquals(3, raiz.contarArquivos());
        }
    }

    @Nested
    @DisplayName("buscar() - complexidade ciclomática 4")
    class Buscar {

        @Test
        @DisplayName("Caminho 1: nome igual ao da pasta retorna a própria pasta")
        void nomeDaPastaRetornaPropriaPasta() {
            Optional<ItemSistemaArquivos> resultado = raiz.buscar("raiz");

            assertTrue(resultado.isPresent());
            assertSame(raiz, resultado.get());
        }

        @Test
        @DisplayName("Caminho 2: pasta vazia não entra no laço e retorna vazio")
        void pastaVaziaRetornaVazio() {
            assertTrue(raiz.buscar("a.txt").isEmpty());
        }

        @Test
        @DisplayName("Caminho 3: item encontrado em subpasta é retornado")
        void itemEncontradoEmSubpasta() {
            Arquivo alvo = new Arquivo("alvo.txt", 10);
            Pasta subpasta = new Pasta("sub");
            subpasta.adicionar(alvo);
            raiz.adicionar(new Arquivo("a.txt", 5));
            raiz.adicionar(subpasta);

            Optional<ItemSistemaArquivos> resultado = raiz.buscar("alvo.txt");

            assertTrue(resultado.isPresent());
            assertSame(alvo, resultado.get());
        }

        @Test
        @DisplayName("Caminho 4: laço percorrido sem encontrar retorna vazio")
        void itemInexistenteRetornaVazio() {
            raiz.adicionar(new Arquivo("a.txt", 5));
            raiz.adicionar(new Pasta("sub"));

            assertTrue(raiz.buscar("inexistente.txt").isEmpty());
        }
    }
}
