package ni.edu.uam.eventosynavegacion_caso2.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ni.edu.uam.eventosynavegacion_caso2.models.Cliente;
import ni.edu.uam.eventosynavegacion_caso2.dao.ClienteDao;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.io.IOException;

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

    private final ClienteDao clienteDao = new ClienteDao();

    private final ObservableList<Cliente> listaClientes =
            clienteDao.obtenerRegistros();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        configurarColumnas();

        tvClientes.setItems(listaClientes);
    }

    private void configurarColumnas() {

        colNombreCompleto.setCellValueFactory(datos -> {

            Cliente cliente = datos.getValue();

            String nombreCompleto =
                    cliente.getNombres()
                            + " "
                            + cliente.getApellidos();

            return new ReadOnlyStringWrapper(nombreCompleto);
        });

        colTipoCliente.setCellValueFactory(datos ->
                new ReadOnlyStringWrapper(
                        datos.getValue().getTipoCliente()
                )
        );

        colCiudad.setCellValueFactory(datos ->
                new ReadOnlyStringWrapper(
                        datos.getValue().getCiudad()
                )
        );

        colFechaNacimiento.setCellValueFactory(datos ->
                new ReadOnlyObjectWrapper<>(
                        datos.getValue().getFechaNacimiento()
                )
        );

        colTipoSolicitud.setCellValueFactory(datos ->
                new ReadOnlyStringWrapper(
                        datos.getValue().getTipoSolicitud()
                )
        );
    }

    @FXML
    private void manejarDobleClic(MouseEvent event) {

        if (event.getClickCount() == 2) {

            Cliente clienteSeleccionado =
                    tvClientes.getSelectionModel().getSelectedItem();

            if (clienteSeleccionado != null) {

                mostrarDetalleCliente(clienteSeleccionado);
            }
        }
    }

    private void mostrarDetalleCliente(Cliente cliente) {

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle("Detalle del cliente");

        alerta.setHeaderText(
                cliente.getNombres()
                        + " "
                        + cliente.getApellidos()
        );

        alerta.setContentText(
                "Tipo de cliente: "
                        + cliente.getTipoCliente()

                        + "\nCiudad: "
                        + cliente.getCiudad()

                        + "\nFecha de nacimiento: "
                        + cliente.getFechaNacimiento()

                        + "\nTipo de solicitud: "
                        + cliente.getTipoSolicitud()

                        + "\nServicios de interés: "
                        + cliente.getServiciosInteres()
        );

        alerta.showAndWait();
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

    public ObservableList<Cliente> getListaClientes() {
        return listaClientes;
    }

    @FXML
    private void volverPrincipal(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/ni/edu/uam/eventosynavegacion_caso2/fxml/principal-view.fxml"
                    )
            );
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Sistema de Clientes");
            stage.centerOnScreen();
        } catch (IOException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pudo regresar al menú principal");
            alerta.setContentText(
                    "Verifique que principal-view.fxml exista correctamente"
            );
            alerta.showAndWait();
        }
    }
}