package nabmak;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import nabmak.parser.NabmakException;
import nabmak.parser.Parser;
import nabmak.storage.Storage;
import nabmak.task.Deadline;
import nabmak.task.Event;
import nabmak.task.Task;
import nabmak.task.TaskList;
import nabmak.task.ToDo;
import nabmak.ui.Ui;

/**
 * Creates and runs the Nabmak application.
 * Handles user interaction, parsing and storage.
 */
public class Nabmak {
    private Ui ui;
    private Storage storage;
    private TaskList tasks;

    /**
     * Creates a Nabmak application and loads in saved tasks.
     */
    public Nabmak() {
        this.ui = new Ui();
        this.storage = new Storage("./data/nabmak.txt");
        this.tasks = new TaskList(storage.load());
    }

    /**
     * Runs the main command loop of application.
     * Reads and processes user commands until they exit.
     */
    public void run() {
        Scanner sc = new Scanner(System.in);

        ui.showHi();

        while (true) {
            String input = sc.nextLine();

            try {
                Parser.parse(input, tasks.size());
            } catch (NabmakException e) {
                ui.showError(e.getMessage());
                continue;
            }

            if (input.equals("bye")) {
                ui.showBye();
                break;
            } else if (input.equals("list")) {
                ui.showList(tasks);
            } else if (input.startsWith("mark ")) {
                int num = Integer.parseInt(input.substring(5));
                Task task = tasks.get(num - 1);
                task.markDone();
                storage.save(tasks.getTasks());

                ui.showDone(task);
            } else if (input.startsWith("unmark ")) {
                int num = Integer.parseInt(input.substring(7));
                Task task = tasks.get(num - 1);
                task.markNotDone();
                storage.save(tasks.getTasks());

                ui.showUndone(task);
            } else if (input.startsWith("todo ")) {
                String desc = input.substring(5);
                tasks.add(new ToDo(desc));
                storage.save(tasks.getTasks());

                ui.showAdded(tasks.get(tasks.size() - 1), tasks.size());
            } else if (input.startsWith("deadline ")) {
                String info = input.substring(9);
                int mid = info.indexOf(" /by ");
                String desc = info.substring(0, mid);
                String deadlineString = info.substring(mid + 5);
                LocalDateTime deadline = LocalDateTime.parse(deadlineString,
                    DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
                tasks.add(new Deadline(desc, deadline));
                storage.save(tasks.getTasks());

                ui.showAdded(tasks.get(tasks.size() - 1), tasks.size());
            } else if (input.startsWith("event ")) {
                String info = input.substring(6);
                int left = info.indexOf(" /from ");
                int right = info.indexOf(" /to ");
                String desc = info.substring(0, left);
                String startString = info.substring(left + 7, right);
                String endString = info.substring(right + 5);
                LocalDateTime start = LocalDateTime.parse(startString,
                    DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
                LocalDateTime end = LocalDateTime.parse(endString,
                    DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
                tasks.add(new Event(desc, start, end));
                storage.save(tasks.getTasks());

                ui.showAdded(tasks.get(tasks.size() - 1), tasks.size());
            } else if (input.startsWith("delete ")) {
                int num = Integer.parseInt(input.substring(7));
                Task deleted = tasks.delete(num - 1);
                storage.save(tasks.getTasks());

                ui.showDeleted(deleted, tasks.size());
            } else if (input.startsWith("find ")) {
                String keyword = input.substring(5);
                TaskList matches = tasks.find(keyword);

                ui.showFind(matches);
            }
        }
        sc.close();
    }

    /**
     * Starts Nabmak application.
     *
     * @param args arguments supplied to application
     */
    public static void main(String[] args) {
        new Nabmak().run();
    }
}
