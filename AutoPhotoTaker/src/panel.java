import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.Robot;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class panel extends JPanel {
    String pos = "no";
    static String fileloc = "";
    static int number = 0;
    JButton button = new JButton("Start/stop photo taker");
    JLabel j = new JLabel("Delay:");
    JLabel j2 = new JLabel("Mode: "+"Off");
    JLabel j3 = new JLabel("Absolute Path:");
    JLabel j4 = new JLabel("Frame per milisecond:");
    JButton play = new JButton("Play video");
    JTextField FileNameField = new JTextField(10);
    JTextField fps = new JTextField("17",3 );
    Timer time = new Timer(Integer.parseInt(fps.getText()), this::ActionPerformed);
    Timer times = new Timer(Integer.parseInt(fps.getText()), this::playing);
    moviepanel m = new moviepanel();


    public void panel() {

        button.addActionListener(this::ButtonPerformed);
        play.addActionListener(this::play);
        add(button);
        add(j);
        add(j2);
        add(j3);
        add(FileNameField);
        add(play);
        add(j4);
        add(fps);
        setSize(300,200);
        setVisible(true);
    }
    public void ButtonPerformed(ActionEvent e) {

        if (time.isRunning()) {
            time.stop();
            fileloc = "";
            j2.setText("Mode: Off");
        }
        else {
                times.stop();
                File loc = new File(FileNameField.getText());
                loc.mkdirs();
                fileloc = loc.getAbsolutePath();
                j2.setText("Mode: On");
                time.start();
        }


    }

    public void play(ActionEvent e) {
        if (!times.isRunning()) {
            time.stop();
            int fpss = Integer.parseInt(fps.getText());
            System.out.println(fpss);
            times = new Timer(fpss, this::playing);
            fileloc = fileloc + "\\" + FileNameField.getText();
            m.moviepanel();
            j2.setText("Mode: On");
            times.start();
            number = 1;
        } else {
            times.stop();
            time.stop();
        }
    }

    public void playing(ActionEvent e) {
        try {

            if (number >= 2)
                m.delt();
            File f = new File(fileloc + File.separator + "SST" + number + ".png");
            if (f.canRead()) {
                System.out.println(fileloc + File.separator + "SST" + number + ".png");
                BufferedImage img = ImageIO.read(f);
                m.addimg(img);
            }
            number++;
        } catch (IOException de) {
            de.printStackTrace();
        }
        catch (NullPointerException ds) {
            m.close();
        }
    }



    public void ActionPerformed(ActionEvent e) {
        time.setDelay(Integer.parseInt(fps.getText()));
        pos = "no";
        savephoto();
    }

    public void savephoto() {
        while (pos.equals("no")) {
            number++;
            File f = new File( fileloc + File.separator + "SST"+number+".png");

            if (!(f.canRead())) {
                try {
                    Robot robot = new Robot();
                    String format = "png";
                    String fileName = fileloc + File.separator + "SST"+number+"."+ format;

                    Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                    BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
                    ImageIO.write(screenFullImage, format, new File(fileName));
                    pos = "yes";
                } catch (IOException | AWTException ex) {
                    ex.printStackTrace();
                }

            }

        }



    }

}
