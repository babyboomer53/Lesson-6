package cse41321.algorithms;

import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.testng.Assert.*;

public class Homework6Test {

    private final PrintStream originalStdOut = System.out;
    private ByteArrayOutputStream consoleContent = new ByteArrayOutputStream();

    private Homework6.BinaryTree<Integer> firstTree;
    private Homework6.BinaryTree<Integer> secondTree;
    private Homework6.TestVisitor testVisitor;

    @org.testng.annotations.BeforeMethod
    public void setUp() {
        /*
            T H E   F I R S T   T R E E
                        (1)
                       /   \
                     (2)   (3)
                    /     /   \
                  (4)    (5)  (6)
                 /               \
               (7)               (8)
                                    \
                                    (9)
         */
        firstTree = new Homework6.BinaryTree<>();
        firstTree.insertRoot(1);
        firstTree.getRoot().insertLeft(2);
        firstTree.getRoot().getLeft().insertLeft(4);
        firstTree.getRoot().getLeft().getLeft().insertLeft(7);
        firstTree.getRoot().insertRight(3);
        firstTree.getRoot().getRight().insertLeft(5);
        firstTree.getRoot().getRight().insertRight(6);
        firstTree.getRoot().getRight().getRight().insertRight(8);
        firstTree.getRoot().getRight().getRight().getRight().insertRight(9);
        /*
                    T H E   S E C O N D   T R E E
                               (6)
                              /   \
                             /     \
                          (4)       (8)
                         /   \     /   \
                       (2)   (5) (7)   (9)
                      /   \
                     /     \
                   (1)     (3)
         */
        secondTree = new Homework6.BinaryTree<>();
        secondTree.insertRoot(6);
        secondTree.getRoot().insertLeft(4);
        secondTree.getRoot().getLeft().insertLeft(2);
        secondTree.getRoot().getLeft().insertRight(5);
        secondTree.getRoot().getLeft().getLeft().insertLeft(1);
        secondTree.getRoot().getLeft().getLeft().insertRight(3);
        secondTree.getRoot().insertRight(8);
        secondTree.getRoot().getRight().insertLeft(7);
        secondTree.getRoot().getRight().insertRight(9);

        testVisitor = new Homework6.TestVisitor();

        // Redirect all System.out to consoleContent.
        System.setOut(new PrintStream(this.consoleContent));
    }

    @org.testng.annotations.AfterMethod
    public void tearDown() {
        System.setOut(this.originalStdOut);     // Restore original standard out
        // Clear the consoleContent.
        this.consoleContent = new ByteArrayOutputStream();
    }

    @Test
    public void inOrderTraversal() {
        Homework6.SinglyLinkedList<Integer> expectedResult = new Homework6.SinglyLinkedList<>();
        expectedResult.insertTail(7);
        expectedResult.insertTail(4);
        expectedResult.insertTail(2);
        expectedResult.insertTail(1);
        expectedResult.insertTail(5);
        expectedResult.insertTail(3);
        expectedResult.insertTail(6);
        expectedResult.insertTail(8);
        expectedResult.insertTail(9);

        Homework6.TreeAlgorithms.traverseInOrder(firstTree, testVisitor);

        assertEquals(testVisitor.getVisitedData(), expectedResult);

        expectedResult = new Homework6.SinglyLinkedList<>();
        expectedResult.insertTail(1);
        expectedResult.insertTail(2);
        expectedResult.insertTail(3);
        expectedResult.insertTail(4);
        expectedResult.insertTail(5);
        expectedResult.insertTail(6);
        expectedResult.insertTail(7);
        expectedResult.insertTail(8);
        expectedResult.insertTail(9);

        testVisitor = new Homework6.TestVisitor();

        Homework6.TreeAlgorithms.traverseInOrder(secondTree, testVisitor);

        assertEquals(testVisitor.getVisitedData(), expectedResult);
    }

