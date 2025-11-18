package pucrs.poo.estacionamento.vaadin;

import pucrs.poo.estacionamento.modelo.*;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Financeiro")
@Route("financeiro")
public class Financeiro extends VerticalLayout {
    public Financeiro() {
        add(new Hr());
        add(new RouterLink("Recarga de Créditos", RecargaDeCreditos.class));
        add(new RouterLink("Receita Total", ReceitaTotal.class));
    }
}