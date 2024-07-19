package src;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Block {
    public String hash;
    public String prevHash;
    private String data; // Just a simple message for now
    private long timeStamp; // repped as the number of milliseconds since January 1, 1970
    private long nonce;

    // test data
    public int attempts;
    public long miningTime;

    /**
     * Block Constructor
     */
    public Block(String data, String prevHash) {
        this.data = data;
        this.prevHash = prevHash;
        this.timeStamp = new Date().getTime();
        this.nonce = 0; // start nonce at 0
        this.hash = calculateHash(); // calculate the hash with the new values

        attempts = 0;
        miningTime = 0;
    }

    /**
     * Calculates the hash for the block using SHA-256.
     * The hash is based on the block's previous hash, timestamp, nonce, and data.
     *
     * @return The calculated hash as a hexadecimal string.
     */
    public String calculateHash() {
        // Concatenate the previous hash, timestamp, nonce, and data to form the input for the hash function
        String calculatedHash = Util.applySha256(
            prevHash +
            Long.toString(timeStamp) +
            
            Long.toString(nonce) +
            data
            );
        return calculatedHash;
    }

    public String calculateHash(long nonce) {
        return Util.applySha256(
            prevHash +
            Long.toString(timeStamp) +
            Long.toString(nonce) +
            data
        );
    }

    public void mineBlock(int difficulty) {
        int threadCount = Runtime.getRuntime().availableProcessors(); // Use available CPU cores
        long noncesPerThread = Long.MAX_VALUE / threadCount;
        AtomicBoolean found = new AtomicBoolean(false);
        List<MiningThread> threads = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            long startNonce = i * noncesPerThread;
            long endNonce = (i == threadCount - 1) ? Long.MAX_VALUE : (i + 1) * noncesPerThread;
            MiningThread thread = new MiningThread(this, difficulty, startNonce, endNonce, found);
            threads.add(thread);
            thread.start();
        }

        long solution = -1;
        for (MiningThread thread : threads) {
            try {
                thread.join();
                if (thread.solution != -1) {
                    solution = thread.solution;
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();

        if (solution != -1) {
            this.nonce = solution;
            this.hash = calculateHash(solution);
            System.out.println("Block Mined!!! : " + hash);
            System.out.println("Mining took: " + (endTime - startTime) + " ms");
        } else {
            System.out.println("Mining failed to find a solution.");
        }
    }

    /**
     * Mines the block to meet the specified difficulty.
     * This method performs the proof-of-work algorithm by incrementing the nonce
     * until the hash of the block starts with a certain number of leading zeros.
     * The number of leading zeros is determined by the difficulty parameter.
     *
     * @param difficulty The number of leading zeros required in the hash for the block to be considered mined.
     */
    public void mineBlockIncrement(int difficulty) {
        // Create a string with 'difficulty' number of '0' characters
        String target = new String(new char[difficulty]).replace('\0', '0');
        long startTime = System.currentTimeMillis();
        int attempts = 0;

        // Continuously increment the nonce and recalculate the hash
        // until the hash starts with 'difficulty' number of '0' characters
        while(!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = calculateHash();
            attempts++;
		}
		
        long endTime = System.currentTimeMillis();
        System.out.println("Block Mined!!! : " + hash);
        System.out.println("Mining took: " + (endTime - startTime) + " ms");
        System.out.println("Attempts: " + attempts);

        this.attempts = attempts;
        miningTime = endTime - startTime;
    }

    public void mineBlockRandom(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        long startTime = System.currentTimeMillis();
        int attempts = 0;
        
        Random r = new Random();
        while(!hash.substring(0, difficulty).equals(target)) {
            nonce = r.nextInt(Integer.MAX_VALUE);
            hash = calculateHash();
            attempts++;
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("Block Mined!!! : " + hash);
        System.out.println("Mining took: " + (endTime - startTime) + " ms");
        System.out.println("Attempts: " + attempts);
        
        this.attempts = attempts;
        miningTime = endTime - startTime;
    }
}