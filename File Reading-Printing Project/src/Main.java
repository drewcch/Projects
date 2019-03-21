import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.layout.RowConstraints;


class Printer {

    void write_output (StringBuffer buffer, int printerIndex, String fileName) {
        BufferedWriter output = null;
        FileWriter write = null;
        try {
            StringBuffer filename = new StringBuffer("./outputs/");
            String printerfile = "PRINTER" + Integer.toString(printerIndex+1);
            filename.append(printerfile);
            File file = new File(filename.toString());

            if (!file.exists()) {
                file.createNewFile();
            }

            write = new FileWriter(file.getAbsoluteFile(), true);
            output = new BufferedWriter(write);
            output.write(buffer.toString());
            output.write("\n");

            final int printer = printerIndex+1;
            final String message = buffer.toString();

            //Displays what file data is printed on which printer on the GUI
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    switch (printer)
                        {
                            case 1:
                                Main.printLabel1.setText(Main.printLabel1.getText() + "Printing " + message + "\n");
                                break;
                            case 2:
                                Main.printLabel2.setText(Main.printLabel2.getText() + "Printing " + message + "\n");
                                break;
                            default:
                                Main.printLabel3.setText(Main.printLabel3.getText() + "Printing " + message + "\n");
                        }
                }
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                output.close();
                write.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void check_exists (int printerIndex) {
        StringBuffer filename = new StringBuffer("./outputs/");
        String printerfile = "PRINTER" + Integer.toString(printerIndex+1);
        filename.append(printerfile);
        File file = new File(filename.toString());

        if (file.exists()) {
            file.delete();
        }
    }

}


class Disk {
    static final int NUM_SECTORS = 1024;
    StringBuffer sectors[] = new StringBuffer[NUM_SECTORS];

    void write (int sector, StringBuffer data) {
        sectors[sector] = new StringBuffer(data.toString());
    }

    void read (int sector, StringBuffer data) {
        data.append(sectors[sector].toString());
    }
}


class DiskManager {
    Hashtable<Integer,Integer> manager = new Hashtable<Integer,Integer>(); //Hashtable mapping each disk index to its starting free sector index
    DirectoryManager directoryManager = new DirectoryManager();

    DiskManager() {
        manager.put(0,0);
        manager.put(1,0);
    }

    int getNextFreeSector(StringBuffer[] sectors, int diskNum) {
        int sectorIndex = manager.get(diskNum);
        while (sectors[sectorIndex] != null) {
            sectorIndex++;
        }
        manager.put(diskNum,sectorIndex);
        return sectorIndex;
    }

}


class DirectoryManager {
    Hashtable<String, FileInfo> T = new Hashtable<String, FileInfo>();

    void enter(String key, FileInfo file) {
        T.put(key, file);
    }

