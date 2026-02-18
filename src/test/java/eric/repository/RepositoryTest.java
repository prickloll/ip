package eric.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eric.EricException;
import eric.task.Deadline;
import eric.task.Task;
import eric.task.Todo;

/**
 * Comprehensive tests for Repository save and load functionality.
 * Tests file I/O operations with various task scenarios.
 */
public class RepositoryTest {
    private static final String TEST_FILE = "./test_repo_tasks.txt";
    private Repository repository;

    @BeforeEach
    public void setUp() {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            boolean deleted = file.delete();
            assert deleted : "Failed to delete test file";
        }
        repository = new Repository(TEST_FILE);
    }

    @Test
    public void load_fileNotFound_returnsEmptyList() throws EricException {
        ArrayList<Task> tasks = repository.load();
        assertEquals(0, tasks.size(), "Should return empty list when file doesn't exist");
    }

    @Test
    public void save_singleTodo_createsFile() throws EricException {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("buy milk"));

        repository.save(tasks);

        assertTrue(new File(TEST_FILE).exists(), "File should be created");
    }

    @Test
    public void saveAndLoad_singleTodo_preservesData() throws EricException {
        ArrayList<Task> original = new ArrayList<>();
        original.add(new Todo("test task"));

        repository.save(original);
        ArrayList<Task> loaded = repository.load();

        assertEquals(1, loaded.size(), "Should load 1 task");
        assertEquals("test task", loaded.get(0).getDescription());
    }

    @Test
    public void saveAndLoad_completedTask_preservesStatus() throws EricException {
        ArrayList<Task> original = new ArrayList<>();
        Task completedTask = new Todo("completed work");
        completedTask.markDone();
        original.add(completedTask);

        repository.save(original);
        ArrayList<Task> loaded = repository.load();

        assertEquals("X", loaded.get(0).getStatusIcon(),
                "Should preserve completed status");
    }

    @Test
    public void saveAndLoad_deadline_preservesDeadlineInfo() throws EricException {
        ArrayList<Task> original = new ArrayList<>();
        original.add(new Deadline("submit assignment", "2026-02-20"));

        repository.save(original);
        ArrayList<Task> loaded = repository.load();

        assertEquals(1, loaded.size());
        assertEquals("submit assignment", loaded.get(0).getDescription());
        assertInstanceOf(Deadline.class, loaded.get(0));
    }

    @Test
    public void saveAndLoad_multipleTasks_preservesOrder() throws EricException {
        ArrayList<Task> original = new ArrayList<>();
        original.add(new Todo("first"));
        original.add(new Todo("second"));
        original.add(new Todo("third"));

        repository.save(original);
        ArrayList<Task> loaded = repository.load();

        assertEquals(3, loaded.size(), "Should load all 3 tasks");
        assertEquals("first", loaded.get(0).getDescription());
        assertEquals("second", loaded.get(1).getDescription());
        assertEquals("third", loaded.get(2).getDescription());
    }

    @Test
    public void save_emptyList_createsEmptyFile() throws EricException {
        ArrayList<Task> empty = new ArrayList<>();

        repository.save(empty);

        assertTrue(new File(TEST_FILE).exists(), "Should create file even if empty");
        ArrayList<Task> loaded = repository.load();
        assertEquals(0, loaded.size(), "Empty save should load empty");
    }

    @Test
    public void save_overwritesPreviousContent_success() throws EricException {
        ArrayList<Task> first = new ArrayList<>();
        first.add(new Todo("first save"));
        repository.save(first);

        ArrayList<Task> second = new ArrayList<>();
        second.add(new Todo("second save"));
        repository.save(second);

        ArrayList<Task> loaded = repository.load();
        assertEquals(1, loaded.size());
        assertEquals("second save", loaded.get(0).getDescription());
    }

    @Test
    public void saveAndLoad_largeDataset_success() throws EricException {
        ArrayList<Task> tasks = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            tasks.add(new Todo("task " + i));
        }

        repository.save(tasks);
        ArrayList<Task> loaded = repository.load();

        assertEquals(50, loaded.size(), "Should handle 50 tasks");
    }

    @Test
    public void saveAndLoad_specialCharactersInDescription_preserved()
            throws EricException {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("buy @store & items (urgent)"));

        repository.save(tasks);
        ArrayList<Task> loaded = repository.load();

        assertEquals("buy @store & items (urgent)", loaded.get(0).getDescription());
    }

    @Test
    public void saveAndLoad_multipleDeadlinesWithSameDate_allPreserved()
            throws EricException {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Deadline("task1", "2026-02-20"));
        tasks.add(new Deadline("task2", "2026-02-20"));
        tasks.add(new Deadline("task3", "2026-03-01"));

        repository.save(tasks);
        ArrayList<Task> loaded = repository.load();

        assertEquals(3, loaded.size());
        for (Task task : loaded) {
            assertInstanceOf(Deadline.class, task);
        }
    }

    @Test
    public void saveAndLoad_mixedCompletedAndIncomplete_preservesAllStatuses()
            throws EricException {
        ArrayList<Task> tasks = new ArrayList<>();
        Task completed = new Todo("done task");
        completed.markDone();
        tasks.add(completed);
        tasks.add(new Todo("pending task"));

        repository.save(tasks);
        ArrayList<Task> loaded = repository.load();

        assertEquals("X", loaded.get(0).getStatusIcon());
        assertEquals(" ", loaded.get(1).getStatusIcon());
    }

    @Test
    public void saveLoadSaveLoad_dataConsistency_maintained() throws EricException {
        ArrayList<Task> original = new ArrayList<>();
        original.add(new Todo("task 1"));
        original.add(new Deadline("task 2", "2026-02-20"));

        repository.save(original);
        ArrayList<Task> firstLoad = repository.load();

        repository.save(firstLoad);
        ArrayList<Task> secondLoad = repository.load();

        assertEquals(firstLoad.size(), secondLoad.size());
        for (int i = 0; i < firstLoad.size(); i++) {
            assertEquals(firstLoad.get(i).getDescription(),
                    secondLoad.get(i).getDescription());
        }
    }
}

