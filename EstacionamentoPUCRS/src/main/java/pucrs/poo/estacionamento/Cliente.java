package pucrs.poo.estacionamento;

import java.time.LocalDateTime;
import java.util.Set;

public abstract class Cliente {
    private String cpf;
    private String nome;
    private String celular;

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
        return null;
    }

    public abstract void cadastraVeiculo(String placa);
    public abstract void calculaCusto(LocalDateTime chegada, LocalDateTime saida);
}