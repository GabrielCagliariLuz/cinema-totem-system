package br.com.cinema.exception;

public class AssentoInexistenteException extends RegraNegocioException{
    public AssentoInexistenteException(String codigoAssento) {
        super("O assento "+ codigoAssento +" não existe nesta sala.");
    }
}
