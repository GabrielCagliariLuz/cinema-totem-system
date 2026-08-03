package br.com.cinema.model;

public class IngressoMeia extends Ingresso{

    public IngressoMeia(Sessao sessao, Assento assento) {
        super(sessao, assento);
    }

    @Override
    public double calcularPrecoFinal() {
        return getSessao().getPrecoIngresso() / 2.0;
    }
}
