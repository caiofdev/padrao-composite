package com.exemplo.composite;

import java.util.Optional;

public class Arquivo extends ItemSistemaArquivos {

    private final long tamanho;

    public Arquivo(String nome, long tamanho) {
        super(nome);
        if (tamanho < 0) {
            throw new IllegalArgumentException("O tamanho não pode ser negativo");
        }
        this.tamanho = tamanho;
    }

    @Override
    public long getTamanho() {
        return tamanho;
    }

    @Override
    public int contarArquivos() {
        return 1;
    }

    @Override
    public Optional<ItemSistemaArquivos> buscar(String nome) {
        if (getNome().equals(nome)) {
            return Optional.of(this);
        }
        return Optional.empty();
    }
}
