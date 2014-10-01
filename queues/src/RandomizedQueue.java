import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int ENLARGE_FACTOR = 2;
    private static final int SHRINK_FACTOR = 4;

    private int size;
    private Item[] arr;

    public RandomizedQueue() {     
        this.size = 0;
        this.arr = (Item[]) new Object[4];
    }

    public boolean isEmpty()  {
        return size() == 0;
    }
    public int size()       {
        return size;
    }
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        arr[size++] = item;
        enlargeArrayIfRequired();
    }

    private void enlargeArrayIfRequired() {
        if (size >= arr.length) {
            resizeArray(size * ENLARGE_FACTOR); 
        }              
    }
    private int arrLength() {
        return arr.length;
    }
    public Item dequeue()     {              
        // delete and return a random item
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomIndex = generateRandomIndex();
        Item itemToReturn = arr[randomIndex];
        arr[randomIndex] = arr[--size];
        arr[size] = null; // avoid loitering
        shrinkArrayIfRequired();
        return itemToReturn;
    }

    private void shrinkArrayIfRequired() {
        if (size > 0 && size <= (arr.length / SHRINK_FACTOR)) {
            resizeArray(size*ENLARGE_FACTOR);
        }              
    }

    private void resizeArray(final int newSize) {
        Item[] newArr = (Item[]) new Object[newSize];
        System.arraycopy(arr, 0, newArr, 0, size);
        this.arr = newArr;
    }
    public Item sample()  { 
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomIndex = generateRandomIndex();
        return arr[randomIndex];
    }

    private int generateRandomIndex() {
        return StdRandom.uniform(size);
    }
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator(size);
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final int size;
        private int index;
        private int[] randomizedIndices;

        RandomizedQueueIterator(int size) {
            this.size = size;
            this.index = 0;
            this.randomizedIndices = new int[this.size];
            for (int i = 0; i < size; i++) {
                randomizedIndices[i] = i;
            }
            StdRandom.shuffle(randomizedIndices);
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return arr[randomizedIndices[index++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        } 

    }


    public static void main(String[] args) {
     } 
}
