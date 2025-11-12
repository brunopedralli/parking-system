package pucrs.poo.estacionamento;

import java.time.LocalDateTime;

public class Pucrs extends Cliente {

    public Pucrs(String cpf, String nome, String celular) {
        super(cpf, nome, celular);
    }

    @Override
    public void cadastraVeiculo(String placa) {
        if (super.getVeiculos().size() < 2)
            super.getVeiculos().add(placa);
    }

    @Override
    public void calculaCusto(LocalDateTime chegada, LocalDateTime saida) {
        return;
    }
}