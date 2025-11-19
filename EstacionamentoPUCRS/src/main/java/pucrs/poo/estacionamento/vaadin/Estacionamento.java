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
        boolean placaVazia = veiculo == null || veiculo.isEmpty();

        if (placaVazia) {
            Notification.show("Insira o veículo que deseja adentrar ou sair do estacionamento", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR); 
            return;
        }
        
        Cliente c = cadClientes.getPorPlaca(veiculo);
            
        String mensagem = "Usuário " + c.getNome() + " salvo com sucesso!";
        Notification.show(mensagem, 3000, Notification.Position.BOTTOM_STRETCH);

        grid.getDataProvider().refreshAll();
        limparFormulario();
    }

    private void limparFormulario() {
        nome.clear();
        cpf.clear();
        celular.clear();
        tipoUsuario.clear();
        nome.focus();
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
    
    public void Entrada() {
        TextField placaField = new TextField("Placa do Veículo");
        Button entrarButton = new Button("Registrar Entrada");

        entrarButton.addClickListener(e -> {
        String placa = placaField.getValue().trim().toUpperCase();

        if (placa.isEmpty() || placa.length() < 7) { 
            Notification.show("Por favor, insira uma placa válida (mínimo 7 caracteres).", 3000, Notification.Position.MIDDLE);
            return;
        }
        try {
        Notification.show("Veículo " + placa + " entrou no estacionamento com sucesso!", 3000, Notification.Position.MIDDLE);
            placaField.setValue("");
        } catch (Exception ex) {
            Notification.show(" ERRO ao registrar entrada: " + ex.getMessage(), 5000, Notification.Position.MIDDLE);
        }
    });
        add(placaField, entrarButton);
    }

    public void Saida() {
        TextField placaField = new TextField("Placa do Veículo");
        Button sairButton = new Button("Registrar Saída");

        sairButton.addClickListener(e -> {
        String placa = placaField.getValue().trim().toUpperCase();

        if (placa.isEmpty() || placa.length() < 7) { 
            Notification.show("Por favor, insira uma placa válida (mínimo 7 caracteres).", 3000, Notification.Position.MIDDLE);
            return;
        }
        try {
        Notification.show("Veículo " + placa + " saiu do estacionamento com sucesso!", 3000, Notification.Position.MIDDLE);
            placaField.setValue("");
        } catch (Exception ex) {
            Notification.show(" ERRO ao registrar saída: " + ex.getMessage(), 5000, Notification.Position.MIDDLE);
        }
    });
        add(placaField, sairButton);
    }
}