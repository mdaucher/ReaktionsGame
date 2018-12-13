import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class GameClient {

    private String host;
    private int port;
    private ArrayList<ActionListener> listeners = new ArrayList<>();
    private boolean connected;
    private static GameConnection connection;

    public GameClient(String host, int port){
        this.host = host;
        this.port = port;

    }

    public void start(){
        connected = true;
        try{

            connection = new GameConnection(new Socket(host, port));

            for(ActionListener listener : listeners) {
                connection.addActionListener(listener);
            }

            connection.connectionStart();

        } catch (Exception e) {
            connected = false;
        }

    }

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void stop() {
        connected = false;

        if (connection != null) {
            connection.connectionStop();
        }
    }

    public void sendMessage(String message) {
        connection.sendMessage(message);
    }


}
