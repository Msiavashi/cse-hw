import com.opencsv.CSVWriter;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.io.*;
import java.util.*;

public class FilePersistBolt extends BaseBasicBolt {

    private transient CSVWriter csvWriter;
    private final String path = "output.csv";
    private HashMap<Integer, Integer> result;

    @Override
    public void prepare(Map<String, Object> topoConf, TopologyContext context) {
        this.result = new HashMap<Integer, Integer>();
        /* Run the file logger in a separate thread to mitigate the I/O overhead.
            Write to file every 5 seconds
         */
        new Thread(() -> {
            while (true) {
                try {
                    writeHashMapToCsv();
                    Thread.sleep(5000);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        super.prepare(topoConf, context);
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        Integer symbol = Integer.parseInt(tuple.getStringByField("symbol"));
        Integer shares = Integer.parseInt(tuple.getStringByField("shares"));
        Integer currentTradedShares = this.result.get(symbol);
        if(currentTradedShares != null) {
            this.result.put(symbol, currentTradedShares +  shares);
        } else {
            this.result.put(symbol, shares);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        //    Empty, since we emit nothing
    }

    private void writeHashMapToCsv() throws IOException {
        FileWriter fileWriter = new FileWriter(this.path, false);
        CSVWriter csvWriter = new CSVWriter(fileWriter);
        ArrayList<Integer> keys = new ArrayList<Integer>(this.result.keySet());
        Collections.sort(keys);
        for(Integer key : keys) {
            csvWriter.writeNext(new String[]{key.toString(), this.result.get(key).toString()}, false);
        }
        csvWriter.close();
        fileWriter.close();
    }
}
