package pucrs.poo.estacionamento;

import java.time.LocalDateTime;

public class Pucrs extends Cliente {

    public Pucrs(String cpf, String nome, String celular) {
        super(cpf, nome, celular);
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