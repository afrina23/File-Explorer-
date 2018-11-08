package sample;

/**
 * Created by Afrina on 19-Apr-17.
 */

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


import java.io.File;
import java.util.ArrayList;






/**
 * Created by Afrina on 19-Apr-17.
 */
public abstract class TreeElement {
    String name;
    ArrayList<TreeElement> children;
    File file;
    HBox itemTree;
    ImageView iconImage;
    int count=0;
    boolean addedInTree;
    public ArrayList<TreeElement> getChildren() {
        if(count==0){
            setChildren();
            count=1;
        }
        return children;
    }

    TreeElement(File iFile){
        file=iFile;
        name=file.getAbsolutePath();
        addedInTree=false;
        itemTree= new HBox();

    }
    abstract void setChildren();
    abstract  void setIconImage();
    public HBox getItemTree(){

        itemTree.getChildren().addAll(iconImage,new Label(name));
        return itemTree;
    }

}


