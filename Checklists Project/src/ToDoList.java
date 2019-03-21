import org.jfree.ui.RefineryUtilities;
import java.util.TreeMap;

public class ToDoList extends Checklist {

    public int priority;

    public TreeMap<Integer, Integer> completedItems = new TreeMap<>();

    public ToDoList() {}

    public ToDoList(int priority) {
        this.priority = priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    //created from refactoring method #2
    public void readChecklist() {
        System.out.println("Checklist " + this.name);
        if (this.priority != 0) {
            System.out.println("  Priority: " + this.priority);
        }
        System.out.println();
    }

    public void updateCompletedItems(boolean newItem) {
        int interval = (int)(((((System.currentTimeMillis()-Main.beginTime)/1000)/30)+1)*30);  //determines which 30 second interval it is currently

        int count;
        if (!this.completedItems.containsKey(interval)) {
            if (this.completedItems.isEmpty()) {
                count = 0;

                for (int i = interval; i > 0; i-=30) {
                    this.completedItems.put(i,count);  //fills in empty 30 second intervals up to the current interval
                }

            }
            else {
                int prevCount = 0;

                for (int i = 30; i <= interval; i+=30) {
                    if (this.completedItems.containsKey(i)) {
                        prevCount = this.completedItems.get(i); //gets the previous interval count to fill in any empty 30 second intervals
                    }
                    else {
                        this.completedItems.put(i,prevCount);
                    }
                }

                count = this.completedItems.get(interval-30); //get count of previous 30 second interval so it accumulates
            }
        }
        else {
            count = this.completedItems.get(interval);
        }

        if (newItem) { //new item being marked complete, increment count of current 30 second interval
            this.completedItems.put(interval, count + 1);
        }

        //if no new item is being marked complete, this function just updates the TreeMap just before it displays the Bar Chart
    }

    public void createHistoryChart(String username) {
        this.updateCompletedItems(false);

        HistoryChart chart = new HistoryChart(username, completedItems);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }

}
