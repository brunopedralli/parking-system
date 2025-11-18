package pucrs.poo.estacionamento;

import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("financeiro")
public class Financeiro extends VerticalLayout {
    public Financeiro() {
        add(new Hr());

        add(new RouterLink("Recarga de Créditos", RecargaDeCreditos.class));
    }
}