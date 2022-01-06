package starter.graphical;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.text.Font;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;

public class App extends Application {
    public static Label indicate = new Label("");

    @Override
    public void start(Stage stage) throws Exception {
        Model model = new Model();
        indicate.setFont(Font.font(40));
        // Create menu items
            MenuBar menubar = new MenuBar();
            Menu fileMenu = new Menu("File");
            MenuItem filenew = new MenuItem("New");
            MenuItem fileload = new MenuItem("Load");
            MenuItem filesave = new MenuItem("Save");
            MenuItem fileQuit = new MenuItem("Quit");
            fileMenu.getItems().addAll(filenew, fileload, filesave, fileQuit);

            Menu helpMenu = new Menu("Help");
            MenuItem helpAbout = new MenuItem("About");
            helpMenu.getItems().addAll(helpAbout);

            // Put menus together
            menubar.getMenus().addAll(fileMenu, helpMenu);

            helpAbout.setOnAction(actionEvent -> {
                new HelpdialogBox();
            });

            filenew.setOnAction(actionEvent->{
                if (model.containShape()) {
                    new closeDialog(model);
                }
                model.newpage();
            });
        fileload.setOnAction(actionEvent->{
            if (model.containShape()) {
                new closeDialog(model);
            }
            model.newpage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Dialog");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text file","*.txt"));
            try {
                File file = fileChooser.showOpenDialog(stage);
                model.load(file);
            } catch (Exception ex){

            }
        });
        filesave.setOnAction(actionEvent->{
            if (model.containShape()) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Dialog");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text file", "*.txt"));
                try {
                    File file = fileChooser.showSaveDialog(stage);
                    model.save(file);
                } catch (Exception ex) {

                }
            }
        });
        fileQuit.setOnAction(actionEvent->{
            if (model.containShape()) {
                new closeDialog(model);
            }
            stage.close();
        });

            // background
            Rectangle bgs = new Rectangle(1250,750);
            bgs.setFill(Color.GREEN);

            // tool bar
            ToolView toolpalette = new ToolView(model);

            // canvas
            final CanvasView drawcanvas = new CanvasView(model);


            // set up layouts
            HBox bottum = new HBox(toolpalette,drawcanvas);
            bottum.setSpacing(10);
            VBox allscrenn = new VBox(menubar,bottum,indicate);
            allscrenn.setMargin(indicate,new Insets(10));
            allscrenn.setSpacing(10);
            allscrenn.setMargin(bottum,new Insets(10));
            Group root = new Group();
            root.getChildren().add(bgs);
            root.getChildren().add(allscrenn);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("SketchIt");
            stage.show();

            scene.setOnKeyPressed(event -> {
                if (event.getCode()==KeyCode.DELETE) {
                    model.delete();
                    indicate.setText("delete a shape on the canvas");
                }
                if (event.getCode()==KeyCode.ESCAPE) {
                    model.unselectShape();
                }
            });

    }

    private class HelpdialogBox extends Stage{
        HelpdialogBox() {
            // sample code: 04.dialog
            TextArea text = new TextArea("Sketchit, Yukai Wang, 20757202");
            text.setWrapText(true);
            text.setPrefWidth(280);
            text.setPrefHeight(125);
            text.relocate(10, 10);
            text.setEditable(false);

            Button ok = new Button("Ok");
            ok.setPrefWidth(75);
            ok.relocate(130, 155);

            ok.setOnAction(actionEvent -> {
                this.close();
            });

            Scene scene = new Scene(new Pane(
                    text, ok), 300, 200);
            this.setScene(scene);
            this.setTitle("About");
            this.setResizable(false);
            this.setAlwaysOnTop(true);
            this.show();
        }
    }
    private class closeDialog extends Stage{
        boolean respond = false;
        closeDialog(Model model) {
            // sample code: 04.dialog
            TextArea text = new TextArea("DO you want to save the picture before leaving?");
            text.setWrapText(true);
            text.setPrefWidth(280);
            text.setPrefHeight(125);
            text.relocate(10, 10);
            text.setEditable(false);

            Button ok = new Button("No");
            ok.setPrefWidth(75);
            ok.relocate(220, 155);

            Button save = new Button("Save");
            save.setPrefWidth(75);
            save.relocate(130, 155);

            ok.setOnAction(actionEvent -> {
                this.close();
            });

            save.setOnAction(actionEvent->{
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Dialog");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text file","*.txt"));
                try {
                    File file = fileChooser.showSaveDialog(this);
                    model.save(file);
                } catch (Exception ex){

                }
                this.close();
            });

            Scene scene = new Scene(new Pane(
                    text, ok, save), 330, 200);
            this.setScene(scene);
            this.setTitle("Save");
            this.setResizable(false);
            this.setAlwaysOnTop(true);
            this.show();

        }
    }


}

