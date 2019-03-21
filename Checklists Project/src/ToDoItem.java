import java.util.Date;

public class ToDoItem extends Item {

    public int priority;
    public Date deadline;
    public DateRange deadlineRange;

    public ToDoItem() {}

    public ToDoItem(int priority) {
        this.priority = priority;
    }

    public ToDoItem(Date deadline) {
        this.deadline = deadline;
    }

    public ToDoItem(DateRange deadlineRange) {
        this.deadlineRange = deadlineRange;
    }

    public ToDoItem(int priority, Date deadline) {
        this.priority = priority;
        this.deadline = deadline;
    }

    public ToDoItem(int priority, DateRange deadlineRange) {
        this.priority = priority;
        this.deadlineRange = deadlineRange;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setDeadlineRange(DateRange deadlineRange) {
        this.deadlineRange = deadlineRange;
    }

    //Created for refactoring method #2
    public void readItem() {
        System.out.println("Item " + this.description);
        if (this.priority != 0) {
            System.out.println("  Priority: " + this.priority);
        }
        if (this.deadline != null) {
            System.out.println("  Deadline: " + this.deadline);
        }
        if (this.deadlineRange != null) {
            System.out.println("  Deadline 1: " + this.deadlineRange.start);
            System.out.println("  Deadline 2: " + this.deadlineRange.end);
        }
        System.out.println();
    }

}
