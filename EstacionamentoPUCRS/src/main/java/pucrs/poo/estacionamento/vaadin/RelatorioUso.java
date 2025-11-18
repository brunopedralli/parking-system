package pucrs.poo.estacionamento.vaadin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Relatório de Uso")
@Route("gerencial/relatorio-de-uso")
public class RelatorioUso extends VerticalLayout {
    public RelatorioUso() {
        add(new Hr());

        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate("gerencial"));
        add(backButton);
    }
}