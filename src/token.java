import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by davie on 29/03/2020.
 */
public class token {

    public static HashMap<String, Integer> duplicateCounts = new HashMap<>();
    public static String IFILE = "java.txt";
    public static String TFILE = "javaTemp.txt";
    public static String OFILE = "java_tokenised.txt";

    public static void main(String[] args)
    {
        try {
            // Create the tokenizer to read from a file
            FileReader rd = new FileReader(IFILE);
            StreamTokenizer st = new StreamTokenizer(rd);
            FileWriter fw = new FileWriter(TFILE);

            String v;
            int FileLength = 0;

            // Prepare the tokenizer for Java-style tokenizing rules
            st.parseNumbers();
            st.wordChars('_', '_');
            st.eolIsSignificant(true);

            // These calls caused comments to be discarded
            st.slashSlashComments(true);
            st.slashStarComments(true);

            // Parse the file
            int token = st.nextToken();
            while (token != StreamTokenizer.TT_EOF) {
                FileLength++;

                token = st.nextToken();
                switch (token) {

                    case StreamTokenizer.TT_NUMBER:
                        // A number was found; the value is in nval
                        v = "NUMBER";
                        fw.write(v);
                        addToList(v);

                        break;
                    case StreamTokenizer.TT_WORD:
                        // A word was found; the value is in sval
                        v = st.sval;
                        fw.write(v);
                        addToList(v);
                        break;

                    case '"':
                        // A double-quoted string was found; sval contains the contents
                        String dqString = st.sval;
                        fw.write("\"\n" + dqString + "\n\"");
                        addToList("\"");
                        if (dqString != "") addToList(dqString);
                        addToList("\"");
                        break;

                    case '\'':
                        // A single-quoted string was found; sval contains the contents
                        String sqString = st.sval;
                        fw.write("\"\n" + sqString + "\n\"");
                        addToList("\"");
                        if (sqString != "") addToList(sqString);
                        addToList("\"");
                        break;

                    case StreamTokenizer.TT_EOL:
                        // End of line character found
                    case StreamTokenizer.TT_EOF:
                        // End of file has been reached
                        break;

                    default:
                        // A regular character was found; the value is the token itself
                        char ch = (char)st.ttype;

                        fw.write(Character.toString(ch));
                        addToList(Character.toString(ch));
                        break;
                }


                fw.write('\n');
                fw.flush();

        }

        System.out.println("Finished making TokenList array, closing filereader");
        fw.close();
        rd.close();

        double n = ((FileLength / 100) * 0.03); // THE NUMBER OF INSTANCES NEEDED TO KEEP IN TOKEN LIST
        System.out.println(n);

        System.out.println("Waiting..");
        TimeUnit.SECONDS.sleep(10);

            rd = new FileReader(TFILE);
            BufferedReader br = new BufferedReader(rd);

            fw = new FileWriter(OFILE);

            //If a unique value (not a duplicate) set to " " whitespace

            String line = br.readLine().trim();
            while(line != null) {

                if (duplicateCounts.containsKey(line)) {
                    if (duplicateCounts.get(line) < n) fw.write("CUSTOM_INPUT");//TokenList.set(i, "CUSTOM_INPUT");
                    else fw.write(line);
                }
                else fw.write("CUSTOM_INPUT");
                fw.write('\n');
                fw.flush();

                line = br.readLine();
            }

            fw.close();


        } catch (Exception e) {
        System.out.println(e);
    }



    }

    public static void addToList(String v)
    {
        v.trim();
        if (duplicateCounts.containsKey(v)) duplicateCounts.put(v, duplicateCounts.get(v) + 1);
        else duplicateCounts.put(v, 1);
    }

}
