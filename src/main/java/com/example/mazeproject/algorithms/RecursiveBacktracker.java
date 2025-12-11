package com.example.mazeproject.algorithms;

import com.example.mazeproject.model.Cell;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.example.mazeproject.datastructures.MyStack;

public class RecursiveBacktracker implements MazeGenerator {

    @Override
    public void generate(Cell[][] grid, int rows, int cols) {
        MyStack<Cell> stack = new MyStack<>();

        Cell current = grid[0][0];
        current.visited = true;
        stack.push(current);

        Random random = new Random();

        while (!stack.isEmpty()) {
            current = stack.peek();
            Cell next = getRandomUnvisitedNeighbor(grid, current, rows, cols, random);

            if (next != null) {
                next.visited = true;

                removeWalls(current, next);

                stack.push(next);
            } else {
                stack.pop();
            }
        }
    }

    private Cell getRandomUnvisitedNeighbor(Cell[][] grid, Cell current, int rows, int cols, Random rand) {
        List<Cell> neighbors = new ArrayList<>();
        int x = current.x;
        int y = current.y;

        // Top
        if (y > 0 && !grid[x][y - 1].visited) neighbors.add(grid[x][y - 1]);
        // Right
        if (x < cols - 1 && !grid[x + 1][y].visited) neighbors.add(grid[x + 1][y]);
        // Bottom
        if (y < rows - 1 && !grid[x][y + 1].visited) neighbors.add(grid[x][y + 1]);
        // Left
        if (x > 0 && !grid[x - 1][y].visited) neighbors.add(grid[x - 1][y]);

        if (!neighbors.isEmpty()) {
            return neighbors.get(rand.nextInt(neighbors.size()));
        }
        return null;
    }

    private void removeWalls(Cell a, Cell b) {
        int x = a.x - b.x;
        int y = a.y - b.y;

        // A is to the left of B (remove A's right, B's left)
        if (x == -1) { a.walls[1] = false; b.walls[3] = false; }
        // A is to the right of B (remove A's left, B's right)
        if (x == 1) { a.walls[3] = false; b.walls[1] = false; }
        // A is above B (remove A's bottom, B's top)
        if (y == -1) { a.walls[2] = false; b.walls[0] = false; }
        // A is below B (remove A's top, B's bottom)
        if (y == 1) { a.walls[0] = false; b.walls[2] = false; }
    }
}