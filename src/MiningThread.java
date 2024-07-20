package src;
import java.util.concurrent.atomic.AtomicBoolean;

class MiningThread extends Thread {
    private final Block block;
    private final int difficulty;
    private final long startNonce;
    private final long endNonce;
    private final AtomicBoolean found;
    public long solution = -1;

    public MiningThread(Block block, int difficulty, long startNonce, long endNonce, AtomicBoolean found) {
        this.block = block;
        this.difficulty = difficulty;
        this.startNonce = startNonce;
        this.endNonce = endNonce;
        this.found = found;
    }

    @Override
    public void run() {
        String target = new String(new char[difficulty]).replace('\0', '0');
        for (long nonce = startNonce; nonce < endNonce && !found.get(); nonce++) {
            String hash = block.calculateHash(nonce);
            if (hash.substring(0, difficulty).equals(target)) {
                solution = nonce;
                found.set(true);
                break;
            }
        }
    }
}
