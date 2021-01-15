import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

class CsvSpout extends BaseRichSpout {

    private SpoutOutputCollector spoutOutputCollector;
    private String csvPath;
    private transient CSVReader csvReader;

    public CsvSpout(String csvPath) {
        this.csvPath = csvPath;
    }

    @Override
    public void open(Map<String, Object> map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.spoutOutputCollector = spoutOutputCollector;
        try {
            FileReader fileReader = new FileReader(this.csvPath);
            this.csvReader = new CSVReader(fileReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nextTuple() {
        try {
            this.csvReader.readNext();   // Ignore the header row
            String[] line = this.csvReader.readNext();
            if (line != null) {
                this.spoutOutputCollector.emit(new Values(line));
            }else{
                System.out.println("Finished reading the file.");
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        try {
            FileReader fileReader = new FileReader(this.csvPath);
            this.csvReader = new CSVReader(fileReader);
            // read csv header to get fields info
            String[] fields = this.csvReader.readNext();
            outputFieldsDeclarer.declare(new Fields(Arrays.asList(fields)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}