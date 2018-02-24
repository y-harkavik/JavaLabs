import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Item {
    private String name;            //name of file
    private String date;            //Upload date
    private FileSize fileSize;      //File size
    private String itemType;        //Object type in table
    private File path;              //path to the file

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
            string.substring(0, string.indexOf(".") + 2);
            return string;
        }
    }

    Item(File filePath,String itemType) {
        setPath(filePath);
        setItemType(itemType);
        setName(path.getName());
        setFileSize();
        setDate();
    }

    void setPath(File filePath) {
        this.path = filePath;
    }

    File getPath() {
        return this.path;
    }
    public void setName(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }

    private void setDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        this.date = LocalDateTime.now().format(dateTimeFormatter);
    }

    public String getDate() {
        return this.date;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemType() {
        return itemType;
    }

    public void setFileSize() {
        fileSize.size = path.length();
    }

    public String getFileSize() {
        return fileSize.toString();
    }

}
