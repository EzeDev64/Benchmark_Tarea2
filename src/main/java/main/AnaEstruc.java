package main;

public class AnaEstruc {
    //Clase creada para guardar los datos de tiempo de Inserción, Búsqueda y eliminación de datos de las diferentes estructuras
    private String title;
    private String insertionTime;
    private String searchTime;
    private String deletionTime;

    public AnaEstruc(String title, String insertionTime, String searchTime, String deletionTime){
        this.title = title;
        this.insertionTime = insertionTime;
        this.searchTime = searchTime;
        this.deletionTime = deletionTime;
    }

    public String getTitle(){
        return this.title;
    }

    public String getInsertionTime(){
        return this.insertionTime;
    }

    public String getSearchTime(){
        return this.searchTime;
    }

    public String getDeletionTime(){
        return this.deletionTime;
    }
}
