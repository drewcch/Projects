import java.util.HashMap;

public class ChecklistSystem {

    public static HashMap<String, User> users = new HashMap<>();
    public static User currentUser;

    public static void login(String username) {
        if (users.containsKey(username)) {
            currentUser = users.get(username);
        }
        else {
            addUser(username);
        }
    }

    public static void logout() {
        currentUser = null;
    }

    public static void addUser(String username) {
        if (!users.containsKey(username)) {
            User newUser = new User();
            newUser.setUsername(username);
            users.put(username, newUser);
        }
        else {
            System.out.println("User already in system!");
        }
    }

    public static void deleteUser(String username) {
        if (users.containsKey(username)) {
            users.remove(username);
            if (currentUser.username.equals(username)) {
                logout();
            }
        }
        else {
            System.out.println("User does not exist!");
        }
    }

    public static void viewUsers() {
        System.out.println("\nViewing Users within the Checklist System: \n");
        for (HashMap.Entry<String,User> pair : users.entrySet()) {
            pair.getValue().readUser();
        }
        System.out.println();
    }

}
