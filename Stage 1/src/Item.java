import javafx.beans.property.SimpleStringProperty;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Item {
    private SimpleStringProperty name = new SimpleStringProperty("");                                    //name of file
    private SimpleStringProperty date= new SimpleStringProperty("");;                                    //Upload date
    private FileSize fileSize = new FileSize();             //File size
    private SimpleStringProperty itemType = new SimpleStringProperty("");;                                //Object type in table
    private SimpleStringProperty fileType = new SimpleStringProperty("");
    private File path;                                      //path to the file
    private SimpleStringProperty itemPreview = new SimpleStringProperty("fffffa");
    private SimpleStringProperty pre = new SimpleStringProperty("sass");



    class FileSize {
        private long size;

        private final long KB = 1024;
        private final long MB = KB * KB;
        private final long GB = MB * KB;

        @Override
        public String toString() {
            if (size <= this.KB) {
                return "1 KB";
            }
            if (size <= this.MB) {
                return formatFileSize(size, this.KB) + " KB";
            }
            if (size <= this.GB) {
                return formatFileSize(size, this.MB) + " MB";
            }
            return formatFileSize(size, this.GB) + " GB";
        }

        private String formatFileSize(long size, long format) {
            String string = Double.toString((double) size / format);
            return string.substring(0, string.indexOf(".") + 2);
        }
    }

    Item(File filePath, String itemType) {
        setPath(filePath);
        setItemType(itemType);
        String name = path.getName();
        setName(name.substring(0,name.lastIndexOf(".")));
        setFileType(name.substring(name.lastIndexOf(".")+1,name.length()));
        setFileSize();
        setDate();
    }
    public String getFileType() {
        return fileType.get();
    }

    public void setFileType(String fileType) {

        this.fileType.set(fileType);
    }

    void setPath(File filePath) {
        this.path = filePath;
    }

    File getPath() {
        return this.path;
    }

    public void setName(String name) {

        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    private void setDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        this.date.set(LocalDateTime.now().format(dateTimeFormatter));
    }

    public String getDate() {
        return this.date.get();
    }

    public void setItemType(String itemType) {
        this.itemType.set(itemType);
    }

    public String getItemType() {
        return itemType.get();
    }

    public void setFileSize() {
        fileSize.size = path.length();
    }

    public String getFileSize() {
        return fileSize.toString();
    }

}
