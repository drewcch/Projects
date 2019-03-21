import javafx.application.Platform;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserThread extends Thread {
    BufferedReader file;
    StringBuffer buffer;
    int userIndex;

    public UserThread (int userIndex) {
        try {
            this.userIndex = userIndex+1;
            StringBuffer filename = new StringBuffer("./inputs/");
            String userfile = "USER" + Integer.toString(this.userIndex);
            filename.append(userfile);
            file = new BufferedReader(new FileReader(filename.toString()));
            buffer = new StringBuffer();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean read_input() {
        try {
            String line = file.readLine();
            if (line != null) {
                buffer.setLength(0);
                buffer.append(line);
                return true;
            }
            else {
                return false;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void run() {
        while (read_input()) {
            String line = buffer.toString();
            if (line.startsWith(".save")) {
                FileInfo filedata = new FileInfo();
                filedata.fileLength = 0;
                filedata.diskNumber = Main.diskResource.request();
                filedata.startingSector = Main.diskManager.getNextFreeSector(Main.disks[filedata.diskNumber].sectors, filedata.diskNumber);

                final String fileName = line.split("\\s+")[1];
                final int initDisk = filedata.diskNumber+1;

                //Displays name of the file the user is creating on the disk on the GUI
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        switch (initDisk) {
                            case 1:
                                Main.diskWriteLabel1.setText(Main.diskWriteLabel1.getText() + "USER " + userIndex + " creating file " + fileName + "\n");
                                break;
                            default:
                                Main.diskWriteLabel2.setText(Main.diskWriteLabel2.getText() + "USER " + userIndex + " creating file " + fileName + "\n");
                        }
                    }
                });

                read_input();
                String subline = buffer.toString();
                while (!subline.startsWith(".end")) {
                    try {
                        Main.disks[filedata.diskNumber].write(filedata.fileLength + filedata.startingSector, buffer);

                        final int sector = filedata.fileLength + filedata.startingSector;
                        final int disk = filedata.diskNumber+1;
                        final String message = buffer.toString();

                        //Displays what data is being written onto which disk and sector by a user on the GUI
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                switch (disk) {
                                    case 1:
                                        Main.diskWriteLabel1.setText(Main.diskWriteLabel1.getText() + "USER " + userIndex + " writing " + message + " to Sector " + sector + "\n");
                                        break;
                                    default:
                                        Main.diskWriteLabel2.setText(Main.diskWriteLabel2.getText() + "USER " + userIndex + " writing " + message + " to Sector " + sector + "\n");
                                }
                            }
                        });

                        filedata.fileLength++;
                        Thread.sleep(200);  //sleep after writing to disk
                        read_input();
                        subline = buffer.toString();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = filedata.startingSector; i < filedata.startingSector+filedata.fileLength; i++) {
                    Main.diskManager.directoryManager.enter(Main.disks[filedata.diskNumber].sectors[i].toString(), filedata);
                }

                Main.diskResource.release(filedata.diskNumber);
            }
            else {
                FileInfo printdata = new FileInfo();
                String file = line.split("\\s+")[1];
                printdata = Main.diskManager.directoryManager.lookup(file);

                PrintJobThread print = new PrintJobThread(printdata, userIndex, file);
                print.start();
            }

        }
    }

}