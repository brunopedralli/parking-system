package pucrs.poo.estacionamento;

import java.time.LocalDateTime;

public class Estudante extends Cliente {
    public int getCreditos() {
        return 0;
    }

    public boolean adicionaCreditos(int creditos) {
        return false;
    }

    public void cadastraVeiculo(String placa) {
        return;
    }

    public void calculaCusto(LocalDateTime chegada, LocalDateTime saida) {
        return;
    }
}