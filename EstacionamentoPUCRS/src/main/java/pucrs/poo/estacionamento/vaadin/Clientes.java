package pucrs.poo.estacionamento.vaadin;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@PageTitle("Clientes")
@Route("clientes")
public class Clientes extends VerticalLayout {
   public Clientes() {
      add(new RouterLink("Cadastro de clientes", Cadastro.class));
      add(new RouterLink("Atualização de Clientes", Edicao.class));
   }
}
