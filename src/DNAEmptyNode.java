// -------------------------------------------------------------------------
/**
 * Handles E Nodes to save space by using a singleton pattern so only one empty
 * node exists.
 * 
 * @author Johan
 * @version Feb 26, 2026
 */
public class DNAEmptyNode
    implements DNATreeNode
{
    // ~ Fields ................................................................
    private static DNAEmptyNode instance = new DNAEmptyNode();

    // ~ Constructors ..........................................................
    /**
     * Private constructure to prevent new instances
     */
    private DNAEmptyNode()
    {
        // prevents creation of new instance
    }


    // ~Public Methods ........................................................
    /**
     * Getter method for instance
     * 
     * @return instance the shared instance
     */
    public static DNAEmptyNode getInstance()
    {
        return instance;
    }


    /**
     * In the case of insertion into an empty node, turn it into a leaf node
     * 
     * @param sequence
     *            current substring being evaluated
     * @param fullSequence
     *            sequence being inserted
     * @param level
     *            current depth in the tree
     * @return the new leaf node containing the sequence
     */
    public DNATreeNode insert(String sequence, String fullSequence, int level)
    {
        return new DNALeafNode(fullSequence);
    }


    /**
     * An empty node cannot contain the sequence
     * 
     * @param sequence
     *            the sequence to find
     * @param level
     *            current depth in the tree
     * @return false since an empty node holds no data
     */
    public boolean contains(String sequence, int level)
    {
        return false;
    }


    /**
     * Attempting to remove from an empty node changes nothing
     * 
     * @param sequence
     *            the sequence to remove
     * @param level
     *            current depth in the tree
     * @return the empty node itself
     */
    public DNATreeNode remove(String sequence, int level)
    {
        return this;
    }


    /**
     * Prints the empty node representation to the string builder with proper
     * indentation
     * 
     * @param level
     *            current depth in the tree used for indentation
     * @param sb
     *            string builder to append the output
     */
    public void print(int level, StringBuilder sb)
    {
        for (int i = 0; i < level * 2; i++)
            sb.append(" ");
        sb.append("E\r\n");
    }


    /**
     * Prints the empty node representation for the length command
     * 
     * @param level
     *            current depth in the tree used for indentation
     * @param sb
     *            string builder to append the output
     */
    public void printLengths(int level, StringBuilder sb)
    {
        print(level, sb);
    }


    /**
     * Prints the empty node representation for the stats command
     * 
     * @param level
     *            current depth in the tree used for indentation
     * @param sb
     *            string builder to append the output
     */
    public void printStats(int level, StringBuilder sb)
    {
        print(level, sb);
    }


    /**
     * Records a visit to an empty node.
     * 
     * @param query
     *            the string being searched for
     * @param level
     *            current depth in the tree
     * @param visited
     *            array keeping track of the visited nodes count
     * @param sb
     *            string builder to collect search results
     */
    public void search(String query, int level, int[] visited, StringBuilder sb)
    {
        visited[0]++;
    }

}
