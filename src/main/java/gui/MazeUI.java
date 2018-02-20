package gui;

import struct.Maze;
import struct.MazeResult;
import struct.Node;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;

public class MazeUI extends JPanel {
    private NodeUI[][] grid;
    private Maze maze;
    private GridBagConstraints gbc;
    private GridBagLayout gbl;
    private Node currentNode;
    Color dbg;

    public MazeUI(Maze maze) {
        this.maze = maze;
        gbl = new GridBagLayout();
        setLayout(gbl);
        this.grid = new NodeUI[maze.getLines()][maze.getCols()];
        gbc = new GridBagConstraints();
        dbg = getBackground();
        reset();
        paint(getGraphics());
        System.out.println("Finish render");
    }

    public void reset() {
        NodeUI n;
        for (int i = 0; i < maze.getLines(); i++) {
            for (int j = 0; j < maze.getCols(); j++) {
                if (grid[i][j] == null) {
                    n = new NodeUI(maze.getGrid()[i][j].p);
                    grid[i][j] = n;
                }
                maze.getGrid()[i][j].setVisited(false);
                gbc.gridx = j;
                gbc.gridy = i;
                setBackground(dbg);

                if (maze.getGrid()[i][j].p.equals(maze.getStart()))
                    grid[i][j].setBackground(Color.RED);
                else if (maze.getGrid()[i][j].p.equals(maze.getEnd()))
                    grid[i][j].setBackground(Color.GREEN);
                else if (maze.getGrid()[i][j].isWall())
                    grid[i][j].setBackground(Color.BLACK);
                else
                    grid[i][j].setBackground(dbg);


                Border border = null;
                if (i < maze.getLines() - 1) {
                    if (j < maze.getCols() - 1) {
                        border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                    }
                } else {
                    if (j < maze.getCols() - 1) {
                        border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                    }
                }
                grid[i][j].setBorder(border);
                add(grid[i][j], gbc);
            }
        }
    }

    public MazeResult resolve() {
        reset();
        repaint();
        ArrayList<Node> openList = new ArrayList<Node>();
        ArrayList<Node> closedList = new ArrayList<Node>();
        openList.add(maze.getNode(maze.getStart()));
        int cpt = 0;
        while (!openList.isEmpty() && !closedList.contains(maze.getNode(maze.getEnd()))) {
            Node current = maze.getMinFNode(openList);
            cpt++;
            current.setVisited(true);
            closedList.add(current);
            openList.remove(current);

            if (!current.p.equals(maze.getStart()) && !current.p.equals(maze.getEnd()))
                grid[current.p.x][current.p.y].setBackground(Color.BLUE);
            this.paintAll(getGraphics());


            ArrayList<Node> neighbors = maze.getNeighbors(current.p);
            //Add neighbors to openList
            for (Node n : neighbors) {
                if (!openList.contains(n))
                    openList.add(n);
                //Set parent node
                if ((n.getParent() == null) || (n.getParent() != null && n.getParent().F() > current.F()))
                    n.setParent(current);
            }
            // sleep to view realtime
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //reconstitute path
        Node tmp = maze.getNode(maze.getEnd());
        ArrayList<Node> path = new ArrayList<>();
        if (tmp.getParent() == null){
            System.out.println("NO SOLUTION FOUND");
            return null;
        } else {
            while (tmp != null) {
                System.out.println(tmp.p);
                if (!tmp.p.equals(maze.getStart()) && !tmp.p.equals(maze.getEnd())){
                    grid[tmp.p.x][tmp.p.y].setBackground(Color.YELLOW);
                    path.add(tmp);
                }
                tmp = tmp.getParent();
            }
            this.paintAll(getGraphics());
            return new MazeResult(path, cpt);
        }
    }
}
