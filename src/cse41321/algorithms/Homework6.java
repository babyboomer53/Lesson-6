package cse41321.algorithms;

import org.testng.internal.Graph;

import java.io.PrintStream;
import java.util.NoSuchElementException;

public class Homework6 {
    /**
     * A generic BinaryTree class
     *
     * @param <E>
     */
    public static class BinaryTree<E> {
        /**
         * An inner class defining a Node type
         */
        public class Node {
            E data;
            Node left;
            Node right;

            // Only allow BinaryTree to create Nodes
            private Node(E data) {
                this.data = data;
            }

            public E getData() {
                return data;
            }

            public Node getLeft() {
                return left;
            }

            public Node getRight() {
                return right;
            }

            public boolean hasLeft() {
                return left != null;
            }

            public boolean hasRight() {
                return right != null;
            }

            public boolean isLeaf() {
                return !hasLeft() && !hasRight();
            }

            public Node insertLeft(E data) throws IllegalStateException {
                if (hasLeft()) {
                    throw new IllegalStateException(
                            "Cannot insert because left child already exists");
                }

                left = new Node(data);
                ++size;

                return left;
            }

            public Node insertRight(E data) throws IllegalStateException {
                if (hasRight()) {
                    throw new IllegalStateException(
                            "Cannot insert because right child already exists");
                }

                right = new Node(data);
                ++size;

                return right;
            }

            public E removeLeft() throws IllegalStateException {
                if (!hasLeft()) {
                    throw new IllegalStateException(
                            "Cannot remove because left child does not exist");
                }
                if (!left.isLeaf()) {
                    throw new IllegalStateException(
                            "Cannot remove because left child is not a leaf");
                }

                E data = left.data;
                left = null;
                --size;

                return data;
            }

            public E removeRight() throws IllegalStateException {
                if (!hasRight()) {
                    throw new IllegalStateException(
                            "Cannot remove because right child does not exist");
                }
                if (!right.isLeaf()) {
                    throw new IllegalStateException(
                            "Cannot remove because right child is not a leaf");
                }

                E data = right.data;
                right = null;
                --size;

                return data;
            }
        }   // End of Node class

        // Definition of the BinaryTree class continues here
        private int size;
        private Node root;

        public int getSize() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public Node getRoot() {
            return root;
        }

        public Node insertRoot(E data) throws IllegalStateException {
            if (!isEmpty()) {
                throw new IllegalStateException(
                        "Cannot insert root into non-empty tree");
            }

            root = new Node(data);
            ++size;

            return root;
        }

        public E removeRoot() throws IllegalStateException {
            if (isEmpty()) {
                throw new IllegalStateException(
                        "Cannot remove root of empty tree");
            }
            if (!root.isLeaf()) {
                throw new IllegalStateException(
                        "Cannot remove because root is not a leaf");
            }

            E data = root.data;
            root = null;
            --size;

            return data;
        }

        public BinaryTree<E> merge(BinaryTree<E> other, E data) {
            BinaryTree<E> merged = new BinaryTree<E>();

            // Merge this and other into new tree with data as root
            merged.root = new Node(data);
            merged.root.left = this.root;
            merged.root.right = other.root;
            merged.size = 1 + this.size + other.size;

            // Remove all nodes from this and other
            this.root = null;
            this.size = 0;
            other.root = null;
            other.size = 0;

            return merged;
        }

    }   // BinaryTree class definition ends here

    /**
     * A generic SinglyLinkedList class
     */
    public static class SinglyLinkedList<E> {
        // An element in a linked list
        public class Element {
            private E data;
            private Element next;

            // Only allow SinglyLinkedList to construct Elements
            private Element(E data) {
                this.data = data;
                this.next = null;
            }

            public E getData() {
                return data;
            }

            public Element getNext() {
                return next;
            }

            private SinglyLinkedList getOwner() {
                return SinglyLinkedList.this;
            }
        }

        private Element head;
        private Element tail;
        private int size;

        public Element getHead() {
            return head;
        }

        public Element getTail() {
            return tail;
        }

