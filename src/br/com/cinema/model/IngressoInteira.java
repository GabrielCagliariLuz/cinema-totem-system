package br.com.cinema.model;

public class IngressoInteira extends Ingresso{

    public IngressoInteira(Sessao sessao, Assento assento) {
        super(sessao, assento);
    }

    @Override
    public double calcularPrecoFinal() {
        return getSessao().getPrecoIngresso();
    }
}
