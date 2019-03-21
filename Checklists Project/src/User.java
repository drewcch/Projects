import java.util.HashMap;

public class User {

    public String username;
    public HashMap<String,Checklist> lists = new HashMap<>();
    public boolean hasGoalList = false;

    public void addChecklist(String name, Checklist list) {
        if (!lists.containsKey(name)) {
            if (!(list instanceof GoalList)) {
                lists.put(name,list);
                lists.get(name).setName(name);
            }
            else {
                if (hasGoalList) {
                    System.out.println("User already has a Goal List!");
                }
                else {
                    hasGoalList = true;
                    lists.put(name,list);
                    lists.get(name).setName(name);
                }
            }

        }
        else {
            System.out.println("Checklist already exists!");
        }
    }

    public void deleteChecklist(String name) {
        if (lists.containsKey(name)) {
            if (lists.get(name) instanceof GoalList) {
                hasGoalList = false;
            }
            lists.remove(name);
        }
        else {
            System.out.println("Checklist does not exist");
        }
    }

    public void setUsername(String name) {
        this.username = name;
    }

    //Created for refactoring method #2
    public void readUser() {
        System.out.println("User " + this.username + "\n");
    }

    public void viewChecklists() {
        System.out.println("\nViewing Checklists within the User: \n");
        for (HashMap.Entry<String,Checklist> pair : lists.entrySet()) {
            pair.getValue().readChecklist();
        }
        System.out.println();
    }

    /*
    To edit the list of checklist, the user must provide the old name, the new name, and optional arguments. The arguments
    can include a priority (integer).
    */
    //Modified after templating
    public void editChecklist(String name, String newName, Object... options) {
        if (lists.containsKey(name)) {
            Checklist newList = lists.get(name);
            newList.setName(newName);

            for (int i = 0; i < options.length; i++) {
                if (options[i] instanceof Integer) {//setting a new priority
                    if (newList instanceof ToDoList) {
                        ((ToDoList) newList).setPriority((int) options[i]);
                    }
                }
            }
            lists.remove(name);
            lists.put(newName,newList);
        }
        else {
            System.out.println("Checklist does not exist");
        }
    }

}
