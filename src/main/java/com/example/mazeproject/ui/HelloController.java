package com.example.mazeproject;

import com.example.mazeproject.algorithms.*;
import com.example.mazeproject.model.Cell;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import java.util.List;
import java.util.Random;

public class HelloController {

    @FXML
    private Canvas mazeCanvas;
    @FXML
    private ComboBox<String> sizeComboBox;
    @FXML
    private ComboBox<String> algorithmComboBox; // NEW: Dropdown for Algorithm

    private Cell[][] grid;
    private int rows;
    private int cols;
    private double cellSize;

    @FXML
    public void initialize() {
        // Setup Size Dropdown
        sizeComboBox.getItems().addAll("10x10", "50x50", "100x100", "200x200");
        sizeComboBox.getSelectionModel().selectFirst();

        // Setup Algorithm Dropdown
        algorithmComboBox.getItems().addAll("Recursive Backtracker", "Prim's Algorithm");
        algorithmComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    protected void onGenerateButtonClick() {
        // 1. Setup Grid Size
        String selectedSize = sizeComboBox.getValue();
        int size = Integer.parseInt(selectedSize.split("x")[0]);
        this.rows = size;
        this.cols = size;
        this.cellSize = mazeCanvas.getWidth() / size;

        grid = new Cell[cols][rows];
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                grid[x][y] = new Cell(x, y);
            }
        }

        // 2. Select Algorithm based on Dropdown
        MazeGenerator generator;
        String selectedAlgo = algorithmComboBox.getValue();

        if (selectedAlgo.equals("Prim's Algorithm")) {
            generator = new PrimsGenerator();
        } else {
            generator = new RecursiveBacktracker();
        }

        // 3. Run Generator
        generator.generate(grid, rows, cols);

        // 4. Add Loops (Optional - keep this if you want multiple paths)
        addLoops(grid, rows, cols);

        // 5. Draw
        drawMaze(grid);
    }

    @FXML
    protected void onSolveButtonClick() {
        if (grid == null) return;
        MazeSolver solver = new DFSSolver();
        Cell start = grid[0][0];
        Cell end = grid[cols - 1][rows - 1];
        List<Cell> path = solver.solve(grid, start, end);
        drawPath(path, Color.RED);
    }

    @FXML
    protected void onBFSButtonClick() {
        if (grid == null) return;
        MazeSolver solver = new BFSSolver();
        Cell start = grid[0][0];
        Cell end = grid[cols - 1][rows - 1];
        List<Cell> path = solver.solve(grid, start, end);
        drawPath(path, Color.BLUE);
    }

    @FXML
    protected void onAStarButtonClick() {
        if (grid == null) return;
        resetGridCosts();
        MazeSolver solver = new AStarSolver();
        Cell start = grid[0][0];
        Cell end = grid[cols - 1][rows - 1];
        List<Cell> path = solver.solve(grid, start, end);
        drawPath(path, Color.GREEN);
    }

    private void resetGridCosts() {
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                grid[x][y].gCost = Double.MAX_VALUE;
                grid[x][y].hCost = 0;
                grid[x][y].fCost = 0;
                grid[x][y].parent = null;
            }
        }
    }

    private void addLoops(Cell[][] grid, int rows, int cols) {
        int loopsToMake = (rows * cols) / 20; // 5% loops
        Random rand = new Random();
        for (int i = 0; i < loopsToMake; i++) {
            int x = rand.nextInt(cols - 1);
            int y = rand.nextInt(rows - 1);
            if (rand.nextBoolean()) {
                if (grid[x][y].walls[1]) {
                    grid[x][y].walls[1] = false;
                    grid[x + 1][y].walls[3] = false;
                }
            } else {
                if (grid[x][y].walls[2]) {
                    grid[x][y].walls[2] = false;
                    grid[x][y + 1].walls[0] = false;
                }
            }
        }
    }

    private void drawMaze(Cell[][] grid) {
        GraphicsContext gc = mazeCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, mazeCanvas.getWidth(), mazeCanvas.getHeight());
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(cellSize < 5 ? 1 : 2);

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                Cell cell = grid[x][y];
                double startX = x * cellSize;
                double startY = y * cellSize;
                if (cell.walls[0]) gc.strokeLine(startX, startY, startX + cellSize, startY);
                if (cell.walls[1]) gc.strokeLine(startX + cellSize, startY, startX + cellSize, startY + cellSize);
                if (cell.walls[2]) gc.strokeLine(startX, startY + cellSize, startX + cellSize, startY + cellSize);
                if (cell.walls[3]) gc.strokeLine(startX, startY + cellSize, startX, startY);
            }
        }
    }

    private void drawPath(List<Cell> path, Color color) {
        GraphicsContext gc = mazeCanvas.getGraphicsContext2D();
        gc.setStroke(color);
        gc.setLineWidth(cellSize / 2);
        for (int i = 0; i < path.size() - 1; i++) {
            Cell a = path.get(i);
            Cell b = path.get(i + 1);
            double x1 = a.x * cellSize + (cellSize / 2.0);
            double y1 = a.y * cellSize + (cellSize / 2.0);
            double x2 = b.x * cellSize + (cellSize / 2.0);
            double y2 = b.y * cellSize + (cellSize / 2.0);
            gc.strokeLine(x1, y1, x2, y2);
        }
    }
}