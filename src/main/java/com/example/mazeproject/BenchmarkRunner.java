package com.example.mazeproject;

import com.example.mazeproject.algorithms.*;
import com.example.mazeproject.model.Cell;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BenchmarkRunner {

    private static final int[] SIZES = {10, 50, 100, 200};
    private static final int TRIALS = 10;
    private static final String CSV_FILE = "benchmark_results.csv";

    public static void main(String[] args) {

        System.out.println("Starting Empirical Analysis...");

        try (FileWriter writer = new FileWriter(CSV_FILE)) {

            writer.write("Generator,Solver,Size,Trial,GenTime(ms),SolveTime(ms),PathLength,MemoryUsed(KB)\n");

            for (int size : SIZES) {
                runGeneratorTests(writer, size, new RecursiveBacktracker(), "RecursiveBacktracker");
                runGeneratorTests(writer, size, new PrimsGenerator(), "Prims");
            }

            System.out.println("Benchmark complete. Results saved to " + CSV_FILE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runGeneratorTests(
            FileWriter writer,
            int size,
            MazeGenerator generator,
            String genName
    ) throws IOException {

        for (int trial = 1; trial <= TRIALS; trial++) {

            Cell[][] baseGrid = createGrid(size);

            long genStart = System.nanoTime();
            generator.generate(baseGrid, size, size);
            long genEnd = System.nanoTime();
            double genTimeMs = (genEnd - genStart) / 1_000_000.0;

            runSolver(writer, genName, "DFS", size, trial, genTimeMs, baseGrid);
            runSolver(writer, genName, "BFS", size, trial, genTimeMs, baseGrid);
            runSolver(writer, genName, "AStar", size, trial, genTimeMs, baseGrid);
        }
    }

    private static void runSolver(
            FileWriter writer,
            String genName,
            String solverName,
            int size,
            int trial,
            double genTimeMs,
            Cell[][] originalGrid
    ) throws IOException {

        Cell[][] grid = cloneGrid(originalGrid);

        MazeSolver solver = getSolver(solverName);
        Cell start = grid[0][0];
        Cell end = grid[size - 1][size - 1];

        System.gc();

        long memBefore = usedMemory();

        long solveStart = System.nanoTime();
        List<Cell> path = solver.solve(grid, start, end);
        long solveEnd = System.nanoTime();

        long memAfter = usedMemory();

        double solveTimeMs = (solveEnd - solveStart) / 1_000_000.0;
        long memoryUsedKb = (memAfter - memBefore) / 1024;

        writer.write(String.format(
                "%s,%s,%d,%d,%.4f,%.4f,%d,%d\n",
                genName,
                solverName,
                size,
                trial,
                genTimeMs,
                solveTimeMs,
                path.size(),
                memoryUsedKb
        ));
    }

    private static Cell[][] createGrid(int size) {
        Cell[][] grid = new Cell[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                grid[x][y] = new Cell(x, y);
            }
        }
        return grid;
    }

    private static Cell[][] cloneGrid(Cell[][] original) {
        int size = original.length;
        Cell[][] copy = new Cell[size][size];

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                copy[x][y] = original[x][y].clone();
            }
        }
        return copy;
    }

    private static long usedMemory() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }

    private static MazeSolver getSolver(String name) {
        switch (name) {
            case "DFS": return new DFSSolver();
            case "BFS": return new BFSSolver();
            case "AStar": return new AStarSolver();
            default: throw new IllegalArgumentException("Unknown solver");
        }
    }
}
