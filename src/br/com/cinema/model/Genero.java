package br.com.cinema.model;

public enum Genero {
    ACAO("Ação"),
    COMEDIA("Comédia"),
    DRAMA("Drama"),
    TERROR("Terror"),
    ROMANCE("Romance"),
    FICCAO_CIENTIFICA("Ficção Científica"),
    SUSPENSE("Suspense"),
    ANIMACAO("Animação");

    private final String nome;

    Genero(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }

    public static Genero deString(String texto) {
        for (Genero g : Genero.values()){
            if (g.nome.equalsIgnoreCase(texto) || g.name().equalsIgnoreCase(texto)){
                return g;
            }
        }
        throw new IllegalArgumentException("Gênero não encontrado: "+ texto);
    }
}
