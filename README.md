# Simple Blockchain in Java

This project implements a basic blockchain in Java. It is designed to help understand the fundamental concepts of blockchain technology, including hashing, proof-of-work, and chain validation.

## Features

- Block creation with timestamp, previous hash, data, and nonce
- SHA-256 hashing for block security
- Proof-of-work algorithm with adjustable difficulty
- Blockchain validation to ensure data integrity
- Serialization of the blockchain to JSON format for easy inspection

## Prerequisites

- Java Development Kit (JDK) 8 or higher

## Installation

1. Clone the repository:

    ```bash
    $ git clone https://github.com/andkob/EduChain.git
    $ cd EduChain
    ```

## Compiling and Running

### Manually
1. Compile the project:

    ```bash
    $ javac -d bin -cp "./lib/*" src/*.java
    ```

2. Run the project:

    ```bash
    $ java -cp "./bin;./lib/*" src.EduChain
    ```

### Using Provided Shell Script
1. Compile and run the project:

    ```bash
    $ ./run-demo.sh
    ```


## Block Class
The `Block` class represents a single block in the blockchain. Each block contains the following properties:

| Field | Description |
|-------|--------------|
| hash | The current block's hash |
| prevHash | The previous block's hash |
| data | The block's data |
| timeStamp | The time when the block was created |
| nonce |The number used for mining |


### Block Methods
- **Block(String data, String prevHash)**: Constructor to initialize the block with data and previous hash.
- **String calculateHash()**: Calculates the hash for the block.
- **void mineBlock(int difficulty)**: Mines the block by finding a hash that meets the difficulty criteria.

## Utility Class
The `Util` class provides utility methods for the blockchain.

### Util Methods
- **static String applySha256(String input)**: Applies the SHA-256 hash function to the input string and returns the resulting hash as a hexadecimal string.

## Demo Class: EduChain.java
The `EduChain` class demonstrates a simple blockchain implementation in Java. It includes methods to validate the blockchain, add blocks, mine them with proof of work, and output the blockchain in JSON format.

The difficulty level for this demonstration can be changed by adjusting the value of the static variable `difficulty`. Example:
> public static int difficulty = 4;

## Acknowledgements
- [Google Gson](https://github.com/google/gson) for JSON serialization
- [SHA-256 Algorithm](https://en.wikipedia.org/wiki/SHA-2) for hashing