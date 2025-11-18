package pucrs.poo.estacionamento.vaadin;

import pucrs.poo.estacionamento.modelo.*;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Entrada de Veículos")
@Route("estacionamento/entrada")
public class Entrada extends VerticalLayout {
    public Entrada() {
        add(new Hr());
    }
}