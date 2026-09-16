module ni.edu.uam.eventosynavegacion_caso2 {

    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    opens ni.edu.uam.eventosynavegacion_caso2 to javafx.fxml;
    opens ni.edu.uam.eventosynavegacion_caso2.controllers to javafx.fxml;

    exports ni.edu.uam.eventosynavegacion_caso2;
}