// -------------------------------------------------------------------------
/**
 * Represents a leaf node containing a single DNA sequence
 * 
 * @author Johan
 * @version Feb 26, 2026
 */
public class DNALeafNode
    implements DNATreeNode
{
    // ~ Fields ................................................................
    private String sequence;

    // ~ Constructors ..........................................................
    /**
     * Creates a new leaf node with the given sequence.
     * 
     * @param sequence
     *            The DNA sequence
     */
    public DNALeafNode(String sequence)
    {
        this.sequence = sequence;
    }


    // ~Public Methods ........................................................
    /**
     * Getter method that returns string sequence
     * 
     * @return sequence the string sequence
     */
    public String getSequence()
    {
        return sequence;
    }


    /**
     * Splits the leaf node into an internal node and pushes data down.
     * 
     * @param seq
     *            current substring being evaluated
     * @param fullSequence
     *            the complete sequence being inserted into the tree
     * @param level
     *            current depth in the tree
     * @return the newly created internal node containing both sequences
     */
    public DNATreeNode insert(String seq, String fullSequence, int level)
    {
        DNAInternalNode newInternal = new DNAInternalNode();

        // Push the existing sequence down into the new internal node
        newInternal.insert(this.sequence, this.sequence, level);

        // Push the new sequence down
        newInternal.insert(seq, fullSequence, level);

        return newInternal;
    }


    /**
     * Checks that node contains sequence
     * 
     * @param searchSequence
     *            the exact sequence to find
     * @param level
     *            current depth in the tree
     * @return true if the node sequence exactly matches the search sequence
     */
    public boolean contains(String searchSequence, int level)
    {
        return this.sequence.equals(searchSequence);
    }


    /**
     * If this leaf holds the sequence being removed, return an empty node.
     * 
     * @param removeSequence
     *            the sequence to remove
     * @param level
     *            current depth in the tree
     * @return the empty node instance if matched, otherwise returns this leaf
     *             node
     */
    public DNATreeNode remove(String removeSequence, int level)
    {
        return DNAEmptyNode.getInstance();
    }


    /**
     * Prints the sequence to the string builder with proper indentation
     * 
     * @param level
     *            current depth in the tree used for indentation
     * @param sb
     *            string builder to accumulate the output
     */
    public void print(int level, StringBuilder sb)
    {
        for (int i = 0; i < level * 2; i++)
            sb.append(" ");
        sb.append(sequence).append("\r\n");
    }


    /**
     * Prints the sequence and its length to the string builder with proper
     * indentation
     * 
     * @param level
     *            current depth in the tree used for indentation
     * @param sb
     *            string builder to accumulate the output
     */
    public void printLengths(int level, StringBuilder sb)
    {
        for (int i = 0; i < level * 2; i++)
            sb.append(" ");
        sb.append(sequence).append(" ").append(sequence.length())
            .append("\r\n");
    }


    /**
     * Prints the sequence and the percentage breakdown of its characters to the
     * string builder
     * 
     * @param level
     *            current depth in the tree used for indentation
     * @param sb
     *            string builder to accumulate the output
     */
    public void printStats(int level, StringBuilder sb)
    {
        for (int i = 0; i < level * 2; i++)
            sb.append(" ");

        int a = 0;
        int c = 0;
        int g = 0;
        int t = 0;
        for (int i = 0; i < sequence.length(); i++)
        {
            char ch = sequence.charAt(i);
            if (ch == 'A')
                a++;
            else if (ch == 'C')
                c++;
            else if (ch == 'G')
                g++;
            else
                t++;
        }

        double len = sequence.length();
        String stats = String.format(
            "A:%.2f C:%.2f G:%.2f T:%.2f",
            (a / len) * 100,
            (c / len) * 100,
            (g / len) * 100,
            (t / len) * 100);

        sb.append(sequence).append(" ").append(stats).append("\r\n");
    }


    /**
     * Checks if the leaf matches the search query (exact or prefix).
     * 
     * @param query
     *            the search string being matched
     * @param level
     *            current depth in the tree
     * @param visited
     *            array tracking the total nodes visited during the search
     * @param sb
     *            string builder to collect the matching sequences
     */
    public void search(String query, int level, int[] visited, StringBuilder sb)
    {
        visited[0]++;

        String actualQuery = query;
        boolean exactMatch = false;

        if (query.endsWith("$"))
        {
            actualQuery = query.substring(0, query.length() - 1);
            exactMatch = true;
        }

        if (exactMatch)
        {
            if (this.sequence.equals(actualQuery))
            {
                sb.append(this.sequence).append("\r\n");
            }
        }
        else
        {
            // If it's a prefix search, check if the sequence starts with the
            // query
            if (this.sequence.startsWith(actualQuery))
            {
                sb.append(this.sequence).append("\r\n");
            }
        }
    }
}
