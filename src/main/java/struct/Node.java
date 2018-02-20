package struct;

import java.awt.*;

public class Node {
    public Point p;

    //Distance to start
    private int G;
    //Distance to end
    private int H;

    private boolean visited;

    private boolean wall;

    private Node parent;

    public Node(int x, int y) {
        this.p = new Point(x,y);
        this.parent = null;
        this.visited = false;
    }

    public boolean isWall() {
        return wall;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }

    public int F(){
        return H+G;
    }

    public int getG() {
        return G;
    }

    public void setG(int g) {
        G = g;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getH() {
        return H;
    }

    public void setH(int h) {
        H = h;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
