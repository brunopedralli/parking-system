package pucrs.poo.estacionamento.vaadin;

import pucrs.poo.estacionamento.modelo.*;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Receita Total")
@Route("financeiro/receita-total")
public class ReceitaTotal extends VerticalLayout {
    public ReceitaTotal() {
        add(new Hr());
    }
}