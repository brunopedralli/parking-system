package pucrs.poo.estacionamento.vaadin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Entrada de Veículos")
@Route("estacionamento/entrada")
public class Entrada extends VerticalLayout {
    public Entrada() {
        add(new Hr());

        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate("estacionamento"));
        add(backButton);
    }
}