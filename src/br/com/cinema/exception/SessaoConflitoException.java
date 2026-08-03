package br.com.cinema.exception;

public class SessaoConflitoException extends RegraNegocioException{
    public SessaoConflitoException(String mensagem) {
        super(mensagem);
    }
}
