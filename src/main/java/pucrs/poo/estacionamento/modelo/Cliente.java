package pucrs.poo.estacionamento.modelo;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public abstract class Cliente {
    private String cpf;
    private String nome;
    private String celular;
    private Set<String> veiculos;

    public Cliente(String cpf, String nome, String celular) {
        this.cpf = cpf;
        this.nome = nome;
        this.celular = celular;
        this.veiculos = new HashSet<>();
    }

    public String getCpf() {
        return this.cpf;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCelular() {
        return this.celular;
    }

    public Set<String> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(String anterior, String novo) {
        boolean semNovo = novo == null || novo.isEmpty();

        this.veiculos.remove(anterior);
        if (!semNovo) this.veiculos.add(novo);
    }

    public abstract boolean cadastraVeiculo(String placa);
    public abstract double calculaCusto(LocalDateTime chegada, LocalDateTime saida);
    public abstract String getTipo();
}