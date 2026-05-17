package estruc_datos;

public class ArrayList <T> implements int_estructura<T>{
    private Object[] list;
    private int size;

    public ArrayList(){
        this.size = 0;
        this.list = null;
    }

    public void push(T data){
        if (this.list == null){
            this.list = new Object[1];
            this.list[0] = data;
            this.size += 1;
        }else{
            Object[] temp = new Object[this.size+1];
            for(int i=0;i<this.list.length;i++){
                temp[i] = this.list[i];
            }
            temp[this.size] = data;
            this.list = temp;
            this.size += 1;
        }
    }

    public void insert(Object data,int position){
        if (position > size-1 && position !=0){
            throw new IndexOutOfBoundsException("Index está fuera de los indices de la lista");
        }

        if (this.list == null){
            this.list = new Object[1];
            this.list[0] = data;
            this.size += 1;
        }else{
            if (position == 0){
                Object[] temp = new Object[this.size+1];
                temp[0]  = data;
                for(int i=0;i<this.list.length;i++){
                    temp[i+1] = this.list[i];
                }
                this.list = temp;
                this.size += 1;
            }else if(position == this.size-1){
                Object[] temp = new Object[this.size+1];
                for(int i=0;i<this.list.length;i++){
                    temp[i] = this.list[i];
                }
                temp[this.size] = data;
                this.list = temp;
                this.size += 1;
            }else{
                Object[] temp = new Object[this.size+1];
                for(int i=0;i<position;i++){
                    temp[i] = this.list[i];
                }
                temp[position] = data;
                for(int i=position;i<this.size;i++){
                    temp[i+1] = this.list[i];
                }
                this.list = temp;
                this.size += 1;
            }
        }
        
    }

    public void delete(int position){
        if (position > size-1 && position !=0){
            throw new IndexOutOfBoundsException("Index está fuera de los indices de la lista");
        }
        if (this.list == null){
            throw new NullPointerException("No hay ningún elemento dentro de la lista");
        }

        Object[] temp = new Object[this.size-1];
        for(int i=0;i<position;i++){
                    temp[i] = this.list[i];
        }
        for(int i=position;i<this.size-1;i++){
            temp[i] = this.list[i+1];
        }
                
        this.list = temp;
        this.size -= 1;
    }

    public Object getAt(int position){
        if (position > this.size-1 && position !=0){
            throw new IndexOutOfBoundsException("Index está fuera de los indices de la lista");
        }
        return this.list[position];
    }

    public int getSize(){
        return this.size;
    }

    //Para poder crear una lista de objetos.
    @Override
    public void insertar(T value) {
        this.push(value);
    }

    @Override
    public void obtenerDato() {
        for(int i=0;i<this.getSize();i++){
            System.out.println(this.getAt(i));
        }
    }

    @Override
    public Object buscar(T value) {
        for(int i=0;i<this.size;i++){
            if (value == this.list[i]){
                return this.list[i];
            }
        }
        return null;
    }

    @Override
    public void eliminar(T value) {
        for(int i=0;i<this.size;i++){
            if (value == this.list[i]){
                this.delete(i);
            }
        }
    }

}
