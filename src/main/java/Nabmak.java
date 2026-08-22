import java.util.Scanner;

public class Nabmak {
    public static void main(String[] args) {
        String banner = "________________________________________";
        String name = "Nabmak";
        String intro = "Yo im " + name + ".";
        String greeting = "Whatchu want?";
        String bye = "BYE!";

        String[] inputs = new String[100]; 
        int inputnum = 0;
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
            }

            if (input.equals("list")) {
                for (int i = 0; i < inputnum; i++) {
                    System.out.println((i+1) + ". " + inputs[i]);
                }
            }

            inputs[inputnum] = input;
            inputnum++;
            System.out.println(banner);
            System.out.println("added: " + input);
        }
        sc.close();
    }
}
