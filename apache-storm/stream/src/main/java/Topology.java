import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

public class Topology {
    public static void main(String[] args) {
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        topologyBuilder.setSpout("CsvSpout", new CsvSpout("src/out.csv"));
        topologyBuilder.setBolt("MultiplierBolt", new MultiplierBolt()).shuffleGrouping("CsvSpout");
        Config config = new Config();
        config.setDebug(false);
        try {
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology("HelloTopology", config, topologyBuilder.createTopology());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
