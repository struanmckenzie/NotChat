/**
 * Represents a singlee node in a linked list that holds a Message.
 */
public class Node {
    private Message data; // Data stored in the node
    private Node next;   // Reference to the next node in the list

    /**
     * Constructor to initialize the node with a Message.
     *
     * @param data The Message to store in the node.
     */
    public Node(Message data) {
        this.data = data;
        this.next = null;
    }

    /**
     * Get the Message stored in the node.
     *
     * @return The Message in the node.
     */
    public Message getData() {
        return data;
    }

    /**
     * Set the Message for the node.
     *
     * @param data The new Message to store in the node.
     */
    public void setData(Message data) {
        this.data = data;
    }

    /**
     * Get the next node in the list.
     *
     * @return A reference to the next node.
     */
    public Node getNext() {
        return next;
    }

    /**
     * Set the next node in the list.
     *
     * @param next A reference to the next node.
     */
    public void setNext(Node next) {
        this.next = next;
    }
}