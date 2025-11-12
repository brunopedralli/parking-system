package pucrs.poo.estacionamento;

import java.time.LocalDateTime;

public class Estudante extends Cliente {
    private int creditos;

    public Estudante(String cpf, String nome, String celular, int creditos) {
        super(cpf, nome, celular);
        this.creditos = creditos;
    }

    public int getCreditos() {
        return 0;
    }

    public boolean adicionaCreditos(int creditos) {
        return false;
    }

    @Override
    public void cadastraVeiculo(String placa) {
        super.getVeiculos().add(placa);
    }

    @Override
    public void calculaCusto(LocalDateTime chegada, LocalDateTime saida) {
        return;
    }
}