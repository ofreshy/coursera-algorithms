import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class PercTest {

    @Test
    public void testNewPercolationObjectNeverPercolates() {
        for ( int i=1; i<10; i++){
            Percolation p = new Percolation(i);


            assertFalse( p.percolates());
        }
    }

    @Test
    public void testPercolationWhenNIs1() {
        Percolation p = new Percolation(1);


        p.open(1, 1);


        assertTrue( p.isOpen(1, 1));
        assertTrue( p.isFull(1, 1));
        assertTrue( p.percolates());
    }

    @Test
    public void testPercolationWhenNIs2() {
        Percolation p = new Percolation(2);


        p.open(1, 1);
        p.open(2, 2);


        assertFalse( p.percolates());
    }
}
