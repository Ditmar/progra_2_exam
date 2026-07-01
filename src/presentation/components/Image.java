package presentation.components;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Image extends JLabel {
    private String imagePath;
    private ImageIcon icon = null;

    public Image(ImageIcon icon) {
        super(icon);
    }

    public Image(String path) {
        super();
        imagePath = path;
        setImage();
    }

    private void setImageSize(Dimension dimension) {
        Integer width = (int) dimension.getWidth();
        Integer height = (int) dimension.getHeight();
        java.awt.Image image = this.icon.getImage().getScaledInstance(width, height,
                java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);
        this.setIcon(icon);
    }

   @Override
    public void setSize(Dimension d) {
        setImageSize(d);
        super.setSize(d);
    }

    private void setImage() {
        icon = new ImageIcon(this.imagePath);
        this.setIcon(icon);
    }
}