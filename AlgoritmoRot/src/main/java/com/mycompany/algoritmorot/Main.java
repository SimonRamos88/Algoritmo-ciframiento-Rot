

package com.mycompany.algoritmorot;

import java.util.Scanner;

public class Main {
    String abc;
    ColaEnlazada<Character> permutado;
    PilasEnlazadas<Character> invertido;
    ListaEnlazada<Character> permutadoList;
    int  permutaciones;
    
    public Main(int permu, String abc){
        this.abc = abc;
        this.permutaciones = permu;
        permutar();
        this.permutadoList = new ListaEnlazada<Character>();
        copiarCola();
        this.invertido = new PilasEnlazadas();
   
    }
    //permu = #permutaciones, len = longitud abecedario
    public void permutar(){
        int i = 0;
        int len = this.abc.length();
        int permu = this.permutaciones;
        ColaEnlazada<Character> cola = new ColaEnlazada();
        //ListaEnlazada<Character> list = new ListaEnlazada();
        while(i<len){
            Node<Character> nodo = new Node<Character>(abc.charAt((i+permu)%len));
            cola.Encolar(nodo); // aniadimos el nodo a la cola
            //list.pushBack(abc.charAt((i+permu)%len));
            i++;
        }
        this.permutado = cola;
        //this.permutadoList = list;
    }
    
    public void copiarCola(){
        Node n = this.permutado.front;
        while(n != this.permutado.rear){
            this.permutadoList.pushBack((char) n.getData());
            n = n.getNext();
        }
        this.permutadoList.pushBack((char) n.getData());
    }
    
    public void encriptar(char ch){
        int i = buscarAbc(ch);
        //Osea, si el caracter si esta, lo encriptamos
        if(i<this.abc.length()){
            char enc = this.permutadoList.getAt(i);
            this.invertido.push(enc);
        }else{//Si no estaba, simplemente lo guardamos tal cual
            this.invertido.push(ch);       
        }
    }
    
    public void performance(String input){
        for(int i=0; i<input.length();i++){
            encriptar((char) input.charAt(i));
        }
        output();
    }
    
    public void output(){
        String cad = "";
        while(!this.invertido.empty()){
            cad += String.valueOf(this.invertido.pop());
        }
        System.out.print(cad);
    }
    
    //Nos da la posicion, en el abecedario, del caracter que buscamos
    // si el caracter no esta, nos da la longitud del string
    public int buscarAbc(char ch){
        int i = 0;
        boolean bandera = true;
        while(bandera && i<this.abc.length()){
            if(this.abc.charAt(i) == ch){
                bandera = false;
            }else{
                i ++;
            }
            
        }
        //System.out.println("Encontrado: " + ch +" en: " + i);
        return i;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int permu = sc.nextInt();
        sc.nextLine();
        String input = sc.nextLine();
        Main xd = new Main(permu, "abcdefghijklmnopqrstuvwxyz");
        //xd.permutadoList.StringTo();
        xd.performance(input);
        //xd.permutar(3,"abcdef");
        //xd.permutado.StringTo();
        //xd.permutadoList.StringTo();
    }
}

class ListaEnlazada <T> {
    private Node head;
    private Node tail;
    private int size;
    
