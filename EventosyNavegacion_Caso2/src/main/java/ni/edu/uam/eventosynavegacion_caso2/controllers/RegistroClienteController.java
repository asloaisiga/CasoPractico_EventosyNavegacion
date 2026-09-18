package ni.edu.uam.eventosynavegacion_caso2.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ni.edu.uam.eventosynavegacion_caso2.models.Cliente;
import ni.edu.uam.eventosynavegacion_caso2.dao.ClienteDao;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.IOException;

public class RegistroClienteController {
    @FXML
    private TextField txtNombres;

    @FXML
    private TextField txtApellidos;

    @FXML
    private ComboBox<String> cbTipoCliente;

    @FXML
    private ComboBox<String> cbCiudad;

    @FXML
    private DatePicker dpFechaNacimiento;

    @FXML
    private ToggleGroup grupoSolicitud;

    @FXML
    private RadioButton rbInformacion;

    @FXML
    private RadioButton rbCotizacion;

    @FXML
    private RadioButton rbReclamo;

    @FXML
    private CheckBox chkInternet;

    @FXML
    private CheckBox chkSoporte;

    @FXML
    private CheckBox chkMantenimiento;

    @FXML
    private ImageView imgFotografia;

    @FXML
    private Button btnSeleccionarFotografia;

    @FXML
    private Label lblRutaFotografia;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnCancelar;


    private File archivoFotografia;

    private final ClienteDao clienteDao = new ClienteDao();


    @FXML
    public void initialize() {

        cbTipoCliente.getItems().addAll(
                "Nuevo",
                "Frecuente",
                "Corporativo"
        );

        cbCiudad.getItems().addAll(
                "Managua",
                "León",
                "Granada",
                "Masaya",
                "Chinandega",
                "Estelí",
                "Matagalpa"
        );
    }