        public int getSize() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public Element insertHead(E data) {
            Element newElement = new Element(data);

            if (isEmpty()) {
                // Insert into empty list
                head = newElement;
                tail = newElement;
            } else {
                // Insert into non-empty list
                newElement.next = head;
                head = newElement;
            }

            ++size;

            return newElement;
        }

        public Element insertTail(E data) {
            Element newElement = new Element(data);

            if (isEmpty()) {
                // Insert into empty list
                head = newElement;
                tail = newElement;
            } else {
                // Insert into non-empty list
                tail.next = newElement;
                tail = newElement;
            }

            ++size;

            return newElement;
        }

        public Element insertAfter(Element element, E data)
                throws IllegalArgumentException {
            // Check pre-conditions
            if (element == null) {
                throw new IllegalArgumentException(
                        "Argument 'element' must not be null");
            }
            if (element.getOwner() != this) {
                throw new IllegalArgumentException(
                        "Argument 'element' does not belong to this list");
            }

            // Insert new element
            Element newElement = new Element(data);
            if (tail == element) {
                // Insert new tail
                element.next = newElement;
                tail = newElement;
            } else {
                // Insert into middle of list
                newElement.next = element.next;
                element.next = newElement;
            }

            ++size;

            return newElement;
        }

        public E removeHead() throws NoSuchElementException {
            // Check pre-conditions
            if (isEmpty()) {
                throw new NoSuchElementException("Cannot remove from empty list");
            }

            // Remove the head
            Element oldHead = head;
            if (size == 1) {
                // Handle removal of the last element
                head = null;
                tail = null;
            } else {
                head = head.next;
            }

            --size;

            return oldHead.data;
        }

        // Note that there is no removeTail.  This cannot be implemented
        // efficiently because it would require O(n) to scan from head until
        // reaching the item _before_ tail.

        public E removeAfter(Element element)
                throws IllegalArgumentException, NoSuchElementException {
            // Check pre-conditions
            if (element == null) {
                throw new IllegalArgumentException(
                        "Argument 'element' must not be null");
            }
            if (element.getOwner() != this) {
                throw new IllegalArgumentException(
                        "Argument 'element' does not belong to this list");
            }
            if (element == tail) {
                throw new IllegalArgumentException(
                        "Argument 'element' must have a non-null next element");
            }

            // Remove element
            Element elementToRemove = element.next;
            if (elementToRemove == tail) {
                // Remove the tail
                element.next = null;
                tail = element;
            } else {
                // Remove from middle of list
                element.next = elementToRemove.next;
            }

            --size;

            return elementToRemove.data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SinglyLinkedList<?> that = (SinglyLinkedList<?>) o;

            if (this.size != that.size) return false;

            // Return whether all elements are the same
            SinglyLinkedList<?>.Element thisElem = this.getHead();
            SinglyLinkedList<?>.Element thatElem = that.getHead();
            while (thisElem != null && thatElem != null) {
                if (!thisElem.getData().equals(thatElem.getData())) {
                    return false;
                }
                thisElem = thisElem.getNext();
                thatElem = thatElem.getNext();
            }

            return true;
        }
    }   // The SinglyLinkedList class definition ends here

    /**
     * A generic Queue class
     *
     * @param <E>
     */
    public static class Queue<E> {
        private SinglyLinkedList<E> list = new SinglyLinkedList<E>();

        public void enqueue(E data) {
            list.insertTail(data);
        }

        public E dequeue() throws NoSuchElementException {
            if (isEmpty()) {
                throw new NoSuchElementException();
            }

            return list.removeHead();
        }

        public E peek() throws NoSuchElementException {
            if (isEmpty()) {
                throw new NoSuchElementException();
            }

            return list.getHead().getData();
        }

        public int getSize() {
            return list.getSize();
        }

        public boolean isEmpty() {
            return list.isEmpty();
        }
    }   // End of Queue class definition

    /**
     * Visitor interface
     */
    public interface Visitor<E> {
        void visit(E data);
    }   // End of Visitor interface definition

    /**
     * TreeAlgorithms class
     */
    public static final class TreeAlgorithms {
        // Pre-order traversal
        public static <E> void traversePreOrder(
                BinaryTree<E> tree,
                Visitor<E> visitor) {
            doTraversePreOrder(tree.getRoot(), visitor);
        }

