import java.io.File;
import java.io.IOException;
import java.util.Scanner;
/**
 * The class containing the main method and implements a database for DNA
 * sequences using a 5-way branching tree structure. It utilizes the Composite
 * and Flyweight design patterns to efficiently store, search, print, and manage
 * biological sequences composed of A, C, G, T, and a $ terminator.
 *
 * @author Johanna
 * @version 02.26.2026
 */

// On my honor:
//
// - I have not used source code obtained from another current or
// former student, or any other unauthorized source, either
// modified or unmodified.
//
// - All source code and documentation used in my program is either
// my original work, or was derived by me from the source code
// published in the textbook for this course. I understand that
// I am permitted to use an LLM tool to assist me with writing
// project code, under the condition that I submit with the
// project a disclosure of LLM use as required for the project.
// I understand that I am responsible for being able to complete
// this work without the use of LLM assistance.
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

public class DNAProj
{
    /**
     * This is nothing but a placeholder for test cases that call the interface
     * methods.
     *
     * @param args
     *            Command line parameters: There are none
     * @throws IOException
     */
    public static void main(String[] args)
        throws IOException
    {
        DNA db = new DNADB(); 

        if (args.length > 0) {
            File inputFile = new File(args[0]);
            Scanner sc = new Scanner(inputFile);

            // 2. Loop through the file and process commands
            while (sc.hasNext()) {
                String command = sc.next();
                
                if (command.equals("insert")) {
                    String seq = sc.next();
                    System.out.println(db.insert(seq));
                } 
                else if (command.equals("remove")) {
                    String seq = sc.next();
                    System.out.println(db.remove(seq));
                } 
                else if (command.equals("print")) {
                    System.out.println(db.print());
                } 
                else if (command.equals("search")) {
                    String seq = sc.next();
                    System.out.println(db.search(seq));
                }
                // Add printLengths or printStats if you want to show those off too!
            }
            sc.close();
        } else {
            System.out.println("Usage: java DNAProj <input-file>");
        }
    }}
