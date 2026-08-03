package br.com.cinema.exception;

public class DadosInvalidosException extends RegraNegocioException {
    public DadosInvalidosException(String mensagem) {
        super(mensagem);
    }
}
