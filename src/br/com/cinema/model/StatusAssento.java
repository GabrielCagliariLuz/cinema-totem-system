package br.com.cinema.model;

public enum StatusAssento {
    LIVRE("Livre"),
    SELECIONADO("Selecionado"),
    OCUPADO("Ocupado"),
    INDISPONIVEL("Indisponível");

    private final String descricao;

    StatusAssento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
