package pucrs.poo.estacionamento.vaadin;

import java.time.LocalDateTime;
import java.util.Map;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import pucrs.poo.estacionamento.modelo.CadastroClientes;
import pucrs.poo.estacionamento.modelo.Cliente;
import pucrs.poo.estacionamento.modelo.Estudante;
import pucrs.poo.estacionamento.modelo.Pucrs;
import pucrs.poo.estacionamento.modelo.ServicoDeEstacionamento;
import pucrs.poo.estacionamento.modelo.Tecnopuc;

@PageTitle("Estacionamento")
@Route("estacionamento")
public class Estacionamento extends VerticalLayout {
    private final CadastroClientes cadClientes;
    private final ServicoDeEstacionamento gerEstacionamento;
    private final Map<String, LocalDateTime> entradas;
    private final Grid<Map.Entry<String, LocalDateTime>> grid;
    private final TextField placa;

    public Estacionamento() {
        cadClientes = CadastroClientes.getInstance();
        gerEstacionamento = ServicoDeEstacionamento.getInstance();
        entradas = gerEstacionamento.getEstacionamento();
        placa = new TextField("Placa");

        grid = new Grid<>();
        grid.setItems(entradas.entrySet());
        grid.addColumn(Map.Entry::getKey).setHeader("Placa");
        grid.addColumn(Map.Entry::getValue).setHeader("Entrada");

        setSpacing(true);
        setPadding(true);

        add(new H2("Entrada e Saída de Veículos"));

        FormLayout formLayout = new FormLayout(placa);

        Button entrar = new Button("Entrar", VaadinIcon.CHECK.create());
        entrar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        entrar.addClickShortcut(Key.ENTER);
        entrar.addClickListener(click -> this.entrar());

        Button sair = new Button("Sair", VaadinIcon.CHECK.create());
        sair.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sair.addClickShortcut(Key.ENTER);
        sair.addClickListener(click -> this.sair());

        Button cancelarButton = new Button("Cancelar");
        Dialog dialogoCancelamento = criaDialogoDeCancelamento();
        cancelarButton.addClickListener(click -> dialogoCancelamento.open());

        HorizontalLayout botoesLayout = new HorizontalLayout(entrar, sair, cancelarButton);

        add(formLayout, botoesLayout, new H2("Estacionamento"), grid);
        add(new Hr());
        
        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate(""));
        add(backButton);
    }

    private void entrar() {
        String veiculo = placa.getValue();
        if (!verificarPlaca(veiculo)) return;
        
        Cliente c = cadClientes.getPorPlaca(veiculo);
        boolean entrou = gerEstacionamento.entrada(veiculo, LocalDateTime.now());
        if (entrou) Notification.show("Usuário " + c.getNome() + " entrou no estacionamento com sucesso!", 3000, Notification.Position.BOTTOM_STRETCH);

        grid.getDataProvider().refreshAll();
        limparFormulario();
    }

    private void sair() {
        String veiculo = placa.getValue();
        if (!verificarPlaca(veiculo)) return;

        Cliente c = cadClientes.getPorPlaca(veiculo);
        boolean saiu = gerEstacionamento.saida(veiculo, LocalDateTime.now());
        if (saiu) Notification.show("Cliente " + c.getNome() + " saiu do estacionamento com sucesso!", 3000, Notification.Position.BOTTOM_STRETCH);

        grid.getDataProvider().refreshAll();
        limparFormulario();
    }

    private boolean verificarPlaca(String placa) {
        boolean placaVazia = placa == null || placa.isEmpty();

        if (placaVazia) {
            Notification.show("Insira o veículo com o qual deseja sair estacionamento", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR); 
            return false;
        }
        
        Cliente c = cadClientes.getPorPlaca(placa);
        if (c == null) {
            Notification.show("Não existem clientes cadastrados com a placa informada", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR); 
            return false;
        }

        return true;
    }

    private void limparFormulario() {
        placa.clear();
        placa.focus();
    }

    private Dialog criaDialogoDeCancelamento() {
        Dialog dialogo = new Dialog();
        dialogo.setHeaderTitle("Confirmar cancelamento");
        dialogo.add(new Paragraph("Você tem certeza que deseja cancelar e limpar o formulário?"));
        Button confirmarCancelamento = new Button("Sim, cancelar", e -> {
            limparFormulario();
            dialogo.close();
        });
        confirmarCancelamento.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        Button fecharDialogo = new Button("Não", e -> dialogo.close());
        dialogo.getFooter().add(fecharDialogo, confirmarCancelamento);
        return dialogo;
    }
}