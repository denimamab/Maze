package struct;

import java.util.ArrayList;

public class MazeResult {
    ArrayList<Node> path;
    int nbrVisitedNodes;

    public MazeResult(ArrayList<Node> path, int nbrVisitedNodes) {
        this.path = path;
        this.nbrVisitedNodes = nbrVisitedNodes;
    }

    public int getNbrVisitedNodes() {
        return nbrVisitedNodes;
    }

    public ArrayList<Node> getPath() {
        return path;
    }
}
