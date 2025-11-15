package pucrs.poo.estacionamento;

import java.time.Duration;
import java.time.LocalDateTime;

public class Tecnopuc extends Cliente {
    private double debitos;

    public Tecnopuc(String cpf, String nome, String celular, double debitos) {
        super(cpf, nome, celular);
        this.debitos = debitos;
    }

    public double getDebitos() {
        return this.debitos;
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
        Duration dt = Duration.between(chegada, saida);
        long minutosTotais = dt.toMinutes();

        double horasCobraveis = Math.ceil(minutosTotais/60.0);
        double custo = horasCobraveis * 1.5;

        this.debitos += custo;
    }
}