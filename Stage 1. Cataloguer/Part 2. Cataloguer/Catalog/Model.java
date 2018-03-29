package Catalog;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Persons.Base;
import Persons.Person;
import Persons.User;

import java.io.*;
import java.lang.String;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class that contains business logic of Catalog window.
 * @author Yauheni
 *@version 1.0
 */
public class Model {
    /** Variable that contains Data objects and allow to work with catalog structure.*/
    private Data data = new Data();
    /** Variable that contains file type in table.*/
    public static final String DOCUMENTS = "DOCUMENTS";
    /** Variable that contains file type in table.*/
    public static final String IMAGES = "IMAGES";
    /** Variable that contains file type in table.*/
    public static final String VIDEO = "VIDEO";
    /** Variable that contains file type in table.*/
    public static final String AUDIO = "AUDIO";

    /** Variable that contains error text in alert.*/
    public static final String PATH_ERROR = "File not found.";
    /** Variable that contains error text in alert.*/
    public static final String SAVE_ERROR = "Save error.";
    /** Variable that contains error text in alert.*/
    public static final String READ_ERROR = "Structure file not found.";
    /** Variable that contains error text in alert.*/
    public static final String SIZE_ERROR = "Один или более файлов не подходят по размеру.";

    /** Variable that contains extensions of added file by file type.*/
    private static final String[] DOCUMENTS_EXTENSIONS = {"*.docx", "*.doc", "*.ppt", ".pptx", "*.xlsx", "*.kwm"};
    /** Variable that contains extensions of added file by file type.*/
    private static final String[] IMAGES_EXTENSIONS = {"*.png", "*.jpg", "*.jpeg", "*.bmp"};
    /** Variable that contains extensions of added file by file type.*/
    private static final String[] VIDEO_EXTENSIONS = {"*.mkv", "*.avi", "*.mp4"};
    /** Variable that contains extensions of added file by file type.*/
    private static final String[] AUDIO_EXTENSIONS = {"*.mp3", "*.wav"};

    /** Variable that contains count accessible memory for add.*/
    private long canAdd = -1;

    /** Variable that contains base of accounts.*/
    private Base baseOfAccounts;
    /** Variable that contains current user.*/
    private Person account;

    /**
     * Return current user.
     * @return Type Person. Current user.
     * @see Person
     * @see User
     * @see Persons.Administrator
     */
    public Person getAccount() {
        return account;
    }

    /**
     * Set current user. If user type is User, set canAdd variable.
     * @param account Current user.
     */
    public void setAccount(Person account) {
        this.account = account;
        if (account instanceof User) {
            setCanAdd((User) account);
        }
    }

    /**
     * Count of accessible memory for add.
     * @return Long canAdd.
     */
    public long getCanAdd() {
        return canAdd;
    }

