import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.lang.String;
import java.io.File;


public class Logic {

    public static final String DOCUMENTS = "DOCUMENTS";
    public static final String IMAGES = "IMAGES";
    public static final String VIDEO = "VIDEO";
    public static final String AUDIO = "AUDIO";

    private static final String[] DOCUMENTS_EXTENSIONS = {"*.docx","*.doc","*.ppt",".pptx","*.xlsx"};
    private static final String[] IMAGES_EXTENSIONS = {"*.png","*.jpg","*.jpeg","*.bmp"};
    private static final String[] VIDEO_EXTENSIONS = {"*.mkv","*.avi","*.mp4"};
    private static final String[] AUDIO_EXTENSIONS = {"*.mp3","*.wav"};

    File getFile(String neededFiles) {
        FileChooser fileChooser= new FileChooser();
        setFilter(fileChooser,neededFiles);
        return fileChooser.showOpenDialog(new Stage());
    }

    private void setFilter(FileChooser fileChooser,String neededFiles) {
        switch(neededFiles) {
            case DOCUMENTS: fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Documents",DOCUMENTS_EXTENSIONS)
            );
            case IMAGES: fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Images",IMAGES_EXTENSIONS)
            );
            case VIDEO: fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Video",VIDEO_EXTENSIONS)
            );
            case AUDIO: fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Audio",AUDIO_EXTENSIONS)
            );
        }
    }
}


