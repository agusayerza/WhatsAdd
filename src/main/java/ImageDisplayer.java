import javax.swing.*;

public class ImageDisplayer {

    JFrame frame = new JFrame();

    public void showImage(String filename) {

        ImageIcon icon = new ImageIcon(filename);
        JLabel label = new JLabel(icon);
        frame.add(label);
        frame.setDefaultCloseOperation
                (JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    public void close(){
        frame.setVisible(false);
    }
}
