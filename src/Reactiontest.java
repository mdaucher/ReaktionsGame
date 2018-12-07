import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Reactiontest extends JFrame {

    private static final int RAND_RANGE = 400;

    private JButton[] button = new JButton[16];
    private javax.swing.Timer myTimer;
    private JButton readyButton = new JButton("Ready");
    private int counter = 0;


    public Reactiontest() {
        super("Reactiontest");
        this.setSize(600, 400);
        this.setLayout(new GridLayout(4, 4));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Buttons erzeugen
        for (int j = 0; j < button.length; j++) {
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

                GameClient.sendMessage("READY");


                play();
            }
        });
        this.setVisible(true);
    }


    private void play() {
        readyButton.setEnabled(false);

        try {

            while (button[w].isEnabled() == false) {
                // Buttons enablen
                button[w].setEnabled(true);
                button[w].setBackground(Color.GREEN);
                counter++;
            }

            final int tmp = w;

            // Grüne Buttons gedrückt?
            button[w].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    button[tmp].setEnabled(false);
                    button[tmp].setBackground(Color.white);
                    counter--;

                    if (counter == 0) {
                        // an den Server die info schicken das der Player wieder ready ist
                    }
                }
            });
        } catch (Exception exe) {
        }
    }

}
