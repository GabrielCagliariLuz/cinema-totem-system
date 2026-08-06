package br.com.cinema.model;

import br.com.cinema.exception.DadosInvalidosException;

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

    public Filme(String titulo, int duracaoEmMinutos, Genero genero, String classificacaoEtaria, String sinopse)
        throws DadosInvalidosException {
        if (titulo == null || titulo.trim().isEmpty()){
            throw  new DadosInvalidosException("O título do filme é obrigatório");
        }
        if (duracaoEmMinutos <= 0){
            throw new DadosInvalidosException("A duração do filme deve ser maior que zero.");
        }
        if (genero == null){
            throw new DadosInvalidosException("O gênero do filme é obrigatório.");
        }
        if (classificacaoEtaria == null || classificacaoEtaria.trim().isEmpty()){
            throw new DadosInvalidosException("A classificação etária é obrigatória.");
        }
        this.id = contadorId++;
        this.titulo = titulo;
        this.duracaoEmMinutos = duracaoEmMinutos;
        this.genero = genero;
        this.classificacaoEtaria = classificacaoEtaria;
        this.sinopse = (sinopse != null) ? sinopse.trim() : "";
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
    public String toString() {
        return this.titulo;
    }

    @Override
    public String gerarRelatorio() {
        return String.format(
                "=== FILME #%d ===\nTítulo: %s\nGênero: %s\nDuração: %d min\nClassificação: %s\nSinopse: %s",
                id, titulo, genero.getNome(), duracaoEmMinutos, classificacaoEtaria, sinopse
        );
    }
}
