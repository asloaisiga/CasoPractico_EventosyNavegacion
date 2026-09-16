package ni.edu.uam.eventosynavegacion_caso2.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class LoginController {

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnEnter;

    @FXML
    private Button btnSalir;

    private static final String USUARIO_VALIDO = "AdminPrincipal";
    private static final String CLAVE_VALIDA = "admin_pr";

    @FXML
    private void loginButtonAction(ActionEvent event) {
        iniciarSesion(event);
    }

    private void iniciarSesion(ActionEvent event) {

        String usuario = txtUser.getText().trim();
        String clave = txtPassword.getText();

        if (usuario.isEmpty() || clave.isEmpty()) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Datos incompletos");
            alerta.setHeaderText(null);
            alerta.setContentText(
                    "Debe ingresar el usuario y la contraseña."
            );

            alerta.showAndWait();
            return;
        }

        if (usuario.equals(USUARIO_VALIDO)
                && clave.equals(CLAVE_VALIDA)) {

            abrirVentanaPrincipal(event);

        } else {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error de inicio de sesión");
            alerta.setHeaderText(null);
            alerta.setContentText(
                    "El usuario o la contraseña son incorrectos."
            );

            alerta.showAndWait();

            txtPassword.clear();
            txtPassword.requestFocus();
        }
    }


    private void abrirVentanaPrincipal(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/ni/edu/uam/eventosynavegaciones_caso2/fxml/principal-view.fxml"
                    )
            );

            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            stage.setScene(new Scene(root));

            stage.setTitle(
                    "Sistema de Registro y Consulta de Clientes"
            );

            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(
                    "No se pudo abrir la ventana principal."
            );
            alerta.setContentText(
                    "Verifique que principal-view.fxml exista."
            );

            alerta.showAndWait();

            e.printStackTrace();
        }
    }

    @FXML
    private void loginKeyPressed(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {

            btnEnter.fire();
        }
    }

    @FXML
    private void salirButtonAction(ActionEvent event) {

        Alert confirmacion = new Alert(
                Alert.AlertType.CONFIRMATION
        );

        confirmacion.setTitle("Salir");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(
                "¿Está seguro de que desea cerrar la aplicación?"
        );

        Optional<ButtonType> resultado =
                confirmacion.showAndWait();

        if (resultado.isPresent()
                && resultado.get() == ButtonType.OK) {

            Platform.exit();
        }
    }
}