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
    private static final String TEXT_FILE_DIRECTORY = "./data/";
    private String filePath;

    /**
     * Initialises the repository with the configured file path.
     *
     * @param filePath The relative file path to the text file.
     */
    public Repository(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Creates directory if it does not exist.
     *
     * @throws IOException If directory cannot be created.
     */
    private void makeFolder() throws IOException {
        Files.createDirectories((Paths.get(TEXT_FILE_DIRECTORY)));
    }

    private void writeTasksToFile(ArrayList<Task> tasks) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        for (Task task : tasks) {
            assert task != null : "Cannot save a null object into the task list.";
            fw.write(task.toFileFormat() + System.lineSeparator());
        }
        fw.close();
    }

    private ArrayList<Task> loadTasksFromFile(File file) throws EricException {
        ArrayList<Task> fileTasks = new ArrayList<>();
        try {
            Scanner s = new Scanner(file);
            while (s.hasNext()) {
                fileTasks.add(Task.fileToTask(s.nextLine()));
            }
            return fileTasks;
        } catch (IOException e) {
            throw new EricException("Met with error trying to load the file!");
        }
    }


    /**
     * Saves current task list to the text file.
     *
     * @param tasks The list of tasks to be saved.
     * @throws EricException If an I/O error happens during the save process.
     */
    public void save(ArrayList<Task> tasks) throws EricException {
        try {
            makeFolder();
            writeTasksToFile(tasks);
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

        File dataFile = new File(filePath);
        if (!dataFile.exists()) {
            return new ArrayList<>();
        }
        return loadTasksFromFile(dataFile);
    }


}
