package src;
import java.util.Date;

public class Block {
    public String hash;
    public String prevHash;
    private String data; // Just a simple message for now
    private long timeStamp; // repped as the number of milliseconds since January 1, 1970
    private int nonce;

    /**
     * Block Constructor
     */
    public Block(String data, String prevHash) {
        this.data = data;
        this.prevHash = prevHash;
        this.timeStamp = new Date().getTime();
        this.nonce = 0; // start nonce at 0
        this.hash = calculateHash(); // calculate the hash with the new values
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
            Integer.toString(nonce) +
            data
            );
        return calculatedHash;
    }

    /**
     * Mines the block to meet the specified difficulty.
     * This method performs the proof-of-work algorithm by incrementing the nonce
     * until the hash of the block starts with a certain number of leading zeros.
     * The number of leading zeros is determined by the difficulty parameter.
     *
     * @param difficulty The number of leading zeros required in the hash for the block to be considered mined.
     */
    public void mineBlock(int difficulty) {
        // Create a string with 'difficulty' number of '0' characters
        String target = new String(new char[difficulty]).replace('\0', '0');

        // Continuously increment the nonce and recalculate the hash
        // until the hash starts with 'difficulty' number of '0' characters
        while(!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
    }
}