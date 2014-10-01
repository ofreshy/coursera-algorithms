import uf.QuickFind;
import uf.QuickUnion;
import uf.UnionFind;
import uf.WeightedUnionFind;


public class Client {

    
    
    private static UnionFind run( UnionFind uf, String input ){
        String[] commands = input.trim().split(" ");
        for ( String cmd : commands ){
            String[] pq =  cmd.split("-");
            int p = Integer.parseInt(pq[0]);
            int q = Integer.parseInt(pq[1]);
            uf.union(p, q);
        }
        return uf;
    }
    
    public static void main( String[] args ){
        String cmds = "1-2 2-9 7-8 5-3 1-6 3-8 0-6 9-8 4-0 ";
        UnionFind uf = newWeightedUnionFind();
        uf = run( uf, cmds);
        System.out.println ( uf );
    }

    private static UnionFind newQuickFind() {
        return new QuickFind(10);
    }
    private static UnionFind newQuickUnion() {
        return new QuickUnion(10);
    }
    private static UnionFind newWeightedUnionFind() {
        return new WeightedUnionFind(10);
    }
}
