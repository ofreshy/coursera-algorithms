import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author offer
 *
 */
public class Board {

    private static final int SPACE = 0;

    /**
     * Constant fields
     */
    private final int N;
    private final int[][] blocks;

    // Cached values
    private int hamming = -1;
    private int manhattan = -1;

    public Board(int[][] blocks) {
        this.N = blocks.length;
        this.blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }       
    }

    public int dimension() { // board dimension N
        return N;
    }

    public int hamming() { 
        // number of blocks out of place
        if (hamming != -1) { 
            return hamming;
        }
        hamming = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int expectedBlock = getExpectedBlock(i, j);
                int actualBlock = blocks[i][j];

                if (actualBlock == SPACE)
                    continue;                
                
                if (expectedBlock != actualBlock) {
                    hamming++;
                }
            }
        }
        return hamming;
    }
    
    private int getExpectedBlock(int i, int j) {
        if (i == N-1 && j == N-1) {
            return SPACE; 
        }
        return i * N + j + 1;
    }

    public int manhattan() { 
        if (manhattan != -1) {
            return manhattan;
        }
        manhattan = 0;
        // sum of Manhattan distances between blocks and goal
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                manhattan += calcManhattanDistance(i, j , blocks[i][j]);
            }
        }

        return manhattan;
    }
    private int calcManhattanDistance(int i, int j, int block) {         
        if (block == SPACE) {
            return 0;
        }
        int ii = (block - 1) / N;
        int jj = (block - 1) % N;
        return (Math.abs(ii-i) + Math.abs(jj-j));
    }

    public boolean isGoal() { // is this board the goal board?
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int expectedBlock = getExpectedBlock(i, j);
                int actualBlock = blocks[i][j];
                if (actualBlock != expectedBlock)
                    return false;                
            }
        }
        return true;
    }

    public Board twin() { 
        Board twin = new Board(blocks);
        boolean lookingForTwin = true;
        for (int i = N-1; lookingForTwin && i >= 0; i--) {
            for (int j = 0; lookingForTwin && j <= N-2; j++) {
                
                if (blocks[i][j] != SPACE && blocks[i][j+1] != SPACE) {
                    twin = exchange(i, j, i, j+1);
                    lookingForTwin = false;
                }
            }
        }
        // a board obtained by exchanging two adjacent blocks in the same row
        return twin;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Board other = (Board) obj;
        if (N != other.N)
            return false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != other.blocks[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() { // all neighboring boards
        int i = 0 , j = 0;
        while (blocks[i][j] != SPACE) {
            j++;
            if (j == N) {
                j = 0;
                i++;
            }
        }
        
        List<Board> neighbours = new LinkedList<Board>();
        // if can go down
        if (i < N-1) {
            neighbours.add(exchange(i, j, i+1, j)); 
        }
        // if can go up
        if (i > 0) {
            neighbours.add(exchange(i, j, i-1, j)); 
        }
        // if can go right
        if (j < N-1) {
            neighbours.add(exchange(i, j, i, j+1)); 
        }
        // if can go left
        if (j > 0) {
            neighbours.add(exchange(i, j, i, j-1)); 
        }
        return neighbours;
    }

   
    private Board exchange(int i, int j , int ii, int jj) {
        int block = blocks[i][j];
        Board newBoard = new Board(blocks);
        newBoard.blocks[i][j] = newBoard.blocks[ii][jj];
        newBoard.blocks[ii][jj] = block;
        return newBoard;       
    }

    public String toString() { 
        StringBuilder sb = new StringBuilder(N+"").append("\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(" ").append(blocks[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    

    public static void main(String[] args) {
        testCase1();
        testCase2();
        testCase3();
        testCase4();
        testCase5();
        testCase6();
        testCase7();
    }

    private static void testCase1() {
        String s1 = "0  1  3  4  2  5  7  8  6";
        Board b1 = fromString(s1);
        System.out.println(b1);
        assert (b1.N == 3);
        assert (!b1.isGoal());
        assert (b1.blocks[0][0] == SPACE);

        assert (b1.hamming() == 4);
        assert (b1.manhattan() == 4);
        
        String[] neighbours = {
                "1  0  3  4  2  5  7  8  6", 
                "4  1  3  0  2  5  7  8  6"};       
        testNeighbours(s1, neighbours);
        
       
    }
    
    private static void testNeighbours(String board, String[] neighbours) {
        Board b = fromString(board);
        List<Board> ns = new ArrayList<Board>(neighbours.length);
        for (String n : neighbours) {
            ns.add(fromString(n));
        }
        int actualNum = 0;
        for (Board neighbour : b.neighbors()) {
            actualNum++;
            assert (ns.contains(neighbour));            
        }
        assert (actualNum == ns.size());
    }

    private static void testCase2() {
       
        testNeighbours("0  1  3  4  2  5  7  8  6", 
                new String[]{ 
                "1  0  3  4  2  5  7  8  6",  
                "4  1  3  0  2  5  7  8  6"});
       
        testNeighbours("8  1  3  4  0  2  7  6  5", 
                new String[]{ 
                "8  0  3  4  1  2  7  6  5",  
                "8  1  3  4  6  2  7  0  5",
                "8  1  3  0  4  2  7  6  5",
                "8  1  3  4  2  0  7  6  5"});        
    }

    /**
     * Goal  
     */
    private static void testCase3() {
        String s = "1  2  3  4  5  6  7  8  0";
        Board b = fromString(s);
        System.out.println(b);
        assert (b.N == 3);
        assert (b.isGoal());
        assert (b.hamming() == 0);
        assert (b.manhattan() == 0);
    }

    /**
     * N = 2 
     */
    private static void testCase4() {
        String s = "1  2  3  0";
        Board b = fromString(s);
        System.out.println(b);
        assert (b.N == 2);
        assert (b.isGoal());
        assert (b.hamming() == 0);
        assert (b.manhattan() == 0);
    }

    /**
     * N = 4 
     */
    private static void testCase5() {
        String s = "1  2  3  4  5  6  7  8  9  10  11  13  12  14  15  0";
        Board b = fromString(s);
        System.out.println(b);
        assert (b.N == 4);
        assert (!b.isGoal());
        assert (b.hamming() == 2);
        assert (b.manhattan() == 8);
    }
    
    /**
     * Non solvable, so replace first two adjacent blocks  
     */
    private static void testCase6() {
        String s = "1  2  3  4  5  6  8  7  0";
        Board b = fromString(s);
        System.out.println(b.twin());
        assertBoard(b.twin(), "1  2  3  4  5  6  7  8  0");
        
        s = "1  2  3  0";
        b = fromString(s);
        System.out.println(b.twin());
        assertBoard(b.twin(), "2  1  3  0");
    }
    
    /**
     * Bad neighbours
     */
    private static void testCase7() {
        String s = "1  2  3  7  0  6  5  4  8 ";
        Board b = fromString(s);
        System.out.println(b);
        System.out.println(b.neighbors());
    }


    private static Board fromString(String s) {
        String[] parsed = s.trim().split("  ");
        int N = (int) Math.sqrt(parsed.length);
        int[][] blocks = new int[N][N];
        int index = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = Integer.parseInt(parsed[index++]);
            }
        }
        return new Board(blocks);
    }
    
    private static void assertBoard(Board actual, String s) {
        Board expected = fromString(s);
        assert (expected.equals(actual));
    }


}
