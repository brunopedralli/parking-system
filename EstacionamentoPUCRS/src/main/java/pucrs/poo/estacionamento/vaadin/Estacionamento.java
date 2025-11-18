package pucrs.poo.estacionamento.vaadin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Estacionamento")
@Route("estacionamento")
public class Estacionamento extends VerticalLayout {
    public Estacionamento() {
        add(new Hr());

        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate(""));
        add(backButton);
    }
    public void Entrada() {
        TextField placaField = new TextField("Placa do Veículo");
        Button entrarButton = new Button("Registrar Entrada");

        entrarButton.addClickListener(e -> {
        String placa = placaField.getValue().trim().toUpperCase();

        if (placa.isEmpty() || placa.length() < 7) { 
            Notification.show("Por favor, insira uma placa válida (mínimo 7 caracteres).", 3000, Notification.Position.MIDDLE);
            return;
        }
        try {
        Notification.show("Veículo " + placa + " entrou no estacionamento com sucesso!", 3000, Notification.Position.MIDDLE);
            placaField.setValue("");
        } catch (Exception ex) {
            Notification.show(" ERRO ao registrar entrada: " + ex.getMessage(), 5000, Notification.Position.MIDDLE);
        }
    });
    add(placaField, entrarButton);
    }
}