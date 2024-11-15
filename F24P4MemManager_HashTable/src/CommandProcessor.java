import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Command Processor
 *
 * @author Jett Morrow jettmorrow Adam Schantz adams03
 * @version 11/12/2024
 */
public class CommandProcessor {

    private Controller controller;
    private int memorySize;
    private int hashTableSize;

    public CommandProcessor(Controller c, int memory, int hash) {
        controller = c;
        memorySize = memory;
        hashTableSize = hash;
    }


    /**
     * method to interprete one line at a time
     * 
     * @param input
     *            the scanner
     * @param output
     *            the output
     * @throws Exception
     */
    public void interpretAllLines(Scanner input, PrintWriter output) throws Exception {
        while (input.hasNext()) {
            this.interpretLine(input, output);
        }
        output.close();
    }


    /**
     * Where each line is procesessed
     * the first word determines the main command which determines what the next
     * possible words could be and mean, all input is send to controller with
     * approiorate methods
     * 
     * @param oneLine
     *            scanner object
     * @param output
     *            our output function
     * @param remainingInputLines
     *            the remainder of the input file
     * @throws Exception 
     */
    public void interpretLine(
        Scanner curLine,
        PrintWriter output) throws Exception {

        // we first have to process the first word (insert, remove, print)
        // depending on first word they have dif following input
        // insert, delete, search, print
        String command = curLine.next().trim();
        int id;
        switch (command) {
            case "insert":
                id = curLine.nextInt();
                curLine.nextLine(); // this is to get to the next line
                String title = curLine.nextLine().trim();
                String dateTime = curLine.next().trim();
                int length = curLine.nextInt();
                int x = curLine.nextInt();
                int y = curLine.nextInt();
                int cost = curLine.nextInt();
                curLine.nextLine(); // go to next line

                Scanner keywordScanner = new Scanner(curLine.nextLine());
                ArrayList<String> keywordList = new ArrayList<>();
                while (keywordScanner.hasNext()) {
                    keywordList.add(keywordScanner.next().trim());
                }
                keywordScanner.close();
                
                Scanner descriptionScanner = new Scanner(curLine.nextLine());
                StringBuilder sb = new StringBuilder();
                while (descriptionScanner.hasNext()) {
                    sb.append(descriptionScanner.next()).append(" ");
                }
                sb.deleteCharAt(sb.length() - 1);
                String description = new String(sb);
                descriptionScanner.close();

                Seminar seminar = new Seminar(id, title, dateTime, length,
                    (short)x, (short)y, cost, keywordList.toArray(
                        new String[0]), description);
                controller.insert(id, seminar, output);

                break;
            case "delete":

                id = curLine.nextInt();
                controller.delete(id, output);
                break;
            case "search":
                id = curLine.nextInt();
                controller.search(id, output);

                break;
            case "print":
                String table = curLine.next();
                switch (table) {
                    case "hashtable":
                        controller.printHashTable(output);
                        break;

                    case "blocks":
                        break;
                }

                break;
        }
        output.flush();
    }
}
