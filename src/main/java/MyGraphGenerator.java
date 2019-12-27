
import edu.uci.ics.jung.algorithms.generators.random.KleinbergSmallWorldGenerator;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.io.PajekNetReader;
import edu.uci.ics.jung.io.PajekNetWriter;
import org.apache.commons.collections15.Factory;

import java.io.File;
import java.io.IOException;

public class MyGraphGenerator {
    private KleinbergSmallWorldGenerator<Integer,String> generator;
    private Graph graph;
    private int latticeSize = 2;
    private double clusteringExponent = 2;

    public void setClusteringExponent(double clusteringExponent) {
        this.clusteringExponent = clusteringExponent;
    }

    public void setLatticeSize(int latticeSize) {
        this.latticeSize = latticeSize;
    }

    static class GraphFactory implements Factory<Graph<Integer,String>> {
        public Graph<Integer,String> create() {
            return new SparseMultigraph<Integer,String>();
        }
    }

    static class VertexFactory implements Factory<Integer> {
        int a = 0;
        public Integer create() {
            return a++;
        }

    }

    static class EdgeFactory implements Factory<String> {
        char aa = 'a';
        public String create() {
            return Character.toString(aa++);
        }

    }

    public Graph<Integer, String> getGraph() {
        return graph;
    }

    public MyGraphGenerator() {
        newGeneration();
    }

    public void newGeneration() {
        generator = new KleinbergSmallWorldGenerator<Integer, String>(
                new GraphFactory(), new VertexFactory(), new EdgeFactory(),  latticeSize, clusteringExponent
        );

        graph = generator.create();
    }

    public void setGraph(File file) {
        PajekNetReader pnr = new PajekNetReader(new EdgeFactory());
        graph = new SparseMultigraph();
        try {
            graph = pnr.load(file.getAbsolutePath(), graph);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveGraph(File file) {
        PajekNetWriter pnw = new PajekNetWriter();
        try {
            pnw.save(graph, file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
