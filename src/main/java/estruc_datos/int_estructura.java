package estruc_datos;

public interface int_estructura<T> {
    void insertar(T value);      
    void obtenerDato();
    Object buscar(T value);
    void eliminar(T value);
    String getName();
}

