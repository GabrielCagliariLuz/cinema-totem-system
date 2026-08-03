package br.com.cinema.model;

import br.com.cinema.exception.AssentoIndisponivelException;
import br.com.cinema.exception.AssentoInexistenteException;
import br.com.cinema.exception.DadosInvalidosException;
import br.com.cinema.exception.SessaoConflitoException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cinema implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Filme> filmes;
    private List<Sala> salas;
    private List<Sessao> sessoes;
    private List<Venda> vendas;

    public Cinema() {
        this.filmes = new ArrayList<>();
        this.salas = new ArrayList<>();
        this.sessoes = new ArrayList<>();
        this.vendas = new ArrayList<>();
    }

    public void cadastrarFilme(Filme filme) throws DadosInvalidosException {
        if (filme == null) {
            throw new DadosInvalidosException("O filme não pode ser nulo.");
        }
        if (filme.getTitulo() == null || filme.getTitulo().trim().isEmpty()){
            throw new DadosInvalidosException("O titulo do filme é obrigatório.");
        }

        if (filmes.contains(filme)){
            throw new DadosInvalidosException("Filme já cadastrado no sistema.");
        }
        this.filmes.add(filme);
    }

    public void cadastrarSala(Sala sala) throws DadosInvalidosException{
        if (sala == null) {
            throw new DadosInvalidosException("A sala não pode ser nula.");
        }
        if (salas.contains(sala)){
            throw new DadosInvalidosException("Sala já cadastrada no sistema.");
        }
        this.salas.add(sala);
    }

    public void adicionarSessao(Sessao novaSessao) throws SessaoConflitoException, DadosInvalidosException {
        if (novaSessao == null){
             throw new DadosInvalidosException("A sessão não pode ser nula.");
        }
        for (Sessao s : sessoes){
            if (s.getSala().equals(novaSessao.getSala()) &&
            s.getHorario().equals(novaSessao.getHorario())){
                throw new SessaoConflitoException(
                        "A Sala "+ novaSessao.getSala().getNumero() + " dejá possui uma sessão agendada para "+ novaSessao.getHorario()
                );
            }
        }
        this.sessoes.add(novaSessao);
    }

    public Venda realizarVenda(List<Ingresso> ingressos) throws DadosInvalidosException, AssentoInexistenteException, AssentoIndisponivelException {
        if (ingressos == null || ingressos.isEmpty()){
            throw new DadosInvalidosException("A lista de ingressos para a venda não pode estar vazia.");
        }
        for (Ingresso ing : ingressos){
            ing.getSessao().ocuparAssento(ing.getAssento().getCodigo());
        }
        Venda novaVenda = new Venda();
        for (Ingresso ing : ingressos){
            novaVenda.adicionarIngresso(ing);
        }
        vendas.add(novaVenda);
        return novaVenda;
    }

    public List<Sessao> buscarSessoesPorFilme(Filme filme){
        List<Sessao> resultado = new ArrayList<>();
        for (Sessao s : sessoes){
            if (s.getFilme().equals(filme)){
                resultado.add(s);
            }
        }
        return resultado;
    }

    public Filme buscarFilmePorTitulo(String titulo){
        if (titulo == null) return null;
        for (Filme f : filmes){
            if (f.getTitulo().equalsIgnoreCase(titulo)){
                return f;
            }
        }
        return null;
    }

    public Sala buscarSalaPorNumero(int numero){
        for (Sala s : salas){
            if (s.getNumero() == numero){
                return s;
            }
        }
        return null;
    }

    public Sessao buscarSessaoPorId(int id){
        for (Sessao s : sessoes){
            if (s.getId() == id){
                return s;
            }
        }
        return null;
    }

    public List<Filme> getFilmes() {
        return filmes;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public List<Sessao> getSessoes() {
        return sessoes;
    }

    public List<Venda> getVendas() {
        return vendas;
    }
}
