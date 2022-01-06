package starter.graphical;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

// from sample code: 02.painters_algorithm

public interface Ishape {
    void draw(Canvas canvas);
    public void translate(double a, double b, Color color);
    boolean hitTest(javafx.scene.input.MouseEvent mouseEvent);
    boolean selectState();
    Color getColor();
    Color getlineColor();
    void changeColor(Color color);
    void unselect();
    void changeLine(Color color);
    String index();
}

class SCircle implements Ishape {
    double x, y, r;
    Color color,line_color = Color.BLACK;
    Boolean hit = false;

    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.setStroke(line_color);
        gc.fillOval(x, y, r, r);
        gc.strokeOval(x,y,r,r);
    }

    public void translate(double a, double b, Color color) {
        r = a;
    }

    SCircle(double _x, double _y, double _r, javafx.scene.paint.Color _color) {
        x = _x;
        y = _y;
        r = _r;
        color = _color;
    }

    public boolean hitTest(javafx.scene.input.MouseEvent mouseEvent){
        double a = Math.pow((x - mouseEvent.getX()), 2) + Math.pow((y - mouseEvent.getY()), 2);
        double distance = Math.sqrt(a);
        if (distance <= r) {
            hit = true;
            return true;
        } else {
            return false;
        }
    }

    public boolean selectState() {
        return hit;
    }

    public Color getColor() {
        return color;
    }
    public Color getlineColor() {
        return line_color;
    }
    public void changeColor(Color _color) {
        color = _color;
    }
    public void unselect() {
        hit = false;
    }
    public void changeLine(Color color) {
        line_color = color;
    }

    public String index() {
        String line = "";
        line += "circle " + x +" " + y + " " +r+ " "+ color.toString() + " " + line_color.toString();
        return line;
    }
}

class SRectangle implements Ishape {
    private double x, y;
    private double w, h;
    private Color color,line_color = Color.BLACK;
    boolean hit = false;

    SRectangle(double _x, double _y, double _w, double _h, Color _color) {
        x = _x;
        y = _y;
        w = _w;
        h = _h;
        color = _color;
    }
    public void translate(double a, double b, Color color) {
        w = a-x;
        h = b-y;
    }
    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.setStroke(line_color);
        gc.fillRect(x, y, w, h);
        gc.strokeRect(x,y,w,h);
    }
    public boolean hitTest(javafx.scene.input.MouseEvent mouseEvent){
        if (mouseEvent.getX() <= x+w && mouseEvent.getY() <= y+h && mouseEvent.getX() >= x && mouseEvent.getY() >= y) {
            hit = true;
            return true;
        }
        return false;
    }
    public boolean selectState() {
        return hit;
    }
    public Color getColor() {
        return color;
    }
    public Color getlineColor() {
        return line_color;
    }
    public void changeColor(Color _color) {
        color = _color;
    }
    public void unselect() {
        hit = false;
    }
    public void changeLine(Color color) {
        line_color = color;
    }
    public String index() {
        String line = "";
        line += "rect " + x +" " + y + " " +w+ " " + h + " " + color.toString() + " " + line_color.toString();
        return line;
    }
}

class SLine implements Ishape {
    private double x, y;
    private double w, h;
    private Color color;
    boolean hit = false;

    SLine(double _x, double _y, double _w, double _h, Color _color) {
        x = _x;
        y = _y;
        w = _w;
        h = _h;
        color = _color;
    }
    public void translate(double a, double b, Color color) {
        w = a;
        h = b;
    }
    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(color);
        gc.strokeLine(x,y,w,h);
    }

    public boolean hitTest(javafx.scene.input.MouseEvent mouseEvent){
        double distance = Line2D.ptSegDist(x,y,w,h,mouseEvent.getX(), mouseEvent.getY());
        if (distance <= 10) {
            hit = true;
            return true;
        }
        return false;
    }
    public boolean selectState() {
        return hit;
    }
    public Color getColor() {
        return color;
    }
    public void changeColor(Color _color) {
        color = _color;
    }
    public void unselect() {
        hit = false;
    }
    public void changeLine(Color _color) {
        color = _color;
    }
    public Color getlineColor() {
        return color;
    }
    public String index() {
        String line = "";
        line += "line " + x +" " + y + " " +w+ " " + h+ " " + color.toString();
        return line;
    }
}
