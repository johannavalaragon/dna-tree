// -------------------------------------------------------------------------
/**
 * Every node type will implement this interface to update the tree recursively
 * 
 * @author Johan
 * @version Feb 26, 2026
 */
public interface DNATreeNode
{
    /**
     * Recursive sequence inserts into the tree
     * 
     * @param sequence
     *            current substring being evaluated
     * @param fullSequence
     *            sequence being inserted
     * @param level
     *            current depth in the tree
     * @author Johan
     * @version Feb 26, 2026
     * @return true if the sequence inserted sucessfully
     */
    public DNATreeNode insert(String sequence, String fullSequence, int level);


    /**
     * Recursive search to see if a sequence exists
     * 
     * @param sequence
     *            the sequence to find
     * @param level
     *            current depth
     * @return true if exact sequence is found
     */
    public boolean contains(String sequence, int level);


    /**
     * Recursive removal of a sequence
     * 
     * @param sequence
     *            the sequence to remove
     * @param level
     *            current depth
     * @return the updated node
     */
    public DNATreeNode remove(String sequence, int level);


    /**
     * Standard tree print
     * 
     * @param level
     *            current depth
     * @param sb
     *            stringBuilder to collect results
     */
    public void print(int level, StringBuilder sb);


    /**
     * Tree print with sequence lengths
     * 
     * @param level
     *            current depth
     * @param sb
     *            stringBuilder to collect results
     */
    public void printLengths(int level, StringBuilder sb);


    /**
     * Tree print with letter percentage stats
     * 
     * @param level
     *            current depth
     * @param sb
     *            stringBuilder to collect results
     */
    public void printStats(int level, StringBuilder sb);


    /**
     * Traverses the tree to find sequences and counts visited nodes.
     * 
     * @param query
     *            the search string
     * @param level
     *            current depth
     * @param visited
     *            array to hold the visit count
     * @param sb
     *            StringBuilder to collect results
     */
    public
        void
        search(String query, int level, int[] visited, StringBuilder sb);
}
