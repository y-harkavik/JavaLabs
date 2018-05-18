import Communicate.Message.Response.ServerResponse.AuthenticationResponse;
import Graphics.ArchiveWindow.ArchiveWindowController;
import Graphics.AuthenticationWindow.AuthenticationWindowController;

public class Main {
    public static void main(String[] args) {
        AuthenticationWindowController authenticationWindowController = new AuthenticationWindowController();
        AuthenticationResponse authenticationResponse = authenticationWindowController.openAuthenticationWindow();
        if (authenticationResponse != null) {
            new ArchiveWindowController(authenticationWindowController.getCurrentClient(),
                    authenticationResponse.getMapOfPersonnelFiles(),
                    authenticationResponse.getAccountMap(),
                    authenticationResponse.getUserLaws()
            ).openMainWindow();
        }
    }
}
