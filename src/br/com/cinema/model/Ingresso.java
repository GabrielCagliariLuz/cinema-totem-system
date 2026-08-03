package br.com.cinema.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Ingresso implements Relatavel{
    private int id;
    private double valorPago;
    private Sessao sessao;
    private Assento assento;
    private LocalDateTime dataHoraCompra;
    private static int contadorId = 1;

    public Ingresso(Sessao sessao, Assento assento) {
        this.id = contadorId++;
        this.sessao = sessao;
        this.assento = assento;
        this.dataHoraCompra = LocalDateTime.now();
        this.valorPago = calcularPrecoFinal();
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
