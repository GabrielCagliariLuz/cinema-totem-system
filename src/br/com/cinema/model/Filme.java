package br.com.cinema.model;

import java.io.Serializable;
import java.util.Objects;

public class Filme implements Relatavel, Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String titulo;
    private int duracaoEmMinutos;
    private Genero genero;
    private String classificacaoEtaria;
    private String sinopse;
    private static int contadorId = 1;

    public Filme(String titulo, int duracaoEmMinutos, Genero genero, String classificacaoEtaria, String sinopse) {
        this.id = contadorId++;
        this.titulo = titulo;
        this.duracaoEmMinutos = duracaoEmMinutos;
        this.genero = genero;
        this.classificacaoEtaria = classificacaoEtaria;
        this.sinopse = sinopse;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getDuracaoEmMinutos() {
        return duracaoEmMinutos;
    }

    public Genero getGenero() {
        return genero;
    }

    public String getClassificacaoEtaria() {
        return classificacaoEtaria;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filme filme = (Filme) o;
        return id == filme.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String gerarRelatorio() {
        return String.format(
                "=== FILME #%d ===\nTítulo: %s\nGênero: %s\nDuração: %d min\nClassificação: %s\nSinopse: %s",
                id, titulo, genero.getNome(), duracaoEmMinutos, classificacaoEtaria, sinopse
        );
    }
}
