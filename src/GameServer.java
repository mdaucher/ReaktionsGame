import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class GameServer {

    private int port;
    private int readyCount;
    private boolean running = false;

    private ArrayList<GameConnection> clients = new ArrayList<>();

    public GameServer(int port){
        this.port = port;
    }

    private ActionListener broadcastListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {

            Random rand = new Random();
            int waitTime = rand.nextInt(3000)+3000;
            System.out.println(waitTime);

            int enaButtons = rand.nextInt(4)+1;

           String messageString = null;


            for(int i = 0; i < enaButtons; i++){

                messageString = messageString + Integer.toString(rand.nextInt(16)) + ";";

            }

            // sende Nachricht an alle Clients, die dem Server derzeit bekannt sind
            for (GameConnection p : clients) {
                // Abfrage ob alle Clients schon bereit sind
                if(ae.getActionCommand() == "READY"){
                    readyCount++;
                    System.out.println("READY");
                }

                if(readyCount == clients.size()){
                    String finalMessageString = messageString;
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(waitTime);

                                p.sendMessage(finalMessageString);


                            } catch (Exception e) {
                            }
                        }
                    }.start();

                }

                // Damit die Nachricht nicht an den Sender zurückgeschickt wird
                if(!p.equals(ae.getSource())){

                    p.sendMessage(ae.getActionCommand());
                }
            }
        }
    };

    public void serverStart(){
        this.running = true;

        Thread serverThread = new Thread() {
            @Override
            public void run() {

                try(ServerSocket server = new ServerSocket(GameServer.this.port)) {

                    while (running) {
                        // Client verbindet sich auf den Server
                        Socket client = server.accept();

                        // Weitere Aktionen mit dem Client
                        // ...
                        GameConnection p = new GameConnection(client);

                        p.addActionListener(broadcastListener);

                        clients.add(p);

                    }

                }catch(Exception e){
                }
            }
        };

        serverThread.start();
    }

    public void serverStop(){
        this.running = false;
    }


    public static void main(String[] args) {
        GameServer gs = new GameServer(1234);
        gs.serverStart();
    }
}
