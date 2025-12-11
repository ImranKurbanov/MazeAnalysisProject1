package com.example.mazeproject.algorithms;

import com.example.mazeproject.datastructures.MyPriorityQueue;
import com.example.mazeproject.model.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AStarSolver implements MazeSolver {

    @Override
    public List<Cell> solve(Cell[][] grid, Cell start, Cell end) {
        MyPriorityQueue<Cell> openSet = new MyPriorityQueue<>(Comparator.comparingDouble(c -> c.fCost));

        boolean[][] closedSet = new boolean[grid.length][grid[0].length];

        start.gCost = 0;
        start.hCost = calculateHeuristic(start, end);
        start.fCost = start.gCost + start.hCost;

        openSet.add(start);

        while (!openSet.isEmpty()) {
            Cell current = openSet.poll();

            if (current == end) {
                return reconstructPath(end);
            }

            closedSet[current.x][current.y] = true;

            for (Cell neighbor : getValidNeighbors(grid, current, closedSet)) {
                double tentativeGCost = current.gCost + 1;

                if (tentativeGCost < neighbor.gCost || !openSet.contains(neighbor)) {
                    neighbor.gCost = tentativeGCost;
                    neighbor.hCost = calculateHeuristic(neighbor, end);
                    neighbor.fCost = neighbor.gCost + neighbor.hCost;
                    neighbor.parent = current;

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return new ArrayList<>();
    }

    private double calculateHeuristic(Cell a, Cell b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    private List<Cell> getValidNeighbors(Cell[][] grid, Cell current, boolean[][] closedSet) {
        List<Cell> neighbors = new ArrayList<>();
        int x = current.x;
        int y = current.y;

        if (y > 0 && !current.walls[0] && !closedSet[x][y - 1]) neighbors.add(grid[x][y - 1]);
        if (x < grid.length - 1 && !current.walls[1] && !closedSet[x + 1][y]) neighbors.add(grid[x + 1][y]);
        if (y < grid[0].length - 1 && !current.walls[2] && !closedSet[x][y + 1]) neighbors.add(grid[x][y + 1]);
        if (x > 0 && !current.walls[3] && !closedSet[x - 1][y]) neighbors.add(grid[x - 1][y]);

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
