import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Container;
import java.awt.image.BufferedImage;

public class moviepanel extends JFrame {
    JLabel jl;

public void moviepanel() {
    setSize(1366,736);
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
}

public void addimg(BufferedImage img) {
    jl = new JLabel(new ImageIcon(img));
    getContentPane().add(jl);
    setVisible(true);
}

public void delt() {
    Container parent = jl.getParent();
    parent.remove(jl);
}

public void close() {
    dispose();
}


}
