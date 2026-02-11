package eric.repository;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import eric.EricException;
import eric.task.Task;


/**
 * Manages the loading and saving of task data to text file
 */
public class Repository {
    private String filePath;

    /**
     * Initialises the repository with the configured file path.
     *
     * @param filePath The relative file path to the text file.
     */
    public Repository(String filePath) {
        assert !filePath.trim().isEmpty() : "File path cannot be empty.";
        this.filePath = filePath;
    }

    /**
     * Creates directory if it does not exist.
     *
     * @throws IOException If directory cannot be created.
     */
    private void makeFolder() throws IOException {
        Files.createDirectories((Paths.get("./data/")));
    }

    /**
     * Saves current task list to the text file.
     *
     * @param tasks The list of tasks to be saved.
     * @throws EricException If an I/O error happens during the save process.
     */
    public void save(ArrayList<Task> tasks) throws EricException {
        assert !filePath.trim().isEmpty() : "File path cannot be empty";
        assert tasks != null : "Cannot save a null task list.";
        try {
            makeFolder();
            FileWriter fw = new FileWriter(filePath);
            for (Task task : tasks) {
                assert task != null : "Cannot save a null object into the task list.";
                fw.write(task.toFileFormat() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            throw new EricException("Met with error while trying to save!");
        }
    }

    /**
     * Loads task from the text file and reconstructs them into a Task ArrayList.
     *
     * @return An ArrayList containing the tasks loaded from the text file.
     * @throws EricException If the file is corrupted or if an I/O error is present.
     */
    public ArrayList<Task> load() throws EricException {
        ArrayList<Task> fileTasks = new ArrayList<>();
        File f = new File(filePath);
        if (!f.exists()) {
            return fileTasks;
        }

        try {
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String line = s.nextLine();
                assert !line.trim().isEmpty() : "Text file has an empty line.";
                fileTasks.add(Task.fileToTask(line));
            }
        } catch (IOException e) {
            throw new EricException("Met with error trying to load the file!");
        }
        assert fileTasks != null : "Repository should always return a list object.";
        return fileTasks;
    }


}