        private static <E> void doTraversePreOrder(
                BinaryTree<E>.Node node,
                Visitor<E> visitor) {
            if (node == null) {
                return;
            }

            visitor.visit(node.getData());
            doTraversePreOrder(node.getLeft(), visitor);
            doTraversePreOrder(node.getRight(), visitor);
        }

        // In-order traversal
        public static <E> void traverseInOrder(
                BinaryTree<E> tree,
                Visitor<E> visitor) {
            doTraverseInOrder(tree.getRoot(), visitor);
        }

        private static <E> void doTraverseInOrder(
                BinaryTree<E>.Node node,
                Visitor<E> visitor) {
            if (node == null) {
                return;
            }

            doTraverseInOrder(node.getLeft(), visitor);
            visitor.visit(node.getData());
            doTraverseInOrder(node.getRight(), visitor);
        }

        // Post-order traversal
        public static <E> void traversePostOrder(
                BinaryTree<E> tree,
                Visitor<E> visitor) {
            doTraversePostOrder(tree.getRoot(), visitor);
        }

        private static <E> void doTraversePostOrder(
                BinaryTree<E>.Node node,
                Visitor<E> visitor) {
            if (node == null) {
                return;
            }

            doTraversePostOrder(node.getLeft(), visitor);
            doTraversePostOrder(node.getRight(), visitor);
            visitor.visit(node.getData());
        }

        // Level-order traversal
        public static <E> void traverseLevelOrder(
                BinaryTree<E> tree,
                Visitor<E> visitor) {
            // Queue holds nodes that have been discovered and must be visited
            Queue<BinaryTree<E>.Node> queue = new Queue<BinaryTree<E>.Node>();

            // Start off with only root in queue
            if (!tree.isEmpty()) {
                queue.enqueue(tree.getRoot());
            }

            // While nodes remain to be visited in the queue
            while (!queue.isEmpty()) {
                // Visit the front node
                BinaryTree<E>.Node node = queue.dequeue();
                visitor.visit(node.getData());

                // Enqueue front node's children
                if (node.hasLeft()) {
                    queue.enqueue(node.getLeft());
                }
                if (node.hasRight()) {
                    queue.enqueue(node.getRight());
                }
            }
        }
    }

    static class TestVisitor implements Visitor<Integer> {

        private SinglyLinkedList<Integer> visitedData = new SinglyLinkedList<Integer>();

        public SinglyLinkedList<Integer> getVisitedData() {
            return visitedData;
        }

        public void visit(Integer data) {
            visitedData.insertTail(data);
        }
    }

    public static int getLeafCount(BinaryTree<Integer>.Node node) {
        if (node == null) {
            return 0;
        }
        if (!node.hasLeft() && !node.hasRight()) {
            return 1;
        } else {
            return getLeafCount(node.getLeft()) + getLeafCount(node.getRight());
        }
    }

    public static BinaryTree<Integer>.Node deleteLeaves(BinaryTree<Integer>.Node node) {
        if (node == null) return null;
        if (!node.hasLeft() && !node.hasRight()) {
            node = null;
        } else {
            node = node.getLeft();
            node = deleteLeaves(node.getLeft());
            node = node.getRight();
            node = deleteLeaves(node.getRight());
        }
        return node;
    }

    public static int countLeaves(BinaryTree<Integer> tree) {
        return getLeafCount(tree.getRoot());
    }

    public static int countNonLeaves(BinaryTree<Integer> tree) {
        return tree.getSize() - getLeafCount(tree.getRoot());
    }

    public static int height(BinaryTree<Integer>.Node node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
    }

    public static int getHeight(BinaryTree<Integer> tree) {
        return height(tree.getRoot());
    }

    public static void printPreOrder(BinaryTree tree) {
        TestVisitor visitor = new TestVisitor();
        TreeAlgorithms.traversePreOrder(tree, visitor);
        SinglyLinkedList<Integer> singlyLinkedList = visitor.getVisitedData();
        SinglyLinkedList<Integer>.Element currentElement = singlyLinkedList.getHead();
        while (currentElement != null) {
            System.out.printf("%d ", currentElement.getData());
            currentElement = currentElement.getNext();
        }
    }

