package ni.edu.uam.eventosynavegacion_caso2.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import ni.edu.uam.eventosynavegacion_caso2.models.Cliente;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ConsultaClienteController implements Initializable {

    @FXML
    private TableView<Cliente> tvClientes;

    @FXML
    private TableColumn<Cliente, String> colNombreCompleto;

    @FXML
    private TableColumn<Cliente, String> colTipoCliente;

    @FXML
    private TableColumn<Cliente, String> colCiudad;

    @FXML
    private TableColumn<Cliente, LocalDate> colFechaNacimiento;

    @FXML
    private TableColumn<Cliente, String> colTipoSolicitud;

    private final ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColumnas();
        tvClientes.setItems(listaClientes);
    }

    private void configurarColumnas() {
        colNombreCompleto.setCellValueFactory(cliente ->
                new ReadOnlyStringWrapper(
                        cliente.getValue().getNombres() + " " +
                                cliente.getValue().getApellidos()
                )
        );

        colTipoCliente.setCellValueFactory(cliente ->
                new ReadOnlyStringWrapper(
                        cliente.getValue().getTipoCliente()
                )
        );

        colCiudad.setCellValueFactory(cliente ->
                new ReadOnlyStringWrapper(
                        cliente.getValue().getCiudad()
                )
        );

        colFechaNacimiento.setCellValueFactory(cliente ->
                new ReadOnlyObjectWrapper<>(
                        cliente.getValue().getFechaNacimiento()
                )
        );

        colTipoSolicitud.setCellValueFactory(cliente ->
                new ReadOnlyStringWrapper(
                        cliente.getValue().getTipoSolicitud()
                )
        );
    }

    @FXML
    private void seleccionarCliente(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Cliente clienteSeleccionado = tvClientes.getSelectionModel().getSelectedItem();

            if (clienteSeleccionado != null) {
                mostrarDetalleCliente(clienteSeleccionado);
            }
        }
    }

    private void mostrarDetalleCliente(Cliente cliente) {
        String servicios;

        if (cliente.getServiciosInteres() == null || cliente.getServiciosInteres().isEmpty()) {
            servicios = "Ninguno";
        } else {
            servicios = String.join(", ", cliente.getServiciosInteres());
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalle del cliente");
        alert.setHeaderText(cliente.getNombres() + " " + cliente.getApellidos());

        alert.setContentText(
                "Tipo de cliente: " + cliente.getTipoCliente() +
                        "\nCiudad: " + cliente.getCiudad() +
                        "\nFecha de nacimiento: " + cliente.getFechaNacimiento() +
                        "\nTipo de solicitud: " + cliente.getTipoSolicitud() +
                        "\nServicios de interés: " + servicios
        );

        alert.showAndWait();
    }

    public void cargarClientes(List<Cliente> clientes) {
        listaClientes.clear();

        if (clientes != null) {
            listaClientes.addAll(clientes);
        }
    }

    public void agregarCliente(Cliente cliente) {
        if (cliente != null) {
            listaClientes.add(cliente);
        }
    }
}