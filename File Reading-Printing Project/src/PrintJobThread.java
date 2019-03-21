import javafx.application.Platform;

public class PrintJobThread extends Thread {
    FileInfo file;
    int userIndex;
    String fileName;

    PrintJobThread (FileInfo printFile, int userIndex, String fileName) {
        file = printFile;
        this.userIndex = userIndex;
        this.fileName = fileName;
    }

    public void run() {
        int printerIndex = Main.printerResource.request();

        final int printer = printerIndex + 1;

        //Displays the file name being printed on the GUI
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                switch(printer) {
                    case 1:
                        Main.printLabel1.setText(Main.printLabel1.getText() + "Now printing file " + fileName + "\n");
                        break;
                    case 2:
                        Main.printLabel2.setText(Main.printLabel2.getText() + "Now printing file " + fileName + "\n");
                        break;
                    default:
                        Main.printLabel3.setText(Main.printLabel3.getText() + "Now printing file " + fileName + "\n");
                }
            }
        });

        for (int i = file.startingSector; i < file.startingSector+file.fileLength; i++) {
            try {
                StringBuffer buffer = new StringBuffer();
                Main.disks[file.diskNumber].read(i,buffer);

                final int sector = i;
                final int disk = file.diskNumber+1;
                final String message = buffer.toString();

                //Displays what data from which disk and sector the user is reading from onto the GUI
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        switch (disk) {
                            case 1:
                                Main.diskReadLabel1.setText(Main.diskReadLabel1.getText() + "USER " + userIndex + " reading " + message + " at Sector " + sector + ", sending to Printer " + printer + "\n");
                                break;
                            default:
                                Main.diskReadLabel2.setText(Main.diskReadLabel2.getText() + "USER " + userIndex + " reading " + message + " at Sector " + sector + ", sending to Printer " + printer + "\n");
                                break;
                        }
                    }
                });

                Thread.sleep(200);  //sleep after reading from a files
                Main.printers[printerIndex].write_output(buffer, printerIndex, fileName);
                Thread.sleep(2750);  //sleep after printing to a file
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Main.printerResource.release(printerIndex);
    }
}