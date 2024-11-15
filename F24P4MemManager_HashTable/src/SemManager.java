
/**
 * {Project Description Here}
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Scanner;

/**
 * The class containing the main method.
 *
 * @author Jett Morrow jettmorrow Adam Schantz adams03
 * @version 11/12/2024
 */

// On my honor:
// - I have not used source code obtained from another current or
// former student, or any other unauthorized source, either
// modified or unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

public class SemManager {
    /**
     * mainnn class
     *
     * @param args
     *            Command line parameters
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args == null) {
            return;
        }

        if (args.length != 3) {
            throw new IllegalArgumentException(
                "Expected 3 arguments:  {initial-memory-size} {initial-hash-size} {command-file}");
        }

        try (Scanner fileInput = new Scanner(new File(args[2]))) {
            PrintWriter stdout = new PrintWriter(System.out);

            // in bytes
            int memorySize = Integer.parseInt(args[0]);
            // size of hashTable must be power of two
            int hashSize = Integer.parseInt(args[1]);

            Controller controller = new Controller(memorySize, hashSize);
            CommandProcessor commandProcessor = new CommandProcessor(controller,
                memorySize, hashSize);

            commandProcessor.interpretAllLines(fileInput, stdout);
            fileInput.close();
            stdout.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (NumberFormatException e) {
            throw new NumberFormatException(
                "{initial-memory-size} && {initial-hash-size} must be a number");
        }
    }
}
