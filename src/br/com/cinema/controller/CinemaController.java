package br.com.cinema.controller;

import br.com.cinema.exception.AssentoIndisponivelException;
import br.com.cinema.exception.AssentoInexistenteException;
import br.com.cinema.exception.DadosInvalidosException;
import br.com.cinema.model.Ingresso;
import br.com.cinema.model.Venda;
import br.com.cinema.service.CinemaService;

import java.util.ArrayList;
import java.util.List;

public class CinemaController {
    private CinemaService service;
    private List<Ingresso> carrinho;

    public CinemaController() {
        this.service = new CinemaService();
        this.carrinho = new ArrayList<>();
    }

    public void adicionarAoCarrinho(Ingresso ingresso) throws DadosInvalidosException {
        if (ingresso == null){
            throw new DadosInvalidosException("O ingresso não pode ser nulo.");
        }
        this.carrinho.add(ingresso);
    }

    public void removerDoCarrinho(Ingresso ingresso) {
        this.carrinho.remove(ingresso);
    }

    public void limparCarrinho(){
        this.carrinho.clear();
    }

    public List<Ingresso> getCarrinho() {
        return new ArrayList<>(carrinho);
    }

    public double getValorTotalCarrinho() {
        double total = 0.0;
        for (Ingresso ingresso : carrinho){
            total += ingresso.calcularPrecoFinal();
        }
        return total;
    }

    public Venda finalizarCompra() throws DadosInvalidosException, AssentoIndisponivelException, AssentoInexistenteException {
        if (carrinho.isEmpty()){
            throw new DadosInvalidosException("Não é possível finalizar a compra com o carrinho vazio.");
        }
        Venda vendaRealizada = service.realizarVenda(carrinho);
        limparCarrinho();
        return vendaRealizada;
    }

    public CinemaService getService(){
        return service;
    }
}
