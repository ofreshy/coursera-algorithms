
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


public class Solver {

    private static final int NO_SOLUTION_MOVES = -1;
    private static final Iterable<GoodBoard> NO_SOLUTION_ITER = null;

    private static final HamComparator HAM = new HamComparator();
    private static final ManComparator MAN = new ManComparator();
    
    private final Comparator<Node> comparator;
    private final BoardSolver original;
    private final BoardSolver equivilance;
    
    private Iterable<GoodBoard> solution;
    private int movesToSolution;
    
    public Solver(GoodBoard initial) {  
        this(initial, true);
    }
    private Solver(GoodBoard initial, boolean useMan) {
        if (useMan) {
            this.comparator     = MAN;
        }
        else {
            this.comparator     = HAM;
        }
        this.original       = new BoardSolver(initial, comparator);
        this.equivilance    = new BoardSolver(initial.twin(), comparator);
        solve();      
    }
    
    private void solve() {
       boolean foundSolution = original.solved() || equivilance.solved();
       //int f5 = 0;
       while (!foundSolution && original.canIter()) {  
           //System.out.println(++f5);
           original.iter();
           if (equivilance.canIter()) {
               equivilance.iter();               
           }
           foundSolution = original.solved() || equivilance.solved();      
       }
       
       if (original.solved()) {
           List<GoodBoard> sol = buildSolution(original.currentNode);
           solution = sol;
           movesToSolution = sol.size()-1;
       }
       else {
           solution = NO_SOLUTION_ITER;
           movesToSolution = NO_SOLUTION_MOVES;
       }        
    }
    private List<GoodBoard> buildSolution(Node currentNode) {
        LinkedList<GoodBoard> sol = new LinkedList<GoodBoard>();
        Node n = currentNode;
        while (n != null) {
            sol.addFirst(n.board);
            n = n.previousNode;
        }
        return sol;
    }
    public boolean isSolvable() {     
        return movesToSolution != NO_SOLUTION_MOVES;        
    }  
    
    public int moves() {     
        return movesToSolution;        
    }
    public Iterable<GoodBoard> solution() { 
        return solution;        
    }
    
    private class BoardSolver {
        private final MinPQ<Node> queue;
        private Node currentNode;
        
        BoardSolver(GoodBoard initialBoard, Comparator<Node> comparator) {
            queue = new MinPQ<Node>(comparator);
            currentNode = newNode(initialBoard, null, 0);                
            queue.insert(currentNode);
        }
        
        Node newNode(GoodBoard board, Node prev, int moves) {
            return new Node(board, prev, moves);
        }
        
        boolean canIter() {
            return !queue.isEmpty();
        }
        
        void iter() {
            currentNode = queue.delMin();
            Node previous = currentNode.previousNode;
            GoodBoard board = currentNode.board;
            int moves = currentNode.movesSoFar + 1;
            GoodBoard motherBoard = null;
            if (previous != null) {
                motherBoard = previous.board;
            }
            for (GoodBoard neighbour : board.neighbors()) {
                if (neighbour.equals(motherBoard)) {
                    continue;
                }
                queue.insert(newNode(neighbour, currentNode, moves));
            }          
        }
        
        boolean solved() {
            return (currentNode.board.isGoal());
        }
        
    }
    
    
    private static class Node {
        private final GoodBoard board;
        private final Node previousNode;
        private final int movesSoFar;
        private int cachedManVal = -1;
        
        public Node(GoodBoard board, Node previousNode, int movesSoFar) {
            this.board = board;
            this.previousNode = previousNode;
            this.movesSoFar = movesSoFar;
        }
        private int manValue() {
            if (cachedManVal == -1) {
                cachedManVal = movesSoFar + board.manhattan();
            }
            return cachedManVal;
        }
        private int hamValue() {
            return movesSoFar + board.hamming();
        }
        @Override
        public String toString() {
            return "Node [board=" + board + ", movesSoFar="
                    + movesSoFar + ", manValue=" + manValue() + "]";
        }
    }
    private static class HamComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return Integer.valueOf(o1.hamValue()).compareTo(o2.hamValue());
        }            
    }
    private static class ManComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return Integer.valueOf(o1.manValue()).compareTo(o2.manValue());
        }            
    }
    
    
    
    
    
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        GoodBoard initial = new GoodBoard(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (GoodBoard board : solver.solution()) 
               StdOut.println(board);
        }
    }
}
