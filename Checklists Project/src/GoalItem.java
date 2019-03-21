import java.util.HashMap;
import java.util.Date;

public class GoalItem extends ToDoItem {

    HashMap<String,GoalItem> subGoals = new HashMap<>();

    public GoalItem() {}

    public GoalItem(int priority) {
        this.priority = priority;
    }

    public GoalItem(Date deadline) {
        this.deadline = deadline;
    }

    public GoalItem(DateRange deadlineRange) {
        this.deadlineRange = deadlineRange;
    }

    public GoalItem(int priority, Date deadline) {
        this.priority = priority;
        this.deadline = deadline;
    }

    public GoalItem(int priority, DateRange deadlineRange) {
        this.priority = priority;
        this.deadlineRange = deadlineRange;
    }

    public void addSubGoal(String name, GoalItem item) {
        if (!subGoals.containsKey(name)) {
            subGoals.put(name, item);
            this.subGoals.get(name).setDescription(name);
        }
        else {
            System.out.println("Goal Item already exists!");
        }
    }

    public void deleteSubGoal(String name) {
        if (subGoals.containsKey(name)) {
            subGoals.remove(name);
        }
        else {
            System.out.println("Goal Item does not exist!");
        }
    }

    public void viewSubGoals() {
        System.out.println("\nViewing Sub-Goals within the Goal Item: \n");
        for (HashMap.Entry<String,GoalItem> pair : subGoals.entrySet()) {
            System.out.println("Goal Item " + pair.getKey());
            if (pair.getValue().priority != 0) {
                System.out.println("  Priority: " + pair.getValue().priority);
            }
            if (pair.getValue().deadline != null) {
                System.out.println("  Deadline: " + pair.getValue().deadline);
            }
            if (pair.getValue().deadlineRange != null) {
                System.out.println("  Deadline 1: " + pair.getValue().deadlineRange.start);
                System.out.println("  Deadline 2: " + pair.getValue().deadlineRange.end);
            }
        }
        System.out.println();
    }

    /*
    To edit a sub goal list, the user must provide the old name, the new name, and several arguments. The arguments
    include a priority (integer), a deadline (Date), or deadline range (two Dates), a priority and deadline, or a
    priority and deadline range.
    */
    //Modified after creating DateRange
    public void editSubGoal(String name, String newName, Object... options) {
        if (subGoals.containsKey(name)) {
            GoalItem newItem = subGoals.get(name);
            newItem.setDescription(newName);

            for (int i = 0; i < options.length; i++) {
                if (options[i] instanceof Integer) {//setting a new priority
                    newItem.setPriority((int)options[i]);
                }
                else if (options[i] instanceof Date) {
                    newItem.setDeadline((Date) options[i]);
                }
                else if (options[i] instanceof DateRange) {
                    newItem.setDeadlineRange((DateRange) options[i]);
                }
            }
            subGoals.remove(name);
            subGoals.put(newName,newItem);
        }
        else {
            System.out.println("Goal Item does not exist");
        }
    }

}
