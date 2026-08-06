// -------------------------------------------------------------------------
/**
 * Internal placeholder node that directs search and insertion. Stores 5
 * childern (A, C, G, T, $)
 * 
 * @author Johan
 * @version Feb 26, 2026
 */
public class DNAInternalNode
    implements DNATreeNode
{
    // ~ Fields ................................................................
    private DNATreeNode[] children;

    // ~ Constructors ..........................................................
    /**
     * Initializes the 5-way branch with Flyweight empty nodes.
     */
    public DNAInternalNode()
    {
        children = new DNATreeNode[5];
        for (int i = 0; i < 5; i++)
        {
            children[i] = DNAEmptyNode.getInstance();
        }
    }


    // ~Public Methods ........................................................
    /**
     * Inserts into the correct branch based on current level.
     * 
     * @param sequence
     *            current substring being evaluated
     * @param fullSequence
     *            the complete sequence being inserted into the tree
     * @param level
     *            the current depth in the tree used to determine the branch
     * @return the current internal node after updating its children
     */
    public DNATreeNode insert(String sequence, String fullSequence, int level)
    {
        int index = getBranchIndex(fullSequence, level);
        children[index] =
            children[index].insert(sequence, fullSequence, level + 1);
        return this;
    }


    /**
     * Checks if the branch contains the sequence by traversing down the tree
     * 
     * @param sequence
     *            the sequence to search for
     * @param level
     *            the current depth in the tree used to determine the branch
     * @return true if the sequence is found within the child branches, false
     *             otherwise
     */
    public boolean contains(String sequence, int level)
    {
        int index = getBranchIndex(sequence, level);
        return children[index].contains(sequence, level + 1);
    }


    /**
     * Maps the character at the current level to an array index. Index 4 ($) is
     * used if the sequence has ended.
     * 
     * @param seq
     *            the sequence being evaluated
     * @param level
     *            the current character position to evaluate
     * @return the index corresponding to the character, using the terminator
     *             branch if the sequence has ended
     */
    private int getBranchIndex(String seq, int level)
    {
        if (level >= seq.length())
        {
            return 4; // The '$' terminator branch
        }

        char c = seq.charAt(level);
        switch (c)
        {
            case 'A':
                return 0;
            case 'C':
                return 1;
            case 'G':
                return 2;
            case 'T':
                return 3;
            default:
                return 4;
        }
    }


    /**
     * Accessor for tree traversal (useful later for printing).
     * 
     * @return the array of children
     */
    public DNATreeNode[] getChildren()
    {
        return children;
    }


    /**
     * Traverses down to remove a node, then checks if it needs to merge.
     * 
     * @param sequence
     *            the sequence to remove
     * @param level
     *            the current depth in the tree
     * @return the updated node, which may be a collapsed leaf node, an empty
     *             node, or this internal node
     */
    public DNATreeNode remove(String sequence, int level)
    {
        int index = getBranchIndex(sequence, level);
        children[index] = children[index].remove(sequence, level + 1);

        int leafCount = 0;
        int emptyCount = 0;
        DNALeafNode lastLeaf = null;

        // Check the status of all 5 children
        for (int i = 0; i < 5; i++)
        {
            if (children[i] instanceof DNAEmptyNode)
            {
                emptyCount++;
            }
            else if (children[i] instanceof DNALeafNode)
            {
                leafCount++;
                lastLeaf = (DNALeafNode)children[i];
            }
            else
            {
                // If there's an internal node, we absolutely cannot merge
                return this;
            }
        }

        // If the internal node is completely empty
        if (emptyCount == 5)
        {
            return DNAEmptyNode.getInstance();
        }

        // If there is exactly one leaf remaining, collapse this internal node
        if (leafCount == 1 && emptyCount == 4)
        {
            return lastLeaf;
        }

        return this;
    }


    /**
     * Standard tree print that appends the internal node indicator and
     * recursively prints children.
     * 
     * @param level
     *            the current depth in the tree used for indentation
     * @param sb
     *            the string builder to accumulate the output
     */
    public void print(int level, StringBuilder sb)
    {
        for (int i = 0; i < level * 2; i++)
            sb.append(" ");
        sb.append("I\r\n");
        for (int i = 0; i < 5; i++)
        {
            children[i].print(level + 1, sb);
        }
    }


    /**
     * Tree print with sequence lengths that appends the internal node indicator
     * and recursively prints children.
     * 
     * @param level
     *            the current depth in the tree used for indentation
     * @param sb
     *            the string builder to accumulate the output
     */
    public void printLengths(int level, StringBuilder sb)
    {
        for (int i = 0; i < level * 2; i++)
            sb.append(" ");
        sb.append("I\r\n");
        for (int i = 0; i < 5; i++)
        {
            children[i].printLengths(level + 1, sb);
        }
    }


    /**
     * Tree print with letter percentage stats that appends the internal node
     * indicator and recursively prints children.
     * 
     * @param level
     *            the current depth in the tree used for indentation
     * @param sb
     *            the string builder to accumulate the output
     */
    public void printStats(int level, StringBuilder sb)
    {
        for (int i = 0; i < level * 2; i++)
            sb.append(" ");
        sb.append("I\r\n");
        for (int i = 0; i < 5; i++)
        {
            children[i].printStats(level + 1, sb);
        }
    }


    /**
     * Routes the search down the specific path, or explores all paths if prefix
     * is exhausted.
     * 
     * @param query
     *            the search string being matched
     * @param level
     *            the current depth in the tree
     * @param visited
     *            an array tracking the total nodes visited during the search
     * @param sb
     *            the string builder to collect the matching sequences
     */
    public void search(String query, int level, int[] visited, StringBuilder sb)
    {
        visited[0]++;

        if (level < query.length())
        {
            // Following the prefix, go down the specific branch
            char c = query.charAt(level);
            int index = 4; // Default to '$'
            if (c == 'A')
                index = 0;
            else if (c == 'C')
                index = 1;
            else if (c == 'G')
                index = 2;
            else if (c == 'T')
                index = 3;

            children[index].search(query, level + 1, visited, sb);
        }
        else
        {
            // The prefix is exhausted, explore all 5 branches to gather all
            // matches
            for (int i = 0; i < 5; i++)
            {
                children[i].search(query, level + 1, visited, sb);
            }
        }
    }
}
