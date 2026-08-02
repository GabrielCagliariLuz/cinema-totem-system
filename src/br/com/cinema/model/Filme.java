package br.com.cinema.model;

public class Filme implements Relatavel{
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
    public String gerarRelatorio() {
        return String.format(
                "=== FILME #%d ===\nTítulo: %s\nGênero: %s\nDuração: %d min\nClassificação: %s\nSinopse: %s",
                id, titulo, genero.getNome(), duracaoEmMinutos, classificacaoEtaria, sinopse
        );
    }
}
