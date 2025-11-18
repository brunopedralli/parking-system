package pucrs.poo.estacionamento.vaadin;

import pucrs.poo.estacionamento.modelo.*;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Saída de Veículos")
@Route("estacionamento/saida")
public class Saida extends VerticalLayout {
    public Saida() {
        add(new Hr());
    }
}