    public static void printInOrder(BinaryTree tree) {
        TestVisitor visitor = new TestVisitor();
        TreeAlgorithms.traverseInOrder(tree, visitor);
        SinglyLinkedList<Integer> singlyLinkedList = visitor.getVisitedData();
        SinglyLinkedList<Integer>.Element currentElement = singlyLinkedList.getHead();
        while (currentElement != null) {
            System.out.printf("%d ", currentElement.getData());
            currentElement = currentElement.getNext();
        }
    }

    public static void printPostOrder(BinaryTree tree) {
        TestVisitor visitor = new TestVisitor();
        TreeAlgorithms.traversePostOrder(tree, visitor);
        SinglyLinkedList<Integer> singlyLinkedList = visitor.getVisitedData();
        SinglyLinkedList<Integer>.Element currentElement = singlyLinkedList.getHead();
        while (currentElement != null) {
            System.out.printf("%d ", currentElement.getData());
            currentElement = currentElement.getNext();
        }

    }

    public static BinaryTree.Node leafDelete(BinaryTree.Node node) {
        // Delete leaf nodes from binary search tree.

        if (node == null) {
            return null;
        }
        if (node.left == null && node.right == null) {
            return null;
        }

        // Else recursively delete in left and right subtrees.
        node.left = leafDelete(node.left);
        node.right = leafDelete(node.right);

        return node;
    }

    public static void removeLeaves(BinaryTree tree) {
        BinaryTree.Node node = tree.getRoot();
        leafDelete(node);
    }

    public static class Driver {
        public static void main(String[] args) {
            Homework6.BinaryTree<Integer> firstTree;
            Homework6.BinaryTree<Integer> secondTree;
            Homework6.TestVisitor testVisitor;

            //   T H E   F I R S T   T R E E

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

            //   T H E   S E C O N D   T R E E

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

            //   T H E   F I R S T   T R E E
            System.out.print("The pre-order traversal of tree #1: ");
            Homework6.printPreOrder(firstTree);
            System.out.println();
            System.out.print("The in-order traversal of tree #1: ");
            Homework6.printInOrder(firstTree);
            System.out.println();
            System.out.print("The post-order traversal of tree #1: ");
            Homework6.printPostOrder(firstTree);
            System.out.println();
            System.out.printf("The height of tree #1 is, %d%n", Homework6.getHeight(firstTree));
            System.out.printf("Tree #1 has %d leaves.%n", Homework6.countLeaves(firstTree));
            System.out.printf("There are %d \"non-leaves\" in tree #1.%n", Homework6.countNonLeaves(firstTree));
            Homework6.removeLeaves(firstTree);
            System.out.print("After removing the leaf nodes from tree #1: ");
            Homework6.printInOrder(firstTree);
            System.out.println();
            System.out.printf("The height of tree #1 after removing the leaf nodes is, %d%n", Homework6.getHeight(firstTree));

            //    T H E   S E C O N D   T R E E
            System.out.println();
            System.out.print("The pre-order traversal of tree #2: ");
            Homework6.printPreOrder(secondTree);
            System.out.println();
            System.out.print("The in-order traversal of tree #2: ");
            Homework6.printInOrder(secondTree);
            System.out.println();
            System.out.print("The post-order traversal of tree #2: ");
            Homework6.printPostOrder(secondTree);
            System.out.println();
            System.out.printf("The height of tree #2 is, %d%n", Homework6.getHeight(secondTree));
            System.out.printf("Tree #2 has %d leaves.%n", Homework6.countLeaves(secondTree));
            System.out.printf("There are %d \"non-leaves\" in tree #2.%n", Homework6.countNonLeaves(secondTree));
            Homework6.removeLeaves(secondTree);
            System.out.print("After removing the leaf nodes from tree #2: ");
            Homework6.printInOrder(secondTree);
            System.out.println();
            System.out.printf("The height of tree #1 after removing the leaf nodes is, %d%n", Homework6.getHeight(secondTree));
        }
    }
}

