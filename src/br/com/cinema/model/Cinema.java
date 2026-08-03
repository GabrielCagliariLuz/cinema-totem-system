package br.com.cinema.model;

import java.util.ArrayList;
import java.util.List;

public class Cinema {
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

    public void cadastrarFilme(Filme filme){
        if (filme != null && !filmes.contains(filme)){
            this.filmes.add(filme);
        }
    }

    public void cadastrarSala(Sala sala){
        if (sala != null && !salas.contains(sala)){
            this.salas.add(sala);
        }
    }

    public boolean adicionarSessao(Sessao novaSessao){
        if (novaSessao == null){
            return false;
        }
        for (Sessao s : sessoes){
            if (s.getSala().equals(novaSessao.getSala()) &&
            s.getHorario().equals(novaSessao.getHorario())){
                return false;
            }
        }
        sessoes.add(novaSessao);
        return true;
    }

    public Venda realizarVenda(List<Ingresso> ingressos){
        if (ingressos == null || ingressos.isEmpty()){
            return null;
        }
        Venda novaVenda = new Venda();
        for (Ingresso ing : ingressos){
            novaVenda.adicionarIngresso(ing);
            ing.getSessao().ocuparAssento(ing.getAssento().getCodigo());
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
