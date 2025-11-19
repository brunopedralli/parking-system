package pucrs.poo.estacionamento.vaadin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@PageTitle("Gerencial")
@Route("gerencial")
public class Gerencial extends VerticalLayout {
    public Gerencial() {
        add(new Hr());
        add(new RouterLink("Relatorio de uso", RelatorioUso.class));
        add(new RouterLink("Total de entradas", TotalEntradas.class));
        
        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate(""));
        add(backButton);
    }
}