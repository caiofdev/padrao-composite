package com.exemplo.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Pasta extends ItemSistemaArquivos {

    private final List<ItemSistemaArquivos> itens = new ArrayList<>();

    public Pasta(String nome) {
        super(nome);
    }

    public void adicionar(ItemSistemaArquivos item) {
        if (item == null) {
            throw new IllegalArgumentException("O item não pode ser nulo");
        }
        if (item == this) {
            throw new IllegalArgumentException("Uma pasta não pode conter a si mesma");
        }
        if (possuiItemComNome(item.getNome())) {
            throw new IllegalStateException("Já existe um item com o nome " + item.getNome());
        }
        itens.add(item);
    }

    public boolean remover(String nome) {
        return itens.removeIf(item -> item.getNome().equals(nome));
    }

    public List<ItemSistemaArquivos> getItens() {
        return Collections.unmodifiableList(itens);
    }

    @Override
    public long getTamanho() {
        long total = 0;
        for (ItemSistemaArquivos item : itens) {
            total += item.getTamanho();
        }
        return total;
    }

    @Override
    public int contarArquivos() {
        int total = 0;
        for (ItemSistemaArquivos item : itens) {
            total += item.contarArquivos();
        }
        return total;
    }

    @Override
    public Optional<ItemSistemaArquivos> buscar(String nome) {
        if (getNome().equals(nome)) {
            return Optional.of(this);
        }
        for (ItemSistemaArquivos item : itens) {
            Optional<ItemSistemaArquivos> encontrado = item.buscar(nome);
            if (encontrado.isPresent()) {
                return encontrado;
            }
        }
        return Optional.empty();
    }

    private boolean possuiItemComNome(String nome) {
        for (ItemSistemaArquivos item : itens) {
            if (item.getNome().equals(nome)) {
                return true;
            }
        }
        return false;
    }
}
