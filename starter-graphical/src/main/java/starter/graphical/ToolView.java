package starter.graphical;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ToolView extends VBox implements IView {
    Model model;
    Color fill,line;
    ToolView(Model model) {
        this.model = model;

        // create upper toolbar
        Button select = new Button("select");
        Button erase = new Button("erase");
        Button DrawLine = new Button("DrawLine");
        Button DrawCircle = new Button("DrawCircle");
        Button DrawRec = new Button("DrawRectangle");
        FlowPane toolpalette = new FlowPane(select,erase,DrawCircle,DrawLine,DrawRec);
        toolpalette.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        toolpalette.setAlignment(Pos.CENTER);
        toolpalette.setPadding(new Insets(10.0));
        toolpalette.setHgap(10);
        toolpalette.setMinHeight(300);

        // create lower toolbar
        Button linecolor = new Button("border color");
        //linecolor.setBackground(new Background(new BackgroundFill(line,CornerRadii.EMPTY, Insets.EMPTY)));
        Button fillcolor = new Button("fill color");
        //linecolor.setBackground(new Background(new BackgroundFill(fill,CornerRadii.EMPTY, Insets.EMPTY)));

        Button linewidth = new Button("Line Width");
        Button linethick = new Button("Line Thickness");
        GridPane iconpalette = new GridPane();
        iconpalette.add(linewidth,1,1);
        iconpalette.add(linethick,1,2);
        iconpalette.add(linecolor,2,1);
        iconpalette.add(fillcolor,2,2);
        iconpalette.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE,CornerRadii.EMPTY, Insets.EMPTY)));
        iconpalette.setPadding(new Insets(10.0));
        iconpalette.setMinHeight(300);
        iconpalette.setHgap(20);
        iconpalette.setVgap(20);
        iconpalette.setAlignment(Pos.CENTER);
        this.getChildren().add(toolpalette);
        this.getChildren().add(iconpalette);
        this.setSpacing(10);


        // handle action

        select.setOnAction(actionEvent -> {
            App.indicate.setText("select a shape on the canvas");
            model.selectShape();
        });

        erase.setOnAction(actionEvent -> {
            App.indicate.setText("delete a shape on the canvas");
            model.delete();
        });

        DrawCircle.setOnAction(actionEvent->{
            App.indicate.setText("draw a circle on the canvas");
            model.drawCircle();
        });

        DrawLine.setOnAction(actionEvent->{
            App.indicate.setText("draw a line on the canvas");
            model.drawLine();
        });

        DrawRec.setOnAction(actionEvent->{
            App.indicate.setText("draw a rectangle on the canvas");
            model.drawRect();
        });

        linecolor.setOnAction(actionEvent->{
            App.indicate.setText("choose a line color for the shape");
            model.changeline();
        });
        fillcolor.setOnAction(actionEvent->{
            App.indicate.setText("choose a line color for the shape");
            model.changefill();
        });

        model.addView(this);
    }

    @Override
    public void updateView() {
        fill = model.getcolor();
        line = model.getlinecolor();
    }
}
