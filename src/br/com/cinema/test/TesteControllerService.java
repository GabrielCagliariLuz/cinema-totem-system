package br.com.cinema.test;

import br.com.cinema.controller.CinemaController;
import br.com.cinema.model.*;
import br.com.cinema.service.CinemaService;

import java.time.LocalDateTime;

public class TesteControllerService {

    public static void main(String[] args) {
        System.out.println("=== INICIANDO TESTE DE INTEGRAÇÃO: CONTROLLER & SERVICE ===\n");

        try {
            // 1. O Totem liga e instancia o Controller (que puxa o Service e o DAO)
            CinemaController controller = new CinemaController();
            CinemaService service = controller.getService();

            // 2. Garante que temos dados no banco para o teste
            if (service.listarFilmes().isEmpty()) {
                System.out.println("-> Banco vazio. Cadastrando dados iniciais via Service...");
                Filme filme = new Filme("Homem-Aranha", 148, Genero.ACAO, "+12", "Filme do miranha.");
                Sala sala = new Sala(2);
                Sessao sessao = new Sessao(sala, filme, LocalDateTime.now().plusDays(1), 40.00);

                service.cadastrarFilme(filme);
                service.cadastrarSala(sala);
                service.adicionarSessao(sessao);
            }

            // 3. Simula a navegação do cliente na tela do Totem
            System.out.println("\n--- TELA 1: ESCOLHENDO SESSÃO ---");
            Sessao sessaoEscolhida = service.listarSessoes().get(0);
            System.out.println("Cliente escolheu assistir: " + sessaoEscolhida.getFilme().getTitulo());

            System.out.println("\n--- TELA 2: SELECIONANDO ASSENTOS E TIPO DE INGRESSO ---");
            Assento assento1 = sessaoEscolhida.buscarAssento("B1");
            Assento assento2 = sessaoEscolhida.buscarAssento("B2");

            if (assento1 != null && assento1.isLivre() && assento2 != null && assento2.isLivre()) {
                // Cliente clicou em adicionar ao carrinho
                Ingresso ingressoInteira = new IngressoInteira(sessaoEscolhida, assento1);
                Ingresso ingressoMeia = new IngressoMeia(sessaoEscolhida, assento2);

                controller.adicionarAoCarrinho(ingressoInteira);
                controller.adicionarAoCarrinho(ingressoMeia);

                System.out.println("✅ Ingressos adicionados ao carrinho.");
                System.out.println("Quantidade no Carrinho: " + controller.getCarrinho().size());
                System.out.printf("Valor Total no Carrinho: R$ %.2f%n", controller.getValorTotalCarrinho());

                // 4. Simula o clique no botão "Finalizar Pagamento"
                System.out.println("\n--- TELA 3: FINALIZANDO COMPRA ---");
                Venda comprovante = controller.finalizarCompra();

                System.out.println("✅ Venda processada e salva no disco pelo Service!");
                System.out.println("\n" + comprovante.gerarRelatorio());

                System.out.println("\nQuantidade no Carrinho após a venda: " + controller.getCarrinho().size() + " (Pronto para o próximo cliente)");

            } else {
                System.out.println("⚠️ Os assentos B1 e B2 já estão ocupados neste banco de dados.");
            }

        } catch (Exception e) {
            System.err.println("❌ Erro durante o fluxo do Totem: " + e.getMessage());
            e.printStackTrace();
        }
    }
}