    @Test
    public void preOrderTraversal() {
        Homework6.SinglyLinkedList<Integer> expectedResult = new Homework6.SinglyLinkedList<>();
        expectedResult.insertTail(1);
        expectedResult.insertTail(2);
        expectedResult.insertTail(4);
        expectedResult.insertTail(7);
        expectedResult.insertTail(3);
        expectedResult.insertTail(5);
        expectedResult.insertTail(6);
        expectedResult.insertTail(8);
        expectedResult.insertTail(9);

        Homework6.TreeAlgorithms.traversePreOrder(firstTree, testVisitor);

        assertEquals(testVisitor.getVisitedData(), expectedResult);

        expectedResult = new Homework6.SinglyLinkedList<>();
        expectedResult.insertTail(6);
        expectedResult.insertTail(4);
        expectedResult.insertTail(2);
        expectedResult.insertTail(1);
        expectedResult.insertTail(3);
        expectedResult.insertTail(5);
        expectedResult.insertTail(8);
        expectedResult.insertTail(7);
        expectedResult.insertTail(9);

        testVisitor = new Homework6.TestVisitor();

        Homework6.TreeAlgorithms.traversePreOrder(secondTree, testVisitor);

        assertEquals(testVisitor.getVisitedData(), expectedResult);
    }

    @Test
    public void postOrderTraversal() {
        Homework6.SinglyLinkedList<Integer> expectedResult = new Homework6.SinglyLinkedList<>();
        expectedResult.insertTail(7);
        expectedResult.insertTail(4);
        expectedResult.insertTail(2);
        expectedResult.insertTail(5);
        expectedResult.insertTail(9);
        expectedResult.insertTail(8);
        expectedResult.insertTail(6);
        expectedResult.insertTail(3);
        expectedResult.insertTail(1);

        Homework6.TreeAlgorithms.traversePostOrder(firstTree, testVisitor);

        assertEquals(testVisitor.getVisitedData(), expectedResult);

        expectedResult = new Homework6.SinglyLinkedList<>();
        expectedResult.insertTail(1);
        expectedResult.insertTail(3);
        expectedResult.insertTail(2);
        expectedResult.insertTail(5);
        expectedResult.insertTail(4);
        expectedResult.insertTail(7);
        expectedResult.insertTail(9);
        expectedResult.insertTail(8);
        expectedResult.insertTail(6);

        testVisitor = new Homework6.TestVisitor();

        Homework6.TreeAlgorithms.traversePostOrder(secondTree, testVisitor);

        assertEquals(testVisitor.getVisitedData(), expectedResult);
    }

    @Test
    public void leafCount() {
        assertEquals(Homework6.countLeaves(firstTree), 3);
        assertEquals(Homework6.countLeaves(secondTree),5);
    }

    @Test
    public void nonleafCount() {
        assertEquals(Homework6.countNonLeaves(firstTree), 6);
        assertEquals(Homework6.countNonLeaves(secondTree),4);
    }

    @Test
    public void height() {
        assertEquals(Homework6.getHeight(firstTree), 5);
        assertEquals(Homework6.getHeight(secondTree),4);
    }

    @Test
    public void printPreOrder(){
        Homework6.printPreOrder(firstTree);
        assertTrue(this.consoleContent.toString().contains("1 2 4 7 3 5 6 8 9"));
        Homework6.printPreOrder(secondTree);
        assertTrue(this.consoleContent.toString().contains("6 4 2 1 3 5 8 7 9"));
    }

    @Test
    public void printInOrder(){
        Homework6.printInOrder(firstTree);
        assertTrue(this.consoleContent.toString().contains("7 4 2 1 5 3 6 8 9"));
        Homework6.printInOrder(secondTree);
        assertTrue(this.consoleContent.toString().contains("1 2 3 4 5 6 7 8 9"));
    }

    @Test
    public void printPostOrder(){
        Homework6.printPostOrder(firstTree);
        assertTrue(this.consoleContent.toString().contains("7 4 2 5 9 8 6 3 1"));
        Homework6.printPostOrder(secondTree);
        assertTrue(this.consoleContent.toString().contains("1 3 2 5 4 7 9 8 6"));
    }
}