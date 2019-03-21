import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

//class created for design pattern #2
public class UserInterface {

    public static void runChecklistSystemView(Scanner scan) {
        //Begin User editing mode
        while (ChecklistSystem.currentUser != null) {
            System.out.println("Current User is: " + ChecklistSystem.currentUser.username + "\n");

            System.out.println("To log out, press L");
            System.out.println("To add a user, press A");
            System.out.println("To delete a user, press D");
            System.out.println("To view all the users in the Checklist System, press V");
            System.out.println("To enter Checklist editing mode, press C");

            String userSelect = scan.nextLine();

            switch (userSelect.toUpperCase()) {
                case "L":
                    ChecklistSystem.logout();
                    break;

                case "A":
                    System.out.print("Enter a username for the new user: ");
                    String name = scan.nextLine();
                    ChecklistSystem.addUser(name);
                    System.out.println("Would you like to switch to this user? Press Y or N");
                    String choice = scan.nextLine();
                    if (choice.toUpperCase().equals("Y")) {
                        ChecklistSystem.currentUser = ChecklistSystem.users.get(name);
                    }
                    break;

                case "D":
                    System.out.print("Enter a username to delete: ");
                    String deleteName = scan.nextLine();
                    ChecklistSystem.deleteUser(deleteName);
                    break;

                case "V":
                    ChecklistSystem.viewUsers();
                    break;

                case "C":
                    runUserView(scan);
                    break;

                default:
                    System.out.println("Invalid option entered");
                    break;
            }
        }
    }

