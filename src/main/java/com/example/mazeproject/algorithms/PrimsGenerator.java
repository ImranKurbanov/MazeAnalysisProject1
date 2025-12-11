package com.example.mazeproject.algorithms;

import com.example.mazeproject.datastructures.MyPriorityQueue;
import com.example.mazeproject.model.Cell;

import java.util.Comparator;
import java.util.Random;

public class PrimsGenerator implements MazeGenerator {

    private class WallCandidate {
        Cell source;
        Cell target;
        int weight;

        public WallCandidate(Cell source, Cell target, int weight) {
            this.source = source;
            this.target = target;
            this.weight = weight;
        }
    }

    @Override
    public void generate(Cell[][] grid, int rows, int cols) {

        MyPriorityQueue<WallCandidate> frontier = new MyPriorityQueue<>(Comparator.comparingInt(w -> w.weight));
        Random rand = new Random();

        int startX = rand.nextInt(cols);
        int startY = rand.nextInt(rows);
        Cell start = grid[startX][startY];

        start.visited = true;

        addNeighborsToFrontier(grid, start, frontier, rows, cols, rand);

        while (!frontier.isEmpty()) {
            WallCandidate candidate = frontier.poll();

            Cell source = candidate.source;
            Cell target = candidate.target;

            if (target.visited) {
                continue;
            }

            target.visited = true;
            removeWalls(source, target);

            addNeighborsToFrontier(grid, target, frontier, rows, cols, rand);
        }
    }

    private void addNeighborsToFrontier(Cell[][] grid, Cell current, MyPriorityQueue<WallCandidate> frontier, int rows, int cols, Random rand) {
        int x = current.x;
        int y = current.y;

        // Top
        if (y > 0 && !grid[x][y - 1].visited) {
            frontier.add(new WallCandidate(current, grid[x][y - 1], rand.nextInt(100)));
        }
        // Right
        if (x < cols - 1 && !grid[x + 1][y].visited) {
            frontier.add(new WallCandidate(current, grid[x + 1][y], rand.nextInt(100)));
        }
        // Bottom
        if (y < rows - 1 && !grid[x][y + 1].visited) {
            frontier.add(new WallCandidate(current, grid[x][y + 1], rand.nextInt(100)));
        }
        // Left
        if (x > 0 && !grid[x - 1][y].visited) {
            frontier.add(new WallCandidate(current, grid[x - 1][y], rand.nextInt(100)));
        }
    }

    private void removeWalls(Cell a, Cell b) {
        int x = a.x - b.x;
        int y = a.y - b.y;

        if (x == -1) { a.walls[1] = false; b.walls[3] = false; } // Right
        if (x == 1) { a.walls[3] = false; b.walls[1] = false; }  // Left
        if (y == -1) { a.walls[2] = false; b.walls[0] = false; } // Bottom
        if (y == 1) { a.walls[0] = false; b.walls[2] = false; }  // Top
    }
}
