import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Repository {
    private String filePath;

    public Repository(String filePath) {
        this.filePath = filePath;
    }

    /** Creates directory if it does not exist */
    private void makeFolder() throws IOException {
        Files.createDirectories((Paths.get("./data/")));
    }

    public void save(ArrayList<Task> tasks) throws EricException {
        try {
            makeFolder();
            FileWriter fw = new FileWriter(filePath);
            for (Task task : tasks) {
                fw.write(task.toFileFormat() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            throw new EricException("Met with error while trying to save!");
        }
    }

    public ArrayList<Task> load() throws EricException {
        ArrayList<Task> fileTasks = new ArrayList<>();
        File f = new File(filePath);
        if (!f.exists()) {
            return fileTasks;
        }

        try {
            Scanner s = new Scanner(f);
            while(s.hasNext()) {
                fileTasks.add(Task.fileToTask(s.nextLine()));
            }
        } catch (IOException e) {
            throw new EricException("Met with error tryting to load the file!");
        }
        return fileTasks;
    }


}
