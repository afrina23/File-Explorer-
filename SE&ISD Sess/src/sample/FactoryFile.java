package sample;

import java.io.File;
import java.io.IOException;

/**
 * Created by Afrina on 19-Apr-17.
 */
public class FactoryFile {
    public static TreeElement getFactoryForTree(File file){
        TreeElement toReturn;
        if(file.isDirectory()){
            toReturn=new TreeItemFolder(file);
        }
        else{
            toReturn=new TreeItemFile(file);
        }
        return toReturn;
    }
    public static TableElement getFactoryForTable(File file) throws IOException {
        TableElement toReturn;
        if(file.isDirectory()){
            toReturn=new TableFolder(file);
        }
        else{
            toReturn=new TableFile(file);
        }


        return toReturn;
    }
}
