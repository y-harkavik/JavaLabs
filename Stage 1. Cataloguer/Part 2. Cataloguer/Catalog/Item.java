package Catalog;

import javafx.beans.property.SimpleStringProperty;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * Class that contains information of added file and work with them.
 * @author Yauheni
 *@version 1.0
 */
public class Item implements Serializable {
    /** SimpleStringProperty that contains name of file.*/
    private transient SimpleStringProperty name = new SimpleStringProperty("");
    /** SimpleStringProperty that contains date, when file was added.*/
    private transient SimpleStringProperty date = new SimpleStringProperty("");
    /** SimpleStringProperty that contains type of file in table.*/
    private transient SimpleStringProperty itemType = new SimpleStringProperty("");
    /** SimpleStringProperty that contains type of file.*/
    private transient SimpleStringProperty fileType = new SimpleStringProperty("");
    /** Variable of FileSize class, that works with size of file.*/
    private FileSize fileSize = new FileSize();
    /**
     * Variable that contains path of file.
     * @see Item.FileSize
     */
    private File path;

    class FileSize implements Serializable {
        /** Variable that contains size of file.*/
        private long size;

        /** Variable that contains size of Kilobyte.*/
        private final long KB = 1024;
        /** Variable that contains size of Megabyte.*/
        private final long MB = KB * KB;
        /** Variable that contains size of Gigabyte.*/
        private final long GB = MB * KB;

        @Override
        /**
         * Override method that return result of formatFileSize.
         * @see FileSize.formatFileSize
         * @see FileSize.KB
         * @see FileSize.MB
         * @see FileSize.GB
         * @return Return string that contains formatted size.
         */
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

        /**
         *
         * @param size Size of file.
         * @param format Constant to which we format size.
         * @return String of formatted size.
         */
        private String formatFileSize(long size, long format) {
            String string = Double.toString((double) size / format);
            return string.substring(0, string.indexOf(".") + 2);
        }
    }

    /**
     * Constructor for Item.
     * @param filePath Path of added file.
     * @param itemType Type of Item in table.
     *                 @see Model.AUDIO
     *                 @see Model.IMAGES
     *                 @see Model.DOCUMENTS
     *                 @see Model.VIDEO
     */
    Item(File filePath, String itemType) {
        setPath(filePath);
        setItemType(itemType);
        String name = path.getName();
        setName(name.substring(0, name.lastIndexOf(".")));
        setFileType(name.substring(name.lastIndexOf(".") + 1, name.length()));
        setFileSize();
        setDate();
        //setPreview("preview");
    }

    /**
     * Serialize item in file.
     * @param stream Output stream that connect with structure file.
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeUTF(getName());
        stream.writeUTF(getDate());
        stream.writeUTF(getItemType());
        stream.writeUTF(getFileType());
    }

    /**
     * Method that read serialized item from file.
     * @param stream Input stream that connect with structure file.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        name = new SimpleStringProperty(stream.readUTF());
        date = new SimpleStringProperty(stream.readUTF());
        itemType = new SimpleStringProperty(stream.readUTF());
        fileType = new SimpleStringProperty(stream.readUTF());
    }
    /**
     * Used to get file type.
     * @return String of file type.
     */
    public String getFileType() {
        return fileType.get();
    }

    /**
     * Set value of SimpleStringProperty fileType.
     * @param fileType Type of file.
     *
     */
    public void setFileType(String fileType) {
        this.fileType.set(fileType);
    }

    /**
     * Set file path.
     * @param filePath Path to file.
     */
    void setPath(File filePath) {
        this.path = filePath;
    }

    /**
     * Return file path.
     * @return File path.
     */
    File getPath() {
        return this.path;
    }

    /**
     * Set name of file.
     * @param name Established file name.
     */
    public void setName(String name) {

        this.name.set(name);
    }

    /**
     * Return file name.
     * @return Name of file.
     */
    public String getName() {
        return name.get();
    }

    /**
     * Set Date when file was added.
     */
    private void setDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        this.date.set(LocalDateTime.now().format(dateTimeFormatter));
    }

    /**
     * Get date when file was added.
     * @return String representation of Date.
     */
    public String getDate() {
        return this.date.get();
    }

    /**
     * Set item type in table.
     * @param itemType Type of item in table.
     */
    public void setItemType(String itemType) {
        this.itemType.set(itemType);
    }

    /**
     * Return file type in table.
     * @return String of file type in table.
     */
    public String getItemType() {
        return itemType.get();
    }

    /**
     * Set size of file.
     */
    public void setFileSize() {
        fileSize.size = path.length();
    }

    /**
     * Return size of file
     * @return Size of file.
     * @see Item.FileSize
     */
    public FileSize getFileSize() {
        return fileSize;
    }
}
