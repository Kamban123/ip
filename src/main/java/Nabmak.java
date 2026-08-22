import java.util.Scanner;

public class Nabmak {
    public static void main(String[] args) {
        String banner = "________________________________________";
        String name = "Nabmak";
        String intro = "Yo im " + name + ".";
        String greeting = "Whatchu wanna do?";
        String bye = "BYE!";

        Task[] tasks = new Task[100]; 
        int taskNum = 0;
        Scanner sc = new Scanner(System.in);
        
        System.out.println(banner);
        System.out.println(intro);
        System.out.println(greeting);
        System.out.println(banner);

        while (true) {
            String input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println(banner);
                System.out.println(bye);
                break;
            } else if (input.equals("list")) {
                System.out.println(banner);
                System.out.println("Your TODOLIST");
                for (int i = 0; i < taskNum; i++) {
                    System.out.println((i+1) + ". " + tasks[i]);
                }
                System.out.println(banner);
            } else if (input.startsWith("mark ")) {
                int num = Integer.parseInt(input.substring(5));
                Task task = tasks[num - 1];
                task.markDone();

                System.out.println("Good that task is DONE");
                System.out.println(task);
                System.out.println(banner);
            } else if (input.startsWith("unmark ")) {
                int num = Integer.parseInt(input.substring(7));
                Task task = tasks[num - 1];
                task.markNotDone();

                System.out.println("Tuff this task not done :(");
                System.out.println(task);
                System.out.println(banner);
            } else if (input.startsWith("todo ")) {
                String desc = input.substring(5);
                tasks[taskNum] = new ToDo(desc);
                taskNum++;

                System.out.println(banner);
                System.out.println("Ok new task!");
                System.out.println(tasks[taskNum - 1]);
                System.out.println("Now got " + taskNum + " tasks.");
                System.out.println(banner);
            } else if (input.startsWith("deadline ")) {
                String info = input.substring(9);
                int mid = info.indexOf(" /by ");
                String desc = info.substring(0, mid);
                String deadline = info.substring(mid + 5);
                tasks[taskNum] = new Deadline(desc, deadline);
                taskNum++;

                System.out.println(banner);
                System.out.println("Ok new task!");
                System.out.println(tasks[taskNum - 1]);
                System.out.println("Now got " + taskNum + " tasks.");
                System.out.println(banner);
            } else if (input.startsWith("event ")) {
                String info = input.substring(6);
                int left = info.indexOf(" /from ");
                int right =  info.indexOf(" /to ");
                String desc = info.substring(0, left);
                String start = info.substring(left + 7, right);
                String end = info.substring(right + 4);
                tasks[taskNum] = new Event(desc, start, end);
                taskNum++;

                System.out.println(banner);
                System.out.println("Ok new task!");
                System.out.println(tasks[taskNum - 1]);
                System.out.println("Now got " + taskNum + " tasks.");
                System.out.println(banner);
            } else {

                tasks[taskNum] = new Task(input);
                taskNum++;
                System.out.println(banner);
                System.out.println("added: " + input);
                System.out.println(banner);
            }
        }
        sc.close();
    }
}
