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
                return Double.toString((double) size / this.KB) + " KB";
            }
            if (size <= this.GB) {
                return Double.toString((double) size / this.MB) + " MB";
            }
            return Double.toString((double) size / this.GB) + " GB";
        }
    }
}
