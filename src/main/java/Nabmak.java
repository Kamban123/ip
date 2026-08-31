import java.util.Scanner;
import java.util.ArrayList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Nabmak {
    private static final DateTimeFormatter DATE_FORMAT = 
        DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    public static void execute(String input, int taskNum) throws NabmakException {
        if (input.equals("todo")) {
            throw new NabmakException("You need to say what task you wanna do.");
        }
        if (input.equals("deadline")) {
            throw new NabmakException("Deadline must have a '<description> /by <date>'.");
        }
        if (input.equals("event")) {
            throw new NabmakException("Event must have a '<description> /from <date> /to <date>'.");
        }
        if (input.equals("mark")) {
            throw new NabmakException("mark which task?");
        }
        if (input.equals("unmark")) {
            throw new NabmakException("unmark which task?");
        }
        if (input.equals("delete")) {
            throw new NabmakException("delete which task?");
        }
        if (input.startsWith("todo ")) {
            String desc = input.substring(5);
            if (desc.isEmpty()) {
                throw new NabmakException("You need to say what task you wanna do.");
            }
        } else if (input.startsWith("deadline ")) {
            String info = input.substring(9);
            int mid = info.indexOf(" /by ");
            if (input.startsWith("deadline /by")) {
                throw new NabmakException("Deadline cant have empty description");
            }
            if (mid == -1 && info.indexOf(" /by") >= 0) {
                throw new NabmakException("Deadline cant have empty end date");
            }
            if (mid == -1) {
                throw new NabmakException("Deadline must have a '<description> /by <date>'.");
            }
            String desc = info.substring(0, mid);
            if (desc.isEmpty()) {
                throw new NabmakException("Deadline cant have empty description");
            }
            String deadline = info.substring(mid + 5);
            if (deadline.isEmpty()) {
                throw new NabmakException("Deadline cant have empty end date");
            }

            try {
                LocalDateTime.parse(deadline, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                throw new NabmakException("Deadline date must be 'dd-MM-yyyy HH:mm'.");
            }
        } else if (input.startsWith("event ")) {
            String info = input.substring(6);
            int left = info.indexOf(" /from ");
            if (input.startsWith("event /from")) {
                throw new NabmakException("Event cant have empty description");
            }
            if (left == -1 && info.indexOf(" /from") >= 0) {
                throw new NabmakException("Event cant have empty start date");
            }
            if (left == -1) {
                throw new NabmakException("Event must have a '<description> /from <date> /to <date>'.");
            }
            int right =  info.indexOf(" /to ");
            if (right == -1) {
                throw new NabmakException("Event must have a '<description> /from <date> /to <date>'.");
            }
            String desc = info.substring(0, left);
            if (desc.isEmpty()) {
                throw new NabmakException("Event cant have empty description");
            }
            if (left + 7 > right) {
                throw new NabmakException("Event cant have empty start date");
            }
            String start = info.substring(left + 7, right);
            if (start.isEmpty()) {
                throw new NabmakException("Event cant have empty start date");
            }

            try {
                LocalDateTime.parse(start, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                throw new NabmakException("Event start date must be 'dd-MM-yyyy HH:mm'.");
            }
            String end = info.substring(right + 4);
            if (end.isEmpty()) {
                throw new NabmakException("Event cant have empty end date");
            }

            end = info.substring(right + 5);

            try {
                LocalDateTime.parse(end, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                throw new NabmakException("Event end date must be 'dd-MM-yyyy HH:mm'.");
            }
        } else if (input.equals("bye") || input.equals("list")) {
            
        } else if (input.startsWith("mark ")) {
            int num;
            try {
                num = Integer.parseInt(input.substring(5).trim());
            } catch (NumberFormatException e) {
                throw new NabmakException("Give valid task number");
            }
            if (num < 1 || num > taskNum) {
                throw new NabmakException("You arent that busy");
            }
        } else if (input.startsWith("unmark ")) {
            int num;
            try {
                num = Integer.parseInt(input.substring(7));
            } catch (NumberFormatException e) {
                throw new NabmakException("Give valid task number");
            }
            if (num < 1 || num > taskNum) {
                throw new NabmakException("You are making yourself busier than needed");
            }
        } else if (input.startsWith("delete ")) {
            int num;
            try {
                num = Integer.parseInt(input.substring(7));
            } catch (NumberFormatException e) {
                throw new NabmakException("Give valid task number");
            }
            if (num < 1 || num > taskNum) {
                throw new NabmakException("You cant delete something that doesnt exist");
            }
        } else {
            throw new NabmakException("Idk whatchu mean. Input either a todo, deadline or event. Or mark, unmark, delete.");
        }
    }
    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage("./data/nabmak.txt");
        TaskList tasks = new TaskList(storage.load());
        Scanner sc = new Scanner(System.in);
        
        ui.showHi();

        while (true) {
            String input = sc.nextLine();

            try {
                execute(input, tasks.size());
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
                int right =  info.indexOf(" /to ");
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
            }
        }
        sc.close();
    }
}
