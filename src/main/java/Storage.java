import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Storage {
    private final String filePath;
    private static final DateTimeFormatter DATE_FORMAT =
        DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void save(ArrayList<Task> tasks) {
        try {
            File file = new File(this.filePath);
            File parent = file.getParentFile();
            
            if (parent != null) {
                parent.mkdirs();
            }

            FileWriter writer = new FileWriter(file);

            for (Task task: tasks) {
                writer.write(taskToStr(task));
                writer.write(System.lineSeparator());
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Couldnt save tasks");
        }
    }

    private String taskToStr(Task task) {
        String done = task.getStatus().equals("[X]") ? "1" : "0";

        if (task instanceof ToDo) {
            return "T | " + done + " | " + task.getDesc();
        } else if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return "D | " + done + " | " + task.getDesc() + " | " + 
                deadline.getDead().format(DATE_FORMAT);
        } else if (task instanceof Event) {
            Event event = (Event) task;
            return "E | " + done + " | " + task.getDesc() 
                + " | " + event.getStart().format(DATE_FORMAT)
                + " | " + event.getEnd().format(DATE_FORMAT);
        }

        return "";
    }

    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(this.filePath);

        if (!file.exists()) {
            return tasks;
        }

        try {
            Scanner sc = new Scanner(file);

            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                Task task = strToTask(line);

                if (task != null) {
                    tasks.add(task);
                }
            }

            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Couldnt load tasks.");
        }

        return tasks;
    }

    private Task strToTask(String str) {
        String[] data = str.split(" \\| ");
        String type = data[0];
        boolean isDone = data[1].equals("1");
        String desc = data[2];
        Task task;

        if (type.equals("T")) {
            task = new ToDo(desc);
        } else if (type.equals("D")) {
            String deadlineString = data[3];
            LocalDateTime deadline = LocalDateTime.parse(deadlineString, DATE_FORMAT);
            task = new Deadline(desc, deadline);
        } else if (type.equals("E")) {
            String startString = data[3];
            String endString = data[4];
            LocalDateTime start = LocalDateTime.parse(startString, DATE_FORMAT);
            LocalDateTime end = LocalDateTime.parse(endString, DATE_FORMAT);
            task = new Event(desc, start, end);
        } else {
            return null;
        }

        if (isDone) {
            task.markDone();
        }

        return task;
    }
}
