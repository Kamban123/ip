package nabmak.ui;

import nabmak.task.Task;
import nabmak.task.TaskList;

public class Ui {
    private static final String BANNER = "________________________________________";
    private static final String INTRO = "Yo im Nabmak.";
    private static final String GREETING = "Whatchu wanna do?";

    public void showHi() {
        System.out.println(BANNER);
        System.out.println(INTRO);
        System.out.println(GREETING);
        System.out.println(BANNER);
    }

    public void showBye() {
        System.out.println(BANNER);
        System.out.println("BYE!");
    }

    public void showError(String message) {
        System.out.println(BANNER);
        System.out.println("TOUGH! " + message);
        System.out.println(BANNER);
    }

    public void showList(TaskList tasks) {
        System.out.println(BANNER);
        System.out.println("Your TODOLIST");

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i+1) + ". " + tasks.get(i));
        }
        System.out.println(BANNER);
    }

    public void showDone(Task task) {
        System.out.println("Good that task is DONE");
        System.out.println(task);
        System.out.println(BANNER);
    }

    public void showUndone(Task task) {
        System.out.println("Tuff this task not done :(");
        System.out.println(task);
        System.out.println(BANNER);
    }

    public void showAdded(Task task, int size) {
        System.out.println(BANNER);
        System.out.println("Ok new task!");
        System.out.println(task);
        System.out.println("Now got " + size + " tasks.");
        System.out.println(BANNER);
    }

    public void showDeleted(Task deleted, int size) {
        System.out.println(BANNER);
        System.out.println("Noted. I've remove the task:");
        System.out.println(deleted);
        System.out.println("Now got " + size + " tasks.");
        System.out.println(BANNER);
    }
}
