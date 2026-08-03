package br.com.cinema.dao;

import br.com.cinema.model.Cinema;

import java.io.*;

public class CinemaDao {
    private static final String NOME_ARQUIVO = "cinema_dados.dat";

    public void salvarDados(Cinema cinema){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))){
            oos.writeObject(cinema);
            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) {
            System.err.println("Eroo ao salvar os dados: "+ e.getMessage());
        }
    }

    public Cinema carregarDados() {
        File arquivo = new File(NOME_ARQUIVO);
        if (!arquivo.exists()){
            return new Cinema();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))){
            return (Cinema) ois.readObject();
        } catch (IOException | ClassNotFoundException e){
            System.err.println("Erro ao carregar dados. Criando novo cinema.");
            return new Cinema();
        }
    }
}
