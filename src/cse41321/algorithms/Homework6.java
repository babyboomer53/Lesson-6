package cse41321.algorithms;

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

        // Definition of the BinaryTree class continues hereâ€¦
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

    public static int getLeafCount(BinaryTree.Node node) {
        if (node == null) {
            return 0;
        }
        if (node.hasLeft() && node.hasRight()) {
            return 1;
        } else {
            return getLeafCount(node.getLeft()) + getLeafCount(node.getRight());
        }
    }

    public static int countLeaves(BinaryTree<Integer> tree) {
        return getLeafCount(tree.getRoot());
    }
    /*
    public static int countNonLeaves(BinaryTree tree) {

    }

    public static int getHeight(BinaryTree tree) {

    }

    public static void printPreOrder(BinaryTree tree) {

    }

    public static void printInOrder(BinaryTree tree) {

    }

    public static void printPostOrder(BinaryTree tree) {

    }

    public static void removeLeaves(BinaryTree tree) {

    }
    */

}

