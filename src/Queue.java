import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

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
     * Get the list node at the bottom of the list
     * 
     * @return A reference to the bottom node (null if the list is empty).
     */
    public Node getBottom() {
        return bottom;
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
     * returns length of queue
     */
    public int getLength() {
        int count = 0;
        Node current = top;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
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
     * Peek at a message at a specific index without removing it from the queue.
     *
     * @param index The position of the message (0-based).
     * @return The Message at the specified index, or null if index is out of
     *         bounds.
     */
    public Message peek(int index) {
        if (index < 0) {
            return null;
        }
        Node current = top;
        int count = 0;
        while (current != null) {
            if (count == index) {
                return current.getData();
            }
            count++;
            current = current.getNext();
        }
        return null;
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
            Node current = top;
            while (current != null) {
                writer.println(current.getData().toCSVRow());
                current = current.getNext();
            }
        } catch (IOException e) {
            System.out.println("Error saving to CSV: " + e.getMessage());
        }
    }

    public void deleteMessageByTimestamp(LocalDateTime timestamp) {
        // Remove the node with matching timestamp from the linked list
        if (isEmpty())
            return;

        // Special case: top node matches
        if (top.getData().getTimestamp().equals(timestamp)) {
            top = top.getNext();
            if (top == null)
                bottom = null;
            return;
        }

        // Search for the node
        Node current = top;
        while (current.getNext() != null) {
            if (current.getNext().getData().getTimestamp().equals(timestamp)) {
                // Remove the next node
                current.setNext(current.getNext().getNext());
                if (current.getNext() == null)
                    bottom = current;
                return;
            }
            current = current.getNext();
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
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() != 0) {
                    push(Message.fromCSVRow(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading from CSV: " + e.getMessage());
        }
    }

    // Gets the last 3 nodes/messages in the queue
    public Message[] getLastThree() {
        List<Message> all = new ArrayList<>();
        Node current = top;
        while (current != null) {
            all.add(current.getData());
            current = current.getNext();
        }
        int size = all.size();
        if (size == 0) {
            return new Message[0];
        }
        int start = Math.max(0, size - 3);
        int count = size - start;
        Message[] result = new Message[count];
        for (int i = 0; i < count; i++) {
            result[i] = all.get(start + i);
        }
        return result;
    }

    // Returns true if the search value is within any of the chat messages
    public boolean containsSeachValue(String searchValue) {
        Node current = top;

        if (current == null && searchValue.equals("")) {
            return true;
        }

        while (current != null) {
            if (current.getData().getContent().contains(searchValue) || searchValue.equals("")) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }
}