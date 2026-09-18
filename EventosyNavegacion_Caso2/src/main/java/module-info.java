module ni.edu.uam.eventosynavegacion_caso2 {

    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.desktop;

    opens ni.edu.uam.eventosynavegacion_caso2 to javafx.fxml;
    opens ni.edu.uam.eventosynavegacion_caso2.controllers to javafx.fxml;

    exports ni.edu.uam.eventosynavegacion_caso2;
}