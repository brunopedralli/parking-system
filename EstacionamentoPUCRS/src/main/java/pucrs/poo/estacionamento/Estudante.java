package pucrs.poo.estacionamento;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Estudante extends Cliente {
    private int creditos;
    private static final Set<Integer> valoresRecarga = new HashSet<>(
        Arrays.asList(10, 50, 100)
    );

    public Estudante(String cpf, String nome, String celular, int creditos) {
        super(cpf, nome, celular);
        this.creditos = creditos;
    }

    public int getCreditos() {
        return this.creditos;
    }

    public boolean adicionaCreditos(int creditos) {
        if (valoresRecarga.contains(creditos)) {
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