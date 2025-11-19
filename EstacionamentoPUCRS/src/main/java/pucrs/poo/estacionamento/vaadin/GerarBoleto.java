package pucrs.poo.estacionamento.vaadin;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import pucrs.poo.estacionamento.modelo.CadastroClientes;
import pucrs.poo.estacionamento.modelo.Cliente;



@PageTitle("Gerar Boleto")
@Route("finaceiro/gerar-boleto")
public class GerarBoleto extends HorizontalLayout {

    public GerarBoleto() {
        add(new Hr());

        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate(""));
        add(backButton);
    }
}