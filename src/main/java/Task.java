/**
 * Represents a task that can be marked as completed or not.
 */

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markUndone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    public String toFileFormat() {
        return (isDone ? "1" : "0" + " | " + description);
    }

    public static Task fileToTask(String line) throws EricException {
        String[] lineParts = line.split("\\| ",  -1);
        if (lineParts.length < 3) {
            throw new EricException("File might be corrupted!");
        }

        String taskType = lineParts[0];
        boolean isDone = lineParts[1].equals("1");
        String description = lineParts[2];

        Task currTask;
        switch(taskType) {
        case "T":
            currTask = new Todo(description);
            break;
        case "D":
            currTask = new Deadline(description, lineParts[3]);
            break;
        case "E":
            currTask = new Event(description, lineParts[3], lineParts[4]);
            break;
        default:
            return null;
        }
        if(isDone) {
            currTask.markDone();
        }
        return currTask;
    }

}