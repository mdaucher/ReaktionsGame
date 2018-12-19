import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class GameConnection {

    private boolean running = false;
    private Socket connection;

    private DataInputStream receive; // Daten vom Server
    private DataOutputStream transmit; // Daten an den Server

    private ArrayList <ActionListener> listener = new ArrayList<>();

    public GameConnection(Socket player){
        this.connection = player;
    }

    public void connectionStart(){
        this.running = true;

        try {
            this.receive = new DataInputStream(this.connection.getInputStream());
            this.transmit = new DataOutputStream(this.connection.getOutputStream());
        } catch (Exception e) {
        }

        Thread t = new Thread(){
            @Override
            public void run() {
                while(running){
                    try {
                        String message = receive.readUTF();
                        // verarbeite Nachricht
                        notifyListener(message);

                    } catch (IOException e) {
                    }
                }
            }
        };

        t.start();
    }

    public void connectionStop(){
        this.running = false;
    }

    public void sendMessage(String message){
        if(this.transmit != null){
            try {
                transmit.writeUTF(message);
                transmit.flush();

            } catch (IOException e) {
            }
        }
    }

    public void notifyListener(String message){
        for(ActionListener l : listener){
            l.actionPerformed(new ActionEvent(this, 0, message));
        }
    }

    public void addActionListener(ActionListener l){
        this.listener.add(l);
    }

}