    //checks if filename is in the saved string keys of the hashtable
    FileInfo lookup(String key) {
        String[] keys = T.keySet().toArray(new String[T.keySet().size()]);
        for (int i = 0; i < keys.length; i++) {
            if (keys[i].contains(key)) {
                return T.get(keys[i]);
            }
        }
        return null;
    }
}


class FileInfo
{
    int diskNumber;
    int startingSector;
    int fileLength;

}


class ResourceManager
{
    boolean isFree[];
    ResourceManager(int numberOfItems)
    {
        isFree = new boolean[numberOfItems];
        for (int i=0; i<isFree.length; ++i)
            isFree[i] = true;
    }
    synchronized int request()
    {
        while (true)
        {
            try {
                for (int i = 0; i < isFree.length; ++i)
                    if ( isFree[i] )
                    {
                        isFree[i] = false;
                        return i;
                    }
                this.wait(); // block until someone releases a Resource
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    synchronized void release( int index )
    {
        isFree[index] = true;
        this.notify(); // let a waiting thread run
    }
}


public class Main extends Application{
    public static final int NUMBER_OF_USERS = 4;
    public static final int NUMBER_OF_DISKS = 2;
    public static final int NUMBER_OF_PRINTERS = 3;

    public static UserThread [] users = new UserThread[NUMBER_OF_USERS];
    public static Disk [] disks = new Disk[NUMBER_OF_DISKS]; //{new Disk(), new Disk()};
    public static Printer [] printers = new Printer[NUMBER_OF_PRINTERS]; //{new Printer(), new Printer(), new Printer()};

    public static DiskManager diskManager = new DiskManager();

    public static ResourceManager diskResource = new ResourceManager(2);
    public static ResourceManager printerResource = new ResourceManager(3);

    public static Label printLabel1 = new Label("");
    public static Label printLabel2 = new Label("");
    public static Label printLabel3 = new Label("");

    public static Label diskWriteLabel1 = new Label("");
    public static Label diskReadLabel1 = new Label("");

    public static Label diskWriteLabel2 = new Label("");
    public static Label diskReadLabel2 = new Label("");

    @Override
    public void start(Stage stage) {
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);

        ScrollPane scrollPrint1 = new ScrollPane();
        scrollPrint1.setPrefViewportWidth(140);
        scrollPrint1.setContent(printLabel1);

        ScrollPane scrollPrint2 = new ScrollPane();
        scrollPrint2.setPrefViewportWidth(140);
        scrollPrint2.setContent(printLabel2);

        ScrollPane scrollPrint3 = new ScrollPane();
        scrollPrint3.setPrefViewportWidth(140);
        scrollPrint3.setContent(printLabel3);

        ScrollPane scrollWriteDisk1 = new ScrollPane();
        scrollWriteDisk1.setPrefViewportWidth(240);
        scrollWriteDisk1.setContent(diskWriteLabel1);

        ScrollPane scrollWriteDisk2 = new ScrollPane();
        scrollWriteDisk2.setPrefViewportWidth(240);
        scrollWriteDisk2.setContent(diskWriteLabel2);

        ScrollPane scrollReadDisk1 = new ScrollPane();
        scrollReadDisk1.setPrefViewportWidth(360);
        scrollReadDisk1.setContent(diskReadLabel1);

        ScrollPane scrollReadDisk2 = new ScrollPane();
        scrollReadDisk2.setPrefViewportWidth(360);
        scrollReadDisk2.setContent(diskReadLabel2);

        Button btn = new Button();
        btn.setText("Start Program");
        btn.setStyle("-fx-text-fill: linear-gradient(from 0% 0% to 100% 100%, red, green); -fx-font-size: 18px; -fx-font-weight: bold;");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                btn.setDisable(true);
                btn.setVisible(false);

                Label printHeading1 = new Label("PRINTER 1");
                printHeading1.setStyle("-fx-text-fill: red; -fx-font-size: 18px; -fx-font-weight: bold;");
                printHeading1.setMaxWidth(Double.MAX_VALUE);
                printHeading1.setAlignment(Pos.CENTER);

                Label printHeading2 = new Label("PRINTER 2");
                printHeading2.setStyle("-fx-text-fill: darkRed; -fx-font-size: 18px; -fx-font-weight: bold;");
                printHeading2.setMaxWidth(Double.MAX_VALUE);
                printHeading2.setAlignment(Pos.CENTER);

                Label printHeading3 = new Label("PRINTER 3");
                printHeading3.setStyle("-fx-text-fill: indianRed; -fx-font-size: 18px; -fx-font-weight: bold;");
                printHeading3.setMaxWidth(Double.MAX_VALUE);
                printHeading3.setAlignment(Pos.CENTER);

                Label diskHeading1 = new Label("DISK 1");
                diskHeading1.setStyle("-fx-text-fill: green; -fx-font-size: 18px; -fx-font-weight: bold;");
                diskHeading1.setMaxWidth(Double.MAX_VALUE);
                diskHeading1.setAlignment(Pos.CENTER);

                Label writeHeading1 = new Label("WRITING");
                writeHeading1.setStyle("-fx-text-fill: darkSeaGreen; -fx-font-size: 14px; -fx-font-weight: bold;");
                writeHeading1.setMaxWidth(Double.MAX_VALUE);
                writeHeading1.setAlignment(Pos.CENTER);

                Label readHeading1 = new Label("READING");
                readHeading1.setStyle("-fx-text-fill: darkGreen; -fx-font-size: 14px; -fx-font-weight: bold;");
                readHeading1.setMaxWidth(Double.MAX_VALUE);
                readHeading1.setAlignment(Pos.CENTER);

                Label diskHeading2 = new Label("DISK 2");
                diskHeading2.setStyle("-fx-text-fill: green; -fx-font-size: 18px; -fx-font-weight: bold;");
                diskHeading2.setMaxWidth(Double.MAX_VALUE);
                diskHeading2.setAlignment(Pos.CENTER);

                Label writeHeading2 = new Label("WRITING");
                writeHeading2.setStyle("-fx-text-fill: darkSeaGreen; -fx-font-size: 14px; -fx-font-weight: bold;");
                writeHeading2.setMaxWidth(Double.MAX_VALUE);
                writeHeading2.setAlignment(Pos.CENTER);

                Label readHeading2 = new Label("READING");
                readHeading2.setStyle("-fx-text-fill: darkGreen; -fx-font-size: 14px; -fx-font-weight: bold;");
                readHeading2.setMaxWidth(Double.MAX_VALUE);
                readHeading2.setAlignment(Pos.CENTER);

                printLabel1.setStyle("-fx-text-fill: red;");
                printLabel2.setStyle("-fx-text-fill: darkRed;");
                printLabel3.setStyle("-fx-text-fill: indianRed;");

                diskWriteLabel1.setStyle("-fx-text-fill: darkSeaGreen;");
                diskWriteLabel2.setStyle("-fx-text-fill: darkSeaGreen;");

                diskReadLabel1.setStyle("-fx-text-fill: darkGreen;");
                diskReadLabel2.setStyle("-fx-text-fill: darkGreen;");

                root.add(printHeading1,0,0);
                root.add(scrollPrint1,0,1);
                root.setRowSpan(scrollPrint1,5);

                root.add(printHeading2,1,0);
                root.add(scrollPrint2,1,1);
                root.setRowSpan(scrollPrint2,5);

                root.add(printHeading3,2,0);
                root.add(scrollPrint3,2,1);
                root.setRowSpan(scrollPrint3,5);

                root.add(diskHeading1,3,0);
                root.setColumnSpan(diskHeading1,2);
                root.add(writeHeading1,3,1);
                root.add(scrollWriteDisk1, 3, 2);
                root.add(readHeading1,4,1);
                root.add(scrollReadDisk1,4,2);

                root.add(diskHeading2,3,3);
                root.setColumnSpan(diskHeading2,2);
                root.add(writeHeading2,3,4);
                root.add(scrollWriteDisk2,3,5);
                root.add(readHeading2,4,4);
                root.add(scrollReadDisk2,4,5);

                RowConstraints row0 = new RowConstraints();
                RowConstraints row1 = new RowConstraints();
                RowConstraints row2 = new RowConstraints();
                RowConstraints row3 = new RowConstraints();
                RowConstraints row4 = new RowConstraints();
                RowConstraints row5 = new RowConstraints();

                row0.setPercentHeight(10);
                row1.setPercentHeight(5);
                row2.setPercentHeight(100);
                row3.setPercentHeight(10);
                row4.setPercentHeight(5);
                row5.setPercentHeight(100);

                root.getRowConstraints().addAll(row0,row1,row2,row3,row4,row5);

                //Begins User Threading Process
                for (int i = 0; i < NUMBER_OF_USERS; i++) {
                    users[i] = new UserThread(i);
                    users[i].start(); //may not be correct
                }

            }
        });

        root.add(btn,0,1);

        Scene scene = new Scene(root);
        stage.setTitle("141OS");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        //Creates Disk array
        for (int i = 0; i < NUMBER_OF_DISKS; i++) {
            disks[i] = new Disk();
        }

        //Creates Printer array
        for (int k = 0; k < NUMBER_OF_PRINTERS; k++) {
            printers[k] = new Printer();
        }

        //Checks to see if printer files already exist and delete them if they do
        for (int j = 0; j < NUMBER_OF_PRINTERS; j++) {
            printers[j].check_exists(j);
        }

        //Starts GUI
        launch(args);

    }

}


