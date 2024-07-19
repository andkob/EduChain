package src;
import java.util.ArrayList;
import java.util.Random;

import com.google.gson.GsonBuilder;

/**
 * The `EduChain` class demonstrates a simple blockchain implementation in Java.
 * It includes methods to validate the blockchain, add blocks, mine them with proof of work,
 * and output the blockchain in JSON format.
 */
public class EduChain {
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static enum Mode {INCREMENT, RANDOM, MULTITHREAD};

    // Options
    public static int difficulty = 5; // ** SET DIFFICULTY LEVEL **
    public static int numBlocks = 3; // ** SET NUMBER OF BLOCKS **
    public static Mode miningMode = Mode.MULTITHREAD; // ** SET MINING MODE **

    public static void main(String[] args) {
        int totalAttempts = 0;
        long totalTime = 0;
        System.out.println("\n\tDifficulty Level: " + difficulty);

        // The first block is called the genesis block.
        // Because there is no previous block we enter “0” as the previous hash.
        for (int i = 0; i < numBlocks; i++) {
            String prevHash;
            if (i == 0) {
                prevHash = "0";
            } else {
                prevHash = blockchain.get(blockchain.size()-1).hash;
            }

            // Create block
            blockchain.add(new Block(genRandomString(), prevHash));

            // Mine block
            System.out.println("Trying to Mine block " + i + "... ");
            Block block = blockchain.get(i);
            if (miningMode == Mode.INCREMENT) {
                block.mineBlockIncrement(difficulty);

            } else if (miningMode == Mode.RANDOM) {
                block.mineBlockRandom(difficulty);

            } else if (miningMode == Mode.MULTITHREAD) {
                block.mineBlock(difficulty);
            }

            totalAttempts += block.attempts;
            totalTime += block.miningTime;
        }

		System.out.println("\nBlockchain is Valid: " + isValidChain());
		
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println("\nThe block chain: ");
		System.out.println(blockchainJson);
        System.out.println("\n\n\tTotal Attempts: " + totalAttempts);
        System.out.println("\tTotal Mining Time: " + totalTime + " ms");
    }

    /**
     * Validates the blockchain by checking the hashes and proof of work for each block.
     * This method ensures that the blockchain has not been tampered with and that all blocks
     * have valid proof of work.
     *
     * @return true if the blockchain is valid, false otherwise.
     */
    public static boolean isValidChain() {
        Block currentBlock;
        Block prevBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        // Check all hashes in the blockchain
        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            prevBlock = blockchain.get(i - 1);

            // Compare registered and current hash
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current hashes are not equal!");
                System.out.println("Block #" + i + " has been altered!");
                return false;
            }

            // Compare previous hash and registered previous hash
            if (!prevBlock.calculateHash().equals(currentBlock.prevHash)) {
                System.out.println("Previous hashes are not equal!");
                int blockNum = i - 1;
                System.out.println("Block #" + blockNum + " has been altered!");
                return false;
            }

            //check if hash is solved
			if(!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
        }
        
        return true;
    }

    // Generate a random 10 character string for the block data
    public static String genRandomString() {
        int length = 10;
        int ASCII_START = 33; // !
        int ASCII_END = 126; // ~
        StringBuilder sb = new StringBuilder();
        Random r = new Random();

        for (int i = 0; i < length; i++) {
            int ascii = ASCII_START + r.nextInt(ASCII_END - ASCII_START + 1);
            sb.append((char) ascii);
        }

        return sb.toString();
    }
}
