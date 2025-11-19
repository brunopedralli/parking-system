package pucrs.poo.estacionamento.modelo;

import pucrs.poo.estacionamento.vaadin.*;
import java.time.LocalDateTime;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class Pucrs extends Cliente {

    public Pucrs(String cpf, String nome, String celular) {
        super(cpf, nome, celular);
    }

    @Override
    public String getTipo() {
        return "Funcionário PUCRS";
    }

    @Override
    public void cadastraVeiculo(String placa) {
        if (super.getVeiculos().size() < 2) {
            super.getVeiculos().add(placa);
        } else {
            Notification.show("O cliente não é permitido a adicionar mais veículos.", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR); 
        }
    }

    @Override
    public void calculaCusto(LocalDateTime chegada, LocalDateTime saida) {
        return;
    }
}