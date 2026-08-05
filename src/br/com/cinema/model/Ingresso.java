package br.com.cinema.model;

import br.com.cinema.exception.AssentoIndisponivelException;
import br.com.cinema.exception.DadosInvalidosException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class Ingresso implements Relatavel, Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private double valorPago;
    private Sessao sessao;
    private Assento assento;
    private LocalDateTime dataHoraCompra;
    private static int contadorId = 1;

    public Ingresso(Sessao sessao, Assento assento) throws DadosInvalidosException, AssentoIndisponivelException {
        if (sessao == null){
            throw new DadosInvalidosException("A sessão não pode ser nula para emissão do ingresso.");
        }
        if (assento == null){
            throw new DadosInvalidosException("O assento não pode ser nulo para emissão do ingresso.");
        }
        if (!assento.isLivre()){
            throw new AssentoIndisponivelException(assento.getCodigo());
        }
        this.id = contadorId++;
        this.sessao = sessao;
        this.assento = assento;
        this.dataHoraCompra = LocalDateTime.now();
        this.valorPago = calcularPrecoFinal();
        if (this.valorPago <= 0){
            throw new DadosInvalidosException("O valor do ingresso deve ser maior que zero.");
        }
    }

    public abstract double calcularPrecoFinal();

    public int getId() {
        return id;
    }

    public double getValorPago() {
        return valorPago;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public Assento getAssento() {
        return assento;
    }

    public LocalDateTime getDataHoraCompra() {
        return dataHoraCompra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingresso ingresso = (Ingresso) o;
        return  Objects.equals(sessao, ingresso.sessao) &&
                Objects.equals(assento, ingresso.assento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessao, assento);
    }

    @Override
    public String gerarRelatorio() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format(
                "=== INGRESSO #%d ===\nFilme: %s\nSala: %d | Assento: %s\nHorário Sessão: %s\nValor Pago: R$ %.2f\nEmitido em: %s",
                id, sessao.getFilme().getTitulo(), sessao.getSala().getNumero(),
                assento.getCodigo(), sessao.getHorario().format(formatter),
                valorPago, dataHoraCompra.format(formatter)
        );
    }
}
