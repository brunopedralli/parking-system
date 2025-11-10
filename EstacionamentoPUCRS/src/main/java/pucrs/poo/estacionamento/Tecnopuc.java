package pucrs.poo.estacionamento;

import java.time.LocalDateTime;

public class Tecnopuc extends Cliente {
    public void cadastraVeiculo(String placa) {
        super.getVeiculos().add(placa);
    }

    public void calculaCusto(LocalDateTime chegada, LocalDateTime saida) {
        return;
    }
}