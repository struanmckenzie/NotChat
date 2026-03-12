import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Represents a queeue of chat Messages.
 * Allows saving the entire queue to a CSV file and loading it back.
 */
public class Queue {
    private Node top; // Reference to the first node in the list
    private Node bottom;

    /**
     * Default constructor. Initializes the list to be empty.
     */
    public Queue() {
        top = null;
        bottom = null;
    }

    /**
     * Get the list node at the top of the list.
     * 
     * @return A reference to the top node (null if the list is empty).
     */
    public Node getTop() {
        return top;
    }

    /**
     * Set the top of the list to the given node.
     * 
     * @param newTop A reference to a Node object to be the new top.
     */
    public void setTop(Node newTop) {
        top = newTop;
    }


    /**
     * Create new bottom node
     */
    public void setBottom(Node newBottom) {
        bottom = newBottom;
    }

    /**
     * Add a new Message to the back of the queue.
     *
     * @param message The Message to enqueue.
     */
    public void push(Message message) {
        Node newNode = new Node(message);
        if (isEmpty()) {
            top = newNode;
            setBottom(newNode);
        } else {
            bottom.setNext(newNode);
            bottom = newNode;
        }
    }

    /**
     * Print all messages in the queue, oldest first.
     */
    public void printQueue() {
        Node current = top;
        while (current != null) {
            System.out.println(current.getData()); // calls Message.toString()
            current = current.getNext();
        }
    }

    /**
     * Check if the list is empty.
     * 
     * @return True if the list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * Remove and return the Message at the front of the queue.
     *
     * @return The oldest Message, or null if the queue is empty.
     */
    public Message pop() {
        if (isEmpty()) {
            System.out.println("Queue is empty. Nothing to remove.");
            return null;
        }
        Message data = top.getData();
        top = top.getNext();
        if (top == null) {
            bottom = null;
        }
        return data;
    }

    /**
     * Saves all messages in the queue to a CSV file.
     * The file will contain one message per line.
     * Existing file contents are overwritten.
     *
     * @param filename Path to the CSV file (e.g. "chat.csv").
     */
    public void saveToCSV(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("sender|content|timestamp"); // header row
            Node current = top;
            while (current != null) {
                writer.println(current.getData().toCSVRow());
                current = current.getNext();
            }
        } catch (IOException e) {
            System.out.println("Error saving to CSV: " + e.getMessage());
        }
    }

    /**
     * Loads messages from a CSV file and appends them to the end of the queue.
     * Skips the header row automatically.
     *
     * @param filename Path to the CSV file (e.g. "chat.csv").
     */
    public void loadFromCSV(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    push(Message.fromCSVRow(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading from CSV: " + e.getMessage());
        }
    }
}