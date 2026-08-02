package br.com.cinema.model;

public class Assento {
    private String codigo;
    private StatusAssento statusAssento;

    public Assento(String codigo, StatusAssento statusAssento) {
        this.codigo = codigo;
        this.statusAssento = statusAssento;
    }

    public Assento(String codigo) {
        this(codigo, StatusAssento.LIVRE);
    }

    public boolean isLivre() {
        return this.statusAssento == StatusAssento.LIVRE;
    }

    public String getCodigo() {
        return codigo;
    }

    public StatusAssento getStatusAssento() {
        return statusAssento;
    }

    public void setStatusAssento(StatusAssento statusAssento) {
        this.statusAssento = statusAssento;
    }

    @Override
    public String toString() {
        return codigo + " (" + statusAssento + ")";
    }
}
