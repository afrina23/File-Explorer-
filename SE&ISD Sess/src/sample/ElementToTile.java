package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Created by Afrina on 19-Apr-17.
 */
public class ElementToTile {
    public static VBox getSingleTile(TableElement toShow){
        VBox tile= new VBox();
        tile.setAlignment(Pos.CENTER);
        tile.setMaxWidth(100);
        Label Name= new Label(toShow.getName());
        tile.getChildren().addAll(toShow.getImage(),Name);

        return  tile;
    }
}
