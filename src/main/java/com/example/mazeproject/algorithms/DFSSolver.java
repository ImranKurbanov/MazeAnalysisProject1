package com.example.mazeproject.algorithms;

import com.example.mazeproject.model.Cell;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.example.mazeproject.datastructures.MyStack;

public class DFSSolver implements MazeSolver {

    @Override
    public List<Cell> solve(Cell[][] grid, Cell start, Cell end) {
        MyStack<Cell> stack = new MyStack<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        stack.push(start);
        visited[start.x][start.y] = true;

        while (!stack.isEmpty()) {
            Cell current = stack.pop();

            if (current == end) {
                return reconstructPath(end);
            }

            // Check all valid neighbors
            for (Cell neighbor : getValidNeighbors(grid, current, visited)) {
                visited[neighbor.x][neighbor.y] = true;
                neighbor.parent = current;
                stack.push(neighbor);
            }
        }

        return new ArrayList<>();
    }

    private List<Cell> getValidNeighbors(Cell[][] grid, Cell current, boolean[][] visited) {
        List<Cell> neighbors = new ArrayList<>();
        int x = current.x;
        int y = current.y;

        if (y > 0 && !current.walls[0] && !visited[x][y - 1]) {
            neighbors.add(grid[x][y - 1]);
        }
        // Right (Index 1)
        if (x < grid.length - 1 && !current.walls[1] && !visited[x + 1][y]) {
            neighbors.add(grid[x + 1][y]);
        }
        // Bottom (Index 2)
        if (y < grid[0].length - 1 && !current.walls[2] && !visited[x][y + 1]) {
            neighbors.add(grid[x][y + 1]);
        }
        // Left (Index 3)
        if (x > 0 && !current.walls[3] && !visited[x - 1][y]) {
            neighbors.add(grid[x - 1][y]);
        }
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