import edu.uci.ics.jung.graph.Graph;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class MyGraphGeneratorTest {
    @Test
    public void test() {
        File file = new File("src\\main\\resources\\graph");
        MyGraphGenerator myGraphGenerator = new MyGraphGenerator();
        myGraphGenerator.setGraph(file);
        Assert.assertEquals(myGraphGenerator.getGraph().getVertexCount(),9);
    }
}
