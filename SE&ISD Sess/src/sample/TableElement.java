package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static javafx.embed.swing.SwingFXUtils.toFXImage;

/**
 * Created by Afrina on 19-Apr-17.
 */
public abstract class TableElement {

    public String name;
    public long Size;
    public String modifyDate;
    public ImageView image;
    public File mainFile;
    ArrayList<TableElement> children;
    //private
    protected Path p;
    protected FileTime fileTime;
    protected BasicFileAttributes view;
    protected int count;
    ImageIcon icon;
    Image imageFromIcon;
    boolean canBeShown;


    TableElement(File iFile) throws IOException {
        mainFile=iFile;
        p = Paths.get(mainFile.getAbsolutePath());
        view = Files.getFileAttributeView(p, BasicFileAttributeView.class).readAttributes();
        setModifyDate();
        setName();
        setSize();
        setImage();
        count=0;
    }


    public ImageView getImage() {
        return image;
    }



    public String getName() {
        return name;
    }

    public void setName() {
        this.name = FileSystemView.getFileSystemView().getSystemDisplayName(mainFile);
    }

    public long getSize() {
        return Size;
    }

    public void setSize() {
        Size = view.size();
    }

    public String getModifyDate() {
        return modifyDate;
    }

    void setModifyDate() {
        fileTime=view.lastModifiedTime();
        modifyDate=new SimpleDateFormat("   dd/MM/yyyy   ").format((fileTime.toMillis()));
    }

     ArrayList<TableElement> getChildren() throws IOException {
        if(count==0){
            if(mainFile.isDirectory())setChildren();
            count=1;
        }
        return children;
    }

    public  void setImage() throws MalformedURLException {
         //image= new ImageView();
        //File  folder= new File("D:\\");
        icon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(mainFile);
        BufferedImage bufferedImage = (BufferedImage) icon.getImage();
        imageFromIcon=toFXImage(bufferedImage,null);
        image = new ImageView( imageFromIcon);
        /*
        String imagepath = mainFile.toURI().toString();
        System.out.println("file:"+imagepath);
        Image from = new Image(imagepath);
        image.setImage(from);*/
    }

    abstract void setChildren() throws IOException;
    /*
    public BufferedImage convertToBufferedImage(Image image)
    {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(null), image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }
*/
}
