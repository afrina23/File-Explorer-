package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static javafx.embed.swing.SwingFXUtils.toFXImage;

/**
 * Created by Afrina on 19-Apr-17.
 */
public class TableFolder extends TableElement {


    TableFolder(File iFile) throws IOException {
        super(iFile);
        children= new ArrayList<>();
        canBeShown=true;
    }

    @Override
    protected void setChildren() throws IOException {
        File[] childFiles= mainFile.listFiles();

        for(int i=0;i<childFiles.length;i++){
            if(childFiles[i].isHidden() || !childFiles[i].canExecute() || !childFiles[i].canWrite()) continue;
            children.add(FactoryFile.getFactoryForTable(childFiles[i]));
        }
    }


}
