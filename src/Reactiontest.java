import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Reactiontest extends JFrame {

    private static final int RAND_RANGE = 400;

    private JButton[] button = new JButton[16];
    private javax.swing.Timer myTimer;
    private JButton readyButton = new JButton("Ready");
    private int counter = 0;


    public Reactiontest (){
        super("Reactiontest");
        this.setSize(600,400);
        this.setLayout(new GridLayout(4,4));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        // Buttons erzeugen
        for(int j = 0; j < button.length; j++){
                button[j] = new JButton();
                this.add(button[j]);
                button[j].setEnabled(false);
                button[j].setBackground(Color.white);
        }

        // Ready Button erzeugen und auf Leiste legen
        JMenuBar menu = new JMenuBar();

        menu.add(readyButton);
        this.setJMenuBar(menu);

        // Aktivierung von ReadyButton
        readyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              play();
            }
        });
        this.setVisible(true);
    }



    private void play(){
        readyButton.setEnabled(false);
        new Thread(){
            public void run(){
                Random myrand = new Random();

                int i = 0;
                i = myrand.nextInt(3000)+3000;
                System.out.println("Time: " +i);

                try {
                    Thread.sleep(i);

                    int k = enaButtons();

                    counter = k;

                    for(int z = 1; z <= k;z++){
                        Random randButton = new Random();
                        int w = randButton.nextInt(16);

                        // Abfrage ob der Button bereits enabled ist, damit in dem selben Durchgang nicht der Selbe mehrmals enabled wird
                        while(button[w].isEnabled() == true){
                            w = randButton.nextInt(16);
                        }
                        // Buttons enablen
                        button[w].setEnabled(true);
                        button[w].setBackground(Color.GREEN);

                        final int tmp = w;
                        // Grüne Buttons gedrückt?
                        button[w].addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                button[tmp].setEnabled(false);
                                button[tmp].setBackground(Color.white);
                                counter--;

                                if(counter ==  0){
                                    play();
                                }
                            }
                        });
                    }
                    //readyButton.setEnabled(true);


                } catch (Exception exe) {
                }
            }
        }.start();
    }


    private int enaButtons(){
        Random rand = new Random();
        int k = rand.nextInt(4)+1;
        System.out.println("Buttons who are enabled: " +k);
        return(k);
    }
}
