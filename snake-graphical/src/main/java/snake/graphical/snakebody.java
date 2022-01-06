package snake.graphical;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
public class snakebody {
    int x; int y; int w = 20; int h = 20; Color color = Color.BLUE;
    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillRect(x, y, w, h);
    }
    snakebody(int _x, int _y) {
        x = _x;
        y = _y;
    }
}
