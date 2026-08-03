package br.com.cinema.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Sessao implements Relatavel, Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private Sala sala;
    private Filme filme;
    private LocalDateTime horario;
    private double precoIngresso;
    private Map<String, Assento> assentosSessao;
    private static int contadorId = 1;

    public Sessao(Sala sala, Filme filme, LocalDateTime horario, double precoIngresso) {
        this.id = contadorId++;
        this.sala = sala;
        this.filme = filme;
        this.horario = horario;
        this.precoIngresso = precoIngresso;
        this.assentosSessao = new HashMap<>();
        for (String codigo : sala.getAssentos().keySet()) {
            this.assentosSessao.put(codigo, new Assento(codigo));
        }
    }

    public Assento buscarAssento(String codigo){
        if (codigo == null) return null;
        return assentosSessao.get(codigo.toUpperCase());
    }

    public boolean ocuparAssento(String codigo){
        Assento assento = buscarAssento(codigo);
        if (assento != null && assento.isLivre()){
            assento.setStatusAssento(StatusAssento.OCUPADO);
            return true;
        }
        return false;
    }

    public int getQtdAssentosDisponiveis() {
        int disponiveis = 0;
        for (Assento a : assentosSessao.values()){
            if (a.isLivre()){
                disponiveis++;
            }
        }
        return disponiveis;
    }

    public int getId() {
        return id;
    }

    public Filme getFilme() {
        return filme;
    }

    public Sala getSala() {
        return sala;
    }

    public double getPrecoIngresso() {
        return precoIngresso;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public Map<String, Assento> getAssentosSessao() {
        return assentosSessao;
    }

    public void setPrecoIngresso(double precoIngresso) {
        this.precoIngresso = precoIngresso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sessao sessao = (Sessao) o;
        return id == sessao.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String gerarRelatorio() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format(
                "=== SESSÃO #%d===\nSala: %d\nFilme: %s\nHorario: %s\nPreço: %.2f\nAssentos Livres: %d/%d",
                id, sala.getNumero(), filme.getTitulo(), horario.format(formatter),
                precoIngresso, getQtdAssentosDisponiveis(), sala.getQtdAssentos()
        );
    }
}
