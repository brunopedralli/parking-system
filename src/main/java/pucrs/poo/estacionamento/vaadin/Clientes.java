package pucrs.poo.estacionamento.vaadin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@PageTitle("Clientes")
@Route("clientes")
public class Clientes extends VerticalLayout {
   public Clientes() {
    
    add(new RouterLink("Cadastro de Clientes", Cadastro.class));
    add(new RouterLink("Atualização de Clientes", Edicao.class));

    Button backButton = new Button("Voltar");
    backButton.addClickListener(e -> UI.getCurrent().navigate(""));
    add(backButton);
   }
}
