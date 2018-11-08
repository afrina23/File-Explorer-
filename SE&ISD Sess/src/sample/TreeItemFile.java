package sample;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static javafx.embed.swing.SwingFXUtils.toFXImage;

/**
 * Created by Afrina on 19-Apr-17.
 */
public class TreeItemFile extends TreeElement {
    ImageIcon icon;
    Image image;

    TreeItemFile(File iFile) {
        super(iFile);


        setIconImage();

    }

    @Override
    void setChildren() {
        children=null;
       // itemTree= new HBox();
    }

    @Override
    void setIconImage() {
        File folder= new File("i.txt");
        FileWriter writer = null;
        try {
            writer = new FileWriter(folder);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        icon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(folder);
        BufferedImage bufferedImage = (BufferedImage) icon.getImage();
        image=toFXImage(bufferedImage,null);
        iconImage = new ImageView(image);
    }


}
