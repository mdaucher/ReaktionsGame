import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Reactiontest extends JFrame {

    private JButton[] button = new JButton[16];
    private javax.swing.Timer myTimer;
    private JButton readyButton = new JButton("Ready");
    private int counter = 0;


    public Reactiontest() {
        super("Reactiongame");
        this.setSize(600, 400);
        this.setLayout(new GridLayout(4, 4));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GameClient gc = new GameClient("localhost",1234);

        gc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println(actionEvent.getActionCommand());

                getMessage(actionEvent.getActionCommand());
            }
        });

        gc.start();

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

                gc.sendMessage("READY");
                readyButton.setEnabled(false);
            }
        });
        this.setVisible(true);
    }

    public void getMessage (String finalMessageString){


        String[] split = finalMessageString.split(";");
        for(int i = 0; i<split.length; i++) {

            // Buttons enablen und gruen setzten welche geklickt werden sollen
            while (button[Integer.parseInt(split[i])].isEnabled() == false) {
                // Buttons enablen
                button[Integer.parseInt(split[i])].setEnabled(true);
                button[Integer.parseInt(split[i])].setBackground(Color.GREEN);
                counter++;final int tmp = Integer.parseInt(split[i]);

                // Grüne Buttons gedrückt?
                button[Integer.parseInt(split[i])].addActionListener(new ActionListener() {
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

            }

        }
    }

}
