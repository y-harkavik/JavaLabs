import java.util.Date;

public class Item {
    private String name;
    private Date date;
    private FileSize fileSize;
    private String itemType;

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

    public void setName(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemType() {
        return itemType;
    }

    public void setFileSize(long  fileSize) {
    }

    public String getFileSize() {
        return fileSize.toString();
    }
}
