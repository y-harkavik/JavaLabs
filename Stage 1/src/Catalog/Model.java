package Catalog;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import person.Base;
import person.Person;
import person.User;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.lang.String;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Model {

    private Data data = new Data();

    public static final String DOCUMENTS = "DOCUMENTS";
    public static final String IMAGES = "IMAGES";
    public static final String VIDEO = "VIDEO";
    public static final String AUDIO = "AUDIO";

    public static final String PATH_ERROR = "File not found.";
    public static final String SAVE_ERROR = "Save error.";
    public static final String READ_ERROR = "Structure file not found.";
    public static final String SIZE_ERROR = "size error";

    private static final String[] DOCUMENTS_EXTENSIONS = {"*.docx", "*.doc", "*.ppt", ".pptx", "*.xlsx", "*.kwm"};
    private static final String[] IMAGES_EXTENSIONS = {"*.png", "*.jpg", "*.jpeg", "*.bmp"};
    private static final String[] VIDEO_EXTENSIONS = {"*.mkv", "*.avi", "*.mp4"};
    private static final String[] AUDIO_EXTENSIONS = {"*.mp3", "*.wav"};

    private long canAdd = -1;

    private Base baseOfAccounts;
    private Person account;

    public Person getAccount() {
        return account;
    }

    public void setAccount (Person account) {
        this.account = account;
        if (account instanceof User) {
            setCanAdd((User) account);
        }
    }

    public long getCanAdd() {
        return canAdd;
    }

    public void setCanAdd(User user) {
        try {
            if (checkDateIsBefore(user.getLastUpdated())) {
                user.setAddedData(0);
                canAdd = User.SIZE_OF_DATA_10MB;
                user.setLastUpdated(getCurrentDate());
            } else {
                canAdd = User.SIZE_OF_DATA_10MB-user.getAddedData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    boolean checkDateIsBefore(LocalDate userDate) {
        if (userDate.isBefore(getCurrentDate())) {
            return true;
        }
        return false;
    }

    public Base getBaseOfAccounts() {
        return baseOfAccounts;
    }

    public void setBaseOfAccounts(Base baseOfAccounts) {
        this.baseOfAccounts = baseOfAccounts;
    }

    class Data {
        private HashMap<String, ArrayList<Item>> listOfItem = new HashMap<String, ArrayList<Item>>();

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

        HashMap<String, ArrayList<Item>> getAllItems() {
            return listOfItem;
        }

        List<Item> getItemsListByKey(String key) {
            return listOfItem.get(key);
        }

        void setListOfItem(HashMap<String, ArrayList<Item>> hashMapOfItems) {
            listOfItem = hashMapOfItems;
        }

        void addItemInList(Item item) {
            listOfItem.get(item.getItemType()).add(item);
        }
    }

    void setList(HashMap<String, ArrayList<Item>> hashMapOfItems) {
        data.setListOfItem(hashMapOfItems);
    }

    void addFiles(String typeOfItem) {
        addItems(getPaths(typeOfItem), typeOfItem);
    }

    void addItems(List<File> files, String typeOfItem) {
        if (files != null) {
            for (File file : files) {
                if(canAdd>0) {
                    if(file.length()<=canAdd) {
                        data.addItemInList(new Item(file, typeOfItem));
                        canAdd-=file.length();
                        ((User)account).setAddedData(file.length());
                    } else {
                        createAlertError(SIZE_ERROR);
                    }
                } else {
                    data.addItemInList(new Item(file, typeOfItem));
                }
            }
        }
    }

    void saveItemsInFile() {
        try (ObjectOutputStream saveFilesStream = new ObjectOutputStream(new FileOutputStream("structure.ser"))) {
            saveFilesStream.writeObject(data.getAllItems());
        } catch (Exception e) {
            createAlertError(SAVE_ERROR);
            e.printStackTrace();
        }
    }

    void removeFile(Item removedItem) {
        if (removedItem != null) {
            data.getItemsListByKey(removedItem.getItemType()).remove(removedItem);
        }
    }

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

    List<Item> getItems(String type) {
        return data.getItemsListByKey(type);
    }

    List<File> getPaths(String neededFiles) {
        FileChooser fileChooser = new FileChooser();
        setFilter(fileChooser, neededFiles);
        return fileChooser.showOpenMultipleDialog(new Stage());
    }

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

    public static void createAlertError(String alertMessage) {
        Alert alertFileNotFound = new Alert(Alert.AlertType.ERROR);
        alertFileNotFound.setTitle("Hello amigo");
        alertFileNotFound.setContentText(alertMessage);
        alertFileNotFound.showAndWait();
    }
}


