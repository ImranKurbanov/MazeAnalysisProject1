# Maze Generation and Solving Project

## Team Members
* Imran Kurbanov
* Sadık Ahmet Kara

## Project Overview
This JavaFX application demonstrates the generation and solving of mazes using various algorithms. It features a graphical user interface (GUI) to visualize the maze creation process and the pathfinding steps.

## Features
* **Generators:**
    * Recursive Backtracker (Stack-based)
    * Prim's Algorithm (Priority Queue-based)
* **Solvers:**
    * Depth-First Search (DFS)
    * Breadth-First Search (BFS)
    * A* Search (A-Star)
* **Custom Data Structures:**
    * `MyStack`: Generic stack implementation.
    * `MyQueue`: Generic queue implementation.
    * `MyPriorityQueue`: Generic binary heap priority queue.
* **Empirical Analysis:** Includes a benchmark runner to test performance across multiple trials and sizes.

## Project Structure
* `src/main/java/com/example/mazeproject/algorithms`: Core logic for generators and solvers.
* `src/main/java/com/example/mazeproject/datastructures`: Custom implementations of Stack, Queue, and PriorityQueue.
* `src/main/java/com/example/mazeproject/model`: The `Cell` class representing grid nodes.
* `src/main/java/com/example/mazeproject/ui`: JavaFX Controller and Application class.
* `src/main/java/com/example/mazeproject/BenchmarkRunner.java`: Script for running performance tests.

## How to Run
### Prerequisites
* Java JDK 21 or higher.
* Maven.

### Running the GUI Application
1. Open the project in IntelliJ IDEA.
2. Navigate to `src/main/java/com/example/mazeproject/ui/HelloApplication.java`.
3. Right-click and select **Run 'HelloApplication.main()'**.

### Running the Benchmark Test
1. Navigate to `src/main/java/com/example/mazeproject/BenchmarkRunner.java`.
2. Right-click and select **Run 'BenchmarkRunner.main()'**.
3. Results will be saved to `benchmark_results.csv` in the project root.

## Reproducing Experiments
To reproduce the graphs in the report:
1. Run `BenchmarkRunner`.
2. Open `benchmark_results.csv` in Excel.
3. Use PivotTables to average the `SolveTime` and `MemoryUsed` columns by `Size` and `Solver`.

## Project Demo
I have recorded a video demonstration of the project features, including the generators and solvers.

[▶️ Click here to watch the Demo Video](https://drive.google.com/file/d/159FrYAHGYjdkPrg_Ba2vcX77ZXaqNc-9/view?usp=sharing)