package br.com.cinema.service;

import br.com.cinema.dao.CinemaDao;
import br.com.cinema.exception.AssentoIndisponivelException;
import br.com.cinema.exception.AssentoInexistenteException;
import br.com.cinema.exception.DadosInvalidosException;
import br.com.cinema.exception.SessaoConflitoException;
import br.com.cinema.model.*;

import java.util.List;

public class CinemaService {
    private Cinema cinema;
    private CinemaDao cinemaDao;

    public CinemaService() {
        this.cinemaDao = new CinemaDao();
        this.cinema = cinemaDao.carregarDados();
        if (this.cinema == null){
            this.cinema = new Cinema();
        }
    }

    public void cadastrarFilme(Filme filme) throws DadosInvalidosException {
        cinema.cadastrarFilme(filme);
        salvar();
    }

    public void cadastrarSala(Sala sala) throws DadosInvalidosException {
        cinema.cadastrarSala(sala);
        salvar();
    }

    public void adicionarSessao(Sessao sessao) throws SessaoConflitoException, DadosInvalidosException {
        cinema.adicionarSessao(sessao);
        salvar();
    }

    public Venda realizarVenda(List<Ingresso> ingressos) throws DadosInvalidosException, AssentoInexistenteException, AssentoIndisponivelException {
        Venda venda = cinema.realizarVenda(ingressos);
        salvar();
        return venda;
    }

    public List<Filme> listarFilmes(){
        return cinema.getFilmes();
    }

    public List<Sala> listarSalas(){
        return cinema.getSalas();
    }

    public List<Sessao> listarSessoes(){
        return cinema.getSessoes();
    }

    public List<Sessao> buscarSessoesPorFilme(Filme filme){
        return cinema.buscarSessoesPorFilme(filme);
    }

    public List<Venda> listarVendas(){
        return cinema.getVendas();
    }

    private void salvar(){
        cinemaDao.salvarDados(cinema);
    }
}