    public ListaEnlazada(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    public ListaEnlazada<T> clean(){
        this.head = null;
        this.tail = null;
        this.size =0;
        return this;
    }
    
    
    public void setTail(Node tail){
        this.tail = tail;
    }
    
    public Node getTail(){
        return this.tail;
    }
    
    public void setHead(Node head){
        this.head = head;
    }
    
    public Node getHead(){
        return this.head;
    }
    
   public int size(){
       return this.size;
   }
   public ListaEnlazada unir(ListaEnlazada list2){
       this.size = this.size + list2.size();
        //Si esta lista esta vacio, la union es la lista 2
        if(empty()){
            this.head = list2.getHead();
            this.tail = list2.getTail();
        }else{
            // obtenemos la cola de lista 1 y que esta apunte a la cabeza de lista 2
            this.tail.setNext(list2.getHead());
            // Verificamos que la lista que vamos a unir no esté vacía
            if(list2.getHead()!=null){
                // la cola de lista 1 ahora va a hacer la de lista 2
                this.tail = list2.getTail();
            }            
            
        }
        return this;
        
    }
    
    
    public boolean empty(){
        return this.head == null;
    }
    
    
    public void pushFront(T el){
        Node n = new Node(el);  
        n.setNext(this.head); 
        this.head = n;
        if(this.tail == null){
            this.tail = head;
        }
        this.size += 1;   
        
    }
    
    public void pushBack(T el){
        Node n = new Node(el);
        if(empty()){
            this.head = n;
            this.tail = this.head;
        }else{
            this.tail.setNext(n);
            this.tail = n;
        }
        this.size += 1;
    }
    
    public T popBack(){       
        T cad = null;
        if(!empty()){
            if(this.head == this.tail){
                this.head = null;
                this.tail = null;
            }else{
                Node aux = this.head;
                while(aux.getNext() != this.tail){
                    aux = aux.getNext();
                }
                cad =  (T) aux.getNext().getData();
                aux.setNext(null);
                this.tail = aux;
            }
            this.size -= 1;
        }
        return cad;
    }
    
    public T popFront(){
        T cad = null;
        if(!empty()){
            cad = (T) this.head.getData();
            this.head = this.head.getNext();
            if(this.head == null){
                this.tail = null;
            }
            
        this.size -= 1;
        }
        
        return cad;
    }    
    
    public T getAt(int index){
        T res = null;
        if(!empty() && index < this.size){
            int i = 0;
            Node aux = this.head;
            while(i < index){
                aux = aux.getNext();
                i+=1;
            }
            res = (T) aux.getData();
        }
        return res;
    }
    
    //Nos devuelve la "posicion" en la que el elemento buscado esta
    // Si el elemento no está, entonces i = size();
    public int buscar(T element){
        int i= 0;
        Node n = this.head;
        while(n!=null && n.getData() != element){
            i ++;
            n = n.getNext();
        }
        
        return i;
    }
    
    public void StringTo(){
        String cad = "[";
        if(empty()){
            cad += "]";
        }else{
            Node n = this.head;
            while(n.getNext() != null){
                cad += n.getData() + ",";
                n = n.getNext();
            }
            cad += n.getData() + "]";
        }     
        System.out.println(cad);
    }


}

class Node<T>{
    private T data;
    private Node next;
    
   public Node(){    
        this(null);
    }
    public Node(T data) {
        this.data = data;
        next = null;
    }
    
    public void setNext(Node n){
        this.next =n;
    }
    
    public Node getNext(){
        return this.next;
    }
    
    public void setData(T data){
        this.data = data;
    }
    
    public T getData(){
        return this.data;
    }
}

class PilasEnlazadas<T> {
    private Node top;    
    
    public Node getTop(){
        return this.top;
    }
    
    public boolean empty(){
        return top.getNext() == null;
    }
    
    public boolean full(){ //Nunca se llena, a menos que nos quedemos sin memoria
        return false;
    }
    
    public void push(T value){
       Node aux = new Node(value);
       aux.setNext(this.top);
       this.top = aux;
       //Destruir aux? No es necesario, es inocuo y solo come como 8 bytes
    }
    
    public T pop(){
        if(!empty()){
            T value = (T)this.top.getData();
            top = this.top.getNext(); //Guardamos la direccion del siguiente nodo
            return value;
        }else{
            throw new IllegalArgumentException("Esta vacio pa");
        }
        
    }
    
    public int toString(Node node){
        if(node.getNext()== null){
            return 0;
        }
        else{
            System.out.println(node.getData());
            return toString(node.getNext());
        }
    }
    
    public PilasEnlazadas(){
        this.top = new Node();
    }
    
    public PilasEnlazadas(int value){
        Node node = new Node(value);
        this.top = node;
    }
    
    
}


class ColaEnlazada <T>{
    Node rear;
    Node front;
   
    public boolean empty(){
        return rear == null;
    }
    
    
    public void Encolar(Node<Character> element){
        if(!empty()){
            this.rear.setNext(element); //Seteamos a que apunte a este nuevo elemento
            this.rear = element; //Hacemos que ahora la referencia vaya a este nuevo elemento para que ahora sea el nuevo rear
        }
        else{
            this.rear = element;
            this.front = element;
        }
    }
    
    public T Desencolar(){
        if(empty()){
            throw new IllegalArgumentException("Esta vacío pa");
        }
        T data =  (T) front.getData();
        if(this.rear == this.front){ //Si solo hay un elemento  
            front = null;
            rear = null;           
        }else{
             front = front.getNext();
        }      
        return data;
    }
    
    public ColaEnlazada(){
           this.rear = null;
           this.front = null;
       }
    
    public void StringTo(){
        String cad = "[";
            if(empty()){
                cad += "]";
            }else{
                Node m = this.front;
                while(m != this.rear){
                    cad +=  m.getData() + ",";
                    m = m.getNext();
                }
                cad += m.getData() + "]";
            }     
            System.out.println(cad);
    }
    
}