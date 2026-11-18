package com.exemplo.composite;

import java.util.Optional;

public abstract class ItemSistemaArquivos {

    private final String nome;

    protected ItemSistemaArquivos(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome não pode ser nulo ou vazio");
        }
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public abstract long getTamanho();

    public abstract int contarArquivos();

    public abstract Optional<ItemSistemaArquivos> buscar(String nome);
}
