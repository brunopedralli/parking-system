package pucrs.poo.estacionamento;

import java.time.LocalDateTime;

public class Tecnopuc extends Cliente {
    private int debitos;

    public Tecnopuc(String cpf, String nome, String celular, int debitos) {
        super(cpf, nome, celular);
        this.debitos = debitos;
    }

    public int getDebitos() {
        return debitos;
    }

    public void abateDebito(int valor) {
        this.debitos -= valor;
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