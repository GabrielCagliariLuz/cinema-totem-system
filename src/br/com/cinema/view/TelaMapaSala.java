package br.com.cinema.view;

import br.com.cinema.controller.CinemaController;
import br.com.cinema.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TelaMapaSala extends JFrame {

    private CinemaController controller;
    private Sessao sessao;
    private TelaPrincipal telaPrincipal;
    private List<Assento> assentosSelecionados;

    private JLabel lblTotal;
    private JLabel lblAssentosTexto;
    private JSpinner spinInteira;
    private JSpinner spinMeia;
    private JButton btnFinalizar;

    private final Color COLOR_BG = new Color(18, 18, 18);
    private final Color COLOR_CARD = new Color(30, 30, 30);
    private final Color COLOR_GREEN = new Color(40, 167, 69);
    private final Color COLOR_RED = new Color(220, 53, 69);
    private final Color COLOR_YELLOW = new Color(255, 193, 7);
    private final Color COLOR_BUSY = new Color(50, 50, 50);

    public TelaMapaSala(CinemaController controller, Sessao sessao, TelaPrincipal telaPrincipal) {
        this.controller = controller;
        this.sessao = sessao;
        this.telaPrincipal = telaPrincipal;
        this.assentosSelecionados = new ArrayList<>();

        configurarJanela();
        inicializarComponentes();
    }

    private void configurarJanela() {
        setTitle("CineMaxx - Seleção de Assentos e Ingressos");
        setSize(880, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);
        setLayout(new BorderLayout(15, 15));
    }

    private void inicializarComponentes() {
        // --- 1. CABEÇALHO ---
        JPanel panelInfo = new JPanel(new GridLayout(2, 1, 5, 5));
        panelInfo.setBackground(COLOR_CARD);
        panelInfo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblFilme = new JLabel(sessao.getFilme().getTitulo().toUpperCase());
        lblFilme.setFont(new Font("Arial", Font.BOLD, 20));
        lblFilme.setForeground(Color.WHITE);

        String infoSessao = String.format("Sala %02d  •  Horário: %s  •  Inteira: R$ %.2f | Meia: R$ %.2f",
                sessao.getSala().getNumero(),
                sessao.getHorario().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")),
                sessao.getPrecoIngresso(),
                sessao.getPrecoIngresso() / 2);
        JLabel lblDet = new JLabel(infoSessao);
        lblDet.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDet.setForeground(Color.LIGHT_GRAY);

        panelInfo.add(lblFilme);
        panelInfo.add(lblDet);
        add(panelInfo, BorderLayout.NORTH);

        // --- 2. PAINEL CENTRAL (TELA + GRADE) ---
        JPanel panelCenter = new JPanel(new BorderLayout(15, 15));
        panelCenter.setBackground(COLOR_BG);

        JLabel lblTela = new JLabel("--- TELA DE CINEMA ---", SwingConstants.CENTER);
        lblTela.setFont(new Font("Arial", Font.BOLD, 14));
        lblTela.setForeground(Color.CYAN);
        lblTela.setOpaque(true);
        lblTela.setBackground(COLOR_CARD);
        lblTela.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        panelCenter.add(lblTela, BorderLayout.NORTH);

        JPanel panelGrade = new JPanel(new GridBagLayout());
        panelGrade.setBackground(COLOR_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);

        char[] fileiras = {'A', 'B', 'C', 'D'};
        int colunas = 6;

        for (int r = 0; r < fileiras.length; r++) {
            char fileira = fileiras[r];

            JLabel lblFila = new JLabel(String.valueOf(fileira), SwingConstants.CENTER);
            lblFila.setFont(new Font("Arial", Font.BOLD, 16));
            lblFila.setForeground(Color.WHITE);

            gbc.gridx = 0;
            gbc.gridy = r;
            panelGrade.add(lblFila, gbc);

            for (int c = 1; c <= colunas; c++) {
                String codigo = fileira + String.valueOf(c);
                Assento assento = sessao.buscarAssento(codigo);

                JButton btnAssento = new JButton(codigo);
                btnAssento.setPreferredSize(new Dimension(45, 45));
                btnAssento.setFont(new Font("Arial", Font.BOLD, 12));
                btnAssento.setFocusPainted(false);
                btnAssento.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

                if (assento != null && assento.isLivre()) {
                    btnAssento.setBackground(COLOR_GREEN);
                    btnAssento.setForeground(Color.WHITE);
                    btnAssento.addActionListener(e -> alternarSelecaoAssento(assento, btnAssento));
                } else {
                    btnAssento.setText("X");
                    btnAssento.setBackground(COLOR_BUSY);
                    btnAssento.setForeground(Color.DARK_GRAY);
                    btnAssento.setEnabled(false);
                }

                gbc.gridx = c;
                gbc.gridy = r;
                panelGrade.add(btnAssento, gbc);
            }
        }

        panelCenter.add(panelGrade, BorderLayout.CENTER);

        JPanel panelLegenda = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        panelLegenda.setBackground(COLOR_BG);
        panelLegenda.add(criarItemLegenda("Livre", COLOR_GREEN));
        panelLegenda.add(criarItemLegenda("Ocupado", COLOR_BUSY));
        panelLegenda.add(criarItemLegenda("Selecionado", COLOR_YELLOW));

        panelCenter.add(panelLegenda, BorderLayout.SOUTH);
        add(panelCenter, BorderLayout.CENTER);

        // --- 3. RODAPÉ ---
        JPanel panelFooter = new JPanel(new BorderLayout(10, 10));
        panelFooter.setBackground(COLOR_CARD);
        panelFooter.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelTiposIngresso = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        panelTiposIngresso.setBackground(COLOR_CARD);

        lblAssentosTexto = new JLabel("Assentos: 0 selecionado(s)");
        lblAssentosTexto.setFont(new Font("Arial", Font.BOLD, 14));
        lblAssentosTexto.setForeground(Color.WHITE);

        JLabel lblInteira = new JLabel("Inteira:");
        lblInteira.setForeground(Color.LIGHT_GRAY);
        spinInteira = new JSpinner(new SpinnerNumberModel(0, 0, 24, 1));
        spinInteira.addChangeListener(e -> recalcularValoresComSpinners());

        JLabel lblMeia = new JLabel("Meia:");
        lblMeia.setForeground(Color.LIGHT_GRAY);
        spinMeia = new JSpinner(new SpinnerNumberModel(0, 0, 24, 1));
        spinMeia.addChangeListener(e -> recalcularValoresComSpinners());

        panelTiposIngresso.add(lblAssentosTexto);
        panelTiposIngresso.add(Box.createHorizontalStrut(10));
        panelTiposIngresso.add(lblInteira);
        panelTiposIngresso.add(spinInteira);
        panelTiposIngresso.add(lblMeia);
        panelTiposIngresso.add(spinMeia);

        JPanel panelAcao = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        panelAcao.setBackground(COLOR_CARD);

        lblTotal = new JLabel("Total: R$ 0,00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotal.setForeground(COLOR_GREEN);

        JButton btnVoltar = new JButton("VOLTAR");
        btnVoltar.setBackground(COLOR_RED);
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.setFocusPainted(false);
        btnVoltar.addActionListener(e -> dispose());

        btnFinalizar = new JButton("FINALIZAR COMPRA");
        btnFinalizar.setBackground(COLOR_GREEN);
        btnFinalizar.setForeground(Color.WHITE);
        btnFinalizar.setFont(new Font("Arial", Font.BOLD, 14));
        btnFinalizar.setFocusPainted(false);
        btnFinalizar.setEnabled(false);
        btnFinalizar.addActionListener(e -> processarCompra());

        panelAcao.add(lblTotal);
        panelAcao.add(btnVoltar);
        panelAcao.add(btnFinalizar);

        panelFooter.add(panelTiposIngresso, BorderLayout.NORTH);
        panelFooter.add(panelAcao, BorderLayout.SOUTH);

        add(panelFooter, BorderLayout.SOUTH);
    }

    private JPanel criarItemLegenda(String texto, Color cor) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        p.setBackground(COLOR_BG);

        JLabel box = new JLabel("   ");
        box.setOpaque(true);
        box.setBackground(cor);
        box.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));

        p.add(box);
        p.add(lbl);
        return p;
    }

    private void alternarSelecaoAssento(Assento assento, JButton btn) {
        if (assentosSelecionados.contains(assento)) {
            assentosSelecionados.remove(assento);
            btn.setBackground(COLOR_GREEN);
            btn.setForeground(Color.WHITE);
        } else {
            assentosSelecionados.add(assento);
            btn.setBackground(COLOR_YELLOW);
            btn.setForeground(Color.BLACK);
        }

        spinInteira.setValue(assentosSelecionados.size());
        spinMeia.setValue(0);

        atualizarResumo();
    }

    private void recalcularValoresComSpinners() {
        int qtdInteira = (int) spinInteira.getValue();
        int qtdMeia = (int) spinMeia.getValue();
        int totalAssentos = assentosSelecionados.size();

        if (qtdInteira + qtdMeia != totalAssentos && totalAssentos > 0) {
            spinInteira.setValue(Math.max(0, totalAssentos - qtdMeia));
        }

        atualizarResumo();
    }

    private void atualizarResumo() {
        int totalAssentos = assentosSelecionados.size();

        if (totalAssentos == 0) {
            lblAssentosTexto.setText("Assentos: 0 selecionado(s)");
            lblTotal.setText("Total: R$ 0,00");
            spinInteira.setValue(0);
            spinMeia.setValue(0);
            btnFinalizar.setEnabled(false);
        } else {
            lblAssentosTexto.setText("Assentos (" + totalAssentos + "): " + getCodigosAssentos());

            int qtdInteiras = (int) spinInteira.getValue();
            int qtdMeias = (int) spinMeia.getValue();

            double valorTotal = (qtdInteiras * sessao.getPrecoIngresso()) + (qtdMeias * (sessao.getPrecoIngresso() / 2));
            lblTotal.setText(String.format("Total: R$ %.2f", valorTotal));
            btnFinalizar.setEnabled(true);
        }
    }

    private String getCodigosAssentos() {
        StringBuilder sb = new StringBuilder();
        for (Assento a : assentosSelecionados) {
            sb.append(a.getCodigo()).append(" ");
        }
        return sb.toString();
    }

    private void processarCompra() {
        try {
            controller.limparCarrinho();

            int qtdInteiras = (int) spinInteira.getValue();
            int indexAssento = 0;

            for (Assento a : assentosSelecionados) {
                Ingresso ing;
                if (indexAssento < qtdInteiras) {
                    ing = new IngressoInteira(sessao, a);
                } else {
                    ing = new IngressoMeia(sessao, a);
                }
                controller.adicionarAoCarrinho(ing);
                indexAssento++;
            }

            Venda venda = controller.finalizarCompra();

            if (telaPrincipal != null) {
                telaPrincipal.atualizarValorCarrinho();
                telaPrincipal.carregarFilmes();
            }

            JOptionPane.showMessageDialog(this,
                    "COMPRA REALIZADA COM SUCESSO!\n\n" + venda.gerarRelatorio(),
                    "Comprovante CineMaxx",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao finalizar compra: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}