    public static void runUserView(Scanner scan) {
        User currentUser = ChecklistSystem.currentUser;
        //Begin Checklist editing mode
        boolean checklistMode = true;

        while (checklistMode) {
            System.out.println("\nCurrently in Checklist editing mode for User " + currentUser.username+"\n");

            System.out.println("To add a checklist, press A");
            System.out.println("To delete a checklist, press D");
            System.out.println("To edit a checklist, press E");
            System.out.println("To view all the checklists in the User, press V");
            System.out.println("To enter Team List editing mode, press T");
            System.out.println("To enter Item editing mode, press I");
            System.out.println("To quit Checklist editing mode, press Q");

            String checkSelect = scan.nextLine();

            switch (checkSelect.toUpperCase()) {
                case "A":
                    boolean addChecklistMode = true;

                    while (addChecklistMode) {
                        System.out.print("Enter a name for the checklist: ");
                        String checkName = scan.nextLine();

                        System.out.println("To create a Shopping List, press S");
                        System.out.println("To create a Goal List, press G");
                        System.out.println("To create a ToDo List, press TD");
                        System.out.println("To create a Team List, press TE");

                        String checkType = scan.nextLine();

                        switch (checkType.toUpperCase()) {
                            case "S":
                                currentUser.addChecklist(checkName, new ShoppingList());
                                break;

                            case "G":
                            case "TD":
                                int listPriority = 0;

                                System.out.println("Would you like to add a priority to this list? Press Y or N");
                                String priorityChoice = scan.nextLine();

                                if (priorityChoice.toUpperCase().equals("Y")) {
                                    System.out.println("Please enter a priority number");
                                    while (true) {
                                        while (!scan.hasNextInt()) {
                                            scan.nextLine(); // Read and discard offending non-int input
                                            System.out.println("Please enter an integer: "); // Re-prompt
                                        }
                                        // At this point in the code, the user has entered an integer
                                        listPriority = scan.nextInt(); // Get the integer
                                        scan.nextLine();
                                        if (listPriority <= 0) {
                                            System.out.println("Please enter a positive integer");
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                }

                                if (listPriority != 0) {
                                    if (checkType.toUpperCase().equals("G")) {
                                        currentUser.addChecklist(checkName, new GoalList(listPriority));
                                    }
                                    else {
                                        currentUser.addChecklist(checkName, new ToDoList(listPriority));
                                    }
                                }
                                else {
                                    if (checkType.toUpperCase().equals("G")) {
                                        currentUser.addChecklist(checkName, new GoalList());
                                    }
                                    else {
                                        currentUser.addChecklist(checkName, new ToDoList());
                                    }
                                }
                                break;

                            case "TE":
                                int teamPriority;

                                System.out.println("Please enter a priority number");
                                while (true) {
                                    while (!scan.hasNextInt()) {
                                        scan.nextLine(); // Read and discard offending non-int input
                                        System.out.println("Please enter an integer: "); // Re-prompt
                                    }
                                    // At this point in the code, the user has entered an integer
                                    teamPriority = scan.nextInt(); // Get the integer
                                    scan.nextLine();
                                    if (teamPriority <= 0) {
                                        System.out.println("Please enter a positive integer");
                                    }
                                    else {
                                        break;
                                    }
                                }
                                TeamList teamlist = new TeamList(teamPriority);
                                teamlist.addUser(currentUser.username,currentUser);
                                currentUser.addChecklist(checkName, teamlist);
                                break;

                            default:
                                System.out.println("Invalid option entered");
                                continue;
                        }
                        addChecklistMode = false;
                    }
                    break;

                case "D":
                    System.out.print("Enter a valid checklist name: ");
                    String checkDelete = scan.nextLine();
                    currentUser.deleteChecklist(checkDelete);
                    break;

                case "E":
                    boolean editChecklistMode = true;

                    while (editChecklistMode) {
                        System.out.print("Enter a valid checklist name: ");
                        String checkEdit = scan.nextLine();

                        if (!(currentUser.lists.containsKey(checkEdit))) {
                            System.out.println("Checklist does not exist");
                        }
                        else {
                            Checklist validList = currentUser.lists.get(checkEdit);

                            System.out.println("To edit a Shopping List, press S");
                            System.out.println("To edit a Goal List, press G");
                            System.out.println("To edit a ToDo List, press TD");
                            System.out.println("To edit a Team List, press TE");

                            String editCheckType = scan.nextLine();

                            switch (editCheckType.toUpperCase()) {
                                case "S":
                                    if (validList instanceof ShoppingList) {
                                        System.out.print("Enter a new name for the list: ");
                                        String newShopListName = scan.nextLine();
                                        currentUser.editChecklist(checkEdit, newShopListName);
                                    }
                                    else {
                                        System.out.println("Checklist name does not match type");
                                    }
                                    break;

                                case "G":
                                case "TD":
                                    if ((validList instanceof GoalList && editCheckType.toUpperCase().equals("G")) ||
                                            ((validList instanceof ToDoList && !(validList instanceof TeamList) && !(validList instanceof GoalList)) //Need to check validList is a TodoList
                                                    && editCheckType.toUpperCase().equals("TD"))) {
                                        System.out.print("Enter a new name for the list: ");
                                        String newCheckName = scan.nextLine();

                                        System.out.println("Would you like to change the priority of this list? Press Y or N");
                                        String priorityChoice = scan.nextLine();

                                        if (priorityChoice.toUpperCase().equals("Y")) {
                                            int newPriority;
                                            System.out.println("Please enter a priority number");
                                            while (true) {
                                                while (!scan.hasNextInt()) {
                                                    scan.nextLine(); // Read and discard offending non-int input
                                                    System.out.println("Please enter an integer: "); // Re-prompt
                                                }
                                                // At this point in the code, the user has entered an integer
                                                newPriority = scan.nextInt(); // Get the integer
                                                scan.nextLine();
                                                if (newPriority <= 0) {
                                                    System.out.println("Please enter a positive integer");
                                                } else {
                                                    break;
                                                }
                                            }
                                            currentUser.editChecklist(checkEdit, newCheckName, newPriority);

                                        } else {
                                            currentUser.editChecklist(checkEdit, newCheckName);
                                        }
                                    }
                                    else {
                                        System.out.println("Checklist name does not match type");
                                    }
                                    break;

                                case "TE":
                                    if (validList instanceof TeamList) {
                                        System.out.print("Enter a new name for the list: ");
                                        String newTeamName = scan.nextLine();

                                        int teamPriority;

                                        System.out.println("Please enter a priority number");
                                        while (true) {
                                            while (!scan.hasNextInt()) {
                                                scan.nextLine(); // Read and discard offending non-int input
                                                System.out.println("Please enter an integer: "); // Re-prompt
                                            }
                                            // At this point in the code, the user has entered an integer
                                            teamPriority = scan.nextInt(); // Get the integer
                                            scan.nextLine();
                                            if (teamPriority <= 0) {
                                                System.out.println("Please enter a positive integer");
                                            } else {
                                                break;
                                            }
                                        }

                                        System.out.println("Would you like to add or delete a user from the list? Press A for add, D for delete");
                                        String addOrDelete = scan.nextLine();

                                        switch (addOrDelete.toUpperCase()) {
                                            case "A":
                                                System.out.print("Enter a username to add: ");
                                                String userToAdd = scan.nextLine();

                                                if (currentUser.lists.containsKey(checkEdit) && currentUser.lists.get(checkEdit) instanceof TeamList) {
                                                    if (ChecklistSystem.users.containsKey(userToAdd)) {
                                                        ((TeamList) currentUser.lists.get(checkEdit)).addUser(userToAdd, ChecklistSystem.users.get(userToAdd));
                                                    } else {
                                                        System.out.println("Username does not exist");
                                                    }
                                                } else {
                                                    System.out.println("Team List does not exist");
                                                }
                                                break;

                                            case "D":
                                                System.out.print("Enter a username to delete: ");
                                                String userToDelete = scan.nextLine();

                                                if (currentUser.lists.containsKey(checkEdit) && currentUser.lists.get(checkEdit) instanceof TeamList) {
                                                    if (ChecklistSystem.users.containsKey(userToDelete)) {
                                                        ((TeamList) currentUser.lists.get(checkEdit)).deleteUser(userToDelete);
                                                    } else {
                                                        System.out.println("Username does not exist");
                                                    }
                                                } else {
                                                    System.out.println("Team List does not exist");
                                                }
                                                break;

                                            default:
                                                break;
                                        }
                                        currentUser.editChecklist(checkEdit, newTeamName, teamPriority);
                                    }
                                    else {
                                        System.out.println("Checklist name does not match type");
                                    }
                                    break;

                                default:
                                    System.out.println("Invalid option entered");
                                    continue;
                            }
                        }
                        editChecklistMode = false;
                    }
                    break;

                case "V":
                    currentUser.viewChecklists();
                    break;

                case "Q":
                    System.out.println("Now quitting Checklist mode...\n");
                    checklistMode = false;
                    break;

                case "T":
                    System.out.print("Enter a valid Team List name: ");
                    String validTeamList = scan.nextLine();
                    if (currentUser.lists.containsKey(validTeamList) && currentUser.lists.get(validTeamList) instanceof TeamList) {
                        TeamList teamList = (TeamList)currentUser.lists.get(validTeamList);
                        runTeamListView(scan, teamList);
                    }
                    else {
                        System.out.println("Invalid Team List name entered");
                    }
                    break;

                case "I":
                    System.out.print("Enter a valid checklist name: ");
                    String validChecklistName = scan.nextLine();
                    if (currentUser.lists.containsKey(validChecklistName)) {
                        Checklist currentList = currentUser.lists.get(validChecklistName);
                        runChecklistView(scan, currentList);
                    }
                    else {
                        System.out.println("Invalid checklist name entered");
                    }
                    break;

                default:
                    System.out.println("Invalid option entered");
                    break;
            }
        }
    }

    public static void runTeamListView(Scanner scan, TeamList teamList) {
        //Begin Team List Editing Mode
        boolean teamListMode = true;

        while (teamListMode) {
            System.out.println("\nCurrently in Team List editing mode for Team List " + teamList.name + "\n");

            System.out.println("To add a user to associate this Team List with, press A");
            System.out.println("To delete a user associated with this Team List, press D");
            System.out.println("To quit Team List Editing Mode, press Q");

            String teamItemSelect = scan.nextLine();

            switch (teamItemSelect.toUpperCase()) {
                case "A":
                    System.out.print("Enter a user to add: ");
                    String userTeamList = scan.nextLine();
                    if (ChecklistSystem.users.containsKey(userTeamList)) {
                        teamList.addUser(userTeamList,ChecklistSystem.users.get(userTeamList));
                    }
                    else {
                        System.out.println("User does not exist");
                    }
                    break;

                case "D":
                    System.out.print("Enter a user to delete: ");
                    String deleteUserList = scan.nextLine();
                    teamList.deleteUser(deleteUserList);
                    break;

                case "Q":
                    System.out.println("Now quitting Team List Mode...\n");
                    teamListMode = false;
                    break;

                default:
                    System.out.println("Invalid option entered");
                    break;
            }
        }
    }

    public static void runChecklistView(Scanner scan, Checklist currentList) {
        //Begin Item editing mode
        boolean itemMode = true;

        while (itemMode) {
            System.out.println("\nCurrently in Item editing mode for Checklist " + currentList.name + "\n");

            System.out.println("To add an item, press A");
            System.out.println("To delete an item, press D");
            System.out.println("To edit an item, press E");
            System.out.println("To mark an item as complete, press M");
            System.out.println("To view all the items in the Checklist, press V");
            System.out.println("To view a bar chart of completed items, press B");
            System.out.println("To enter Team Item editing mode, press T");
            System.out.println("To enter Goal Item editing mode, press G");
            System.out.println("To check the status of an Item's timer, press C");
            System.out.println("To quit Item editing mode, press Q");

            String itemSelect = scan.nextLine();

            switch (itemSelect.toUpperCase()) {
                case "A":
                    boolean addItemMode = true;

                    while (addItemMode) {
                        System.out.print("Enter a name for the item: ");
                        String itemName = scan.nextLine();

                        System.out.println("To create a Shopping Item, press S");
                        System.out.println("To create a Goal Item, press G");
                        System.out.println("To create a ToDo Item, press TD");
                        System.out.println("To create a Team Item, press TE");

                        String itemType = scan.nextLine();

                        switch (itemType.toUpperCase()) {
                            case "S":
                                if (currentList instanceof ShoppingList) {
                                    currentList.addItem(itemName, new ShoppingItem());
                                }
                                else {
                                    System.out.println("Checklist type and Item type do not match");
                                }

                                break;

                            case "G":
                            case "TD":
                                if ((currentList instanceof GoalList && itemType.toUpperCase().equals("G")) ||
                                        ((currentList instanceof ToDoList && !(currentList instanceof TeamList) && !(currentList instanceof GoalList))  //Need to check currentList is a TodoList
                                                && itemType.toUpperCase().equals("TD"))) {

                                    int itemPriority = 0;

                                    System.out.println("Would you like to add a priority to this item? Press Y or N");
                                    String itemPriorityChoice = scan.nextLine();

                                    if (itemPriorityChoice.toUpperCase().equals("Y")) {
                                        System.out.println("Please enter a priority number");
                                        while (true) {
                                            while (!scan.hasNextInt()) {
                                                scan.nextLine(); // Read and discard offending non-int input
                                                System.out.println("Please enter an integer: "); // Re-prompt
                                            }
                                            // At this point in the code, the user has entered an integer
                                            itemPriority = scan.nextInt(); // Get the integer
                                            scan.nextLine();
                                            if (itemPriority <= 0) {
                                                System.out.println("Please enter a positive integer");
                                            }
                                            else {
                                                break;
                                            }
                                        }
                                    }
                                    Date deadline = null;
                                    Date deadline1 = null;
                                    Date deadline2 = null;
                                    String str[] = {"year", "month", "day" };

                                    System.out.println("If you would like to add a deadline to this item, press D");
                                    System.out.println("If you would like to add a deadline range to this item, press DR");
                                    System.out.println("If you do not want a deadline, press anything else");

                                    String deadlineChoice = scan.nextLine();

                                    switch (deadlineChoice.toUpperCase()) {
                                        case "D":
                                            System.out.println("Currently entering a deadline: \n");
                                            String dates = "";

                                            while (true) {

                                                for (int i = 0; i < 3; i++) {
                                                    System.out.println("Please enter a " + str[i] + ": ");
                                                    String input = scan.nextLine();
                                                    dates = dates + input + "/";
                                                }
                                                dates = dates.substring(0, dates.length() - 1);

                                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/M/d");
                                                try {
                                                    deadline = dateFormat.parse(dates);
                                                    break;
                                                } catch (ParseException e) {
                                                    System.out.println("Invalid date entered");
                                                    dates = "";
                                                }
                                            }
                                            break;

                                        case "DR":
                                            System.out.println("Currently entering the first deadline: \n");
                                            String date1= "";

                                            while (true) {

                                                for (int i = 0; i < 3; i++) {
                                                    System.out.println("Please enter a " + str[i] + ": ");
                                                    String input = scan.nextLine();
                                                    date1 = date1 + input + "/";
                                                }
                                                date1 = date1.substring(0, date1.length() - 1);

                                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/M/d");
                                                try {
                                                    deadline1 = dateFormat.parse(date1);
                                                    break;
                                                } catch (ParseException e) {
                                                    System.out.println("Invalid date entered");
                                                    date1 = "";
                                                }
                                            }

                                            System.out.println("Currently entering the second deadline: \n");
                                            String date2 = "";

                                            while (true) {

                                                for (int i = 0; i < 3; i++) {
                                                    System.out.println("Please enter a " + str[i] + ": ");
                                                    String input = scan.nextLine();
                                                    date2 = date2 + input + "/";
                                                }
                                                date2 = date2.substring(0, date2.length() - 1);

                                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/M/d");
                                                try {
                                                    deadline2 = dateFormat.parse(date2);
                                                    break;
                                                } catch (ParseException e) {
                                                    System.out.println("Invalid date entered");
                                                    date2 = "";
                                                }
                                            }
                                            break;

                                        default:
                                            break;
                                    }

                                    if (itemPriority != 0) {
                                        if (deadline != null) {
                                            if (itemType.toUpperCase().equals("G")) {
                                                currentList.addItem(itemName, new GoalItem(itemPriority, deadline));
                                            } else {
                                                currentList.addItem(itemName, new ToDoItem(itemPriority, deadline));
                                            }
                                        } else if (deadline1 != null && deadline2 != null) {
                                            DateRange deadlineRange = new DateRange(deadline1, deadline2);
                                            if (itemType.toUpperCase().equals("G")) {
                                                currentList.addItem(itemName, new GoalItem(itemPriority, deadlineRange));
                                            } else {
                                                currentList.addItem(itemName, new ToDoItem(itemPriority, deadlineRange));
                                            }
                                        } else {
                                            if (itemType.toUpperCase().equals("G")) {
                                                currentList.addItem(itemName, new GoalItem(itemPriority));
                                            } else {
                                                currentList.addItem(itemName, new ToDoItem(itemPriority));
                                            }
                                        }
                                    } else {
                                        if (deadline != null) {
                                            if (itemType.toUpperCase().equals("G")) {
                                                currentList.addItem(itemName, new GoalItem(deadline));
                                            } else {
                                                currentList.addItem(itemName, new ToDoItem(deadline));
                                            }
                                        } else if (deadline1 != null && deadline2 != null) {
                                            DateRange deadlineRange = new DateRange(deadline1, deadline2);
                                            if (itemType.toUpperCase().equals("G")) {
                                                currentList.addItem(itemName, new GoalItem(deadlineRange));
                                            } else {
                                                currentList.addItem(itemName, new ToDoItem(deadlineRange));
                                            }
                                        } else {
                                            if (itemType.toUpperCase().equals("G")) {
                                                currentList.addItem(itemName, new GoalItem());
                                            } else {
                                                currentList.addItem(itemName, new ToDoItem());
                                            }
                                        }
                                    }
                                }
                                else {
                                    System.out.println("Checklist type and Item type do not match");
                                }
                                break;

                            case "TE":
                                if (currentList instanceof TeamList) {
                                    int teamPriority;
                                    System.out.println("Please enter a priority number");
                                    while (true) {
                                        while (!scan.hasNextInt()) {
                                            scan.nextLine(); // Read and discard offending non-int input
                                            System.out.println("Please enter an integer: "); // Re-prompt
                                        }
                                        // At this point in the code, the user has entered an integer
                                        teamPriority = scan.nextInt(); // Get the integer
                                        scan.nextLine();
                                        if (teamPriority <= 0) {
                                            System.out.println("Please enter a positive integer");
                                        }
                                        else {
                                            break;
                                        }
                                    }

                                    System.out.println("Currently entering a deadline: \n");
                                    String str[] = {"year", "month", "day" };
                                    String date = "";
                                    Date teamDeadline = null;

                                    while (true) {

                                        for (int i = 0; i < 3; i++) {
                                            System.out.println("Please enter a " + str[i] + ": ");
                                            String input = scan.nextLine();
                                            date = date + input + "/";
                                        }
                                        date = date.substring(0, date.length() - 1);

                                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/M/d");
                                        try {
                                            teamDeadline = dateFormat.parse(date);
                                            break;
                                        } catch (ParseException e) {
                                            System.out.println("Invalid date entered");
                                            date = "";
                                        }
                                    }
                                    TeamItem teamItem = new TeamItem(teamPriority, teamDeadline);
                                    teamItem.addUser(ChecklistSystem.currentUser.username, ChecklistSystem.currentUser);
                                    currentList.addItem(itemName, teamItem);
                                }
                                else {
                                    System.out.println("Checklist type and Item type do not match");
                                }
                                break;

                            default:
                                System.out.println("Invalid option entered");
                                continue;
                        }
                        addItemMode = false;
                    }
                    break;

                case "D":
                    System.out.print("Enter a valid item name: ");
                    String itemDelete = scan.nextLine();
                    currentList.deleteItem(itemDelete);
                    break;

                case "E":
                    boolean editItemMode = true;

                    while (editItemMode) {
                        System.out.print("Enter a valid item name: ");
                        String editName = scan.nextLine();

                        if (!(currentList.items.containsKey(editName))) {
                            System.out.println("Item does not exist");
                        }
                        else {
                            Item currentItem = currentList.items.get(editName);

                            System.out.println("To edit a Shopping Item, press S");
                            System.out.println("To edit a Goal Item, press G");
                            System.out.println("To edit a ToDo Item, press TD");
                            System.out.println("To edit a Team Item, press TE");

                            String editItemType = scan.nextLine();

                            switch (editItemType.toUpperCase()) {
                                case "S":
                                    if (currentItem instanceof ShoppingItem) {
                                        System.out.print("Enter a new name for the item: ");
                                        String newShopItemName = scan.nextLine();
                                        currentList.editItem(editName, newShopItemName);
                                    }
                                    else {
                                        System.out.println("Item name does not match type");
                                    }
                                    break;

                                case "G":
                                case "TD":
                                    if ((currentItem instanceof GoalItem && editItemType.toUpperCase().equals("G")) ||
                                            ((currentItem instanceof ToDoItem && !(currentItem instanceof TeamItem) && !(currentItem instanceof GoalItem))  //Need to check currentItem is ToDo Item
                                                    && editItemType.toUpperCase().equals("TD"))) {
                                        System.out.print("Enter a new name for the item: ");
                                        String newItemName = scan.nextLine();

                                        int newPriority = 0;
                                        System.out.println("Would you like to change the priority of this item? Press Y or N");
                                        String priorityChoice = scan.nextLine();

                                        if (priorityChoice.toUpperCase().equals("Y")) {
                                            System.out.println("Please enter a priority number");
                                            while (true) {
                                                while (!scan.hasNextInt()) {
                                                    scan.nextLine(); // Read and discard offending non-int input
                                                    System.out.println("Please enter an integer: "); // Re-prompt
                                                }
                                                // At this point in the code, the user has entered an integer
                                                newPriority = scan.nextInt(); // Get the integer
                                                scan.nextLine();
                                                if (newPriority <= 0) {
                                                    System.out.println("Please enter a positive integer");
                                                } else {
                                                    break;
                                                }
                                            }

                                        }

                                        Date deadline = null;
                                        Date deadline1 = null;
                                        Date deadline2 = null;
                                        String str[] = {"year", "month", "day"};

                                        System.out.println("If you would like to edit the deadline of this item, press D");
                                        System.out.println("If you would like to edit the deadline range of this item, press DR");
                                        System.out.println("If you do not want edit the deadline, press anything else");

                                        String deadlineChoice = scan.nextLine();

                                        switch (deadlineChoice.toUpperCase()) {
                                            case "D":
                                                System.out.println("Currently entering a deadline: \n");
                                                String dates = "";

                                                while (true) {

                                                    for (int i = 0; i < 3; i++) {
                                                        System.out.println("Please enter a " + str[i] + ": ");
                                                        String input = scan.nextLine();
                                                        dates = dates + input + "/";
                                                    }
                                                    dates = dates.substring(0, dates.length() - 1);

                                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/M/d");
                                                    try {
                                                        deadline = dateFormat.parse(dates);
                                                        break;
                                                    } catch (ParseException e) {
                                                        System.out.println("Invalid date entered");
                                                        dates = "";
                                                    }
                                                }
                                                break;

                                            case "DR":
                                                System.out.println("Currently entering the first deadline: \n");
                                                String date1 = "";

                                                while (true) {

                                                    for (int i = 0; i < 3; i++) {
                                                        System.out.println("Please enter a " + str[i] + ": ");
                                                        String input = scan.nextLine();
                                                        date1 = date1 + input + "/";
                                                    }
                                                    date1 = date1.substring(0, date1.length() - 1);

                                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/M/d");
                                                    try {
                                                        deadline1 = dateFormat.parse(date1);
                                                        break;
                                                    } catch (ParseException e) {
                                                        System.out.println("Invalid date entered");
                                                        date1 = "";
                                                    }
                                                }

                                                System.out.println("Currently entering the second deadline: \n");
                                                String date2 = "";

                                                while (true) {

                                                    for (int i = 0; i < 3; i++) {
                                                        System.out.println("Please enter a " + str[i] + ": ");
                                                        String input = scan.nextLine();
                                                        date2 = date2 + input + "/";
                                                    }
                                                    date2 = date2.substring(0, date2.length() - 1);

                                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/M/d");
                                                    try {
                                                        deadline2 = dateFormat.parse(date2);
                                                        break;
                                                    } catch (ParseException e) {
                                                        System.out.println("Invalid date entered");
                                                        date2 = "";
                                                    }
                                                }
                                                break;

                                            default:
                                                break;
                                        }

                                        if (newPriority != 0) {
                                            if (deadline != null) {
                                                currentList.editItem(editName, newItemName, newPriority, deadline);
                                            } else if (deadline1 != null && deadline2 != null) {
                                                DateRange deadlineRange = new DateRange(deadline1,deadline2);
                                                currentList.editItem(editName, newItemName, newPriority, deadlineRange);
                                            } else {
                                                currentList.editItem(editName, newItemName);
                                            }
                                        } else {
                                            if (deadline != null) {
                                                currentList.editItem(editName, newItemName, deadline);
                                            } else if (deadline1 != null && deadline2 != null) {
                                                DateRange deadlineRange = new DateRange(deadline1,deadline2);
                                                currentList.editItem(editName, newItemName, deadlineRange);
                                            } else {
                                                currentList.editItem(editName, newItemName);
                                            }
                                        }
                                    }
                                    else {
                                        System.out.println("Item name does not match type");
                                    }
                                    break;

                                case "TE":
                                    if (currentItem instanceof TeamItem) {
                                        System.out.print("Enter a new name for the item: ");
                                        String newTeamName = scan.nextLine();

                                        int teamPriority;
                                        System.out.println("Please enter a priority number");
                                        while (true) {
                                            while (!scan.hasNextInt()) {
                                                scan.nextLine(); // Read and discard offending non-int input
                                                System.out.println("Please enter an integer: "); // Re-prompt
                                            }
                                            // At this point in the code, the user has entered an integer
                                            teamPriority = scan.nextInt(); // Get the integer
                                            scan.nextLine();
                                            if (teamPriority <= 0) {
                                                System.out.println("Please enter a positive integer");
                                            } else {
                                                break;
                                            }
                                        }

                                        System.out.println("Currently entering a deadline: \n");
                                        String str1[] = {"year", "month", "day"};
                                        String date = "";
                                        Date teamDeadline = null;

                                        while (true) {

                                            for (int i = 0; i < 3; i++) {
                                                System.out.println("Please enter a " + str1[i] + ": ");
                                                String input = scan.nextLine();
                                                date = date + input + "/";
                                            }
                                            date = date.substring(0, date.length() - 1);

                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/M/d");
                                            try {
                                                teamDeadline = dateFormat.parse(date);
                                                break;
                                            } catch (ParseException e) {
                                                System.out.println("Invalid date entered");
                                                date = "";
                                            }
                                        }
                                        System.out.println("Would you like to add or delete a user associated with the item? Press A for add, D for delete");
                                        String addOrDelete = scan.nextLine();

                                        switch (addOrDelete.toUpperCase()) {
                                            case "A":
                                                System.out.print("Enter a username to add: ");
                                                String userToAdd = scan.nextLine();

                                                if (currentList.items.containsKey(editName) && currentList.items.get(editName) instanceof TeamItem) {
                                                    if (ChecklistSystem.users.containsKey(userToAdd)) {
                                                        ((TeamItem) currentList.items.get(editName)).addUser(userToAdd, ChecklistSystem.users.get(userToAdd));
                                                    } else {
                                                        System.out.println("Username does not exist");
                                                    }
                                                } else {
                                                    System.out.println("Team Item does not exist");
                                                }
                                                break;

                                            case "D":
                                                System.out.print("Enter a username to delete: ");
                                                String userToDelete = scan.nextLine();

                                                if (currentList.items.containsKey(editName) && currentList.items.get(editName) instanceof TeamItem) {
                                                    if (ChecklistSystem.users.containsKey(userToDelete)) {
                                                        ((TeamItem) currentList.items.get(editName)).deleteUser(userToDelete);
                                                    } else {
                                                        System.out.println("Username does not exist");
                                                    }
                                                } else {
                                                    System.out.println("Team Item does not exist");
                                                }
                                                break;

                                            default:
                                                break;
                                        }

                                        currentList.editItem(editName, newTeamName, teamPriority, teamDeadline);
                                    }
                                    else {
                                        System.out.println("Item name does not match type");
                                    }
                                    break;

                                default:
                                    System.out.println("Invalid option entered");
                                    continue;
                            }
                        }
                        editItemMode = false;
                    }
                    break;

                case "M":
                    System.out.print("Enter a valid Item name: ");
                    String itemName = scan.nextLine();
                    if (currentList.items.containsKey(itemName)) {
                        Item item = currentList.items.get(itemName);
                        item.markComplete();
                        if ((currentList instanceof ToDoList && item instanceof ToDoItem) && !(currentList instanceof TeamList)) {
                            ((ToDoList) currentList).updateCompletedItems(true);
                        }
                        item.beginTimer(currentList);
                    }
                    else {
                        System.out.println("Invalid Item name entered");
                    }
                    break;

                case "B":
                    if ((currentList instanceof ToDoList) && !(currentList instanceof TeamList)) {
                        ToDoList list = (ToDoList) currentList;
                        list.createHistoryChart(ChecklistSystem.currentUser.username);
                    }
                    else {
                        System.out.println("Current checklist is not a ToDo or Goal List");
                    }
                    break;

                case "V":
                    currentList.viewItems();
                    break;

                case "T":
                    System.out.print("Enter a valid Team Item name: ");
                    String validTeamItem = scan.nextLine();
                    if (currentList.items.containsKey(validTeamItem) && currentList.items.get(validTeamItem) instanceof TeamItem) {
                        TeamItem teamItem = (TeamItem)currentList.items.get(validTeamItem);
                        runTeamItemView(scan, teamItem);
                    }
                    else {
                        System.out.println("Invalid Team Item name entered");
                    }
                    break;

                case "G":
                    System.out.print("Enter a valid Goal Item name: ");
                    String validGoalItem = scan.nextLine();
                    if (currentList.items.containsKey(validGoalItem) && currentList.items.get(validGoalItem) instanceof GoalItem) {
                        GoalItem currentGoal = (GoalItem)currentList.items.get(validGoalItem);
                        runGoalItemView(scan, currentGoal);
                    }
                    else {
                        System.out.println("Invalid Goal Item name entered");
                    }
                    break;

                case "C":
                    System.out.print("Enter a valid name for a completed Item: ");
                    String completedItem = scan.nextLine();
                    if (currentList.items.containsKey(completedItem)) {
                        currentList.checkTimer(completedItem);
                    }
                    else {
                        System.out.println("Invalid Item name entered");
                    }
                    break;

                case "Q":
                    System.out.println("Now quitting Item mode...\n");
                    itemMode = false;
                    break;

                default:
                    System.out.println("Invalid option entered");
                    break;
            }
        }
    }

    public static void runTeamItemView(Scanner scan, TeamItem teamItem) {
        boolean teamItemMode = true;

        while (teamItemMode) {
            //Begin Team Item Editing Mode
            System.out.println("\nCurrently in Team Item editing mode for Team Item " + teamItem.description + "\n");

            System.out.println("To add a user to associate this Team Item with, press A");
            System.out.println("To delete a user associated with this Team item, press D");
            System.out.println("To quit Team Item Editing Mode, press Q");

            String teamItemSelect = scan.nextLine();

            switch (teamItemSelect.toUpperCase()) {
                case "A":
                    System.out.print("Enter a user to add: ");
                    String userTeamItem = scan.nextLine();
                    if (ChecklistSystem.users.containsKey(userTeamItem)) {
                        teamItem.addUser(userTeamItem,ChecklistSystem.users.get(userTeamItem));
                    }
                    else {
                        System.out.println("User does not exist");
                    }
                    break;

                case "D":
                    System.out.print("Enter a user to delete: ");
                    String deleteUserItem = scan.nextLine();
                    teamItem.deleteUser(deleteUserItem);
                    break;

                case "Q":
                    System.out.println("Now quitting Team Item Mode...\n");
                    teamItemMode = false;
                    break;

                default:
                    System.out.println("Invalid option entered");
                    break;
            }
        }
    }

    public static void runGoalItemView(Scanner scan, GoalItem currentGoal) {
        boolean goalItemMode = true;

        while (goalItemMode) {
            System.out.println("\nCurrently in Goal Item editing mode for Goal Item " + currentGoal.description + "\n");

            System.out.println("To add a sub-goal item, press A");
            System.out.println("To delete a sub-goal item, press D");
            System.out.println("To edit a sub-goal item, press E");
            System.out.println("To mark a sub-goal item as complete, press M");
            System.out.println("To view all the sub-goals in the Goal Item, press V");
            System.out.println("To enter sub-goal item editing mode, press G");
            System.out.println("To quit Goal Item editing mode, press Q");

            String itemSelect = scan.nextLine();

            switch (itemSelect.toUpperCase()) {
                case "A":
                    System.out.print("Enter a name for the sub-goal: ");
                    String itemName = scan.nextLine();

                    int itemPriority = 0;
                    System.out.println("Would you like to add a priority to this sub-goal? Press Y or N");
                    String itemPriorityChoice = scan.nextLine();

                    if (itemPriorityChoice.toUpperCase().equals("Y")) {
                        System.out.println("Please enter a priority number");
                        while (true) {
                            while (!scan.hasNextInt()) {
                                scan.nextLine(); // Read and discard offending non-int input
                                System.out.println("Please enter an integer: "); // Re-prompt
                            }
                            // At this point in the code, the user has entered an integer
                            itemPriority = scan.nextInt(); // Get the integer
                            scan.nextLine();
                            if (itemPriority <= 0) {
                                System.out.println("Please enter a positive integer");
                            }
                            else {
                                break;
                            }
                        }
                    }
                    Date deadline = null;
                    Date deadline1 = null;
                    Date deadline2 = null;
                    String str[] = {"year","month","day"};

                    System.out.println("If you would like to add a deadline to this sub-goal, press D");
                    System.out.println("If you would like to add a deadline range to this sub-goal, press DR");
                    System.out.println("If you do not want a deadline, press anything else");

                    String deadlineChoice = scan.nextLine();

                    switch (deadlineChoice.toUpperCase()) {
                        case "D":
                            System.out.println("Currently entering a deadline: \n");
                            String dates = "";

                            while (true) {

                                for (int i = 0; i < 3; i++) {
                                    System.out.println("Please enter a " + str[i] + ": ");
                                    String input = scan.nextLine();
                                    dates = dates + input + "/";
                                }
                                dates = dates.substring(0, dates.length() - 1);

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/M/d");
                                try {
                                    deadline = dateFormat.parse(dates);
                                    break;
                                } catch (ParseException e) {
                                    System.out.println("Invalid date entered");
                                    dates = "";
                                }
                            }
                            break;

                        case "DR":
                            System.out.println("Currently entering the first deadline: \n");
                            String date1= "";

                            while (true) {

                                for (int i = 0; i < 3; i++) {
                                    System.out.println("Please enter a " + str[i] + ": ");
                                    String input = scan.nextLine();
                                    date1 = date1 + input + "/";
                                }
                                date1 = date1.substring(0, date1.length() - 1);

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/M/d");
                                try {
                                    deadline1 = dateFormat.parse(date1);
                                    break;
                                } catch (ParseException e) {
                                    System.out.println("Invalid date entered");
                                    date1 = "";
                                }
                            }

                            System.out.println("Currently entering the second deadline: \n");
                            String date2 = "";

                            while (true) {

                                for (int i = 0; i < 3; i++) {
                                    System.out.println("Please enter a " + str[i] + ": ");
                                    String input = scan.nextLine();
                                    date2 = date2 + input + "/";
                                }
                                date2 = date2.substring(0, date2.length() - 1);

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/M/d");
                                try {
                                    deadline2 = dateFormat.parse(date2);
                                    break;
                                } catch (ParseException e) {
                                    System.out.println("Invalid date entered");
                                    date2 = "";
                                }
                            }
                            break;

                        default:
                            break;
                    }

                    if (itemPriority != 0) {
                        if (deadline != null) {
                            currentGoal.addSubGoal(itemName, new GoalItem(itemPriority, deadline));
                        }
                        else if (deadline1 != null && deadline2 != null) {
                            DateRange deadlineRange = new DateRange(deadline1,deadline2);
                            currentGoal.addSubGoal(itemName, new GoalItem(itemPriority, deadlineRange));
                        }
                        else {
                            currentGoal.addSubGoal(itemName, new GoalItem(itemPriority));
                        }
                    }
                    else {
                        if (deadline != null) {
                            currentGoal.addSubGoal(itemName, new GoalItem(deadline));
                        }
                        else if (deadline1 != null && deadline2 != null) {
                            DateRange deadlineRange = new DateRange(deadline1, deadline2);
                            currentGoal.addSubGoal(itemName, new GoalItem(deadlineRange));
                        }
                        else {
                            currentGoal.addSubGoal(itemName, new GoalItem());
                        }
                    }
                    break;

                case "D":
                    System.out.print("Enter a valid sub-goal name: ");
                    String itemDelete = scan.nextLine();
                    currentGoal.deleteSubGoal(itemDelete);
                    break;

                case "E":
                    System.out.print("Enter a valid sub-goal name: ");
                    String editName = scan.nextLine();

                    System.out.print("Enter a new name for the sub-goal: ");
                    String newItemName = scan.nextLine();

                    int newPriority = 0;
                    System.out.println("Would you like to change the priority of this sub-goal? Press Y or N");
                    String priorityChoice = scan.nextLine();

                    if (priorityChoice.toUpperCase().equals("Y")) {
                        System.out.println("Please enter a priority number");
                        while (true) {
                            while (!scan.hasNextInt()) {
                                scan.nextLine(); // Read and discard offending non-int input
                                System.out.println("Please enter an integer: "); // Re-prompt
                            }
                            // At this point in the code, the user has entered an integer
                            newPriority = scan.nextInt(); // Get the integer
                            scan.nextLine();
                            if (newPriority <= 0) {
                                System.out.println("Please enter a positive integer");
                            }
                            else {
                                break;
                            }
                        }

                    }

                    Date editDeadline = null;
                    Date editDeadline1 = null;
                    Date editDeadline2 = null;
                    String[] str1 = {"year","month","day"};

                    System.out.println("If you would like to edit the deadline of this sub-goal, press D");
                    System.out.println("If you would like to edit the deadline range of this sub-goal, press DR");
                    System.out.println("If you do not want edit the deadline, press anything else");

                    String editDeadlineChoice = scan.nextLine();

                    switch (editDeadlineChoice.toUpperCase()) {
                        case "D":
                            System.out.println("Currently entering a deadline: \n");
                            String dates = "";

                            while (true) {

                                for (int i = 0; i < 3; i++) {
                                    System.out.println("Please enter a " + str1[i] + ": ");
                                    String input = scan.nextLine();
                                    dates = dates + input + "/";
                                }
                                dates = dates.substring(0, dates.length() - 1);

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/M/d");
                                try {
                                    editDeadline = dateFormat.parse(dates);
                                    break;
                                } catch (ParseException e) {
                                    System.out.println("Invalid date entered");
                                    dates = "";
                                }
                            }
                            break;

                        case "DR":
                            System.out.println("Currently entering the first deadline: \n");
                            String date1= "";

                            while (true) {

                                for (int i = 0; i < 3; i++) {
                                    System.out.println("Please enter a " + str1[i] + ": ");
                                    String input = scan.nextLine();
                                    date1 = date1 + input + "/";
                                }
                                date1 = date1.substring(0, date1.length() - 1);

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/M/d");
                                try {
                                    editDeadline1 = dateFormat.parse(date1);
                                    break;
                                } catch (ParseException e) {
                                    System.out.println("Invalid date entered");
                                    date1 = "";
                                }
                            }

                            System.out.println("Currently entering the second deadline: \n");
                            String date2 = "";

                            while (true) {

                                for (int i = 0; i < 3; i++) {
                                    System.out.println("Please enter a " + str1[i] + ": ");
                                    String input = scan.nextLine();
                                    date2 = date2 + input + "/";
                                }
                                date2 = date2.substring(0, date2.length() - 1);

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/M/d");
                                try {
                                    editDeadline2 = dateFormat.parse(date2);
                                    break;
                                } catch (ParseException e) {
                                    System.out.println("Invalid date entered");
                                    date2 = "";
                                }
                            }
                            break;

                        default:
                            break;
                    }

                    if (newPriority != 0) {
                        if (editDeadline != null) {
                            currentGoal.editSubGoal(editName,newItemName,newPriority,editDeadline);
                        }
                        else if (editDeadline1 != null && editDeadline2 != null) {
                            DateRange editDeadlineRange = new DateRange (editDeadline1,editDeadline2);
                            currentGoal.editSubGoal(editName, newItemName, newPriority, editDeadlineRange);
                        }
                        else {
                            currentGoal.editSubGoal(editName,newItemName,newPriority);
                        }
                    }
                    else {
                        if (editDeadline != null) {
                            currentGoal.editSubGoal(editName,newItemName,editDeadline);
                        }
                        else if (editDeadline1 != null && editDeadline2 != null) {
                            DateRange editDeadlineRange = new DateRange (editDeadline1,editDeadline2);
                            currentGoal.editSubGoal(editName, newItemName, editDeadlineRange);
                        }
                        else {
                            currentGoal.editSubGoal(editName,newItemName);
                        }
                    }
                    break;

                case "M":
                    System.out.print("Enter a valid Sub-Goal Item name: ");
                    String subGoalItemName = scan.nextLine();
                    if (currentGoal.subGoals.containsKey(subGoalItemName)) {
                        GoalItem subGoal = currentGoal.subGoals.get(subGoalItemName);
                        subGoal.markComplete();
                        subGoal.beginTimer(currentGoal);
                    }
                    else {
                        System.out.println("Invalid Item name entered");
                    }
                    break;

                case "V":
                    currentGoal.viewSubGoals();
                    break;

                case "G":
                    System.out.print("Enter a valid Sub-Goal Item name: ");
                    String validGoalItem = scan.nextLine();
                    if (currentGoal.subGoals.containsKey(validGoalItem)) {
                        GoalItem subGoal = currentGoal.subGoals.get(validGoalItem);
                        runGoalItemView(scan,subGoal);
                    }
                    else {
                        System.out.println("Invalid Sub-Goal Item name entered");
                    }
                    break;

                case "Q":
                    System.out.println("Now quitting Goal Item mode...\n");
                    goalItemMode = false;
                    break;

                default:
                    System.out.println("Invalid option entered");
                    break;
            }
        }
    }

}

