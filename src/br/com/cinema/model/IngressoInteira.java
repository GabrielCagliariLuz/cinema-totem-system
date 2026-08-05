package br.com.cinema.model;

import br.com.cinema.exception.AssentoIndisponivelException;
import br.com.cinema.exception.DadosInvalidosException;

public class IngressoInteira extends Ingresso{

    public IngressoInteira(Sessao sessao, Assento assento) throws DadosInvalidosException, AssentoIndisponivelException {
        super(sessao, assento);
    }

    @Override
    public double calcularPrecoFinal() {
        return getSessao().getPrecoIngresso();
    }
}
