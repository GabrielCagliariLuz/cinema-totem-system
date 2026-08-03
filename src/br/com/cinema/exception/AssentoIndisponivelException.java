package br.com.cinema.exception;

public class AssentoIndisponivelException extends RuntimeException {
    public AssentoIndisponivelException(String codigoAssento) {
        super("O assento "+ codigoAssento + " já está ocupado ou indisponível.");
    }
}
