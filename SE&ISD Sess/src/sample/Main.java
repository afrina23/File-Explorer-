package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


    public class Main extends Application {
        //start application
        static GridPane startPage;
        static  Stage stage;

        //file names
        static File currentDir;
        File rootOfFile;

        //component of table view
        TableView<TableElement> fileTable;
        TableElement currentShow;

        TreeView<HBox> tree;
        TreeItem<HBox> rootOfTree;
        static HashMap< TreeItem<HBox> ,TreeElement> FileTreeItemPair;
        static HashMap< VBox,TableElement> FileTilePair;
        //component of choicebox
        boolean tableSelected=true;
        ObservableList<String> selectView= FXCollections.
                observableArrayList("TableView","TilesView");
        ChoiceBox ViewChoice;
        TreeElement rootOfFileInfo;
        //component of tileView
        static ScrollPane scrollPane;
        static TilePane tilePane;
        static ArrayList<VBox> listOfTiles;

        @Override
        public void start(Stage primaryStage) throws Exception{
            //initializing files
            currentDir = new File(".");
            currentShow=FactoryFile.getFactoryForTable(currentDir);
            // rootOfFile= new File("D:\\SONGS\\");
            rootOfFile=FileSystemView.getFileSystemView().getRoots()[0];
            rootOfFileInfo=FactoryFile.getFactoryForTree(rootOfFile);
            //initializing choice box
            ViewChoice= new ChoiceBox();
            ViewChoice.setItems(selectView);
            ViewChoice.setValue("TableView");


            FileTreeItemPair= new HashMap<>();
            FileTilePair= new HashMap<>();
            stage=primaryStage;

            buildWholeTree();
            showPage();
            selectChoiceBox();
            selectTreeItem();
        }
        //builds the tree
        public void buildWholeTree() throws IOException {
            //root
            rootOfTree= new TreeItem<>(rootOfFileInfo.getItemTree());
            File[] f = File.listRoots();
            for (int i = 0; i < f.length; i++)
            {
                if(!f[i].canExecute()) continue;
                rootOfFileInfo=FactoryFile.getFactoryForTree(f[i]);
                System.out.println("ROOT NAME!!!!!!!!!!!!!"+f[i]);
                TreeItem drive= makeChild(rootOfFileInfo.getItemTree(),rootOfTree);
                System.out.println("Parent of drive "+ f[i].getParent());

                //expandFiles(f[i],drive);
                FileTreeItemPair.put(drive,rootOfFileInfo);

            }


            rootOfTree.setExpanded(false);
            // getAllFilesWithChild(rootOfFile,rootOfTree);
            FileTreeItemPair.put(rootOfTree,rootOfFileInfo);
            tree= new TreeView<>(rootOfTree);
            tree.setShowRoot(false);

            // hBox.getChildren().add(tree);

        }


        //gets the whole file system and intserts it in the tree
        public static void  getChildrenOfTreeItem(TreeElement file,TreeItem root) throws IOException {
            FileSystemView fsv = FileSystemView.getFileSystemView();

                //  getCreationDetails(file);
                ArrayList<TreeElement> child= file.getChildren();
                if(child != null){
                    for(int i=0;i<child.size();i++){
                        // if(child[i].isHidden() || !child[i].canExecute()) continue;
                        String name=child.get(i).name;
                        System.out.println(name);

                        // HBox newItem= CreateHBoxOfTreeItem(child[i]);
                        TreeItem ch=makeChild(child.get(i).getItemTree(),root);
                        FileTreeItemPair.put(ch,child.get(i));
                        //getAllFilesWithChild(child[i],ch);
                    }
                }




        }
        //creates a tree item from HBoz and makes it the child of parent
        public static TreeItem<HBox> makeChild(HBox child, TreeItem<HBox> Parent){
            TreeItem<HBox> item= new TreeItem<>(child);
            item.setExpanded(false);
            Parent.getChildren().add(item);
            return item;
        }

        public  void selectTreeItem(){
            tree.getSelectionModel().selectedItemProperty().addListener((v,oldvalue,newValue)->{
                if(newValue!= null){
                    System.out.println(newValue.getValue()+ "tree item selected!!!!!!!!!!!!!!!!!!!");


                    TreeElement newDes= FileTreeItemPair.get(newValue);

                    if(newDes.getChildren() != null)try {
                        currentShow=FactoryFile.getFactoryForTable(newDes.file);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(!newDes.addedInTree){
                        //currentDir=newDes;
                        try {
                            getChildrenOfTreeItem(newDes,newValue);
                            newDes.addedInTree= true;

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        showPage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            });
        }

        //choice box listener
        public void selectChoiceBox(){
            ViewChoice.getSelectionModel().selectedIndexProperty()
                    .addListener(new ChangeListener<Number>() {
                        public void changed(ObservableValue ov, Number value, Number new_value) {
                            System.out.println(selectView.get(new_value.intValue()));
                            String choice=selectView.get(new_value.intValue());
                            if(choice.equals("TilesView")) tableSelected=false;
                            else{
                                tableSelected=true;
                            }
                            try {
                                showPage();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });


        }


        public void showPage() throws IOException {

            //getting the startpage
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(sample.Main.class.getResource("sample.fxml"));
            startPage=loader.load();


            //inserting the choicebox and tree
            startPage.add(ViewChoice,0,0);
            startPage.add(tree,0,1);
            System.out.println("Table dekhabe!!!!!"+ tableSelected);
            //showing the tableView or TileView

            if(tableSelected) showTablePane();

            else{
                showTilePane();
            }

            //setting the scene
            Scene scene= new Scene(startPage);
            stage.setScene(scene);
            stage.show();

        }
        //showing the tile Pane

        public  void showTilePane() throws IOException {
            scrollPane= new ScrollPane();
            creteTilePane(currentShow);
            if(currentDir.isDirectory()) selectTile();
            startPage.add(scrollPane,1,1);
        }

        //creating the selected pane

        public static void creteTilePane(TableElement toShow) throws IOException {

            tilePane = new TilePane();
            tilePane.setPrefColumns(3);
            //  tilePane.setPrefTileHeight(10);
            tilePane.setHgap(2);
            listOfTiles= new ArrayList<>();
            if(toShow.canBeShown){
                ArrayList<TableElement> children=toShow.getChildren();
                for(int i=0;i<children.size();i++){
                    VBox tile= ElementToTile.getSingleTile(children.get(i));
                    listOfTiles.add(tile);

                   FileTilePair.put(tile,children.get(i));
                }
                /*
                for (File file : files) {
                    if(file.isHidden()) continue;
                    FileInformation info = new FileInformation(file);
                    System.out.println(info.getName()+" file child ");
                    listOfTiles.add(getSingleTile(info.getName(),new ImageView(info.getImage())));
                }*/
            }

            tilePane.getChildren().addAll(listOfTiles);
            scrollPane.setContent(tilePane);


        }


        //tile select listener
        public void selectTile(){
            for(int i=0;i<listOfTiles.size();i++){
                Label l= (Label) listOfTiles.get(i).getChildren().get(1);
                VBox now= listOfTiles.get(i);
                listOfTiles.get(i).setOnMouseClicked(e->{
                    TableElement toShow=FileTilePair.get(now);

                    System.out.println(l.getText());
                    //File newDir=getFileFromName(l.getText());
                    if(toShow.canBeShown){
                        currentShow=toShow;
                        //currentDir=newDir;
                        try {
                            //showTilePane();
                            showPage();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }


                });

            }

        }

        //shows the table pane
        public void showTablePane() throws IOException {
            crateTableColumn();
            selectTableItem();
            startPage.add(fileTable,1,1);
        }


        //creates the table

        public  void  crateTableColumn() throws IOException {
            TableColumn<TableElement,Image> image= new TableColumn<>("ICON");
            TableColumn<TableElement,String> name= new TableColumn<>("Name");
            TableColumn<TableElement,Long> Size= new TableColumn<>("Size");
            TableColumn<TableElement,String> Date= new TableColumn<>("ModifyDate");
            image.setCellValueFactory(new PropertyValueFactory<>("image"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            Size.setCellValueFactory(new PropertyValueFactory<>("Size"));
            Date.setCellValueFactory(new PropertyValueFactory<>("modifyDate"));
            fileTable= new TableView<>();
            fileTable.setItems(getFileListForTable());
            fileTable.getColumns().addAll(image,name,Size,Date);



        }
        //getting the list of files for showing in the table
        public ObservableList<TableElement> getFileListForTable() throws IOException {
            ObservableList<TableElement> fileList = FXCollections.observableArrayList();


                ArrayList<TableElement> children= currentShow.getChildren();
               if(children != null){
                   for(int i=0;i<children.size();i++){
                       fileList.add(children.get(i));
                   }
               }

               //if directory chilo ekhane
            /*
                 File[] files = currentDir.listFiles();
                for (File file : files) {
                    if(file.isHidden()) continue;
                    FileInformation info = new FileInformation(file);
                    fileList.add(new TableItemInfo(info.getName(),info.getSize(),info.getModifyDate()
                            ,new ImageView(info.getImage())));
                }
            */
            return fileList;
        }

        //table item listener
        public void selectTableItem(){

            fileTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    TableElement selectedFile = newSelection;

                    //File newDir=getFileFromName(selectedFile.getName());
                    if (selectedFile.canBeShown) {
                        // currentDir=newDir;
                        currentShow = selectedFile;

                        try {
                            //showTablePane();
                            showPage();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


        }
        //getting the child file from the names of child files
        public static void main(String[] args) {
            //File fw= new File("a.txt");
            launch(args);
        }
    }

