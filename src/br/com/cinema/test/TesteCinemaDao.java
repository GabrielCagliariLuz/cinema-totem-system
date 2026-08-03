package br.com.cinema.test;

import br.com.cinema.dao.CinemaDao;
import br.com.cinema.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TesteCinemaDao {
    public static void main(String[] args) {
        CinemaDao cinemaDao = new CinemaDao();
        System.out.println("=== 1. CARREGANDO DADOS DO DISCO ===");
        Cinema cinema = cinemaDao.carregarDados();
        if (cinema.getFilmes().isEmpty()){
            System.out.println("-> Nenhum dado encontrado. Inicializando massa de teste...");

            // Instancie com os construtores da sua classe Filme/Sala/Sessao
            Filme filme1 = new Filme("Batman", 175, Genero.ACAO, "+12", "Em seu segundo ano de combate ao crime, Batman descobre a corrupção em Gotham City.");
            Sala sala1 = new Sala(1);
            Sessao sessao1 = new Sessao(sala1, filme1,LocalDateTime.now().plusHours(2), 30.00);

            cinema.cadastrarFilme(filme1);
            cinema.cadastrarSala(sala1);
            cinema.adicionarSessao(sessao1);
        } else {
            System.out.println("-> Dados recuperados com sucesso do arquivo cinema_dados.dat!");
        }
        exibirRelatorio(cinema);

        System.out.println("\n=== 2. SIMULANDO COMPRA DE INGRESSO NO TOTEM ===");
        if (!cinema.getSessoes().isEmpty()){
            Sessao sessaoSelecionada = cinema.getSessoes().get(0);
            Assento assentoA1 = sessaoSelecionada.buscarAssento("A1");
            if (assentoA1 != null && assentoA1.isLivre()){
                Ingresso ingresso = new IngressoInteira(sessaoSelecionada, assentoA1);
                List<Ingresso> carrinho = new ArrayList<>();
                carrinho.add(ingresso);
                Venda venda = cinema.realizarVenda(carrinho);
                if (venda != null){
                    System.out.println("✅ Venda realizada com sucesso! Ingresso do assento A1 emitido.");
                }
            } else {
                System.out.println("⚠️ O assento A1 já está ocupado ou não existe.");
            }
        }
        exibirRelatorio(cinema);
        System.out.println("\n=== 3. SALVANDO ESTADO VIA SERIALIZABLE ===");
        cinemaDao.salvarDados(cinema);
        System.out.println("Teste concluído!");


    }

    private static void exibirRelatorio(Cinema cinema) {
        System.out.println("---------------------------------");
        System.out.println("• Filmes:   " + cinema.getFilmes().size());
        System.out.println("• Salas:    " + cinema.getSalas().size());
        System.out.println("• Sessões:  " + cinema.getSessoes().size());
        System.out.println("• Vendas:   " + cinema.getVendas().size());

        if (!cinema.getSessoes().isEmpty()) {
            Sessao s = cinema.getSessoes().get(0);
            System.out.println("• Assentos Livres na Sessão #1: " + s.getQtdAssentosDisponiveis() + "/" + s.getSala().getQtdAssentos());
        }
        System.out.println("---------------------------------");
    }
}
