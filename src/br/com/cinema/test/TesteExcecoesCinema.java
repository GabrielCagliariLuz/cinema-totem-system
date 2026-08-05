package br.com.cinema.test;

import br.com.cinema.exception.AssentoIndisponivelException;
import br.com.cinema.exception.AssentoInexistenteException;
import br.com.cinema.exception.DadosInvalidosException;
import br.com.cinema.exception.SessaoConflitoException;
import br.com.cinema.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TesteExcecoesCinema {

    public static void main(String[] args) {
        System.out.println("=== INICIANDO TESTES DE EXCEÇÕES E REGRAS DE NEGÓCIO ===\n");

        testarSalaInvalida();
        testarSessaoNoPassado();
        testarConflitoDeSessao();
        testarOcupacaoDeAssentoInexistente();
        testarOcupacaoDeAssentoJaOcupado();

        System.out.println("\n=== TODOS OS TESTES DE EXCEÇÕES PASSARAM COM SUCESSO! ===");
    }

    private static void testarSalaInvalida() {
        try {
            System.out.print("1. Testando criação de sala com número negativo (-1)... ");
            new Sala(-1);
            System.err.println("❌ FALHA: Deveria ter lançado DadosInvalidosException!");
        } catch (DadosInvalidosException e) {
            System.out.println("✅ OK: " + e.getMessage());
        }
    }

    private static void testarSessaoNoPassado() {
        try {
            System.out.print("2. Testando criação de sessão no passado... ");
            Sala sala = new Sala(1);
            Filme filme = new Filme("Matrix", 136, Genero.FICCAO_CIENTIFICA, "+14", "Ficção científica.");
            new Sessao(sala, filme, LocalDateTime.now().minusDays(1), 25.0);
            System.err.println("❌ FALHA: Deveria ter lançado DadosInvalidosException!");
        } catch (DadosInvalidosException e) {
            System.out.println("✅ OK: " + e.getMessage());
        }
    }

    private static void testarConflitoDeSessao() {
        try {
            System.out.print("3. Testando cadastro de duas sessões no mesmo horário e sala... ");
            Cinema cinema = new Cinema();
            Sala sala = new Sala(1);
            Filme filme = new Filme("Batman", 175, Genero.ACAO, "+12", "Ação");
            LocalDateTime horario = LocalDateTime.now().plusHours(3);

            Sessao s1 = new Sessao(sala, filme, horario, 30.0);
            Sessao s2 = new Sessao(sala, filme, horario, 30.0);

            cinema.cadastrarSala(sala);
            cinema.cadastrarFilme(filme);
            cinema.adicionarSessao(s1);
            cinema.adicionarSessao(s2); // Deve lançar SessaoConflitoException

            System.err.println("❌ FALHA: Deveria ter lançado SessaoConflitoException!");
        } catch (SessaoConflitoException e) {
            System.out.println("✅ OK: " + e.getMessage());
        } catch (DadosInvalidosException e) {
            System.err.println("❌ Erro inesperado: " + e.getMessage());
        }
    }

    private static void testarOcupacaoDeAssentoInexistente() {
        try {
            System.out.print("4. Testando ocupação de assento inexistente (Z99)... ");
            Sala sala = new Sala(1);
            Filme filme = new Filme("Batman", 175, Genero.ACAO, "+12", "Ação");
            Sessao sessao = new Sessao(sala, filme, LocalDateTime.now().plusHours(2), 30.0);

            sessao.ocuparAssento("Z99");
            System.err.println("❌ FALHA: Deveria ter lançado AssentoInexistenteException!");
        } catch (AssentoInexistenteException e) {
            System.out.println("✅ OK: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Erro inesperado: " + e.getMessage());
        }
    }

    private static void testarOcupacaoDeAssentoJaOcupado() {
        try {
            System.out.print("5. Testando compra dupla do mesmo assento (A1)... ");
            Cinema cinema = new Cinema();
            Sala sala = new Sala(1);
            Filme filme = new Filme("Batman", 175, Genero.ACAO, "+12", "Ação");
            Sessao sessao = new Sessao(sala, filme, LocalDateTime.now().plusHours(2), 30.0);

            cinema.cadastrarSala(sala);
            cinema.cadastrarFilme(filme);
            cinema.adicionarSessao(sessao);

            Assento a1 = sessao.buscarAssento("A1");
            Ingresso ing1 = new IngressoInteira(sessao, a1);
            Ingresso ing2 = new IngressoInteira(sessao, a1);

            List<Ingresso> c1 = new ArrayList<>();
            c1.add(ing1);
            cinema.realizarVenda(c1); // Vende A1 com sucesso

            List<Ingresso> c2 = new ArrayList<>();
            c2.add(ing2);
            cinema.realizarVenda(c2); // Deve falhar pois A1 já está ocupado

            System.err.println("❌ FALHA: Deveria ter lançado AssentoIndisponivelException!");
        } catch (AssentoIndisponivelException e) {
            System.out.println("✅ OK: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Erro inesperado: " + e.getMessage());
        }
    }
}