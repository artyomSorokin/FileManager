package net.sorokin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class FileManager {

    //elements
    private Label title = new Label(InetAddress.getLocalHost().getHostName());
    private Button root = new Button("root");
    private Button back = new Button("back");
    private TextField workingDir = new TextField();
    private TextArea output = new TextArea();

    private ObservableList<String> dirs = FXCollections.observableArrayList();
    private ListView<String> listView = new ListView<>(dirs);

    private String homedir = "";
    private String curdir = "";


    public FileManager(Scene scene) throws UnknownHostException {

        //setup elements
        listView.setMinSize(400, 500);
        listView.setOnMouseClicked((e) -> {
            navigate(listView.getSelectionModel().getSelectedItem());
        });
        root.setOnMouseReleased((e) -> {
            curdir = homedir;
            showDirs(getCurrent());
        });
        back.setOnMouseReleased((e) -> {
            back();
        });

        //output Text Area
        output.setPadding(new Insets(5, 5, 5, 5));
        output.setWrapText(true);
        output.setEditable(false);
        //get init data
        curdir = homedir = System.getProperty("user.home");
        showDirs(getCurrent());
    }


    public Pane getPane() {
        //gonna use a border layout for this scene
        BorderPane bp = new BorderPane();
        //format
        bp.setPadding(new Insets(5, 5, 5, 5));

        //top
        HBox top = new HBox(new Group());
        top.getChildren().add(title);
        top.getChildren().add(root);
        top.getChildren().add(back);
        bp.setTop(top);

        //center
        VBox center = new VBox();
        center.getChildren().add(workingDir);
        center.getChildren().add(listView);
        workingDir.setText(getCurentDir());
        bp.setCenter(center);

        return bp;
    }

    private String[] getCurrent() {
        String[] local;
        File f = new File(getCurentDir());
        if (f.isFile()) {
            //NOP
        }
        local = f.list();
        if (local != null) {
            Arrays.sort(local);
        }
        return local;

    }

    private void back() {
        if (getCurentDir().lastIndexOf('/') != 0) {
            gotoDir(getCurentDir().substring(0, getCurentDir().lastIndexOf('/')));
        } else {
            gotoDir("/");
        }
    }

    private void gotoDir(String dir) {
        if (dir == null || dir.equals("")) {
            gotoDir("/");
        }
        curdir = dir;
        workingDir.setText(getCurentDir());
        showDirs(getCurrent());
    }

    private void navigate(String name) {
        if (name != null) {
            //this 4 is to skip the "(l) " in front of each list element
            if (getCurentDir().equals("/")) {
                curdir += name.substring(4, name.length());
            } else {
                // we need to check if we're going to a dir
                if (!new File(getCurentDir() + "/" + name).isFile()) {
                    curdir += "/" + name.substring(4, name.length());
                }
                // if not, do nothing
            }
            workingDir.setText(getCurentDir());
            showDirs(getCurrent());
        }
    }

    private void showDirs(String[] names) {
        if (names != null) {
            //label dirs vs files
            for (int i = 0; i < names.length; i++) {
                if (new File(getCurentDir() + "/" + names[i]).isDirectory()) {
                    names[i] = "(d) " + names[i];
                } else {
                    names[i] = "(f) " + names[i];
                }
            }
            dirs.clear();
            dirs.addAll(names);
        } else {
            dirs.clear();
        }
    }

    private String getCurentDir() {
        return curdir;
    }
}
