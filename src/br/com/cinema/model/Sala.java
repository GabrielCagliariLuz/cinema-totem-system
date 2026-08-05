package br.com.cinema.model;

import br.com.cinema.exception.DadosInvalidosException;

import java.io.Serializable;
import java.util.*;

public class Sala implements Serializable {
    private static final long serialVersionUID = 1L;
    private int numero;
    private Map<String, Assento> assentos;
    private int qtdAssentos;

    public Sala(int numero, char ultimaFileira, int assentosPadrao) throws DadosInvalidosException {
        if (numero <= 0){
            throw new DadosInvalidosException("O número da sala não pode ser negativo ou 0");
        }
        char ultimaFileiraUpper = Character.toUpperCase(ultimaFileira);
        if (ultimaFileiraUpper < 'A' || ultimaFileiraUpper > 'Z'){
            throw new DadosInvalidosException("A última fileira deve ser uma letra válida de 'A' a 'Z'.");
        }
        if (assentosPadrao <= 0){
            throw new DadosInvalidosException("Número de assentos inválido.");
        }
        this.numero = numero;
        this.assentos = new HashMap<>();
        inicializarAssentos(ultimaFileira, assentosPadrao);
    }

    public Sala(int numero) throws DadosInvalidosException{
        this(numero, 'H', 10);
    }

    private void inicializarAssentos(char ultimaFileira, int assentosPadrao) {
        for (char fileira = 'A'; fileira <= ultimaFileira; fileira++){
            boolean ehUltima = (fileira == ultimaFileira);
            int totalNaFileira = ehUltima ? (assentosPadrao + 4) : assentosPadrao;
            for (int numero = 1; numero <= totalNaFileira; numero++){
                String codigo = ""+fileira+numero;
                assentos.put(codigo, new Assento(codigo));
            }
        }
        this.qtdAssentos = assentos.size();
    }

    public int getNumero() {
        return numero;
    }

    public Map<String, Assento> getAssentos() {
        return assentos;
    }

    public int getQtdAssentos() {
        return qtdAssentos;
    }

    public Assento buscarAssento(String codigo){
        if (codigo == null) return null;
        return assentos.get(codigo.toUpperCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sala sala = (Sala) o;
        return numero == sala.numero;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(numero);
    }

    @Override
    public String toString() {
        return "Sala " + numero + " (" + qtdAssentos + " assentos)";
    }
}
