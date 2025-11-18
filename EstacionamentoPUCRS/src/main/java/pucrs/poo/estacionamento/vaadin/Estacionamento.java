package pucrs.poo.estacionamento.vaadin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@PageTitle("Estacionamento")
@Route("estacionamento")
public class Estacionamento extends VerticalLayout {
    public Estacionamento() {
        add(new Hr());

        add(new RouterLink("Entrada de veículos", Entrada.class));
        add(new RouterLink("Saída de veículos", Saida.class));

        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate(""));
        add(backButton);
    }
}