package pucrs.poo.estacionamento.vaadin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Total de Entradas")
@Route("gerencial/total-de-entradas")
public class TotalDeEntradas extends VerticalLayout {
    public TotalDeEntradas() {
        add(new Hr());

        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate("gerencial"));
        add(backButton);
    }
}