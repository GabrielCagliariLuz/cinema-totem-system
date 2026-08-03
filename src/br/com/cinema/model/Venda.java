package br.com.cinema.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Venda implements Relatavel, Serializable {
    private static final long serialVersionUID = 1L;
    private int codigo;
    private List<Ingresso> ingressos;
    private LocalDateTime dataHora;
    private static int contadorId = 1;

    public Venda() {
        this.codigo = contadorId++;
        this.ingressos = new ArrayList<>();
        this.dataHora = LocalDateTime.now();
    }

    public void adicionarIngresso(Ingresso ingresso){
        this.ingressos.add(ingresso);
    }

    public void removerIngresso(Ingresso ingresso){
        this.ingressos.remove(ingresso);
    }

    public double getValorTotal(){
        double valor = 0.0;
        for (Ingresso ing: ingressos){
            valor += ing.calcularPrecoFinal();
        }
        return valor;
    }

    public int getCodigo() {
        return codigo;
    }

    public List<Ingresso> getIngressos() {
        return ingressos;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venda venda = (Venda) o;
        return codigo == venda.codigo;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(codigo);
    }

    @Override
    public String gerarRelatorio() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("=== COMPROVANTE DA VENDA #%d ===%n", codigo));
        sb.append(String.format("Data/Hora: %s%n", dataHora.format(formatter)));
        sb.append("Ingressos:%n");
        for (Ingresso ing: ingressos){
            sb.append(String.format(" - %s | Assento: %s  R$ %.2f%n",
                    ing.getSessao().getFilme().getTitulo(),
                    ing.getAssento().getCodigo(),
                    ing.calcularPrecoFinal()));
        }
        sb.append(String.format("Total Pago: R$ %.2f", getValorTotal()));
        return sb.toString();
    }
}
