package com.example.mazeproject.algorithms;

import com.example.mazeproject.model.Cell;
import java.util.List;

public interface MazeSolver {
    List<Cell> solve(Cell[][] grid, Cell start, Cell end);
}
