import org.apache.hadoop.examples.RandomTextWriter;
import org.apache.hadoop.util.ToolRunner;

public class RandomTextDataGenerator {

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new RandomTextWriter(), args);
        System.exit(exitCode);
    }
}