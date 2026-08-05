package br.com.cinema.model;

import br.com.cinema.exception.AssentoIndisponivelException;
import br.com.cinema.exception.DadosInvalidosException;

public class IngressoMeia extends Ingresso{

    public IngressoMeia(Sessao sessao, Assento assento) throws DadosInvalidosException, AssentoIndisponivelException {
        super(sessao, assento);
    }

    @Override
    public double calcularPrecoFinal() {
        return getSessao().getPrecoIngresso() / 2.0;
    }
}
