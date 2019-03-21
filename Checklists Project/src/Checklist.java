import java.util.Date;
import java.util.HashMap;

public abstract class Checklist {

    public String name;

    public HashMap<String, Item> items = new HashMap<>();

    public final void setName(String name) {
        this.name = name;
    }

    public final void addItem(String name, Item item) {
        if (!items.containsKey(name)) {
            items.put(name, item);
            items.get(name).setDescription(name);
        }
        else {
            System.out.println("Item already exists!");
        }
    }

    public final void deleteItem(String name) {
        if (items.containsKey(name)) {
            items.remove(name);
        }
        else {
            System.out.println("Item does not exist!");
        }
    }

    /*
    To edit an itemList, the user must provide the old name, the new name, and several arguments. The arguments
    include a priority (integer), a deadline (Date), or deadline range (two Dates), a priority and deadline, or a
    priority and deadline range.
    */
    //Modified after templating and creating DateRange
    public final void editItem(String name, String newName, Object... options) {
        if (items.containsKey(name)) {
            Item newItem = items.get(name);
            newItem.setDescription(newName);

            for (int i = 0; i < options.length; i++) {
                if (options[i] instanceof Integer) {//setting a new priority
                    if (newItem instanceof ToDoItem) {
                        ((ToDoItem) newItem).setPriority((int) options[i]);
                    }
                }
                else if (options[i] instanceof Date) {
                    if (newItem instanceof ToDoItem) {
                        ((ToDoItem) newItem).setDeadline((Date) options[i]);
                    }
                }
                else if (options[i] instanceof DateRange) {
                    if (newItem instanceof ToDoItem) {
                        ((ToDoItem) newItem).setDeadlineRange((DateRange)options[i]);
                    }
                }
            }
            items.remove(name);
            items.put(newName,newItem);
        }
        else {
            System.out.println("Item does not exist");
        }
    }

    //created from refactoring method #2
    public abstract void readChecklist();

    public final void viewItems() {
        System.out.println("\nViewing Items within the Checklist: \n");
        for (HashMap.Entry<String,Item> pair : items.entrySet()) {
            pair.getValue().readItem();
        }
        System.out.println();
    }

    public final void checkTimer(String name) {
        if (items.containsKey(name)) {
            if (items.get(name).status) {
                long timeLeft = 24*60*60 - (System.currentTimeMillis() - items.get(name).startTime)/1000;
                System.out.println("Time left before deletion: " + timeLeft + " seconds");
            }
            else {
                System.out.println("Item is not complete");
            }
        }
        else {
            System.out.println("Item does not exist");
        }
    }

}
