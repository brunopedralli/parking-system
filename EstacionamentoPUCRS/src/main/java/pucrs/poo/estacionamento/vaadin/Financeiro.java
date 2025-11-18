package pucrs.poo.estacionamento.vaadin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@PageTitle("Financeiro")
@Route("financeiro")
public class Financeiro extends VerticalLayout {
    public Financeiro() {
        add(new Hr());
        add(new RouterLink("Recarga de Créditos", RecargaDeCreditos.class));
        add(new RouterLink("Receita Total", ReceitaTotal.class));

        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate(""));
        add(backButton);
    }
}