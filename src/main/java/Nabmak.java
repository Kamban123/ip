import java.util.Scanner;
import java.util.ArrayList;

public class Nabmak {
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
            String end = info.substring(right + 4);
            if (end.isEmpty()) {
                throw new NabmakException("Event cant have empty end date");
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
        String banner = "________________________________________";
        String name = "Nabmak";
        String intro = "Yo im " + name + ".";
        String greeting = "Whatchu wanna do?";
        String bye = "BYE!";

        ArrayList<Task> tasks = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        
        System.out.println(banner);
        System.out.println(intro);
        System.out.println(greeting);
        System.out.println(banner);

        while (true) {
            String input = sc.nextLine();

            try {
                execute(input, tasks.size());
            } catch (NabmakException e) {
                System.out.println(banner);
                System.out.println("TOUGH! " + e.getMessage());
                System.out.println(banner);
                continue;
            }
            
            if (input.equals("bye")) {
                System.out.println(banner);
                System.out.println(bye);
                break;
            } else if (input.equals("list")) {
                System.out.println(banner);
                System.out.println("Your TODOLIST");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i+1) + ". " + tasks.get(i));
                }
                System.out.println(banner);
            } else if (input.startsWith("mark ")) {
                int num = Integer.parseInt(input.substring(5));
                Task task = tasks.get(num - 1);
                task.markDone();

                System.out.println("Good that task is DONE");
                System.out.println(task);
                System.out.println(banner);
            } else if (input.startsWith("unmark ")) {
                int num = Integer.parseInt(input.substring(7));
                Task task = tasks.get(num - 1);
                task.markNotDone();

                System.out.println("Tuff this task not done :(");
                System.out.println(task);
                System.out.println(banner);
            } else if (input.startsWith("todo ")) {
                String desc = input.substring(5);
                tasks.add(new ToDo(desc));

                System.out.println(banner);
                System.out.println("Ok new task!");
                System.out.println(tasks.get(tasks.size() - 1));
                System.out.println("Now got " + tasks.size() + " tasks.");
                System.out.println(banner);
            } else if (input.startsWith("deadline ")) {
                String info = input.substring(9);
                int mid = info.indexOf(" /by ");
                String desc = info.substring(0, mid);
                String deadline = info.substring(mid + 5);
                tasks.add(new Deadline(desc, deadline));

                System.out.println(banner);
                System.out.println("Ok new task!");
                System.out.println(tasks.get(tasks.size() - 1));
                System.out.println("Now got " + tasks.size() + " tasks.");
                System.out.println(banner);
            } else if (input.startsWith("event ")) {
                String info = input.substring(6);
                int left = info.indexOf(" /from ");
                int right =  info.indexOf(" /to ");
                String desc = info.substring(0, left);
                String start = info.substring(left + 7, right);
                String end = info.substring(right + 4);
                tasks.add(new Event(desc, start, end));

                System.out.println(banner);
                System.out.println("Ok new task!");
                System.out.println(tasks.get(tasks.size() - 1));
                System.out.println("Now got " + tasks.size() + " tasks.");
                System.out.println(banner);
            } else if (input.startsWith("delete ")) {
                int num = Integer.parseInt(input.substring(7));
                Task deleted = tasks.remove(num - 1);

                System.out.println(banner);
                System.out.println("Noted. I've remove the task:");
                System.out.println(deleted);
                System.out.println("Now got " + tasks.size() + " tasks.");
                System.out.println(banner);
            }
        }
        sc.close();
    }
}
