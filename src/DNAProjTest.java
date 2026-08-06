import student.TestCase;
import student.testingsupport.annotations.ScoringWeight;

/**
 * @author CS3114/5040 staff
 * @version Spring 2026
 */
public class DNAProjTest
    extends TestCase
{
    private DNA it;

    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp()
    {
        it = new DNADB();
    }


    /**
     * Test output formatting
     */
    public void testSampleInput()
    {
        assertFuzzyEquals("Sequence |ACGT| inserted", it.insert("ACGT"));
        assertFuzzyEquals("Sequence |ACGT| already exists", it.insert("ACGT"));
        assertFuzzyEquals("Sequence |ACGT| removed", it.remove("ACGT"));
        assertFuzzyEquals("Sequence |AAAA| inserted", it.insert("AAAA"));
        assertFuzzyEquals("Sequence |AA| inserted", it.insert("AA"));
        assertFuzzyEquals("Sequence |ACG| does not exist", it.remove("ACG"));
        assertFuzzyEquals(
            "tree dump:\r\n" + "I\r\n" + "  I\r\n" + "    I\r\n"
                + "      AAAA\r\n" + "      E\r\n" + "      E\r\n"
                + "      E\r\n" + "      AA\r\n" + "    E\r\n" + "    E\r\n"
                + "    E\r\n" + "    E\r\n" + "  E\r\n" + "  E\r\n" + "  E\r\n"
                + "  E",
            it.print());
        assertFuzzyEquals(
            "tree dump with lengths:\r\n" + "I\r\n" + "  I\r\n" + "    I\r\n"
                + "      AAAA 4\r\n" + "      E\r\n" + "      E\r\n"
                + "      E\r\n" + "      AA 2\r\n" + "    E\r\n" + "    E\r\n"
                + "    E\r\n" + "    E\r\n" + "  E\r\n" + "  E\r\n" + "  E\r\n"
                + "  E",
            it.printLengths());
        assertFuzzyEquals(
            "tree dump with stats:\r\n" + "I\r\n" + "  I\r\n" + "    I\r\n"
                + "      AAAA A:100.00 C:0.00 G:0.00 T:0.00\r\n" + "      E\r\n"
                + "      E\r\n" + "      E\r\n"
                + "      AA A:100.00 C:0.00 G:0.00 T:0.00\r\n" + "    E\r\n"
                + "    E\r\n" + "    E\r\n" + "    E\r\n" + "  E\r\n"
                + "  E\r\n" + "  E\r\n" + "  E",
            it.printStats());
        assertFuzzyEquals(
            "AAAA\r\n" + "# of nodes visited: 4",
            it.search("AAAA$"));
        assertFuzzyEquals(
            "AAAA\r\n" + "AA\r\n" + "# of nodes visited: 8",
            it.search("AA"));
        assertFuzzyEquals(
            "No sequence found\r\n" + "# of nodes visited: 3",
            it.search("ACGT$"));
    }


    /**
     * Example tests for bad input error formatting
     */
    public void testBadInput()
    {
        assertFuzzyEquals(
            "testBadInput",
            "Bad input: Sequence may not be null\r\n",
            it.insert(null));
        assertFuzzyEquals(
            "testBadInput",
            "Bad input: Sequence may not be empty\r\n",
            it.insert(""));
        assertFuzzyEquals(
            "testBadInput",
            "Bad Input Sequence |AXA|\r\n",
            it.insert("AXA"));
        assertFuzzyEquals(
            "testBadInput",
            "Bad Input Sequence |A A|\r\n",
            it.insert("A A"));
        assertFuzzyEquals(
            "testBadInput",
            "Bad Input Sequence |A |\r\n",
            it.insert("A "));
        assertFuzzyEquals(
            "testBadInput",
            "Bad Input Sequence |A$|\r\n",
            it.insert("A$"));
        assertFuzzyEquals(
            "testBadInput",
            "Bad input sequence |A$A|\r\n",
            it.search("A$A"));
        assertFuzzyEquals(
            "Bad input: Sequence may not be null\r\n",
            it.remove(null));
        assertFuzzyEquals(
            "Bad input: Sequence may not be empty\r\n",
            it.remove(""));
    }


    /**
     * Kills mutators hiding in the Empty Node and string truncation edge cases.
     */
    public void testEmptyTree()
    {
        // Test printing an empty tree
        assertFuzzyEquals("tree dump:\r\n" + "E", it.print());

        // Test searching an empty tree
        assertFuzzyEquals(
            "No sequence found\r\n" + "# of nodes visited: 1",
            it.search("A"));

        // Test removing from an empty tree
        assertFuzzyEquals("Sequence |A| does not exist", it.remove("A"));
    }


    /**
     * Kills the heavy mutators in DNAInternalNode by forcing the tree to split,
     * and then forcing it to merge back together.
     */
    public void testMergeAndCollapse()
    {
        it.insert("A");
        it.insert("AC");

        // Tree is now split. Removing "AC" should trigger the internal node
        // to realize it only has one leaf ("A") left, and collapse itself.
        assertFuzzyEquals("Sequence |AC| removed", it.remove("AC"));

        // Verify it collapsed back to a single leaf at the root
        assertFuzzyEquals("tree dump:\r\n" + "A", it.print());

        // Remove the last node to trigger the emptyCount == 5 collapse
        assertFuzzyEquals("Sequence |A| removed", it.remove("A"));

        assertFuzzyEquals("tree dump:\r\n" + "E", it.print());
    }


    /**
     * Kills the math operation mutators in DNALeafNode and tests deep prefix
     * searches.
     */
    public void testSearchAndStats()
    {
        // Inserting a sequence with all 4 letters forces the printStats math
        // to calculate non-zero values, killing the arithmetic mutators.
        it.insert("ACGT");
        it.insert("ACGA");

        // This maps the EXACT 5-way branch structure as the tree splits deeply
        assertFuzzyEquals(
            "tree dump with stats:\r\n" + "I\r\n" + "  I\r\n" + "    E\r\n"
                + "    I\r\n" + "      E\r\n" + "      E\r\n" + "      I\r\n"
                + "        ACGA A:50.00 C:25.00 G:25.00 T:0.00\r\n"
                + "        E\r\n" + "        E\r\n"
                + "        ACGT A:25.00 C:25.00 G:25.00 T:25.00\r\n"
                + "        E\r\n" + "      E\r\n" + "      E\r\n" + "    E\r\n"
                + "    E\r\n" + "    E\r\n" + "  E\r\n" + "  E\r\n" + "  E\r\n"
                + "  E",
            it.printStats());

        // Prefix search that returns multiple results
        assertFuzzyEquals(
            "ACGA\r\n" + "ACGT\r\n" + "# of nodes visited: 9",
            it.search("ACG"));

        // Exact search that fails deep in the tree
        assertFuzzyEquals(
            "No sequence found\r\n" + "# of nodes visited: 5",
            it.search("ACGC$"));
    }


    /**
     * Targets surviving arithmetic and conditional mutators by utilizing
     * alternative array indices and testing a non-collapsing merge scenario
     */
    public void testCoverageKillers()
    {
        // Inserts into alternative branches to kill array index mutators and
        // length arithmetic mutators
        it.insert("C");
        it.insert("G");
        it.insert("T");

        assertFuzzyEquals(
            "tree dump with lengths:\r\n" + "I\r\n" + "  E\r\n" + "  C 1\r\n"
                + "  G 1\r\n" + "  T 1\r\n" + "  E",
            it.printLengths());

        // Inserts items into the same branch and removes only one to test the
        // scenario where
        // the tree must not collapse because sibling sequences still exist
        it.insert("AA");
        it.insert("AC");
        it.insert("AG");
        it.remove("AA");

        assertFuzzyEquals(
            "tree dump:\r\n" + "I\r\n" + "  I\r\n" + "    E\r\n" + "    AC\r\n"
                + "    AG\r\n" + "    E\r\n" + "    E\r\n" + "  C\r\n"
                + "  G\r\n" + "  T\r\n" + "  E",
            it.print());

        // Searches for an exact match on a non-default branch
        assertFuzzyEquals("G\r\n" + "# of nodes visited: 2", it.search("G$"));

        // Searches for a sequence that fails on a non-existent branch
        assertFuzzyEquals(
            "No sequence found\r\n" + "# of nodes visited: 2",
            it.search("TGC"));
    }


    /**
     * Targets deep tree conditional logic, prefix false-matches, and internal
     * node sibling merges to eliminate surviving condition and arithmetic
     * mutators
     */
    public void testEdgeCases()
    {
        it.insert("AC");

        // Prefix search reaches a leaf but fails the startsWith check
        assertFuzzyEquals(
            "No sequence found\r\n" + "# of nodes visited: 1",
            it.search("ACT"));

        // Exact search reaches a leaf but fails the equals check
        assertFuzzyEquals(
            "No sequence found\r\n" + "# of nodes visited: 1",
            it.search("A$"));

        // Creates nested internal nodes to test complex removal logic
        it.insert("A");
        it.insert("ACA");
        it.insert("ACT");

        // Removes a node where the parent checks its children and correctly
        // does not collapse
        assertFuzzyEquals("Sequence |A| removed", it.remove("A"));

        // Inserts an odd-length sequence to kill division mutators by creating
        // uneven percentages
        it.insert("ACGTA");
        assertTrue(
            it.printStats().contains("ACGTA A:40.00 C:20.00 G:20.00 T:20.00"));

        // Searches for a sequence that shares a prefix but diverges at an
        // internal node
        assertFuzzyEquals(
            "No sequence found\r\n" + "# of nodes visited: 4",
            it.search("ACC$"));

        // Tests a removal failure that successfully navigated an internal node
        assertFuzzyEquals("Sequence |ACG| does not exist", it.remove("ACG"));
    }


    /**
     * Targets space-formatting mutators by using strict assertEquals to catch
     * whitespace indentation differences and exercises deep branch conditionals
     */
    public void testStrictFormatting()
    {
        it.insert("A");
        it.insert("C");

        assertEquals(
            "tree dump:\r\n" + "I\r\n" + "  A\r\n" + "  C\r\n" + "  E\r\n"
                + "  E\r\n" + "  E",
            it.print().trim());

        assertEquals(
            "tree dump with lengths:\r\n" + "I\r\n" + "  A 1\r\n" + "  C 1\r\n"
                + "  E\r\n" + "  E\r\n" + "  E",
            it.printLengths().trim());

        assertEquals(
            "tree dump with stats:\r\n" + "I\r\n"
                + "  A A:100.00 C:0.00 G:0.00 T:0.00\r\n"
                + "  C A:0.00 C:100.00 G:0.00 T:0.00\r\n" + "  E\r\n"
                + "  E\r\n" + "  E",
            it.printStats().trim());
    }


    /**
     * Kills arithmetic and spacing mutators by using strict assertEquals to
     * verify exact indentation levels across Internal, Leaf, and Empty nodes
     */
    public void testSpacing()
    {
        it.insert("A");
        it.insert("C");
        it.insert("G");
        it.insert("T");
        it.insert("AA");

        // .trim() strips the reference solution's trailing newline but keeps
        // internal spaces strict
        assertEquals(
            "tree dump with lengths:\r\n" + "I\r\n" + "  I\r\n" + "    AA 2\r\n"
                + "    E\r\n" + "    E\r\n" + "    E\r\n" + "    A 1\r\n"
                + "  C 1\r\n" + "  G 1\r\n" + "  T 1\r\n" + "  E",
            it.printLengths().trim());

        assertEquals(
            "tree dump:\r\n" + "I\r\n" + "  I\r\n" + "    AA\r\n" + "    E\r\n"
                + "    E\r\n" + "    E\r\n" + "    A\r\n" + "  C\r\n"
                + "  G\r\n" + "  T\r\n" + "  E",
            it.print().trim());

        it.remove("AA");
        assertEquals(
            "tree dump:\r\n" + "I\r\n" + "  A\r\n" + "  C\r\n" + "  G\r\n"
                + "  T\r\n" + "  E",
            it.print().trim());

        assertFuzzyEquals("Sequence |C| already exists", it.insert("C"));
    }


    /**
     * Kills the conditional mutators hiding in the validation methods by
     * testing boundary constraints and invalid characters
     */
    public void testConditionals()
    {
        // Tests invalid characters to trigger the boolean returns inside the
        // loops
        assertFuzzyEquals("Bad Input Sequence |B|\r\n", it.insert("B"));
        assertFuzzyEquals("Bad Input Sequence |a|\r\n", it.insert("a"));
        assertFuzzyEquals("Bad Input Sequence |A1|\r\n", it.insert("A1"));

        // Tests sequence removal validation constraints
        assertFuzzyEquals("Bad Input Sequence |A$|\r\n", it.remove("A$"));
        assertFuzzyEquals("Bad Input Sequence |X|\r\n", it.remove("X"));

        // Tests sequence search validation constraints ensuring the terminator
        // is placed correctly
        assertFuzzyEquals("Bad input sequence |$A|\r\n", it.search("$A"));
        assertFuzzyEquals("Bad input sequence |A$B|\r\n", it.search("A$B"));
        assertFuzzyEquals("Bad input sequence |X|\r\n", it.search("X"));
    }


    /**
     * Tests where an empty string and a lone '$' are legitimate search terms.
     */
    public void testEmptySearch()
    {
        // A lone "$" searches for the empty string.
        assertFuzzyEquals(
            "No sequence found\r\n" + "# of nodes visited: 1",
            it.search("$"));

        it.insert("A");
        it.insert("C"); // Forces root to become an Internal Node

        assertFuzzyEquals(
            "No sequence found\r\n" + "# of nodes visited: 2",
            it.search("$"));

        // An empty string "" is a prefix for everything.
        // It should traverse the entire tree and print all sequences.
        assertFuzzyEquals(
            "A\r\n" + "C\r\n" + "# of nodes visited: 6",
            it.search(""));
    }


    /**
     * Tests contains sequence logic by checking duplicates at Level 3 to
     * eliminate deep arithmetic mutators
     */
    public void testContainDupesDeep()
    {
        // Inserting these creates nested internal nodes down to Level 3
        it.insert("A");
        it.insert("AC");
        it.insert("ACA");

        // Forces the tree to deeply search for the sequence at Level 3,
        // which catches mutators that sabotage the level addition logic
        assertFuzzyEquals("Sequence |ACA| already exists", it.insert("ACA"));
    }


    /**
     * Tests deep strict spacing and triggering a false-match during a leaf node
     * removal attempt.
     */
    public void testPrintStatsSpacing()
    {
        // Insert "A" so the root is a LeafNode("A").
        // Removing "C" to evaluate if "A".equals("C")
        it.insert("A");
        assertFuzzyEquals("Sequence |C| does not exist", it.remove("C"));

        // The 'A' branch becomes another Internal Node
        // The leaves "A" and "AA" are now pushed down to Level 2.
        it.insert("AA");

        // At Level 2, the indentation must be 4 spaces.
        assertEquals(
            "tree dump with stats:\r\n" + "I\r\n" + "  I\r\n"
                + "    AA A:100.00 C:0.00 G:0.00 T:0.00\r\n" + "    E\r\n"
                + "    E\r\n" + "    E\r\n"
                + "    A A:100.00 C:0.00 G:0.00 T:0.00\r\n" + "  E\r\n"
                + "  E\r\n" + "  E\r\n" + "  E",
            it.printStats().trim());
    }
}
