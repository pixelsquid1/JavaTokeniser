import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

/**
 * Created by davie on 21/05/2020.
 */
public class removeLowFrequencyTokens {

    public static HashMap<String, Integer> duplicateCounts = new HashMap<>();
    public static String TFILE = "javaTemp.txt";
    public static String OFILE = "java_tokenised.txt";

    public static int n = 100000;

    public static void main(String[] args){
        try {
            FileReader rd = new FileReader(TFILE);
            BufferedReader br = new BufferedReader(rd);

            FileWriter fw = new FileWriter(OFILE);

            //If a unique value (not a duplicate) set to " " whitespace

            String line = br.readLine();
            while (line != null) {

                if (duplicateCounts.get(line) < n) fw.write("CUSTOM_INPUT");//TokenList.set(i, "CUSTOM_INPUT");
                else fw.write(line);
                fw.write('\n');
                fw.flush();

                line = br.readLine();
            }

            fw.close();


        } catch (Exception e) {
            System.out.println(e);
        }

    }
}