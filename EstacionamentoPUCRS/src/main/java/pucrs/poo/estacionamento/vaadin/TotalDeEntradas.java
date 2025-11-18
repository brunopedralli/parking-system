package pucrs.poo.estacionamento.vaadin;

import pucrs.poo.estacionamento.modelo.*;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Total de Entradas")
@Route("gerencial/total-de-entradas")
public class TotalDeEntradas extends VerticalLayout {
    public TotalDeEntradas() {
        add(new Hr());
    }
}