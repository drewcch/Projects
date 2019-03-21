import java.util.Timer;
import java.util.TimerTask;

public abstract class Item {

    public String description;
    public boolean status = false;
    public long startTime;
    public Timer timer;

    public final void setDescription(String description) {
        this.description = description;
    }

    public final void markComplete() {
        status = true;
    }

    public final void beginTimer(Object obj) {

        class DeleteItem extends TimerTask {
            @Override
            public void run() {
                System.out.println("Item " + description + " will now be deleted");
                if (obj instanceof Checklist) { //Checks to see if the passed in argument is a checklist that will have its item deleted
                    Checklist list = (Checklist) obj;
                    list.deleteItem(description);
                }
                else if (obj instanceof GoalItem) { //Checks to see if the passed in argument is a Goal item that will have its sub-goal deleted
                    GoalItem item = (GoalItem) obj;
                    item.deleteSubGoal(description);
                }

                //timer.cancel(); //Not necessary because we call System.exit
                //System.exit(0); //Stops the AWT thread (and everything else)
            }
        }

        System.out.println("24 hour timer has been set to delete Item " + this.description);
        timer = new Timer();
        startTime = System.currentTimeMillis();

        timer.schedule(new DeleteItem(),24*60*60*1000);
    }

    //Created for refactoring method #2
    public abstract void readItem();

}


