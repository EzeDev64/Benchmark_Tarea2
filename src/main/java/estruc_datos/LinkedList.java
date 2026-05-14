package estruc_datos;

public class LinkedList <T extends Comparable<? super T>> implements int_estructura<T>{
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedList(){
        head = null;
        tail = null;
        size = 0;
    }

    public void push(T data){
        Node<T> node = new Node<T>(data);
        
        if (size==0){
            this.head = node;
            this.tail = node;
            this.size += 1;
        }else{
            this.tail.setNext(node);
            this.tail = node;
            this.size += 1;
        }


    }

    public void insert(T data, int position){
        if (position > size-1 && position !=0){
            throw new IndexOutOfBoundsException("Index está fuera de los indices de la lista");
        }

        Node<T> node = new Node<T>(data);
        
        //Si la lista está vacía
        if (size==0){
            this.head = node;
            this.tail = node;
            this.size += 1;
        }else{ 
            if (position == 0){
                Node<T> first = this.head;
                node.setNext(first);
                this.head = node;
                this.size += 1;
            }else if (position == this.size-1){
                this.tail.setNext(node);
                this.tail = node;
                this.size += 1;
            }else{
                Node<T> prev = this.head;
                for (int i=0;i<this.size;i++){
                    if(i == position-1){
                        break;
                    }else{
                        prev = prev.getnext();
                    }
                }

                node.setNext(prev.getnext());
                prev.setNext(node);
                this.size += 1;
            }
        }
    }

    public void delete(int position){
        if (position > size-1 && position !=0){
            throw new IndexOutOfBoundsException("Index está fuera de los indices de la lista");
        }
        if (this.head == null){
            throw new NullPointerException("No hay ningún elemento dentro de la lista");
        }

        if (position == 0){
            this.head = head.getnext();
        }else{
            Node<T> prev = this.head;
            for (int i=0;i<this.size;i++){
                if(i == position-1){
                    break;
                }else{
                    prev = prev.getnext();
                }
            }
            prev.setNext(prev.getnext().getnext());
            if(position==this.size-1){
                this.tail = prev;
            }
        }

        this.size-=1;
    }

    public T getAt(int position){
        if (position > size-1 && position !=0){
            throw new IndexOutOfBoundsException("Index está fuera de los indices de la lista");
        }

        Node<T> node = this.head;

        for (int i=0;i<this.size;i++){
            if(i == position){
                return node.getData();
            }else{
                node = node.getnext();
            }
        }

        return null;
    }
    public void HeadTail(){
        System.out.print(this.head.getData());
        System.out.print(" ");
        System.out.println(this.tail.getData());
    }

    public int getSize(){
        return this.size;
    }

    @Override
    public void insertar(T value) {
        //Para poder crear una lista de objetos.
        this.push(value);
    }

    @Override
    public void obtenerDato() {
        for(int i=0;i<this.getSize();i++){
            System.out.println(this.getAt(i));
        }
    }

}

class Node <T extends Comparable<? super T>>{
        private Node<T> next;
        private T data;

        public Node(T data){
            this.data = data;
            this.next = null;
        }

        public Node<T> getnext(){
            return this.next;
        }

        public void setNext(Node<T> node){
            this.next = node;
        }

        public T getData(){
            return this.data;
        }

        

}
