import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

public class Topology {
    public static void main(String[] args) {
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        topologyBuilder.setSpout("CsvSpout", new CsvSpout("src/input.csv"));
        topologyBuilder.setBolt("StockFilterBolt", new StockFilterBolt(), 2).shuffleGrouping("CsvSpout");
        topologyBuilder.setBolt("FilePersistBolt", new FilePersistBolt(), 1).shuffleGrouping("StockFilterBolt");
        Config config = new Config();
        config.setDebug(true);
        try {
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology("ExchangeTopology", config, topologyBuilder.createTopology());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
