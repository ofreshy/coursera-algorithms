public class Subset {


    public static void main(String[] args) {
        int K = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = readFromStdIn(K);
        getKGeneration(rq, K);
    }
    
    private static RandomizedQueue<String> readFromStdIn() {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {          
            String s = StdIn.readString();
            rq.enqueue(s);
        }
        return rq;
    }
    
    private static RandomizedQueue<String> readFromStdIn(int K) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        int i = 0;
        while (!StdIn.isEmpty() && i++ < K) {
            String s = StdIn.readString();
            rq.enqueue(s);
        }
        
        while (!StdIn.isEmpty()) {          
            String s = StdIn.readString();
            int r = StdRandom.uniform(1, i+1);
            if (r <= K) {
                rq.dequeue();
                rq.enqueue(s);
            }
            i++;
        }
        return rq;
    }
    
    private static void getKGeneration(RandomizedQueue<String> rq, int K) {
        for (int i = 0; i < K; i++) {
            System.out.println(rq.dequeue());
        }
    }
}
