package starter.graphical;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.Node;
import javafx.scene.paint.Color;

public class CanvasView extends Canvas implements IView {
    Model model;
    CanvasView(Model model) {
        this.model = model;

        this.setFocusTraversable(true);
        this.requestFocus();
        this.setLayoutX(400);
        this.setLayoutY(200);
        this.setHeight(600);
        this.setWidth(800);
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setLineWidth(5);
        gc.setStroke(Color.BLACK);

        gc.setFill(Color.GRAY);
        gc.fillRect(0,0,800,600);
        gc.setFill(Color.WHITE);
        gc.fillRect(5,5,790,590);

        model.addView(this);
        model.addCanvas(this);
    }

    @Override
    public void updateView() {
        for (Ishape shape : model.getShapes()) {
            shape.draw(this);
        }
    }
}
