package cse41321.algorithms;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class Homework6Test {

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

    @org.testng.annotations.AfterMethod
    public void tearDown() {
        firstTree = null;
        secondTree = null;
        testVisitor = null;
    }
}