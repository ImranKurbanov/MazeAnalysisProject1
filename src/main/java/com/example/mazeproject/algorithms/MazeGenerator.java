package com.example.mazeproject.algorithms;

import com.example.mazeproject.model.Cell;

public interface MazeGenerator {
    void generate(Cell[][] grid, int rows, int cols);
}
