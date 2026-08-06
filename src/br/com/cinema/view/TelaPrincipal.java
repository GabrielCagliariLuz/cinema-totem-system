package br.com.cinema.view;

import br.com.cinema.controller.CinemaController;
import br.com.cinema.model.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class TelaPrincipal extends JFrame {

    private CinemaController controller;

    private DefaultListModel<Filme> modelFilmes;
    private JList<Filme> listaFilmes;
    private DefaultListModel<Sessao> modelSessoes;
    private JList<Sessao> listaSessoes;

    private JLabel lblPoster;
    private JLabel lblTituloFilme;
    private JLabel lblInfoFilme;
    private JTextArea txtSinopse;
    private JLabel lblCarrinhoTotal;
    private JButton btnAvancar;

    private final Color COLOR_BG = new Color(18, 18, 18);
    private final Color COLOR_CARD = new Color(30, 30, 30);
    private final Color COLOR_RED = new Color(220, 53, 69);
    private final Color COLOR_GREEN = new Color(40, 167, 69);
    private final Color COLOR_TEXT_MUTED = new Color(170, 170, 170);

    public TelaPrincipal(CinemaController controller) {
        this.controller = controller;
        garantirFilmesIniciais();
        configurarJanela();
        inicializarComponentes();
        carregarFilmes();
    }

    private void garantirFilmesIniciais() {
        if (controller.getService().listarFilmes().isEmpty()) {
            try {
                Filme f1 = new Filme("Batman", 176, Genero.ACAO, "+14", "O Batman investiga a corrupção em Gotham City enquanto persegue o Charada.");
                Filme f2 = new Filme("Divertida Mente 2", 96, Genero.ANIMACAO, "Livre", "Riley entra na adolescência e novas emoções chegam ao centro de controle.");
                Filme f3 = new Filme("Duna: Parte 2", 166, Genero.FICCAO_CIENTIFICA, "+14", "Paul Atreides se une a Chani e aos Fremen em busca de vingança.");
                Filme f4 = new Filme("Homem-Aranha", 148, Genero.ACAO, "+12", "Peter Parker lida com as consequências da revelação de sua identidade.");

                Sala sala1 = new Sala(1);
                Sala sala2 = new Sala(2);

                controller.getService().cadastrarFilme(f1);
                controller.getService().cadastrarFilme(f2);
                controller.getService().cadastrarFilme(f3);
                controller.getService().cadastrarFilme(f4);

                controller.getService().cadastrarSala(sala1);
                controller.getService().cadastrarSala(sala2);

                controller.getService().adicionarSessao(new Sessao(sala1, f1, LocalDateTime.now().plusHours(2), 35.00));
                controller.getService().adicionarSessao(new Sessao(sala2, f1, LocalDateTime.now().plusHours(5), 35.00));
                controller.getService().adicionarSessao(new Sessao(sala1, f2, LocalDateTime.now().plusHours(3), 28.00));
                controller.getService().adicionarSessao(new Sessao(sala2, f3, LocalDateTime.now().plusHours(4), 40.00));
                controller.getService().adicionarSessao(new Sessao(sala1, f4, LocalDateTime.now().plusHours(1), 30.00));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void configurarJanela() {
        setTitle("CineMaxx - Seleção de Filmes");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);
        setLayout(new BorderLayout(15, 15));
    }

    private void inicializarComponentes() {
        // --- 1. CABEÇALHO ---
        JPanel panelHeader = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelHeader.setBackground(COLOR_CARD);
        panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JLabel lblLogo = new JLabel("CINE MAXX  |  SELEÇÃO DE FILMES");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 22));
        lblLogo.setForeground(COLOR_RED);
        panelHeader.add(lblLogo);
        add(panelHeader, BorderLayout.NORTH);

        // --- 2. LISTA DE FILMES ---
        modelFilmes = new DefaultListModel<>();
        listaFilmes = new JList<>(modelFilmes);
        listaFilmes.setBackground(COLOR_CARD);
        listaFilmes.setForeground(Color.WHITE);
        listaFilmes.setSelectionBackground(COLOR_RED);
        listaFilmes.setSelectionForeground(Color.WHITE);
        listaFilmes.setFont(new Font("Arial", Font.BOLD, 15));
        listaFilmes.setFixedCellHeight(45);

        listaFilmes.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                atualizarDetalhesFilme(listaFilmes.getSelectedValue());
            }
        });

        JScrollPane scrollFilmes = new JScrollPane(listaFilmes);
        scrollFilmes.setPreferredSize(new Dimension(280, 0));
        scrollFilmes.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_RED), " FILMES EM CARTAZ ",
                0, 0, new Font("Arial", Font.BOLD, 12), COLOR_RED));
        scrollFilmes.getViewport().setBackground(COLOR_CARD);

        add(scrollFilmes, BorderLayout.WEST);

        // --- 3. PAINEL CENTRAL COM POSTER E DETALHES ---
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.setBackground(COLOR_BG);

        JPanel panelCard = new JPanel(new BorderLayout(15, 15));
        panelCard.setBackground(COLOR_CARD);
        panelCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        lblPoster = new JLabel();
        lblPoster.setPreferredSize(new Dimension(140, 200));
        lblPoster.setOpaque(true);
        lblPoster.setBackground(new Color(45, 45, 45));
        lblPoster.setHorizontalAlignment(SwingConstants.CENTER);
        lblPoster.setForeground(Color.GRAY);
        lblPoster.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelCard.add(lblPoster, BorderLayout.WEST);

        JPanel panelTextos = new JPanel(new BorderLayout(5, 5));
        panelTextos.setBackground(COLOR_CARD);

        lblTituloFilme = new JLabel("Selecione um filme");
        lblTituloFilme.setFont(new Font("Arial", Font.BOLD, 22));
        lblTituloFilme.setForeground(Color.WHITE);

        lblInfoFilme = new JLabel("Gênero: - | Duração: - | Classificação: -");
        lblInfoFilme.setFont(new Font("Arial", Font.PLAIN, 13));
        lblInfoFilme.setForeground(COLOR_TEXT_MUTED);

        txtSinopse = new JTextArea(5, 20);
        txtSinopse.setWrapStyleWord(true);
        txtSinopse.setLineWrap(true);
        txtSinopse.setEditable(false);
        txtSinopse.setBackground(COLOR_CARD);
        txtSinopse.setForeground(Color.LIGHT_GRAY);
        txtSinopse.setFont(new Font("Arial", Font.ITALIC, 13));

        JPanel panelTopText = new JPanel(new GridLayout(2, 1, 2, 2));
        panelTopText.setBackground(COLOR_CARD);
        panelTopText.add(lblTituloFilme);
        panelTopText.add(lblInfoFilme);

        panelTextos.add(panelTopText, BorderLayout.NORTH);
        panelTextos.add(txtSinopse, BorderLayout.CENTER);

        panelCard.add(panelTextos, BorderLayout.CENTER);

        modelSessoes = new DefaultListModel<>();
        listaSessoes = new JList<>(modelSessoes);
        listaSessoes.setBackground(COLOR_CARD);
        listaSessoes.setForeground(Color.WHITE);
        listaSessoes.setSelectionBackground(COLOR_GREEN);
        listaSessoes.setFont(new Font("Arial", Font.PLAIN, 14));
        listaSessoes.setFixedCellHeight(35);

        listaSessoes.addListSelectionListener(e -> {
            btnAvancar.setEnabled(listaSessoes.getSelectedValue() != null);
        });

        JScrollPane scrollSessoes = new JScrollPane(listaSessoes);
        scrollSessoes.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_GREEN), " SESSÕES DISPONÍVEIS ",
                0, 0, new Font("Arial", Font.BOLD, 12), COLOR_GREEN));
        scrollSessoes.getViewport().setBackground(COLOR_CARD);

        panelCentral.add(panelCard, BorderLayout.NORTH);
        panelCentral.add(scrollSessoes, BorderLayout.CENTER);

        add(panelCentral, BorderLayout.CENTER);

        // --- 4. RODAPÉ ---
        JPanel panelFooter = new JPanel(new BorderLayout(10, 10));
        panelFooter.setBackground(COLOR_CARD);
        panelFooter.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        lblCarrinhoTotal = new JLabel(String.format("CARRINHO: R$ %.2f", controller.getValorTotalCarrinho()));
        lblCarrinhoTotal.setFont(new Font("Arial", Font.BOLD, 16));
        lblCarrinhoTotal.setForeground(COLOR_GREEN);

        btnAvancar = new JButton("SELECIONAR ASSENTOS ->");
        btnAvancar.setBackground(COLOR_GREEN);
        btnAvancar.setForeground(Color.WHITE);
        btnAvancar.setFont(new Font("Arial", Font.BOLD, 14));
        btnAvancar.setFocusPainted(false);
        btnAvancar.setEnabled(false);
        btnAvancar.addActionListener(e -> abrirTelaAssentos());

        panelFooter.add(lblCarrinhoTotal, BorderLayout.WEST);
        panelFooter.add(btnAvancar, BorderLayout.EAST);

        add(panelFooter, BorderLayout.SOUTH);
    }

    public void carregarFilmes() {
        modelFilmes.clear();
        for (Filme f : controller.getService().listarFilmes()) {
            modelFilmes.addElement(f);
        }
        if (!modelFilmes.isEmpty()) {
            listaFilmes.setSelectedIndex(0);
        }
    }

    private void atualizarDetalhesFilme(Filme filme) {
        if (filme == null) return;

        lblTituloFilme.setText(filme.getTitulo().toUpperCase());
        lblInfoFilme.setText(String.format("Gênero: %s  •  Duração: %d min  •  Classificação: %s",
                filme.getGenero(), filme.getDuracaoEmMinutos(), filme.getClassificacaoEtaria()));
        txtSinopse.setText(filme.getSinopse());

        String nomeArquivo = switch (filme.getTitulo().toLowerCase().trim()) {
            case "batman" -> "poster_batman.jpg";
            case "divertida mente 2" -> "poster_divertidamente.jpg";
            case "duna: parte 2", "duna" -> "poster_duna.jpeg";
            case "homem-aranha", "homem aranha" -> "poster_homem_aranha.jpg";
            default -> "";
        };

        String caminhoImagem = "src/resources/" + nomeArquivo;
        ImageIcon icon = new ImageIcon(caminhoImagem);

        if (!nomeArquivo.isEmpty() && icon.getIconWidth() > 0) {
            Image img = icon.getImage().getScaledInstance(140, 200, Image.SCALE_SMOOTH);
            lblPoster.setIcon(new ImageIcon(img));
            lblPoster.setText("");
        } else {
            lblPoster.setIcon(null);
            lblPoster.setText("<html><center>SEM POSTER</center></html>");
        }

        modelSessoes.clear();
        for (Sessao s : controller.getService().buscarSessoesPorFilme(filme)) {
            modelSessoes.addElement(s);
        }
    }

    public void atualizarValorCarrinho() {
        lblCarrinhoTotal.setText(String.format("CARRINHO: R$ %.2f", controller.getValorTotalCarrinho()));
    }

    private void abrirTelaAssentos() {
        Sessao sessaoSelecionada = listaSessoes.getSelectedValue();
        if (sessaoSelecionada != null) {
            TelaMapaSala telaMapa = new TelaMapaSala(controller, sessaoSelecionada, this);
            telaMapa.setVisible(true);
        }
    }
}