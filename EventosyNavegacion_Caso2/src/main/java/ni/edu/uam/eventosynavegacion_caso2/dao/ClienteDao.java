package ni.edu.uam.eventosynavegacion_caso2.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ni.edu.uam.eventosynavegacion_caso2.models.Cliente;

public class ClienteDao {
    private static  final ObservableList<Cliente> clientes =
            FXCollections.observableArrayList();

    public void agregar(Cliente cliente) {
        clientes.add(cliente);
    }

    public ObservableList<Cliente> obtenerRegistros() {
        return clientes;
    }
}
