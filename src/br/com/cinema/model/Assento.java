package br.com.cinema.model;

import java.util.Objects;

public class Assento{
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assento assento = (Assento) o;
        return  codigo != null ? codigo.equalsIgnoreCase(assento.codigo) : assento.codigo == null;
    }

    @Override
    public int hashCode() {
        return codigo != null ? codigo.toLowerCase().hashCode() : 0;
    }

    @Override
    public String toString() {
        return codigo + " (" + statusAssento + ")";
    }
}
