package sample;

import java.io.File;
import java.io.IOException;

/**
 * Created by Afrina on 19-Apr-17.
 */
public class TableFile extends TableElement {
    TableFile(File iFile) throws IOException {
        super(iFile);
        canBeShown=false;
    }

    @Override
    void setChildren() {
        children=null;
    }
}
