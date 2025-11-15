package pucrs.poo.estacionamento;

import java.time.Duration;
import java.time.LocalDateTime;

public class Estudante extends Cliente {
    private int creditos;

    public Estudante(String cpf, String nome, String celular, int creditos) {
        super(cpf, nome, celular);
        this.creditos = creditos;
    }

    public int getCreditos() {
        return this.creditos;
    }

    public boolean adicionaCreditos(int creditos) {
        switch (creditos) {
            case 15:
            case 50:
            case 100:
            case 150:
                this.creditos += creditos;
                return true;
        }

        return false;
    }

    @Override
    public void cadastraVeiculo(String placa) {
        if (super.getVeiculos().size() < 2)
            super.getVeiculos().add(placa);
    }

    @Override
    public void calculaCusto(LocalDateTime chegada, LocalDateTime saida) {
        this.creditos -= 15;
    }
}