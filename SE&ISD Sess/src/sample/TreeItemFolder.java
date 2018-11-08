package sample;


import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import static javafx.embed.swing.SwingFXUtils.toFXImage;

/**
 * Created by Afrina on 19-Apr-17.
 */
public class TreeItemFolder extends TreeElement {
    ImageIcon icon;
    Image image;

    TreeItemFolder(File iFile) {
        super(iFile);
        children=new ArrayList<>();

        setIconImage();

    }

    @Override
    void setIconImage() {
        File  folder= new File("D:\\");
        icon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(folder);
        BufferedImage bufferedImage = (BufferedImage) icon.getImage();
        image=toFXImage(bufferedImage,null);
        iconImage = new ImageView( image);

    }
    @Override
    protected void setChildren(){
        File[] childFiles= file.listFiles();
        for(int i=0;i<childFiles.length;i++){
            if(childFiles[i].isHidden() || !childFiles[i].canExecute()) continue;
            children.add(FactoryFile.getFactoryForTree(childFiles[i]));
        }
    }

}