    @FXML
    private void seleccionarFotografia() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Seleccionar fotografía");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Archivos de imagen",
                        "*.png",
                        "*.jpg",
                        "*.jpeg"
                )
        );

        Stage ventana = (Stage) btnSeleccionarFotografia
                .getScene()
                .getWindow();

        File archivoSeleccionado =
                fileChooser.showOpenDialog(ventana);

        if (archivoSeleccionado != null) {

            archivoFotografia = archivoSeleccionado;

            Image imagen = new Image(
                    archivoSeleccionado.toURI().toString()
            );

            imgFotografia.setImage(imagen);

            lblRutaFotografia.setText(
                    archivoSeleccionado.getName()
            );
        }
    }


    @FXML
    private void guardarCliente() {

        if (!validarFormulario()) {
            return;
        }

        String nombres =
                txtNombres.getText().trim();

        String apellidos =
                txtApellidos.getText().trim();

        String tipoCliente =
                cbTipoCliente.getValue();

        String ciudad =
                cbCiudad.getValue();

        LocalDate fechaNacimiento =
                dpFechaNacimiento.getValue();

        String tipoSolicitud =
                obtenerTipoSolicitud();

        List<String> serviciosInteres =
                obtenerServiciosInteres();

        String rutaFotografia =
                archivoFotografia.getAbsolutePath();


        Cliente cliente = new Cliente(
                nombres,
                apellidos,
                tipoCliente,
                ciudad,
                fechaNacimiento,
                tipoSolicitud,
                serviciosInteres,
                rutaFotografia
        );

        clienteDao.agregar(cliente);

        Alert alerta = new Alert(
                Alert.AlertType.INFORMATION
        );

        alerta.setTitle("Registro exitoso");

        alerta.setHeaderText(
                "Cliente registrado correctamente"
        );

        alerta.setContentText(
                "Cliente: "
                        + cliente.getNombres()
                        + " "
                        + cliente.getApellidos()
                        + "\nTipo: "
                        + cliente.getTipoCliente()
                        + "\nCiudad: "
                        + cliente.getCiudad()
                        + "\nSolicitud: "
                        + cliente.getTipoSolicitud()
        );

        alerta.showAndWait();

        limpiarFormulario();
    }


    private boolean validarFormulario() {

        if (txtNombres.getText().trim().isEmpty()) {

            mostrarAdvertencia(
                    "Debe ingresar los nombres."
            );

            txtNombres.requestFocus();

            return false;
        }

        if (txtApellidos.getText().trim().isEmpty()) {

            mostrarAdvertencia(
                    "Debe ingresar los apellidos."
            );

            txtApellidos.requestFocus();

            return false;
        }

        if (cbTipoCliente.getValue() == null) {

            mostrarAdvertencia(
                    "Debe seleccionar un tipo de cliente."
            );

            return false;
        }

        if (cbCiudad.getValue() == null) {

            mostrarAdvertencia(
                    "Debe seleccionar una ciudad."
            );

            return false;
        }

        if (dpFechaNacimiento.getValue() == null) {

            mostrarAdvertencia(
                    "Debe seleccionar una fecha de nacimiento."
            );

            return false;
        }

        if (dpFechaNacimiento
                .getValue()
                .isAfter(LocalDate.now())) {

            mostrarAdvertencia(
                    "La fecha de nacimiento no puede ser futura."
            );

            return false;
        }

        if (grupoSolicitud.getSelectedToggle() == null) {

            mostrarAdvertencia(
                    "Debe seleccionar un tipo de solicitud."
            );

            return false;
        }

        if (!chkInternet.isSelected()
                && !chkSoporte.isSelected()
                && !chkMantenimiento.isSelected()) {

            mostrarAdvertencia(
                    "Debe seleccionar al menos un servicio de interés."
            );

            return false;
        }

        if (archivoFotografia == null) {

            mostrarAdvertencia(
                    "Debe seleccionar una fotografía."
            );

            return false;
        }

        return true;
    }


    private String obtenerTipoSolicitud() {

        RadioButton seleccionado =
                (RadioButton)
                        grupoSolicitud.getSelectedToggle();

        return seleccionado.getText();
    }


    private List<String> obtenerServiciosInteres() {

        List<String> servicios =
                new ArrayList<>();

        if (chkInternet.isSelected()) {
            servicios.add("Internet");
        }

        if (chkSoporte.isSelected()) {
            servicios.add("Soporte técnico");
        }

        if (chkMantenimiento.isSelected()) {
            servicios.add("Mantenimiento");
        }

        return servicios;
    }


    private void mostrarAdvertencia(String mensaje) {

        Alert alerta = new Alert(
                Alert.AlertType.WARNING
        );

        alerta.setTitle("Datos incompletos");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }


    @FXML
    private void limpiarFormulario() {

        txtNombres.clear();
        txtApellidos.clear();

        cbTipoCliente.setValue(null);
        cbCiudad.setValue(null);

        dpFechaNacimiento.setValue(null);

        grupoSolicitud.selectToggle(null);

        chkInternet.setSelected(false);
        chkSoporte.setSelected(false);
        chkMantenimiento.setSelected(false);

        imgFotografia.setImage(null);

        archivoFotografia = null;

        lblRutaFotografia.setText(
                "Sin fotografía"
        );

        txtNombres.requestFocus();
    }


    @FXML
    private void cancelar() {

        Alert confirmacion = new Alert(
                Alert.AlertType.CONFIRMATION
        );

        confirmacion.setTitle("Cancelar registro");
        confirmacion.setHeaderText(null);

        confirmacion.setContentText(
                "¿Desea cancelar el registro del cliente?"
        );

        Optional<ButtonType> respuesta =
                confirmacion.showAndWait();

        if (respuesta.isPresent()
                && respuesta.get() == ButtonType.OK) {

            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(
                                "/ni/edu/uam/eventosynavegacion_caso2/fxml/principal-view.fxml"
                        )
                );

                Parent root = loader.load();

                Stage ventana =
                        (Stage) btnCancelar
                                .getScene()
                                .getWindow();

                ventana.setScene(new Scene(root));
                ventana.setTitle("Sistema de Clientes");
                ventana.centerOnScreen();

            } catch (IOException e) {
                mostrarAdvertencia(
                        "No se pudo regresar a la ventana principal."
                );
            }
        }
    }
}
