# How To Implement `Message.java` in `ChatPage.java`

This guide shows how to connect your `Message` class to `ChatPage` so that you dont look like an idiot trying to understand my code. Message allows us to store all messages in a simple format "Author | message | data and time".

## 1. Add a queue field to `ChatPage`

In `ChatPage`, add this field with your other class fields:

```java
private final Queue messages = new Queue();
```

This stores all chat messages as `Message` objects. PRETTY SIMPLE STUFF RIGHT?

## 2. Update `sendMessage()` to create a `Message`

Replace your current `sendMessage()` with something like this (make sure that you impliment it corretly):

```java
private void sendMessage() {
	String text = messageField.getText().trim();
	if (!text.isEmpty()) {
		Message msg = new Message("You", text);
		messages.push(msg);
		chatArea.append(msg.toString() + "\n");
		messageField.setText("");
	}
}
```

What this does (DUMDED DOWN):
- Creates a new `Message` with sender (`"You"`) and content (`text`) and the date and time.
- Pushes it into your queue.
- Displays the formatted message using `toString()`.

## 3. Optional: Save chat history to CSV

After `messages.push(msg);`, add:

```java
messages.saveToCSV("chat.csv");
```

This writes all queued messages to file.

## 4. Optional: Load previous messages when opening `ChatPage`

In the constructor (after UI setup), you can load old messages:

```java
messages.loadFromCSV("chat.csv");
```

Then loop through queue nodes and append each message to `chatArea`.

## Why this is better

Using `Message` objects gives us:
- Sender
- Content
- Timestamp

in one structured object, making it easier to print, save, and reload chat history. 
WELL DONE GUYS WE HAVE GOT THIS 🎉
