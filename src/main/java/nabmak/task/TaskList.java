package nabmak.task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int index) {
        return tasks.remove(index);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public TaskList find(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getDesc().contains(keyword)) {
                matches.add(task);
            }
        }

        return new TaskList(matches);
    }
}
