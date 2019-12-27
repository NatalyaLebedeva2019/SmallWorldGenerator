import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MyFrame extends JFrame {
    private MyGraphGenerator myGraphGenerator;
    private CircleLayout layout;
    private BasicVisualizationServer<Integer,String> vv;

    public MyFrame() {
        super("Graph View");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myGraphGenerator = new MyGraphGenerator();

        layout = new CircleLayout<Integer, String>(myGraphGenerator.getGraph());
        layout.setSize(new Dimension(450,450));
        vv = new BasicVisualizationServer<Integer,String>(layout);
        vv.setPreferredSize(new Dimension(550,550)); //Sets the viewing area size
        final JTextField latticeSize = new JTextField("2", 10);
        final JTextField clusteringExponent = new JTextField("2", 10);
        Label latticeSizeLabel = new Label("Lattice size");
        Label clusteringExpLabel = new Label("Clustering exponent");

        final JPanel jPanel = new JPanel();
        JButton generateButton = new JButton("Generate");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myGraphGenerator.setLatticeSize(Integer.parseInt(latticeSize.getText()));
                myGraphGenerator.setClusteringExponent(Double.parseDouble(clusteringExponent.getText()));
                myGraphGenerator.newGeneration();
                layout = new CircleLayout<Integer, String>(myGraphGenerator.getGraph());
                layout.setSize(new Dimension(450,450));
                vv.setGraphLayout(layout);
            }
        });

        JButton openGraphButton = new JButton("Open graph");
        openGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Открыть файл");

                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    myGraphGenerator.setGraph(file);
                    layout = new CircleLayout<Integer, String>(myGraphGenerator.getGraph());
                    layout.setSize(new Dimension(450,450));
                    vv.setGraphLayout(layout);
                }
            }
        });

        JButton saveGraphButton = new JButton("Save graph");
        saveGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Сохранение файла");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showSaveDialog(null);

                if (result == JFileChooser.APPROVE_OPTION ) {
                    myGraphGenerator.saveGraph(fileChooser.getSelectedFile());
                    JOptionPane.showMessageDialog(null,
                            "Файл '" + fileChooser.getSelectedFile() + " ) сохранен");
                }
            }
        });

        jPanel.add(vv);
        jPanel.add(latticeSizeLabel);
        jPanel.add(latticeSize);
        jPanel.add(clusteringExpLabel);
        jPanel.add(clusteringExponent);
        jPanel.add(openGraphButton);
        jPanel.add(generateButton);
        jPanel.add(saveGraphButton);
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(jPanel);
        setVisible(true);
        setSize(550, 700);
    }

    public static void main(String[] args) {
        new MyFrame();
    }
}
