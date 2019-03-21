import java.util.HashMap;

public class TeamList extends ToDoList {

    public HashMap<String,User> users = new HashMap<>();

    public TeamList(int priority) {
        this.priority = priority;
    }

    public void addUser(String name, User user) {
        if (!users.containsKey(name)) {
            users.put(name, user);
        } else {
            System.out.println("User already exists!");
        }
    }

    public void deleteUser(String name) {
        if (users.containsKey(name)) {
            users.remove(name);
        } else {
            System.out.println("User does not exist!");
        }
    }

    //created from refactoring method #2
    public void readChecklist() {
        System.out.println("Checklist " + this.name);
        System.out.println("  Priority: " + this.priority);
        System.out.println("  Users associated with this item: ");
        for (HashMap.Entry<String,User> userPair : this.users.entrySet()) {
            System.out.println("    User " + userPair.getKey());
        }
        System.out.println();
    }

}
