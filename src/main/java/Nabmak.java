import java.util.Scanner;

public class Nabmak {
    public static void main(String[] args) {
        String banner = "________________________________________";
        String name = "Nabmak";
        String intro = "Yo im " + name + ".";
        String greeting = "Whatchu want?";
        String bye = "BYE!";

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
            System.out.println(banner);
            System.out.println(input);
        }
        sc.close();
    }
}