    /**
     * Set size of accessible memory for add.
     * @param user Current user.
     */
    public void setCanAdd(User user) {
        try {
            if (checkDateIsBefore(user.getLastUpdated())) {
                user.setAddedData(0);
                canAdd = User.SIZE_OF_DATA_10MB;
                user.setLastUpdated(getCurrentDate());
            } else {
                canAdd = User.SIZE_OF_DATA_10MB - user.getAddedData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get current date.
     * @return Current date.
     */
    LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    /**
     * Check date of last add.
     * @param userDate Last date when user added files.
     * @return True - if user added before current date. False - if user date coincides with current date.
     *
     */
    boolean checkDateIsBefore(LocalDate userDate) {
        if (userDate.isBefore(getCurrentDate())) {
            return true;
        }
        return false;
    }

    /**
     * Return base of account.
     * @return Base of accounts.
     * @see Base
     */
    public Base getBaseOfAccounts() {
        return baseOfAccounts;
    }

    /**
     * Set base of accounts.
     * @param baseOfAccounts Added base.
     *                       @see Base
     */
    public void setBaseOfAccounts(Base baseOfAccounts) {
        this.baseOfAccounts = baseOfAccounts;
    }

    /**
     * Supporting inner class for work with Data.
     */
    class Data {
        /** Variable that contains list of items with keys.*/
        private HashMap<String, ArrayList<Item>> listOfItem = new HashMap<String, ArrayList<Item>>();

        /**
         * Create a lists of items by Type in table.
         */
        Data() {
            /*listOfItem.put(DOCUMENTS, FXCollections.observableArrayList());
            listOfItem.put(IMAGES, FXCollections.observableArrayList());
            listOfItem.put(VIDEO, FXCollections.observableArrayList());
            listOfItem.put(AUDIO, FXCollections.observableArrayList());*/
            listOfItem.put(DOCUMENTS, new ArrayList<Item>());
            listOfItem.put(IMAGES, new ArrayList<Item>());
            listOfItem.put(VIDEO, new ArrayList<Item>());
            listOfItem.put(AUDIO, new ArrayList<Item>());
        }

        /**
         *Return list of all items.
         * @return HashMap of Data that contains items and keys.
         * @see Item
         */
        HashMap<String, ArrayList<Item>> getAllItems() {
            return listOfItem;
        }

        /**
         * Return item list by key.
         * @param key Type of items in table.
         * @return List of items by key.
         */
        List<Item> getItemsListByKey(String key) {
            return listOfItem.get(key);
        }

        /**
         * Set list of all items.
         * @param hashMapOfItems List of all items.
         */
        void setListOfItem(HashMap<String, ArrayList<Item>> hashMapOfItems) {
            listOfItem = hashMapOfItems;
        }

        /**
         * Add item in list.
         * @param item Added item.
         */
        void addItemInList(Item item) {
            listOfItem.get(item.getItemType()).add(item);
        }
    }

    /**
     * Set list of items by cause Data.setListOfItem
     * @param hashMapOfItems
     */
    void setList(HashMap<String, ArrayList<Item>> hashMapOfItems) {
        data.setListOfItem(hashMapOfItems);
    }

    /**
     * Add files by cause addItems.
     * @param typeOfItem Type of files in table for add.
     */
    void addFiles(String typeOfItem) {
        addItems(getPaths(typeOfItem), typeOfItem);
    }

    /**
     * Add items in Data list.
     * @param files Cheesed files.
     * @param typeOfItem Type of item in table.
     */
    void addItems(List<File> files, String typeOfItem) {
        if (files != null) {
            for (File file : files) {
                if (canAdd > 0) {
                    if (file.length() <= canAdd) {
                        data.addItemInList(new Item(file, typeOfItem));
                        canAdd -= file.length();
                        ((User) account).setAddedData(file.length());
                    } else {
                        createAlertError(SIZE_ERROR);
                    }
                } else {
                    data.addItemInList(new Item(file, typeOfItem));
                }
            }
        }
    }

    /**
     * Save list of items in file.
     */
    private void saveItemsInFile() {
        try (ObjectOutputStream saveFilesStream = new ObjectOutputStream(new FileOutputStream("structure.ser"))) {
            saveFilesStream.writeObject(data.getAllItems());
        } catch (Exception e) {
            createAlertError(SAVE_ERROR);
            e.printStackTrace();
        }
    }

    void getItemsFromFile() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("structure.ser"))) {
            setList((HashMap<String, ArrayList<Item>>) objectInputStream.readObject());
        } catch (Exception e) {
            createAlertError(Model.READ_ERROR);
        }
    }
    /**
     * Remove file in list.
     * @param removedItem Removed file.
     */
    void removeFile(Item removedItem) {
        if (removedItem != null) {
            data.getItemsListByKey(removedItem.getItemType()).remove(removedItem);
        }
    }

    /**
     * Find items in list.
     * @param searchRequest Search request
     * @param itemType Item type in table.
     * @return List of found items.
     */
    List<Item> findNeededItems(String searchRequest, String itemType) {
        List<Item> resultOfSearch = new ArrayList<Item>();
        if (!getItems(itemType).isEmpty()) {
            for (Item findItem : data.getItemsListByKey(itemType)) {
                if (findItem.getName().toLowerCase().contains(searchRequest.toLowerCase())) {
                    resultOfSearch.add(findItem);
                }
            }
        }
        return resultOfSearch;
    }

    /**
     * Get list of items by key.
     * @param type Type of files.
     * @return List of items
     */
    List<Item> getItems(String type) {
        return data.getItemsListByKey(type);
    }

    /**
     * Choose files and get paths of files.
     * @param neededFiles Extensions of needed files.
     * @return List of
     */
    List<File> getPaths(String neededFiles) {
        FileChooser fileChooser = new FileChooser();
        setFilter(fileChooser, neededFiles);
        return fileChooser.showOpenMultipleDialog(new Stage());
    }

    /**
     * Set extensions of needed files.
     * @param fileChooser Causing fileChooser.
     * @param neededFiles Extensions of needed files.
     */
    private void setFilter(FileChooser fileChooser, String neededFiles) {
        switch (neededFiles) {
            case DOCUMENTS:
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Documents", DOCUMENTS_EXTENSIONS)
                );
                break;
            case IMAGES:
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Images", IMAGES_EXTENSIONS)
                );
                break;
            case VIDEO:
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Video", VIDEO_EXTENSIONS)
                );
                break;
            case AUDIO:
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Audio", AUDIO_EXTENSIONS)
                );
                break;
        }
    }

    /**
     * Create alert error.
     * @param alertMessage Alert message
     */
    public static void createAlertError(String alertMessage) {
        Alert alertFileNotFound = new Alert(Alert.AlertType.ERROR);
        alertFileNotFound.setTitle("Hello amigo");
        alertFileNotFound.setContentText(alertMessage);
        alertFileNotFound.showAndWait();
    }

    /**
     * Save items and base in file.
     */
    public void saveProgramInformation() {
        if(baseOfAccounts!=null) {
            saveItemsInFile();
            getBaseOfAccounts().saveBase();
        }
    }
}


