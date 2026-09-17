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
    private static final DateTimeFormatter DATE_FORMAT =
        DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Creates a Nabmak application and loads in saved tasks.
     */
    public Nabmak() {
        this.ui = new Ui();
        this.storage = new Storage("./data/nabmak.txt");
        this.tasks = new TaskList(storage.load());
    }

    /**
     * Adds a task to the task list, saves it, and returns the confirmation.
     *
     * @param task task to add
     * @return confirmation message
     */
    private String addTask(Task task) {
        tasks.add(task);
        storage.save(tasks.getTasks());

        return "Ok new task!\n" + task
            + "\nNow got " + tasks.size() + " tasks.";
    }

    /**
     * Processes a single user command and returns Nabmak's response.
     *
     * @param input the command entered by the user
     * @return response to the command
     */
    public String processCommand(String input) {
        try {
            Parser.parse(input, tasks.size());
        } catch (NabmakException e) {
            return "TOUGH! " + e.getMessage();
        }

        if (input.equals("bye")) {
            return "BYE!";
        } else if (input.equals("list")) {
            StringBuilder output = new StringBuilder("Your TODOLIST\n");

            for (int i = 0; i < tasks.size(); i++) {
                output.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
            }

            return output.toString();
        } else if (input.startsWith("mark ")) {
            int num = Integer.parseInt(input.substring(5));
            Task task = tasks.get(num - 1);
            task.markDone();
            storage.save(tasks.getTasks());

            return "Good that task is DONE\n" + task.toString();
        } else if (input.startsWith("unmark ")) {
            int num = Integer.parseInt(input.substring(7));
            Task task = tasks.get(num - 1);
            task.markNotDone();
            storage.save(tasks.getTasks());
            return "Tuff this task not done :(\n" + task.toString();
        } else if (input.startsWith("todo ")) {
            return addTask(new ToDo(input.substring(5)));
        } else if (input.startsWith("deadline ")) {
            String info = input.substring(9);
            int mid = info.indexOf(" /by ");
            String desc = info.substring(0, mid);
            LocalDateTime deadline = LocalDateTime.parse(info.substring(mid + 5), DATE_FORMAT);
            return addTask(new Deadline(desc, deadline));
        } else if (input.startsWith("event ")) {
            String info = input.substring(6);
            int left = info.indexOf(" /from ");
            int right = info.indexOf(" /to ");
            String desc = info.substring(0, left);
            LocalDateTime start = LocalDateTime.parse(info.substring(left + 7, right), DATE_FORMAT);
            LocalDateTime end = LocalDateTime.parse(info.substring(right + 5), DATE_FORMAT);
            return addTask(new Event(desc, start, end));
        } else if (input.startsWith("delete ")) {
            int num = Integer.parseInt(input.substring(7));
            Task deleted = tasks.delete(num - 1);
            storage.save(tasks.getTasks());
            return "Noted. I've removed the task:\n" + deleted + "\nNow got " + tasks.size()
                + " tasks.";
        } else if (input.startsWith("find ")) {
            TaskList matches = tasks.find(input.substring(5));
            StringBuilder response = new StringBuilder("You looking for these?:\n");
            for (int i = 0; i < matches.size(); i++) {
                response.append(i + 1).append(". ").append(matches.get(i)).append("\n");
            }
            return response.toString();
        }

        return "";
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
            System.out.println(processCommand(input));

            if (input.equals("bye")) {
                break;
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
