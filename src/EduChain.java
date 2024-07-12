package src;
import java.util.ArrayList;
import com.google.gson.GsonBuilder;

/**
 * The `EduChain` class demonstrates a simple blockchain implementation in Java.
 * It includes methods to validate the blockchain, add blocks, mine them with proof of work,
 * and output the blockchain in JSON format.
 */
public class EduChain {
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 4;

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

    public static void main(String[] args) {
        System.out.println("\n\tDifficulty Level: " + difficulty);

        // The first block is called the genesis block.
        // Because there is no previous block we enter “0” as the previous hash.
        blockchain.add(new Block("Hi im the first block!", "0"));
        System.out.println("Trying to Mine block 1... ");
		blockchain.get(0).mineBlock(difficulty);
		
		blockchain.add(new Block("Yo im the second block",blockchain.get(blockchain.size()-1).hash));
		System.out.println("Trying to Mine block 2... ");
		blockchain.get(1).mineBlock(difficulty);
		
		blockchain.add(new Block("Hey im the third block",blockchain.get(blockchain.size()-1).hash));
		System.out.println("Trying to Mine block 3... ");
		blockchain.get(2).mineBlock(difficulty);	
		
		System.out.println("\nBlockchain is Valid: " + isValidChain());
		
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println("\nThe block chain: ");
		System.out.println(blockchainJson);
    }
}
