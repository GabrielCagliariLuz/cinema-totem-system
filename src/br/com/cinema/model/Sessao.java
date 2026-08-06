package br.com.cinema.model;

import br.com.cinema.exception.AssentoIndisponivelException;
import br.com.cinema.exception.AssentoInexistenteException;
import br.com.cinema.exception.DadosInvalidosException;

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
    private Map<String, Assento> mapaAssentos;
    private static int contadorId = 1;

    public Sessao(Sala sala, Filme filme, LocalDateTime horario, double precoIngresso) throws DadosInvalidosException {
        if (sala == null){
            throw new DadosInvalidosException("A sessão tem que ter uma sala.");
        }
        if (filme == null){
            throw new DadosInvalidosException("A sessão tem que ter um filme.");
        }
        if (horario == null) {
            throw new DadosInvalidosException("O horário da sessão é obrigatório.");
        }
        if (horario.isBefore(LocalDateTime.now())){
            throw new DadosInvalidosException("Horário da sessão inválido.");
        }
        if (precoIngresso <= 0){
            throw new DadosInvalidosException("O preço do ingresso deve ser maior que zero.");
        }

        this.id = contadorId++;
        this.sala = sala;
        this.filme = filme;
        this.horario = horario;
        this.precoIngresso = precoIngresso;
        this.mapaAssentos = clonarAssentos(sala);
    }

    private Map<String, Assento> clonarAssentos(Sala sala){
        Map<String, Assento> novosAssentos = new HashMap<>();
        if (sala != null && sala.getAssentos() != null){
            for (Map.Entry<String, Assento> entry : sala.getAssentos().entrySet()) {
                novosAssentos.put(entry.getKey(), new Assento(entry.getValue().getCodigo()));
            }
        }
        return novosAssentos;
    }

    private Object readResolve(){
        if (this.mapaAssentos == null){
            this.mapaAssentos = clonarAssentos(this.sala);
        }
        return this;
    }

    public Assento buscarAssento(String codigo){
        if (codigo == null) return null;
        if (this.mapaAssentos == null){
            this.mapaAssentos = clonarAssentos(this.sala);
        }
        return mapaAssentos.get(codigo.toUpperCase());
    }

    public void ocuparAssento(String codigo) throws AssentoInexistenteException, AssentoIndisponivelException {
        Assento assento = buscarAssento(codigo);
        if (assento == null){
            throw new AssentoInexistenteException(codigo);
        }
        if (!assento.isLivre()){
            throw new AssentoIndisponivelException(codigo);
        }
        assento.setStatusAssento(StatusAssento.OCUPADO);
    }

    public int getQtdAssentosDisponiveis() {
        if (this.mapaAssentos == null){
            this.mapaAssentos = clonarAssentos(this.sala);
        }
        int disponiveis = 0;
        for (Assento a : mapaAssentos.values()){
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
        if (this.mapaAssentos == null){
            this.mapaAssentos = clonarAssentos(this.sala);
        }
        return mapaAssentos;
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
    public String toString() {
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM HH:mm");
        return String.format("Sala %d - %s (R$ %.2f)",
                sala.getNumero(), horario.format(formatter), precoIngresso);
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
