import java.util.LinkedList;
import java.util.List;

/**
 * This is my preferred implementation of the board which uses
 * an inner class Index in order navigate .
 * 
 * Unfortunately, 
 * it does not pass most of the memory tests as it 
 * requires 24 Bytes! more than allowed
 * ( A constant factor )
 * 
 * @author offer
 *
 */
public class GoodBoard {

    private static final int SPACE = 0;

    /**
     * Constant fields
     */
    private final int N;
    private final int[][] blocks;

    // Cached values
    private int hamming = -1;
    private int manhattan = -1;
    private Index spaceIndex = null;

    public GoodBoard(int[][] blocks) {
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
        int expectedBlock = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                expectedBlock++;
                int actualBlock = blocks[i][j];

                if (actualBlock == SPACE)
                    continue;                

                if (i == N-1 && j == N -1) {
                    expectedBlock = SPACE; 
                }
                if (expectedBlock != actualBlock) {
                    hamming++;
                }
            }
        }
        return hamming;
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
        return hamming() == 0;
    }

    public GoodBoard twin() { 
        GoodBoard twin = new GoodBoard(blocks);
        boolean lookingForTwin = true;
        for (int i = N-1; lookingForTwin && i >= 0; i--) {
            for (int j = 0; lookingForTwin && j < N-1; j++) {
                Index i1 = new Index(i , j);
                Index i2 = new Index(i, j+1);
                if (get(i1) != SPACE && get(i2) != SPACE) {
                    twin = exchange(i1, i2);
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
        GoodBoard other = (GoodBoard) obj;
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

    public Iterable<GoodBoard> neighbors() { // all neighboring boards
        spaceIndex = findSpaceIndex();
        List<GoodBoard> neighbours = new LinkedList<GoodBoard>();
        if (spaceIndex.canGoDown()) {
            neighbours.add(exchange(spaceIndex, spaceIndex.goDown())); 
        }
        if (spaceIndex.canGoUp()) {
            neighbours.add(exchange(spaceIndex, spaceIndex.goUp()));
        }
        if (spaceIndex.canGoRight()) {
            neighbours.add(exchange(spaceIndex, spaceIndex.goRight()));
        }
        if (spaceIndex.canGoLeft()) {
            neighbours.add(exchange(spaceIndex, spaceIndex.goLeft()));
        }
        return neighbours;
    }

    private int get(Index i) {
        return blocks[i.row][i.col];
    }

    private Index findSpaceIndex() {
        if (spaceIndex != null) {
            return spaceIndex;
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == SPACE) {
                    spaceIndex = new Index(i, j);
                }
            }
        }
        return spaceIndex;
    }


    private GoodBoard exchange(Index i1, Index i2) {
        int block = blocks[i1.row][i1.col];
        GoodBoard newBoard = new GoodBoard(blocks);
        newBoard.blocks[i1.row][i1.col] = newBoard.blocks[i2.row][i2.col];
        newBoard.blocks[i2.row][i2.col] = block;
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

    private class Index {
        private final int row;
        private final int col;
        Index(int row, int col) { 
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Index other = (Index) obj;

            if (col != other.col)
                return false;
            if (row != other.row)
                return false;
            return true;
        }

        public String toString() {
            return "("+row+","+col+")"; 
        }

        private boolean canGoUp() {
            return row > 0;
        }
        private boolean canGoDown() {
            return row < (N-1);
        }
        private boolean canGoRight() {
            return col < (N-1);
        }
        private boolean canGoLeft() {
            return col > 0;
        }
        private Index goUp() {
            return new Index(row-1, col);
        }
        private Index goDown() {
            return new Index(row+1, col);
        }
        private Index goRight() {
            return new Index(row, col+1);
        }
        private Index goLeft() {
            return new Index(row, col-1);
        }
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
        GoodBoard b1 = fromString(s1);
        System.out.println(b1);
        assert (b1.N == 3);
        assert (!b1.isGoal());
        assert (b1.blocks[0][0] == SPACE);

        assert (b1.hamming() == 4);
        assert (b1.manhattan() == 4);

        Index firstIndex = b1.findSpaceIndex();
        assert (firstIndex.equals(b1.new Index(0 , 0)));
        assert (!firstIndex.canGoLeft());
        assert (firstIndex.canGoRight());
        assert (!firstIndex.canGoUp());
        assert (firstIndex.canGoDown());
        assert (firstIndex.goRight().equals(b1.new Index(0 , 1)));
        assert (firstIndex.goDown().equals(b1.new Index(1 , 0)));

        Index oneRight = firstIndex.goRight();
        GoodBoard b2 = b1.exchange(firstIndex, firstIndex.goRight());
        assert (b2.get(firstIndex) == 1);
        assert (b2.get(oneRight) == 0);
        assert (b2.findSpaceIndex().equals(oneRight));
    }

    private static void testCase2() {
        String s = "8  1  3  4  0  2  7  6  5";
        GoodBoard b = fromString(s);
        System.out.println(b);
        assert (b.N == 3);
        assert (!b.isGoal());
        assert (b.hamming() == 5);
        assert (b.manhattan() == 10);

        Index spaceIndex = b.findSpaceIndex();
        assert (spaceIndex.equals(b.new Index(1, 1)));
        assert (spaceIndex.canGoLeft());
        assert (spaceIndex.canGoRight());
        assert (spaceIndex.canGoUp());
        assert (spaceIndex.canGoDown());
    }

    /**
     * Goal  
     */
    private static void testCase3() {
        String s = "1  2  3  4  5  6  7  8  0";
        GoodBoard b = fromString(s);
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
        GoodBoard b = fromString(s);
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
        GoodBoard b = fromString(s);
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
        GoodBoard b = fromString(s);
        System.out.println(b.twin());
        assertBoard(b.twin(), "1  2  3  4  5  6  7  8  0");
    }
    
    /**
     * Bad neighbours
     */
    private static void testCase7() {
        String s = "1  2  3  7  0  6  5  4  8 ";
        GoodBoard b = fromString(s);
        System.out.println(b);
        System.out.println(b.neighbors());
    }


    private static GoodBoard fromString(String s) {
        String[] parsed = s.trim().split("  ");
        int N = (int) Math.sqrt(parsed.length);
        int[][] blocks = new int[N][N];
        int index = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = Integer.parseInt(parsed[index++]);
            }
        }
        return new GoodBoard(blocks);
    }
    
    private static void assertBoard(GoodBoard actual, String s) {
        GoodBoard expected = fromString(s);
        assert (expected.equals(actual));
    }


}
