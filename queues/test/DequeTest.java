import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;


public class DequeTest {

    @Test
    public void testEmptyDq() {     
        Deque<Integer> dq = new Deque<Integer>();
        assertEquals(0, dq.size());
        assertTrue(dq.isEmpty());
    }
    
    @Test
    public void testAddToEmptyDq() {
        Deque<Integer> dq = new Deque<Integer>();
        
        dq.addFirst(1);
        assertEquals(1, dq.size());
        assertFalse(dq.isEmpty());        
    }
    
    @Test
    public void testAddAndRemoveToEmptyDq1() {
        Deque<Integer> dq = new Deque<Integer>();
        dq.addFirst(1);
        dq.removeFirst();
        
        assertEquals(0, dq.size());
        assertTrue(dq.isEmpty());
    }
    @Test
    public void testAddAndRemoveToEmptyDq2() {
        Deque<Integer> dq = new Deque<Integer>();
        dq.addFirst(1);
        dq.removeLast();
        
        assertEquals(0, dq.size());
        assertTrue(dq.isEmpty());
    }
    @Test
    public void testAddAndRemoveToEmptyDq3() {
        Deque<Integer> dq = new Deque<Integer>();
        
        dq.addFirst(1);
        dq.addFirst(2);
        dq.addLast(3);
        dq.addLast(4);
             
        while (!dq.isEmpty()) {
            dq.removeFirst();
        }
        assertEquals(0, dq.size());
        assertTrue(dq.isEmpty());
    }
    @Test
    public void testAddAndRemoveToEmptyDq4() {
        Deque<Integer> dq = new Deque<Integer>();
        
        dq.addFirst(1);
        dq.addFirst(2);
        dq.addLast(3);
        dq.addLast(4);
             
        while (!dq.isEmpty()) {
            dq.removeLast();
        }
        assertEquals(0, dq.size());
        assertTrue(dq.isEmpty());
    }
    @Test
    public void testIterator() {
        Deque<Integer> dq = new Deque<Integer>();
        
        dq.addLast(1);
        dq.addLast(2);
        dq.addLast(3);
        dq.addLast(4);
        dq.addFirst(0);
        int[] expectedArr = {0, 1, 2, 3, 4};
        
        Iterator<Integer> it = dq.iterator();
        int expectedIndex = 0;
        while (it.hasNext()) {
            int actual = it.next();
            int expected = expectedArr[expectedIndex++]; 
            assertEquals(expected, actual);
        }
    }

}
