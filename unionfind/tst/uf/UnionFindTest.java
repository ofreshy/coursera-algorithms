package uf;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class UnionFindTest {

	private final int N = 10;
	
	private List<UnionFind> models;
	
 	private final int[][] unionCmd1 	= {{1,2}, {1,3}, {1,4}, {1,5}, {1,6}, {1,7}, {1,8}, {1,9}};
	private final int[][] connected1	= {{2,9}};
	private final int[][] disconnected1	= {};
	
	
	private final int[][] unionCmd2 	= {};
	private final int[][] connected2	= {};
	private final int[][] disconnected2	= {{1,2}, {1,3}, {1,4}, {1,5}, {1,6}, {1,7}, {1,8}, {1,9}};
	
	private final int[][] unionCmd3 	= {{1,2}, {1,3}, {3,5}, {7,8}, {8,9}};
	private final int[][] connected3	= {{2,3}, {1,3}, {2,5}, {7,9}};
	private final int[][] disconnected3	= {{1,8}, {1,6}, {6,7}};
	
	@Before
	public void setUp() throws Exception{ 
		models = Arrays.asList(
				new QuickFind(N),
				new QuickUnion(N),
				new WeightedUnionFind(N)
		);
	}
	
	private static UnionFind connect( UnionFind uf, int[][] unions ){
		for ( int[] pq : unions ){
			uf.union(pq[0], pq[1]);
		}
		return uf;
	}
	
	
	@Test
	public void testAllConnected() {
		for ( UnionFind model : models ){
			// Test all connections
			testModel( model, unionCmd1, connected1, disconnected1);
		}
	}
	
	@Test
	public void testNoneConnected() {
		for ( UnionFind model : models ){
			testModel( model, unionCmd2, connected2, disconnected2);
		}
	}
	
	@Test
	public void testSomeConnected() {
		for ( UnionFind model : models ){
			testModel( model, unionCmd3, connected3, disconnected3);
		}
	}
	
	private void testModel( UnionFind model, int[][] unions, int[][] connected, int[][] disconnected){
		connect(model, unions);
		assertModelConnections(model , connected, true);
		assertModelConnections(model, disconnected, false);
	}

	private void assertModelConnections(UnionFind model, int[][] connections, boolean testConnection) {
		for ( int[] pq : connections ){
			int p = pq[0];
			int q = pq[1];
			boolean connected = model.connected(p, q);
			String msg = model.getClass().getSimpleName() + " failed on p="+p+",q="+q;
			if ( testConnection )
				assertTrue( msg,  connected);
			else{
				assertFalse( msg, connected);
			}
		}
	}

}
