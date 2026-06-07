package pucrs.poo.estacionamento.vaadin;

import java.util.Set;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import pucrs.poo.estacionamento.modelo.CadastroClientes;
import pucrs.poo.estacionamento.modelo.Cliente;
import pucrs.poo.estacionamento.modelo.Estudante;

@PageTitle("Recarga de Créditos")
@Route("financeiro/recarga-de-creditos")
public class RecargaDeCreditos extends VerticalLayout {
    private final CadastroClientes cadClientes;
    private final TextField nome;
    private final TextField cpf;
    private final TextField celular;
    private final IntegerField creditos;
    private final ComboBox<Integer> creditosAdd;
    private final Button salvarButton;
    private final Button cancelarButton;
    private final Grid<Estudante> grid;
    Set<Integer> valores = Estudante.getValoresRecarga();
    Estudante estudanteSelecionado;

    public RecargaDeCreditos() {
        cadClientes = CadastroClientes.getInstance();

        nome = new TextField("Nome");
        nome.setReadOnly(true);

        cpf = new TextField("CPF");
        cpf.setReadOnly(true);

        celular = new TextField("Celular");
        celular.setReadOnly(true);

        creditos = new IntegerField("Créditos");
        creditos.setReadOnly(true);

        creditosAdd = new ComboBox<>("Recarga");
        creditosAdd.setItems(valores);

        grid = new Grid<>(Estudante.class);

        setSpacing(true);
        setPadding(true);

        add(new H2("Recarga de Créditos para Estudantes"));

        FormLayout formLayout = new FormLayout(nome, cpf, celular, creditos, creditosAdd);

        salvarButton = new Button("Adicionar", VaadinIcon.CHECK.create());
        salvarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        salvarButton.addClickShortcut(Key.ENTER);
        salvarButton.addClickListener(click -> this.adicionarCreditos());

        cancelarButton = new Button("Cancelar");
        Dialog dialogoCancelamento = criaDialogoDeCancelamento();
        cancelarButton.addClickListener(click -> dialogoCancelamento.open());

        HorizontalLayout botoesLayout = new HorizontalLayout(salvarButton, cancelarButton);

        grid.setItems(cadClientes.getLista().stream()
                                            .filter((Cliente c) -> c instanceof Estudante)
                                            .map((Cliente c) -> (Estudante) c)
                                            .toList());

        grid.setColumns("nome", "cpf", "celular", "creditos");
        grid.asSingleSelect().addValueChangeListener(event -> preparaEdicaoEstudante(event));

        add(formLayout, botoesLayout, new H2("Estudantes Cadastrados"), grid);
        add(new Hr());

        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate("financeiro"));
        add(backButton);

        habilitarFormulario(false);
    }

    private void adicionarCreditos() {
        if (creditosAdd.getValue() == null) {
            Notification.show("Selecione uma quantidade de créditos para adicionar", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR); 
            return;
        }

        Estudante c = (Estudante) cadClientes.getPorCpf(cpf.getValue());
        c.adicionaCreditos(creditosAdd.getValue());

        String mensagem = "Crédito adicionados com sucesso!";
        Notification.show(mensagem, 3000, Notification.Position.BOTTOM_STRETCH);

        grid.getDataProvider().refreshAll();
        limparFormulario();
        habilitarFormulario(false);
    }

    private void preencherFormulario(Estudante e) {
        nome.setValue(e.getNome());
        cpf.setValue(e.getCpf());
        celular.setValue(e.getCelular());
        creditos.setValue(e.getCreditos());
    }

    private void habilitarFormulario(boolean opcao) {
        creditosAdd.setEnabled(opcao);
        salvarButton.setEnabled(opcao);
        cancelarButton.setEnabled(opcao);
    }

    private void preparaEdicaoEstudante(ComponentValueChangeEvent<Grid<Estudante>, Estudante> event) {
        estudanteSelecionado = event.getValue();

        if (estudanteSelecionado != null) {
            preencherFormulario(estudanteSelecionado);
            habilitarFormulario(true);
        } 
        else {
            limparFormulario();
            habilitarFormulario(false);
        }
    }

    private void limparFormulario() {
        grid.asSingleSelect().clear();
        nome.clear();
        cpf.clear();
        celular.clear();
        creditos.clear();
        creditosAdd.clear();
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
}