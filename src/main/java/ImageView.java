import javax.swing.*;

class ImageView {

    private final JFrame frame = new JFrame();

    void showImage(String filename) {

        ImageIcon icon = new ImageIcon(filename);
        JLabel label = new JLabel(icon);
        frame.add(label);
        frame.setDefaultCloseOperation
                (JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    void close(){
        frame.setVisible(false);
    }
}
