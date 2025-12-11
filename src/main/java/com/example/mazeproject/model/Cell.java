package com.example.mazeproject.model;

public class Cell {
    public int x, y;
    public boolean visited = false;

    public boolean[] walls = {true, true, true, true};

    public Cell parent;
    public double gCost, hCost, fCost;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public Cell clone() {
        Cell c = new Cell(this.x, this.y);
        c.copyWallsFrom(this);
        return c;
    }

    public void copyWallsFrom(Cell other) {
        this.walls[0] = other.walls[0]; // Top
        this.walls[1] = other.walls[1]; // Right
        this.walls[2] = other.walls[2]; // Bottom
        this.walls[3] = other.walls[3]; // Left

        this.visited = false;

        this.parent = null;
        this.gCost = 0;
        this.hCost = 0;
        this.fCost = 0;
    }
}
