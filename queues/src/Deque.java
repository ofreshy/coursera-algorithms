import java.util.Iterator;
import java.util.NoSuchElementException;



/**
 * Array based implementation of the Deque
 * @author offer
 *
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {
   
    private Node firstNode;
    private Node lastNode;
    private int size;

    public Deque() {
        this.size = 0;
        firstNode = null;
        lastNode = null; 
    }
    
    public boolean isEmpty()  {              
       return size == 0;
    }
    public int size() {                 
        return size;
    }
    public void addFirst(Item item) {    
        checkNonNullItem(item);
        if (isEmpty()) {
            firstNode   = new Node(item, null, null);
            lastNode    = firstNode;
        }
        else {
            Node nowSecond = firstNode;
            firstNode = new Node(item, nowSecond, null);
            nowSecond.previous = firstNode;
        }
        size++;
    }

    public void addLast(Item item) {       
        checkNonNullItem(item);
        if (isEmpty()) {
            firstNode   = new Node(item, null, null);
            lastNode    = firstNode;
        }
        else {
            Node previousLast = lastNode;
            lastNode = new Node(item, null, previousLast);
            previousLast.next = lastNode;
        }
        size++;
    }
    
    private void checkNonNullItem(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
    }
    
    /**
     * 
     * @return
     */
    public Item removeFirst() {            
        if (isEmpty()) {
            throw new NoSuchElementException(
                    " cannot remove an item from an empty queue ");
        }
        Item item = firstNode.item;
        if (lastNode == firstNode) {
            lastNode = null;
            firstNode = null;
        }
        else {
            firstNode = firstNode.next;
            firstNode.previous = null;
        }
        size--;
        return item;
    }
    
   
    /**
     * 
     * @return
     */
    public Item removeLast() {              // delete and return the item at the end
        if (isEmpty()) {
            throw new NoSuchElementException(
                    " cannot remove an item from an empty queue ");
        }
        Item item = lastNode.item;
        if (lastNode == firstNode) {
            lastNode = null;
            firstNode = null;
        }
        else {
            lastNode = lastNode.previous;
            lastNode.next = null;
        }
        size--;
        return item;
    }
    
    
    
    public Iterator<Item> iterator() {       
        // return an iterator over items in order from front to end
        return new DequeIterator(firstNode);
    }
    public static void main(String[] args) {  // unit testing

    }
    
    private class Node {
        private Item item;
        private Node next;
        private Node previous;
        Node(Item item, Node next, Node previous) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
        @Override
        public String toString() {
            return "Node [item=" + item 
                    + ", next=" + extractItemString(next)
                    + ", previous=" + extractItemString(previous) 
                    + "]";
        }
        private String extractItemString(Node n) {
            if (n == null) {
                return "null";
            }
            return next.item.toString(); 
        }  
    }
    
    
    
    private class DequeIterator implements Iterator<Item> {
        private Node current;
        
        DequeIterator(Node current) {
            this.current = current;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
           throw new UnsupportedOperationException();
        }
        
    }
}