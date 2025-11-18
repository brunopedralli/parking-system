package pucrs.poo.estacionamento.vaadin;

import pucrs.poo.estacionamento.modelo.*;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Estacionamento")
@Route("estacionamento")
public class Estacionamento extends VerticalLayout {
    public Estacionamento() {
        add(new Hr());

        add(new RouterLink("Entrada de veículos", Entrada.class));
        add(new RouterLink("Saída de veículos", Saida.class));
    }
}