package pucrs.poo.estacionamento.vaadin;

import pucrs.poo.estacionamento.modelo.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Home")
@Route("")
public class MainView extends VerticalLayout {
   public MainView() {
      Button sayHelloButton = new Button("Say hello");
      sayHelloButton.addClickListener(e -> {
             Notification.show("Hello world!");
      });

      add(sayHelloButton);
      add(new Hr());

      add(new RouterLink("Cadastro de Clientes", CadastroVaadin.class));
      add(new RouterLink("Financeiro", Financeiro.class));
      add(new RouterLink("Estacionamento", Estacionamento.class));
   }
}