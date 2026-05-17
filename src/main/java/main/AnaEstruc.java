package main;

public class AnaEstruc {
    //Clase creada para guardar los datos de tiempo de Inserción, Búsqueda y eliminación de datos de las diferentes estructuras
    private String title;
    private Float insertionTime;
    private Float searchTime;
    private Float deletionTime;

    public AnaEstruc(String title, Float insertionTime, Float searchTime, Float deletionTime){
        this.title = title;
        this.insertionTime = insertionTime;
        this.searchTime = searchTime;
        this.deletionTime = deletionTime;
    }

    public String getTitle(){
        return this.title;
    }

    public Float getInsertionTime(){
        return this.insertionTime;
    }

    public Float getSearchTime(){
        return this.searchTime;
    }

    public Float getDeletionTime(){
        return this.deletionTime;
    }
}
