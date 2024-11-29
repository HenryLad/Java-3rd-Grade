package stock;

public class StockMovementList {

    private Node head;
    private Node tail;


    public StockMovementList() {
        head = tail = null;
        tail = null;
    }

    public void put(ValuedStockMovement valuedStockMovement){
        Node newNode = new Node(valuedStockMovement);

        if(head == null){
            // List ist Leer
            head = newNode;
            // List ist nicht mehr leer
        }
        else{
            tail.next = newNode;
        }
        tail = newNode;

    }

    @Override
    public String toString() {
        String result = "";
        Node node = head;
        while(node != null)
        {
            result += node.ValuedStockMovement.toString() + "\n";
            node = node.next;
        }
        return result;
    }

    private class Node{
        ValuedStockMovement ValuedStockMovement;
        Node next;


        Node (ValuedStockMovement ValuedStockMovement)
        {
            this.ValuedStockMovement = ValuedStockMovement;
        }
    }








}
