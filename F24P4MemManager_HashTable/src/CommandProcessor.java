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
     */
    public void interpretAllLines(Scanner input, PrintWriter output) {
        while (input.hasNext()) {
            this.interpretLine(input, output, input);
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
     */
    public void interpretLine(
        Scanner oneLine,
        PrintWriter output,
        Scanner remainingInputLines) {

        // we first have to process the first word (insert, remove, print)
        // depending on first word they have dif following input
        // insert, delete, search, print
        String command = oneLine.next().trim();
        int id;
        switch (command) {
            case "insert":
                id = oneLine.nextInt();
                oneLine.nextLine(); // this is to get to the next line
                String title = oneLine.nextLine().trim();
                String dateTime = oneLine.next().trim();
                int length = oneLine.nextInt();
                int x = oneLine.nextInt();
                int y = oneLine.nextInt();
                int cost = oneLine.nextInt();
                oneLine.nextLine(); // go to next line

                Scanner keywordScanner = new Scanner(oneLine.nextLine());
                ArrayList<String> keywordList = new ArrayList<>();
                while (keywordScanner.hasNext()) {
                    keywordList.add(keywordScanner.next().trim());
                }

                StringBuilder sb = new StringBuilder();
                while (oneLine.hasNext()) {
                    sb.append(oneLine.next()).append(" ");
                }
                sb.deleteCharAt(sb.length() - 1);
                String description = new String(sb);

                Seminar seminar = new Seminar(id, title, dateTime, length,
                    (short)x, (short)y, cost, keywordList.toArray(
                        new String[0]), description);
                controller.insert(id, seminar, output);

                break;
            case "delete":
                
                id = oneLine.nextInt();
                controller.delete(id, output);

                break;
            case "search":

                break;
            case "print":

                break;
        }
        output.flush();
    }
}
