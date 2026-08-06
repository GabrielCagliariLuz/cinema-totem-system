package br.com.cinema;

import br.com.cinema.controller.CinemaController;
import br.com.cinema.view.TelaPrincipal;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Inicializa a interface Swing dentro da Event Dispatch Thread (boa prática do Swing)
        SwingUtilities.invokeLater(() -> {
            CinemaController controller = new CinemaController();
            TelaPrincipal tela = new TelaPrincipal(controller);
            tela.setVisible(true);
        });
    }
}