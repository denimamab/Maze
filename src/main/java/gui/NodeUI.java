package gui;

import javax.swing.*;
import java.awt.*;

public class NodeUI extends JPanel {
    public Point p;

    public NodeUI(Point p) {
        this.p = p;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(50, 50);
    }
}
