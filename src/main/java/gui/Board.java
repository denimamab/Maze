package gui;

import struct.Maze;
import struct.MazeResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class Board extends JFrame implements ActionListener {

    JPanel maze, config, resolve, generate, results;
    JFormattedTextField mazeRows, mazeCols;
    Maze mazeBoard;
    MazeUI mazeUI;
    Point start, end;

    JFormattedTextField startX, startY, endX, endY;

    JRadioButton usingManhattan, usingPythagore;
    JButton generateBtn, resolveBtn;
    JLabel resultsLab, mazeColsLab, mazeRowsLab;

    JLabel startLab, startXLab, startYLab;
    JLabel endLab, endXLab, endYLab;


    public static void main(String[] args) {
        new Board();
    }

    public Board() {
        setLayout(new GridLayout(1, 2));
        setTitle("Maze board - w/ resolution using Manhattan and Pythagore");
        maze = new JPanel();
        /**
         * TEST painting
         */
        start = new Point(3, 0);
        end = new Point(6, 9);
        mazeBoard = new Maze(10, 10, start, end);
        mazeUI = new MazeUI(mazeBoard);
        maze.add(mazeUI);
        add(maze);

        /*
            RIGHT SIDE - Config + results
         */
        config = new JPanel();
        config.setLayout(new BoxLayout(config, BoxLayout.Y_AXIS));
        config.setBackground(Color.YELLOW);

        //Generate
        generate = new JPanel();
        generate.setLayout(new FlowLayout());
        generateBtn = new JButton("Generate");
        generateBtn.addActionListener(this);
        mazeRows = new JFormattedTextField(NumberFormat.getNumberInstance());
        mazeRows.setValue(10L);
        mazeRows.setPreferredSize(generateBtn.getPreferredSize());
        mazeRowsLab = new JLabel("Rows");
        mazeCols = new JFormattedTextField(NumberFormat.getNumberInstance());
        mazeCols.setValue(10L);
        mazeCols.setPreferredSize(generateBtn.getPreferredSize());
        mazeColsLab = new JLabel("Cols");

        JPanel dimensions = new JPanel();
        dimensions.setLayout(new FlowLayout());
        JPanel positionStart = new JPanel();
        positionStart.setLayout(new FlowLayout());
        JPanel positionEnd = new JPanel();
        positionEnd.setLayout(new FlowLayout());

        dimensions.add(mazeRowsLab);
        dimensions.add(mazeRows);
        dimensions.add(mazeColsLab);
        dimensions.add(mazeCols);
        startXLab = new JLabel("X");
        startYLab = new JLabel("Y");

        startLab = new JLabel("Start position :");
        startX = new JFormattedTextField(NumberFormat.getNumberInstance());
        startY = new JFormattedTextField(NumberFormat.getNumberInstance());
        startX.setValue(3L);
        startY.setValue(0L);
        startX.setPreferredSize(generateBtn.getPreferredSize());
        startY.setPreferredSize(generateBtn.getPreferredSize());
        positionStart.add(startLab);
        positionStart.add(startXLab);
        positionStart.add(startX);
        positionStart.add(startYLab);
        positionStart.add(startY);

        endLab = new JLabel("Exit position :");
        endXLab = new JLabel("X");
        endYLab = new JLabel("Y");
        endX = new JFormattedTextField(NumberFormat.getNumberInstance());
        endY = new JFormattedTextField(NumberFormat.getNumberInstance());
        endX.setPreferredSize(generateBtn.getPreferredSize());
        endY.setPreferredSize(generateBtn.getPreferredSize());
        endX.setValue(6L);
        endY.setValue(9L);

        positionEnd.add(endLab);
        positionEnd.add(endXLab);
        positionEnd.add(endX);
        positionEnd.add(endYLab);
        positionEnd.add(endY);

        JPanel a = new JPanel();
        a.setLayout(new BoxLayout(a, BoxLayout.Y_AXIS));
        a.add(dimensions);
        a.add(positionStart);
        a.add(positionEnd);
        a.add(generateBtn);

        generate.add(a);

        generate.setBorder(BorderFactory.createTitledBorder("Generate new maze"));

        resolve = new JPanel();
        resolve.setLayout(new FlowLayout());
        resolve.setBorder(BorderFactory.createTitledBorder("Resolve maze"));
        resolveBtn = new JButton("Resolve");
        resolveBtn.addActionListener(this);
        usingManhattan = new JRadioButton("Using Manhattan");
        usingPythagore = new JRadioButton("Using Pythagore");
        ButtonGroup heuristic = new ButtonGroup();
        usingManhattan.setSelected(true);
        heuristic.add(usingManhattan);
        heuristic.add(usingPythagore);

        resolve.add(usingManhattan);
        resolve.add(usingPythagore);
        resolve.add(resolveBtn);


        results = new JPanel();
        results.setBorder(BorderFactory.createTitledBorder("Results"));
        resultsLab = new JLabel();

        results.add(resultsLab);

        config.add(generate);
        config.add(resolve);
        config.add(results);

        add(config);

        setMinimumSize(new Dimension(1050, 550));
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if (source == generateBtn) {
            // generate new maze and repaint
            Long nbrRows = (Long) mazeRows.getValue();
            Long nbrCols = (Long) mazeCols.getValue();
            Long SPx = (Long) startX.getValue();
            Long SPy = (Long) startY.getValue();
            Long EPx = (Long) endX.getValue();
            Long EPy = (Long) endY.getValue();

            Point start = new Point(SPx.intValue(), SPy.intValue());
            Point end = new Point(EPx.intValue(), EPy.intValue());
            mazeBoard = new Maze(nbrRows.intValue(), nbrCols.intValue(), start, end);
            mazeUI = new MazeUI(mazeBoard);

            maze.removeAll();
            maze.revalidate();
            maze.repaint();
            maze.add(mazeUI);
            maze.repaint();
            resultsLab.setText("");
        }

        if (source == resolveBtn) {
            // check using heuristic
            if (usingManhattan.isSelected()) {
                mazeBoard.calculateWights(Maze.heuristic.MANHATTAN);
            }
            if (usingPythagore.isSelected()) {
                mazeBoard.calculateWights(Maze.heuristic.PYTHAGORE);
            }

            MazeResult resultPath = mazeUI.resolve();
            if (resultPath == null)
                resultsLab.setText("No solution found.");
            else
                resultsLab.setText("<html><body>Number of visited nodes : " + resultPath.getNbrVisitedNodes()
                        + "<br>Number of nodes in minimum path :" + resultPath.getPath().size()+"</body></html>");

            //Print results on result label
        }
    }
}
