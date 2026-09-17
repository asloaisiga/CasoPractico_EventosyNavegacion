package ni.edu.uam.eventosynavegacion_caso2.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ni.edu.uam.eventosynavegacion_caso2.models.Actividad;

public class ActividadDao {

    private static final ObservableList<Actividad> actividades =
            FXCollections.observableArrayList();

    public void agregar(Actividad actividad) {
        actividades.add(actividad);
    }

    public ObservableList<Actividad> obtenerRegistros() {
        return actividades;
    }

    public void eliminar(Actividad actividad) {
        actividades.remove(actividad);
    }

    public void limpiar() {
        actividades.clear();
    }

    public boolean estaVacio() {
        return actividades.isEmpty();
    }
}