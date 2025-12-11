package com.example.mazeproject.algorithms;

import com.example.mazeproject.model.Cell;
import com.example.mazeproject.datastructures.MyQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BFSSolver implements MazeSolver {

    @Override
    public List<Cell> solve(Cell[][] grid, Cell start, Cell end) {
        MyQueue<Cell> queue = new MyQueue<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        queue.enqueue(start);
        visited[start.x][start.y] = true;

        while (!queue.isEmpty()) {
            Cell current = queue.dequeue();

            if (current == end) {
                return reconstructPath(end);
            }

            for (Cell neighbor : getValidNeighbors(grid, current, visited)) {
                visited[neighbor.x][neighbor.y] = true;
                neighbor.parent = current;

                queue.enqueue(neighbor);
            }
        }

        return new ArrayList<>();
    }

    private List<Cell> getValidNeighbors(Cell[][] grid, Cell current, boolean[][] visited) {
        List<Cell> neighbors = new ArrayList<>();
        int x = current.x;
        int y = current.y;

        // Top
        if (y > 0 && !current.walls[0] && !visited[x][y - 1]) neighbors.add(grid[x][y - 1]);
        // Right
        if (x < grid.length - 1 && !current.walls[1] && !visited[x + 1][y]) neighbors.add(grid[x + 1][y]);
        // Bottom
        if (y < grid[0].length - 1 && !current.walls[2] && !visited[x][y + 1]) neighbors.add(grid[x][y + 1]);
        // Left
        if (x > 0 && !current.walls[3] && !visited[x - 1][y]) neighbors.add(grid[x - 1][y]);

        return neighbors;
    }

    private List<Cell> reconstructPath(Cell end) {
        List<Cell> path = new ArrayList<>();
        Cell current = end;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }
}