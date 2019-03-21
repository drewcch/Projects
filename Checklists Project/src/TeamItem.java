import java.util.Date;
import java.util.HashMap;

public class TeamItem extends ToDoItem {

    public HashMap<String, User> users = new HashMap<>();

    public TeamItem(int priority, Date deadline) {
        this.priority = priority;
        this.deadline = deadline;
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

    //Created for refactoring method #2
    public void readItem() {
        System.out.println("Item " + this.description);
        System.out.println("  Priority: " + this.priority);
        System.out.println("  Deadline: " + this.deadline);
        System.out.println("  Users associated with this item: ");
        for (HashMap.Entry<String,User> userPair : this.users.entrySet()) {
            System.out.println("    User " + userPair.getKey());
        }
        System.out.println();
    }

}
