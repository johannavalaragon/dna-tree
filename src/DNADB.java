// -------------------------------------------------------------------------

/**
 * The database implementation for this project. This manages the commands for
 * the DNA tree.
 *
 * @author CS3114/5040 Staff
 * @version Spring 2026
 */
public class DNADB
    implements DNA
{

    private DNATreeNode root;

    // ----------------------------------------------------------
    /**
     * Create a new DNADB object.
     */
    public DNADB()
    {
        root = DNAEmptyNode.getInstance();
    }


    /**
     * Validates if the sequence contains only A, C, G, T.
     */
    private boolean isValidSequence(String sequence)
    {
        for (int i = 0; i < sequence.length(); i++)
        {
            char c = sequence.charAt(i);
            if (c != 'A' && c != 'C' && c != 'G' && c != 'T')
            {
                return false;
            }
        }
        return true;
    }


    /**
     * Validation for searches: A, C, G, T, and optionally $ at the VERY END.
     */
    private boolean isValidSearch(String sequence)
    {
        for (int i = 0; i < sequence.length(); i++)
        {
            char c = sequence.charAt(i);
            if (c == '$')
            {
                if (i != sequence.length() - 1)
                {
                    return false; // $ is invalid unless it's the last char
                }
            }
            else if (c != 'A' && c != 'C' && c != 'G' && c != 'T')
            {
                return false;
            }
        }
        return true;
    }


    // ----------------------------------------------------------
    /**
     * Insert a DNA string into the database
     * 
     * @param sequence
     *            The sequence to insert
     * @return The outcomes message string
     */
    public String insert(String sequence)
    {
        if (sequence == null)
        {
            return "Bad input: Sequence may not be null\r\n";
        }
        if (sequence.equals(""))
        {
            return "Bad input: Sequence may not be empty\r\n";
        }
        if (!isValidSequence(sequence))
        {
            return "Bad Input Sequence |" + sequence + "|\r\n";
        }

        // Prevent duplicate insertions
        if (root.contains(sequence, 0))
        {
            return "Sequence |" + sequence + "| already exists";
        }

        root = root.insert(sequence, sequence, 0);
        return "Sequence |" + sequence + "| inserted";
    }


    // ----------------------------------------------------------
    /**
     * Remove a DNA string into the database
     * 
     * @param sequence
     *            The sequence to remove
     * @return The outcomes message string
     */
    public String remove(String sequence)
    {
        if (sequence == null)
        {
            return "Bad input: Sequence may not be null\r\n";
        }
        if (sequence.equals(""))
        {
            return "Bad input: Sequence may not be empty\r\n";
        }
        if (!isValidSequence(sequence))
        {
            return "Bad Input Sequence |" + sequence + "|\r\n";
        }

        if (!root.contains(sequence, 0))
        {
            return "Sequence |" + sequence + "| does not exist";
        }

        root = root.remove(sequence, 0);
        return "Sequence |" + sequence + "| removed";
    }


    // ----------------------------------------------------------
    /**
     * Print the tree
     * 
     * @return the print string
     */
    public String print()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("tree dump:\r\n");
        root.print(0, sb);

        String result = sb.toString();
        if (result.endsWith("\r\n"))
        {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }


    // ----------------------------------------------------------
    /**
     * Print the lengths
     * 
     * @return the print string
     */
    public String printLengths()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("tree dump with lengths:\r\n");
        root.printLengths(0, sb);

        String result = sb.toString();
        if (result.endsWith("\r\n"))
        {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }


    // ----------------------------------------------------------
    /**
     * Print the stats
     * 
     * @return the print string
     */
    public String printStats()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("tree dump with stats:\r\n");
        root.printStats(0, sb);

        String result = sb.toString();
        if (result.endsWith("\r\n"))
        {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }


    // ----------------------------------------------------------
    /**
     * Search for a given string
     * 
     * @param sequence
     *            The sequence to search for
     * @return the print string
     */
    public String search(String sequence)
    {
        if (sequence == null)
        {
            return "Bad input: Sequence may not be null\r\n";
        }
        
        if (!isValidSearch(sequence))
        {
            return "Bad input sequence |" + sequence + "|\r\n";
        }

        StringBuilder sb = new StringBuilder();
        int[] visited = new int[1]; // Using array as a mutable integer

        root.search(sequence, 0, visited, sb);

        if (sb.length() == 0)
        {
            sb.append("No sequence found\r\n");
        }

        sb.append("# of nodes visited: ").append(visited[0]);

        return sb.toString();
    }
}
