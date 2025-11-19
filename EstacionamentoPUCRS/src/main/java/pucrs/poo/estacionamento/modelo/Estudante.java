package pucrs.poo.estacionamento.modelo;

import pucrs.poo.estacionamento.vaadin.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

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

    @Override
    public String getTipo() {
        return "Estudante";
    }

    public boolean adicionaCreditos(int creditos) {
        if (valoresRecarga.contains(creditos)) {
            this.creditos += creditos;
            return true;
        }

        return false;
    }

    @Override
    public boolean cadastraVeiculo(String placa) {
        if (super.getVeiculos().size() < 2) {
            super.getVeiculos().add(placa);
            return true;
        } 
        else {
            Notification.show("O cliente não é permitido a adicionar mais veículos.", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR); 
            return false;
        }
    }

    @Override
    public void calculaCusto(LocalDateTime chegada, LocalDateTime saida) {
        this.creditos -= 15;
    }
}