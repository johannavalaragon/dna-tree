# DNA Tree Database

A high-performance custom database built to handle large sequence datasets. This project implements a 5-way branching Trie for prefix-indexed retrieval, optimizing memory usage through structural design patterns rather than relying on standard library structures.

## Technical Highlights

* **Optimized Branching:** Engineered a 5-way branching Trie, intentionally sizing the branching factor to the specific alphabet of the dataset (A, C, G, T, and a $ terminator) rather than defaulting to a standard binary tree.
* **Memory Management:** Reduced peak heap memory usage by **13%** during large-scale sequence ingestion by strictly applying the **Composite** and **Flyweight** design patterns. 
* **Instance Sharing:** Handled polymorphic internal and leaf nodes by collapsing them into shared instances, preventing memory bloat during large-scale data ingestion.

## Tech Stack
* **Language:** Java
* **Concepts:** Data Structures (Tries), Object-Oriented Design, Memory Optimization
* **Design Patterns:** Composite, Flyweight

## Setup and Installation
To run this project locally, ensure you have the Java Development Kit (JDK) installed.

1. Clone the repository and navigate into the project directory:
   ```bash
   git clone https://github.com/johannavalaragon/dna-tree.git
   cd dna-tree
2. Compile the Java files:
   ```bash
   javac *.java
   ```

## Usage
The program operates via a command-line interface and reads commands from a provided text file.

**Execution Command:**
```bash
java DNAProj [input-file.txt]
```

**Example Execution:**
```bash
java DNAProj input.txt
```
