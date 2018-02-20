package struct;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Maze {
    private int lines, cols;
    private Node[][] grid;
    private Point start, end;

    public static enum heuristic {MANHATTAN, PYTHAGORE}


    public Maze(int lines, int cols, Point start, Point end) {
        this.lines = lines;
        this.cols = cols;
        this.grid = new Node[lines][cols];
        this.start = start;
        this.end = end;

        checkPoint(start);
        checkPoint(end);
        initGrid();
    }

    private void checkPoint(Point p) {
        if (p.x < 0)
            p.x = 0;
        if (p.y < 0)
            p.y = 0;
        if (p.x >= lines)
            p.x = lines - 1;
        if (p.y >= cols)
            p.y = cols - 1;
    }


    private void initGrid() {
        Random r = new Random();
        int countWalls = 0;
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < cols; j++) {
                Node n = new Node(i, j);
                if (n.p.equals(start) || n.p.equals(end))
                    n.setWall(false);
                else {
                    if(countWalls > lines * cols * 38 / 100)
                        n.setWall(false);
                    else{
                        n.setWall(r.nextBoolean());
                        if(n.isWall())
                            countWalls++;
                    }
                }
                grid[i][j] = n;
            }
        }
    }

    public void calculateWights(Maze.heuristic method) {
        System.out.println(method);
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < cols; j++) {
                if (!grid[i][j].isWall()) {
                    if (method == heuristic.MANHATTAN) {
                        grid[i][j].setG(manhattan(start, grid[i][j].p));
                        grid[i][j].setH(manhattan(end, grid[i][j].p));
                    }
                    if (method == heuristic.PYTHAGORE) {
                        grid[i][j].setG(pythagore(start, grid[i][j].p));
                        grid[i][j].setH(pythagore(end, grid[i][j].p));
                    }
                }
            }
        }

    }

    public void display() {
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < cols; j++) {
                if (!grid[i][j].isWall()) {
                    if (grid[i][j].p.equals(start))
                        System.out.print(" S ");
                    else if (grid[i][j].p.equals(end))
                        System.out.print(" E ");
                    else
                        System.out.print(" . ");
                } else
                    System.out.print(" X ");
            }
            System.out.println();
        }
    }

    public int manhattan(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    public int pythagore(Point a, Point b) {
        return (int) Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    public Node getNode(Point p) {
        return grid[p.x][p.y];
    }

    public ArrayList<Node> getNeighbors(Point current) {
        Node up = null, down = null, left = null, right = null;
        ArrayList<Node> neighbors = new ArrayList<Node>();
        //up
        if (current.x - 1 >= 0)
            up = getNode(new Point(current.x - 1, current.y));
        if (current.x + 1 < lines)
            down = getNode(new Point(current.x + 1, current.y));

        if (current.y - 1 >= 0)
            left = getNode(new Point(current.x, current.y - 1));
        if (current.y + 1 < cols)
            right = getNode(new Point(current.x, current.y + 1));

        if (up != null && !up.isWall() && !up.isVisited())
            neighbors.add(up);
        if (down != null && !down.isWall() && !down.isVisited())
            neighbors.add(down);
        if (left != null && !left.isWall() && !left.isVisited())
            neighbors.add(left);
        if (right != null && !right.isWall() && !right.isVisited())
            neighbors.add(right);

        return neighbors;
    }

    public Node getMinFNode(ArrayList<Node> nodes) {
        if (!nodes.isEmpty()) {
            Node minNode = nodes.get(0);
            for (Node n : nodes) {
                if (n.F() < minNode.F())
                    minNode = n;
            }
            return minNode;
        }
        return null;
    }

    public int getLines() {
        return lines;
    }

    public int getCols() {
        return cols;
    }

    public Node[][] getGrid() {
        return grid;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
