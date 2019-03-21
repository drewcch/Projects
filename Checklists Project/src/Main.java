import java.util.Scanner;

//class created for design pattern #2
public class Main {

    public static long beginTime = System.currentTimeMillis();

    public static void main (String[] args) {
        Scanner scan = new Scanner(System.in);

        //Begin Checklist Program
        while (true) {
            System.out.println("Welcome to the Checklist System!");
            System.out.print("Before beginning, please enter a username to login: ");

            String username = scan.nextLine();
            ChecklistSystem.login(username);
            ChecklistSystem.currentUser = ChecklistSystem.users.get(username);

            System.out.println("You are now logged in!\n");
            UserInterface.runChecklistSystemView(scan);
        }
    }

}
