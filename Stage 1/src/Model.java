import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.lang.String;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Model {
    private Data data = new Data();

    public static final String DOCUMENTS = "DOCUMENTS";
    public static final String IMAGES = "IMAGES";
    public static final String VIDEO = "VIDEO";
    public static final String AUDIO = "AUDIO";

    private static final String[] DOCUMENTS_EXTENSIONS = {"*.docx", "*.doc", "*.ppt", ".pptx", "*.xlsx"};
    private static final String[] IMAGES_EXTENSIONS = {"*.png", "*.jpg", "*.jpeg", "*.bmp"};
    private static final String[] VIDEO_EXTENSIONS = {"*.mkv", "*.avi", "*.mp4"};
    private static final String[] AUDIO_EXTENSIONS = {"*.mp3", "*.wav"};

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

        List<Item> getItemsList(String key) {
            return listOfItem.get(key);
        }

        void addItemInList(Item item) {
            listOfItem.get(item.getItemType()).add(item);
        }
    }

    List<Item> addFiles(String typeOfItem) {
        saveFiles(getPaths(typeOfItem), typeOfItem);
        return getItems(typeOfItem);
    }

    List<Item> getItems(String type) {
        return data.getItemsList(type);
    }

    void saveFiles(List<File> files, String typeOfItem) {
        if (files!=null) {
            for (File file : files) {
                data.addItemInList(new Item(file, typeOfItem));
            }
        }
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
            case IMAGES:
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Images", IMAGES_EXTENSIONS)
                );
            case VIDEO:
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Video", VIDEO_EXTENSIONS)
                );
            case AUDIO:
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Audio", AUDIO_EXTENSIONS)
                );
        }
    }
}


