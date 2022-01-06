package starter.graphical;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.lang.Math;
import java.awt.event.*;
import java.util.Scanner;

import javafx.stage.FileChooser;

// part of code used from sample code 03:hellomvc
public class Model {
    private ArrayList<IView> views = new ArrayList<IView>();
    private ArrayList<Ishape> shapes = new ArrayList<Ishape>();
    double original_x,original_y,r;
    CanvasView canvas;
    Color previous_color, line_color,fill_color;
    boolean selected = false;
    Ishape selectedshape;

    public void addView(IView view) {
        views.add(view);
        view.updateView();
    }

    public void addCanvas(CanvasView view) {
        canvas = view;
    }

    private void notifyObservers() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GRAY);
        gc.fillRect(0,0,800,600);
        gc.setFill(Color.WHITE);
        gc.fillRect(5,5,790,590);
        for (IView view : this.views) {
            view.updateView();
        }
    }


    public void drawCircle() {
        canvas.setOnMouseClicked(mouseEvent->{});
        canvas.setOnMousePressed(mouseEvent -> {
            original_x = mouseEvent.getX();
            original_y = mouseEvent.getY();
            r = 0;
            shapes.add(new SCircle(original_x,original_y,r, Color.RED));

            App.indicate.setText("drag mouse to draw a circle");
        });
        canvas.setOnMouseReleased( mouseEvent -> {
            App.indicate.setText("draw a circle on the canvas");
        });
        canvas.setOnMouseDragged(mouseEvent -> {
            double a = Math.pow((original_x - mouseEvent.getX()), 2) + Math.pow((original_y - mouseEvent.getY()), 2);
            r = Math.sqrt(a);
            shapes.get(shapes.size() - 1).translate(r,0, Color.RED);
            notifyObservers();
        });
    }

    public void drawRect() {
        canvas.setOnMouseClicked(mouseEvent->{});

        canvas.setOnMousePressed(mouseEvent -> {
            original_x = mouseEvent.getX();
            original_y = mouseEvent.getY();
            shapes.add(new SRectangle(original_x,original_y,original_x,original_y, Color.BLUE));

            App.indicate.setText("drag mouse to draw a rectangle");
        });
        canvas.setOnMouseReleased( mouseEvent -> {
            App.indicate.setText("draw a rectangle on the canvas");
        });
        canvas.setOnMouseDragged(mouseEvent -> {
            shapes.get(shapes.size() - 1).translate(mouseEvent.getX(),mouseEvent.getY(), Color.RED);
            notifyObservers();
        });
    }

    public void drawLine() {
        canvas.setOnMouseClicked(mouseEvent->{});

        canvas.setOnMousePressed(mouseEvent -> {
            original_x = mouseEvent.getX();
            original_y = mouseEvent.getY();
            shapes.add(new SLine(original_x,original_y,original_x,original_y, Color.YELLOW));

            App.indicate.setText("drag mouse to draw a line");
        });
        canvas.setOnMouseReleased( mouseEvent -> {
            App.indicate.setText("draw a line on the canvas");
        });
        canvas.setOnMouseDragged(mouseEvent -> {
            shapes.get(shapes.size() - 1).translate(mouseEvent.getX(),mouseEvent.getY(), Color.RED);
            notifyObservers();
        });
    }
    ArrayList<Ishape> getShapes() {
        return shapes;
    }

    void selectShape(){
        canvas.setOnMousePressed(mouseEvemt->{});
        canvas.setOnMouseReleased(mouseEvemt->{});
        canvas.setOnMouseDragged(mouseEvemt->{});
        canvas.setOnMouseClicked(mouseEvent-> {
            for (Ishape shape : shapes) {
                if (shape.selectState()) {
                    shape.changeColor(previous_color);
                    shape.unselect();
                    notifyObservers();
                }
            }
            for (int i = shapes.size()-1; i >= 0; --i) {
                if (shapes.get(i).hitTest(mouseEvent)) {
                    selected = true;
                    selectedshape = shapes.get(i);
                    break;
                }
            }
            for (Ishape shape : shapes) {
                if (shape.selectState()) {
                    previous_color = shape.getColor();
                    shape.changeColor(Color.PURPLE);
                    App.indicate.setText("an item has been selected");
                    notifyObservers();
                }
            }

        });
    }
    void unselectShape() {
        for (Ishape shape : shapes) {
            if (shape.selectState()) {
                shape.changeColor(previous_color);
                shape.unselect();
            }
        }
        notifyObservers();
        App.indicate.setText("select a shape on the canvas");
        selected = false;
    }
    void delete() {
        canvas.setOnMousePressed(mouseEvemt->{});
        canvas.setOnMouseReleased(mouseEvemt->{});
        canvas.setOnMouseDragged(mouseEvemt->{});
        canvas.setOnMouseClicked(mouseEvent-> {
            for (int i = shapes.size()-1; i >= 0; --i) {
                if (shapes.get(i).hitTest(mouseEvent)) {
                    shapes.remove(i);
                }
            }
            notifyObservers();
            selected = false;
        });
        for(int i = shapes.size()-1; i >= 0; --i){
            if (shapes.get(i).selectState()) {
                shapes.remove(i);
            }
        }
        notifyObservers();
        selected = false;
    }

    void changeline() {
        new ColorDialogBox();
        canvas.setOnMousePressed(mouseEvemt->{});
        canvas.setOnMouseReleased(mouseEvemt->{});
        canvas.setOnMouseDragged(mouseEvemt->{});
        canvas.setOnMouseClicked(mouseEvent-> {
            for (int i = shapes.size()-1; i >= 0; --i) {
                if (shapes.get(i).hitTest(mouseEvent)) {
                    shapes.get(i).changeLine(line_color);
                }
            }
            notifyObservers();
        });
        /*if (selected) {
            selectedshape.changeLine(line_color);
            notifyObservers();
        }*/
    }
    void changefill() {
        new ColorDialogBox();
        canvas.setOnMousePressed(mouseEvemt->{});
        canvas.setOnMouseReleased(mouseEvemt->{});
        canvas.setOnMouseDragged(mouseEvemt->{});
        canvas.setOnMouseClicked(mouseEvent-> {
            for (int i = shapes.size()-1; i >= 0; --i) {
                if (shapes.get(i).hitTest(mouseEvent)) {
                    shapes.get(i).changeColor(line_color);
                }
            }
            notifyObservers();
        });
        /*if (selected) {
            selectedshape.changeColor(line_color);
            notifyObservers();
        }*/
    }
    Color getlinecolor(){
        if (selected) return selectedshape.getlineColor();
        return Color.BLACK;
    }
    Color getcolor() {
        if (selected) return selectedshape.getColor();
        return Color.BLACK;
    }

    void newpage() {
        shapes.clear();
        notifyObservers();
    }

    void save(File file) {
        try {
            PrintWriter pw = new PrintWriter(file);
            String line = "";
            for (Ishape shape : shapes) {
                line = shape.index();
                pw.println(line);
            }
            pw.flush();
            pw.close();
        } catch (Exception e){

        }
    }

    void load(File file) {

        try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNext()) {
                    String n = scanner.next();
                    if (n.equals("circle")) {
                        double x = Double.parseDouble(scanner.next());
                        double y = Double.parseDouble(scanner.next());
                        double r = Double.parseDouble(scanner.next());
                        Color color = Color.web(scanner.next());
                        Color line_color = Color.web(scanner.next());
                        shapes.add(new SCircle(x,y,r,color));
                        shapes.get(shapes.size()-1).changeLine(line_color);
                    } else if (n.equals("line")) {
                        double x = Double.parseDouble(scanner.next());
                        double y = Double.parseDouble(scanner.next());
                        double w = Double.parseDouble(scanner.next());
                        double h = Double.parseDouble(scanner.next());
                        Color color = Color.web(scanner.next());
                        shapes.add(new SLine(x,y,w,h,color));
                    } else if (n.equals("rect")){
                        double x = Double.parseDouble(scanner.next());
                        double y = Double.parseDouble(scanner.next());
                        double w = Double.parseDouble(scanner.next());
                        double h = Double.parseDouble(scanner.next());
                        Color color = Color.web(scanner.next());
                        Color line_color = Color.web(scanner.next());
                        shapes.add(new SRectangle(x,y,w,h,color));
                        shapes.get(shapes.size()-1).changeLine(line_color);
                    }
                }

        } catch (Exception e){}
        notifyObservers();
    }

    boolean containShape() {
        return !shapes.isEmpty();
    }

    private class ColorDialogBox extends Stage {
        ColorDialogBox() {
            Button btn1 = new Button("");
            btn1.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
            Button btn2 = new Button();
            btn2.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
            Button btn3 = new Button();
            btn3.setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, Insets.EMPTY)));
            Button btn4 = new Button();
            btn4.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
            Button btn5 = new Button();
            btn5.setBackground(new Background(new BackgroundFill(Color.MAGENTA, CornerRadii.EMPTY, Insets.EMPTY)));
            Button btn6 = new Button();
            btn6.setBackground(new Background(new BackgroundFill(Color.GOLD, CornerRadii.EMPTY, Insets.EMPTY)));
            btn1.setPrefWidth(75);
            btn2.setPrefWidth(75);
            btn3.setPrefWidth(75);
            btn4.setPrefWidth(75);
            btn5.setPrefWidth(75);
            btn6.setPrefWidth(75);

            GridPane grid = new GridPane();
            grid.add(btn1,1,1);
            grid.add(btn2,1,2);
            grid.add(btn3,1,3);
            grid.add(btn4,2,1);
            grid.add(btn5,2,2);
            grid.add(btn6,2,3);
            grid.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE,CornerRadii.EMPTY, Insets.EMPTY)));
            grid.setPadding(new Insets(10.0));
            grid.setMinHeight(300);
            grid.setHgap(20);
            grid.setVgap(20);
            grid.setAlignment(Pos.CENTER);
            btn1.setOnAction(ActionEvent->{
                line_color = Color.BLACK;
                App.indicate.setText("selected black");
            });
            btn2.setOnAction(ActionEvent->{
                line_color = Color.PINK;
                App.indicate.setText("selected pink");
            });
            btn3.setOnAction(ActionEvent->{
                line_color = Color.CYAN;
                App.indicate.setText("selected cyan");
            });
            btn4.setOnAction(ActionEvent->{
                line_color = Color.ORANGE;
                App.indicate.setText("selected orange");
            });
            btn5.setOnAction(ActionEvent->{
                line_color = Color.MAGENTA;
                App.indicate.setText("selected magenta");
            });
            btn6.setOnAction(ActionEvent->{
                line_color = Color.GOLD;
                App.indicate.setText("selected gold");
            });
            this.setScene(new Scene(grid));
            this.setTitle("About");
            this.setResizable(false);
            this.setAlwaysOnTop(true);
            this.show();

        }
    }
}
