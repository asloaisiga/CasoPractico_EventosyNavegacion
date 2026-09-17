package ni.edu.uam.eventosynavegacion_caso2.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ni.edu.uam.eventosynavegacion_caso2.dao.ActividadDao;
import ni.edu.uam.eventosynavegacion_caso2.models.Actividad;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class PrincipalController {
    @FXML
    private ListView<Actividad> lvActividad;
    private final ActividadDao actividadDao = new ActividadDao();

    @FXML
    public void initialize() {
        lvActividad.setItems(actividadDao.obtenerRegistros());
        actividadDao.agregar(new Actividad("Sesión iniciada correctamente"));
    }
    @FXML
    private void abrirRegistroCliente(ActionEvent event){
        actividadDao.agregar(new Actividad("Se abrió la registro de clientes"));
        abrirVista(event, "/ni/edu/uam/eventosynavegacion_caso2/fxml/Cliente-view.fxml", "Registro de clientes");
    }

    @FXML
    private void abrirConsultaClientes(ActionEvent event) {
        actividadDao.agregar(new Actividad("Se abrió la consulta de clientes"));
        abrirVista(event, "/ni/edu/uam/matricula/consulta-cliente-view.fxml", "Consulta de clientes"
        );
    }

    @FXML
    private void seleccionarCarpeta(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar carpeta");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        File carpeta = directoryChooser.showDialog(stage);

        if (carpeta != null) {
            actividadDao.agregar(new Actividad("Carpeta seleccionada: " + carpeta.getAbsolutePath())
            );

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Carpeta seleccionada");
            alerta.setHeaderText(null);
            alerta.setContentText(
                    "La carpeta seleccionada es:\n" + carpeta.getAbsolutePath()
            );
            alerta.showAndWait();
        }
    }
    @FXML
    private void cerrarSesion(ActionEvent event) {
        Alert confirmacion = new Alert(
                Alert.AlertType.CONFIRMATION
        );

        confirmacion.setTitle("Cerrar sesión");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(
                "¿Está seguro de que desea cerrar la sesión?"
        );

        Optional<ButtonType> resultado =
                confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {

            abrirVista(event, "/ni/edu/uam/eventosynavegacion_caso2/fxml/login-view.fxml", "Inicio de sesión"
            );
        }
    }

    @FXML
    private void salirAplicacion(ActionEvent event) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);

        confirmacion.setTitle("Salir");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de que desea cerrar la aplicación?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            Platform.exit();
        }
    }
    @FXML
    private void mostrarAcercaDe(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.setTitle("Acerca del sistema");
        dialog.setHeaderText("Sistema de Registro y Consulta de Clientes");
        dialog.setContentText("Aplicación para registrar y consultar solicitudes de clientes."
        );
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait();
    }
    @FXML
    private void verActividadSeleccionada(ActionEvent event) {
        Actividad actividad = lvActividad.getSelectionModel().getSelectedItem();

        if (actividad == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Actividad");
            alerta.setHeaderText(null);
            alerta.setContentText("Seleccione una actividad de la lista.");
            alerta.showAndWait();
            return;
        }

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Actividad seleccionada");
        alerta.setHeaderText(null);
        alerta.setContentText(
                actividad.toString()
        );
        alerta.showAndWait();
    }
    @FXML
    private void limpiarActividad(ActionEvent event) {
        if (actividadDao.estaVacio()) {
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Limpiar actividad");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Desea eliminar toda la actividad?");
        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            actividadDao.limpiar();
        }
    }

    private void abrirVista(
            ActionEvent event,
            String ruta,
            String titulo
    ) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(titulo);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pudo abrir la ventana.");
            alerta.setContentText("Verifique que el archivo FXML exista correctamente.");
            alerta.showAndWait();
        }
    }
}
