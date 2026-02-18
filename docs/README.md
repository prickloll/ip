# Eric User Guide

**Eric** is a personal task management chatbot that helps you track your todos, deadlines, and events through simple text commands.

<p align="center">
  <img src="Ui.png" alt="Eric Screenshot" width="250">
</p>

---

## Quick Start

1. Ensure you have Java 17 or above installed
2. Download the latest `eric.jar` from the releases page
3. Double-click the file or run `java -jar eric.jar`
4. Start typing commands!

---

## Command Summary

| Command | Format | Example |
|---------|--------|---------|
| **Todo** | `todo <description>` | `todo read book` |
| **Deadline** | `deadline <description> /by <yyyy-MM-dd>` | `deadline submit report /by 2026-02-20` |
| **Event** | `event <description> /from <yyyy-MM-dd> /to <yyyy-MM-dd>` | `event meeting /from 2026-02-20 /to 2026-02-21` |
| **List** | `list` | `list` |
| **Mark** | `mark <task number>` | `mark 1` |
| **Unmark** | `unmark <task number>` | `unmark 1` |
| **Delete** | `delete <task number>` | `delete 1` |
| **Find** | `find <keywords> [flags]` | `find meeting /event /sort` |
| **Exit** | `bye` | `bye` |

### Find Command Flags

| Flag | Purpose | Example |
|------|---------|---------|
| `/all` | Strict match (all keywords must match) | `find read book /all` |
| `/todo` | Search only Todo tasks | `find book /todo` |
| `/deadline` | Search only Deadline tasks | `find report /deadline` |
| `/event` | Search only Event tasks | `find meeting /event` |
| `/date <yyyy-MM-dd>` | Filter by date | `find /date 2026-02-20` |
| `/sort` | Sort results alphabetically | `find book /sort` |

---

## Features

### Adding a Todo: `todo`

Adds a simple task without any date/time attached.

**Format:** `todo <description>`

**Example:**
```
todo read book
```

**Expected Output:**
```
Got it. I've added this task:
  [T][ ] read book
Now you have 1 task in the list.
```

---

### Adding a Deadline: `deadline`

Adds a task with a specific deadline.

**Format:** `deadline <description> /by <yyyy-MM-dd>`

**Example:**
```
deadline submit report /by 2026-02-20
```

**Expected Output:**
```
Got it. I've added this task:
  [D][ ] submit report (by: Feb 20 2026)
Now you have 2 tasks in the list.
```

---

### Adding an Event: `event`

Adds a task that spans a period of time.

**Format:** `event <description> /from <yyyy-MM-dd> /to <yyyy-MM-dd>`

**Example:**
```
event team meeting /from 2026-02-20 /to 2026-02-21
```

**Expected Output:**
```
Got it. I've added this task:
  [E][ ] team meeting (from: Feb 20 2026 to: Feb 21 2026)
Now you have 3 tasks in the list.
```

---

### Listing All Tasks: `list`

Displays all tasks in your list.

**Format:** `list`

**Expected Output:**
```
Here are the tasks in your list:
1. [T][ ] read book
2. [D][ ] submit report (by: Feb 20 2026)
3. [E][ ] team meeting (from: Feb 20 2026 to: Feb 21 2026)
```

---

### Marking a Task as Done: `mark`

Marks a task as completed.

**Format:** `mark <task number>`

**Example:**
```
mark 1
```

**Expected Output:**
```
Nice! I've marked this task as done:
  [T][X] read book
```

---

### Unmarking a Task: `unmark`

Marks a task as not done.

**Format:** `unmark <task number>`

**Example:**
```
unmark 1
```

**Expected Output:**
```
OK, I've marked this task as not done yet:
  [T][ ] read book
```

---

### Deleting a Task: `delete`

Removes a task from the list.

**Format:** `delete <task number>`

**Example:**
```
delete 1
```

**Expected Output:**
```
Noted. I've removed this task:
  [T][ ] read book
Now you have 2 tasks in the list.
```

---

### Finding Tasks: `find`

Searches for tasks matching keywords, dates, or task types.

**Format:** `find <keywords> [/all] [/todo|/deadline|/event] [/date yyyy-MM-dd] [/sort]`

**Examples:**

1. **Basic search:**
   ```
   find book
   ```

2. **Strict search (all keywords must match):**
   ```
   find read book /all
   ```

3. **Search by task type:**
   ```
   find report /deadline
   ```

4. **Search by date:**
   ```
   find /date 2026-02-20
   ```

5. **Combined search:**
   ```
   find meeting /event /date 2026-02-20 /sort
   ```

---

### Exiting the Program: `bye`

Closes the chatbot.

**Format:** `bye`

---

## FAQ

**Q: How are my tasks saved?**

A: Tasks are automatically saved to a file (`data/Eric.txt`) after every command. They are loaded automatically when you restart the app.

**Q: Can I edit the save file directly?**

A: Yes, but be careful with the format:
- Todo: `T | <0/1> | <description>`
- Deadline: `D | <0/1> | <description> | <yyyy-MM-dd>`
- Event: `E | <0/1> | <description> | <yyyy-MM-dd> | <yyyy-MM-dd>`

(`0` = not done, `1` = done)

**Q: What if I enter an invalid command?**

A: Eric will display an error message explaining what went wrong and how to fix it.

---

## Date Format

All dates must be in **yyyy-MM-dd** format (e.g., `2026-02-20`).

---

## Task Symbols

| Symbol | Meaning |
|--------|---------|
| `[T]` | Todo task |
| `[D]` | Deadline task |
| `[E]` | Event task |
| `[X]` | Task completed |
| `[ ]` | Task not completed |
