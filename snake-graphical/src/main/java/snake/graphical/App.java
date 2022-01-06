package snake.graphical;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.animation.AnimationTimer;
import javafx.util.Duration;

import javax.swing.text.LabelView;
import java.awt.event.KeyEvent;
import java.util.*;


public class App extends Application {

        Vector<Double> x = new Vector();
        Vector<Double> y = new Vector();
        Vector<Double> applex = new Vector();
        Vector<Double> appley = new Vector();
        ArrayList<Rectangle> snake = new ArrayList<>();
        ArrayList<Circle> apples = new ArrayList<>();
        int width = 1280;
        int height = 800;
        int size = 5;       // change size to increase the snake length
        int step = 10;
        int level = 1;
        int plevel = 0;
        int score = 0;
        int app = 5;
        boolean up = true;
        boolean down = false;
        boolean left = false;
        boolean right = false;
        boolean pause = false;
        boolean increase = false;
        boolean gameover = false;
        int time = 30;
        Label l = new Label();
        Label s = new Label();
        String sco = "score: ";
        AnimationTimer t;
        Timeline clock;

        void initGame() {
                double pos;
                snake.clear();
                apples.clear();

                for (int i = 0; i < size; ++i) {
                        pos = 400 + i * 100 * step;
                        x.addElement(new Double(40));
                        y.addElement(pos);
                        snake.add(new Rectangle(30, 30));
                }
                for (int i = 0; i < 15; ++i) {
                        applex.addElement(new Double((i+1) * 40));
                        appley.addElement(new Double(i+2 * 30));
                        apples.add(new Circle(30, Color.RED));
                }
        }

        void move() {
                for (int i = size-1; i > 0; --i) {
                        double xtemp = x.elementAt(i-1);
                        x.setElementAt(xtemp,i);
                        y.setElementAt(y.elementAt(i-1), i);
                }
                if (up) {
                        y.setElementAt(y.elementAt(0)-step*level,0);
                } else if (down) {
                        y.setElementAt(y.elementAt(0)+step*level,0);
                } else if (left) {
                        x.setElementAt(x.elementAt(0)-step*level,0);
                } else {
                        x.setElementAt(x.elementAt(0)+step*level,0);
                }
                hitTest();
        }
        void hitTest() {
                Random rand = new Random();
                if (up) {
                        if (y.elementAt(0) < 0) {
                                gameover = true;
                        }
                }
                if (down) {
                        if (y.elementAt(0) > height) {
                                gameover = true;
                        }
                }
                if (left) {
                        if (x.elementAt(0) < 0) {
                                gameover = true;
                        }
                }
                if (right) {
                        if (x.elementAt(0) > width) {
                                gameover = true;
                        }
                }

                for (int i = 2; i < size; ++i) {
                        if (Math.abs(x.elementAt(0) - x.elementAt(i)) <= 5 && Math.abs(y.elementAt(0) - y.elementAt(i)) <= 5) {
                                gameover = true;
                        }
                }

             for (int i = 0; i < app; ++i) {
                     if (Math.abs(x.elementAt(0) - applex.elementAt(i)) <= 50 && Math.abs(y.elementAt(0) - appley.elementAt(i)) <= 50) {
                             size++;
                             increase = true;
                             x.addElement(x.elementAt(size - 2));
                             y.addElement(y.elementAt(size - 2));
                             snake.add(new Rectangle(30, 30));
                             score++;
                             applex.setElementAt(rand.nextDouble() * width, i);
                             appley.setElementAt(rand.nextDouble() * height, i);
                             String sound = getClass().getClassLoader().getResource("click.mp3").toString();
                             AudioClip clip = new AudioClip(sound);
                             clip.play();
                     }
             }
        }

        void lab() {
                if (time > 0) {
                        time--;
                        l.setText(Integer.toString(time));
                }
        }

        @Override
    public void start(Stage stage) throws Exception {
            stage.setTitle("Snake Game");
            initGame();
            // scene splash ---------------------------------------------------------

            // Create menu items
            /*MenuBar menubar = new MenuBar();
            Menu fileMenu = new Menu("Option");
            MenuItem fileQuit = new MenuItem("Quit");
            fileMenu.getItems().addAll(fileQuit);

            Menu editMenu = new Menu("Levels");
            MenuItem editCut = new MenuItem("Level1");
            MenuItem editCopy = new MenuItem("Level2");
            MenuItem editPaste = new MenuItem("Level3");
            editMenu.getItems().addAll(editCut, editCopy, editPaste);

            // Map accelerator keys to menu items
            fileQuit.setAccelerator(new KeyCodeCombination(KeyCode.Q));
            editCut.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT1));
            editCopy.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT2));
            editPaste.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT3));

            // Put menus together
            menubar.getMenus().addAll(fileMenu, editMenu);*/


            Text text = new Text(50, 225, "Snake Game \n" +
                    "by Kevin (y3229wan)\n" +
                    "use arrow key to control snake \n" + "click this line for more rules");
                text.setFont(Font.font(40));
            Button btnStart = new Button("Start");
            btnStart.setLayoutX(50);
            btnStart.setLayoutY(400);


            // background
            Rectangle bgs = new Rectangle(1280,800);
            bgs.setFill(Color.GREEN);
            Group root = new Group();
            root.getChildren().add(bgs);
            root.getChildren().add(btnStart);
            root.getChildren().add(text);


            // root.setPadding(new Insets(10));
            Scene sceneSplash = new Scene(root, 1280, 800);

            // scene level one ----------------------------------------------------------------------------------
                // background
                Rectangle bg1 = new Rectangle(1280,800);
                bg1.setFill(Color.GREEN);

                Group root1 = new Group();
            root1.getChildren().add(bg1);
            root1.getChildren().add(l);
            l.setFont(Font.font(100));
            root1.getChildren().add(s);
            s.setFont(Font.font(100));
            s.setLayoutX(800);
            s.setLayoutY(100);
            Scene scene1 = new Scene(root1, 1280, 800);

            // add snake to scene 1
            for (int i = 0; i < size; ++i) {
                    snake.get(i).setX(x.elementAt(i));
                    snake.get(i).setY(y.elementAt(i));
                    root1.getChildren().add(snake.get(i));
            }for (int i = 0; i < app; ++i) {
                    apples.get(i).setCenterX(applex.elementAt(i));
                    apples.get(i).setCenterY(appley.elementAt(i));
                    root1.getChildren().add(apples.get(i));
            }
            // scene level two ----------------------------------------------------------------------------------
            Group root2 = new Group();
                // background
                Rectangle bg2 = new Rectangle(1280,800);
                bg2.setFill(Color.BLUE);
                root2.getChildren().add(bg2);
            Scene scene2 = new Scene(root2, 1280, 800);

            // scene level three ----------------------------------------------------------------------------------
            Group root3 = new Group();
            //root3.getChildren().add(background);
                // background
                Rectangle bg3 = new Rectangle(1280,800);
                bg3.setFill(Color.YELLOW);
                root3.getChildren().add(bg3);
            Scene scene3 = new Scene(root3, 1280, 800);

            // scene end ----------------------------------------------------------------------------------
            Group rootEnd = new Group();
                Rectangle bge = new Rectangle(1280,800);
                bge.setFill(Color.GREEN);
                rootEnd.getChildren().add(bge);
                Text gg = new Text("GameOver\n" + "Your score is " + score);
                gg.setLayoutX(50);
                gg.setLayoutY(100);
                gg.setFont(Font.font(100));
                rootEnd.getChildren().add(gg);
            Scene sceneEnd = new Scene(rootEnd, 1280, 800);

            // scene help -----------------------------------------------------------------------------------
            Group rooth = new Group();
                Rectangle bgh = new Rectangle(1280,800);
                bgh.setFill(Color.GREEN);
                rooth.getChildren().add(bgh);
            Text help = new Text(50,225, "The game screen displays a snake, which is always in motion, and fruit randomly positioned around the screen.\n" +
                    "The direction of the snake can be controlled by the arrows keys. The snake will move forward unless LEFT or RIGHT arrow keys are pressed, which will cause the snake \n" +
                    "to turn in that direction (relative to it's current path).\n" +
                    "The objective of the snake is to eat the fruit. When the snake eats a piece of the fruit disappears and is immediately replaced by another piece of fruit randomly \n" +
                    "positioned. Every time the snake eats a piece of fruit, it gets one block longer.\n" +
                    "A timer ticks down on each level. When the timer runs out, the next level is loaded, with increasingly more fruit (see below). The snake's position and size is not \n" +
                    "changed when the level changes (i.e. it starts a level in the same position/same size as was in the previous level).\n" +
                    "The snake can die by eating itself (when it collides with itself) or by hitting the edge of the screen.");
            rooth.getChildren().add(help);
            Button back = new Button("Start game");
            back.setOnAction(event -> {
                    stage.setTitle("Scene 1");
                    stage.setScene(scene1);
                    String sound = getClass().getClassLoader().getResource("click.mp3").toString();
                    AudioClip clip = new AudioClip(sound);
                    clip.play();
                    t = new AnimationTimer() {
                            @Override
                            public void handle(long now) {

                                    move();
                                    for (int i = 0; i < size; ++i) {
                                            snake.get(i).setX(x.elementAt(i));
                                            snake.get(i).setY(y.elementAt(i));
                                    }
                                    if(increase) {
                                            root1.getChildren().add(snake.get(size-1));
                                            increase = false;
                                    }
                                    for (int i = 0; i < app; ++i) {
                                            apples.get(i).setCenterX(applex.elementAt(i));
                                            apples.get(i).setCenterY(appley.elementAt(i));
                                    }
                                    s.setText(sco+score);
                                    if (gameover) {
                                            t.stop();
                                            clock.stop();
                                            gg.setText("GameOver\n" + "Your score is " + score);
                                            stage.setScene(sceneEnd);
                                    }
                                    if (time == 0 && level == 1) {
                                            time = 30;
                                            level++;
                                            app = 10;
                                            root2.getChildren().add(l);
                                            root2.getChildren().add(s);
                                            for (int i = 0; i < size; ++i) {
                                                    snake.get(i).setX(x.elementAt(i));
                                                    snake.get(i).setY(y.elementAt(i));
                                                    root2.getChildren().add(snake.get(i));
                                            }for (int i = 0; i < app; ++i) {
                                                    apples.get(i).setCenterX(applex.elementAt(i));
                                                    apples.get(i).setCenterY(appley.elementAt(i));
                                                    root2.getChildren().add(apples.get(i));
                                            }
                                            stage.setScene(scene2);
                                    } else if (time == 0 && level == 2) {
                                            level++;
                                            time = -1;
                                            app = 15;
                                            root3.getChildren().add(s);
                                            for (int i = 0; i < size; ++i) {
                                                    snake.get(i).setX(x.elementAt(i));
                                                    snake.get(i).setY(y.elementAt(i));
                                                    root3.getChildren().add(snake.get(i));
                                            }for (int i = 0; i < app; ++i) {
                                                    apples.get(i).setCenterX(applex.elementAt(i));
                                                    apples.get(i).setCenterY(appley.elementAt(i));
                                                    root3.getChildren().add(apples.get(i));
                                            }
                                            stage.setScene(scene3);
                                    }
                            }
                    };
                    clock = new Timeline(new KeyFrame(Duration.seconds(1), e->lab()));
                    clock.setCycleCount(Timeline.INDEFINITE);
                    t.start();
                    clock.play();
            });
            back.setLayoutY(150);
            back.setLayoutX(50);
            rooth.getChildren().add(back);

            Scene sceneH = new Scene(rooth, 1280, 800);

            // Setup handlers
            text.setOnMouseClicked(mouseEvent -> {
                stage.setTitle("How to Play");
                stage.setScene(sceneH);
            });
                btnStart.setOnAction(event -> {
                        stage.setTitle("Scene 1");
                        stage.setScene(scene1);
                        String sound = getClass().getClassLoader().getResource("click.mp3").toString();
                        AudioClip clip = new AudioClip(sound);
                        clip.play();
                        t = new AnimationTimer() {
                                @Override
                                public void handle(long now) {

                                        move();
                                        for (int i = 0; i < size; ++i) {
                                                snake.get(i).setX(x.elementAt(i));
                                                snake.get(i).setY(y.elementAt(i));
                                        }
                                        if(increase) {
                                                root1.getChildren().add(snake.get(size-1));
                                                increase = false;
                                        }
                                        for (int i = 0; i < app; ++i) {
                                                apples.get(i).setCenterX(applex.elementAt(i));
                                                apples.get(i).setCenterY(appley.elementAt(i));

                                        }
                                        s.setText(sco+score);
                                        if (gameover) {
                                                t.stop();
                                                clock.stop();
                                                gg.setText("GameOver\n" + "Your score is " + score);
                                                stage.setScene(sceneEnd);
                                        }
                                        if (time == 0 && level == 1) {
                                                time = 30;
                                                level++;
                                                app=10;
                                                root2.getChildren().add(l);
                                                root2.getChildren().add(s);
                                                for (int i = 0; i < size; ++i) {
                                                        snake.get(i).setX(x.elementAt(i));
                                                        snake.get(i).setY(y.elementAt(i));
                                                        root2.getChildren().add(snake.get(i));
                                                }
                                                for (int i = 0; i < app; ++i) {
                                                        apples.get(i).setCenterX(applex.elementAt(i));
                                                        apples.get(i).setCenterY(appley.elementAt(i));
                                                        root2.getChildren().add(apples.get(i));
                                                }
                                                stage.setScene(scene2);
                                        } else if (time == 0 && level == 2) {
                                                level++;
                                                time = -1;
                                                app=15;
                                                root3.getChildren().add(s);
                                                for (int i = 0; i < size; ++i) {
                                                        snake.get(i).setX(x.elementAt(i));
                                                        snake.get(i).setY(y.elementAt(i));
                                                        root3.getChildren().add(snake.get(i));
                                                }
                                                for (int i = 0; i < app; ++i) {
                                                        apples.get(i).setCenterX(applex.elementAt(i));
                                                        apples.get(i).setCenterY(appley.elementAt(i));
                                                        root3.getChildren().add(apples.get(i));
                                                }
                                                stage.setScene(scene3);
                                        }
                                }
                        };
                        clock = new Timeline(new KeyFrame(Duration.seconds(1), e->lab()));
                        clock.setCycleCount(Timeline.INDEFINITE);
                        t.start();
                        clock.play();
                });
                sceneSplash.setOnKeyPressed(event -> {
                        if (event.getCode()==KeyCode.P) {
                                stage.setTitle("Scene 1");
                                stage.setScene(scene1);
                                String sound = getClass().getClassLoader().getResource("click.mp3").toString();
                                AudioClip clip = new AudioClip(sound);
                                clip.play();
                                t = new AnimationTimer() {
                                        @Override
                                        public void handle(long now) {

                                                move();
                                                for (int i = 0; i < size; ++i) {
                                                        snake.get(i).setX(x.elementAt(i));
                                                        snake.get(i).setY(y.elementAt(i));
                                                }
                                                if(increase) {
                                                        root1.getChildren().add(snake.get(size-1));
                                                        increase = false;
                                                }
                                                for (int i = 0; i < app; ++i) {
                                                        apples.get(i).setCenterX(applex.elementAt(i));
                                                        apples.get(i).setCenterY(appley.elementAt(i));

                                                }
                                                s.setText(sco+score);
                                                if (gameover) {
                                                        t.stop();
                                                        clock.stop();
                                                        gg.setText("GameOver\n" + "Your score is " + score);
                                                        stage.setScene(sceneEnd);
                                                }
                                                if (time == 0 && level == 1) {
                                                        time = 30;
                                                        level++;
                                                        app=10;
                                                        root2.getChildren().add(l);
                                                        root2.getChildren().add(s);
                                                        for (int i = 0; i < size; ++i) {
                                                                snake.get(i).setX(x.elementAt(i));
                                                                snake.get(i).setY(y.elementAt(i));
                                                                root2.getChildren().add(snake.get(i));
                                                        }for (int i = 0; i < app; ++i) {
                                                                apples.get(i).setCenterX(applex.elementAt(i));
                                                                apples.get(i).setCenterY(appley.elementAt(i));
                                                                root2.getChildren().add(apples.get(i));
                                                        }

                                                        stage.setScene(scene2);
                                                } else if (time == 0 && level == 2) {
                                                        level++;
                                                        time = -1;
                                                        app=15;
                                                        root3.getChildren().add(s);
                                                        for (int i = 0; i < size; ++i) {
                                                                snake.get(i).setX(x.elementAt(i));
                                                                snake.get(i).setY(y.elementAt(i));
                                                                root3.getChildren().add(snake.get(i));
                                                        }
                                                        for (int i = 0; i < app; ++i) {
                                                                apples.get(i).setCenterX(applex.elementAt(i));
                                                                apples.get(i).setCenterY(appley.elementAt(i));
                                                                root2.getChildren().add(apples.get(i));
                                                        }
                                                        stage.setScene(scene3);
                                                }
                                        }
                                };
                                clock = new Timeline(new KeyFrame(Duration.seconds(1), e -> lab()));
                                clock.setCycleCount(Timeline.INDEFINITE);
                                t.start();
                                clock.play();
                        }});



            // listener
                sceneSplash.setOnKeyPressed(event -> {
                        if(event.getCode() == KeyCode.LEFT) {
                                if (up) {
                                        up = false;
                                        left = true;
                                } else if (down) {
                                        down = false;
                                        right = true;
                                } else if (left) {
                                        left = false;
                                        down = true;
                                } else {
                                        up = true;
                                        right = false;
                                }
                        } else if(event.getCode() == KeyCode.RIGHT) {
                                if (up) {
                                        up = false;
                                        right = true;
                                } else if (down) {
                                        down = false;
                                        left = true;
                                } else if (left) {
                                        left = false;
                                        up = true;
                                } else {
                                        down = true;
                                        right = false;
                                }
                        }
                        if (event.getCode() == KeyCode.P) {
                                if (pause) {

                                } else {
                                        t.stop();
                                        clock.stop();
                                        stage.setScene(sceneSplash);
                                }
                        }
                        if (event.getCode() == KeyCode.R) {
                                time = 30;
                                level = 1;
                                size = 5;
                                t.stop();
                                clock.stop();

                                stage.setScene(sceneSplash);
                        }
                        if (event.getCode() == KeyCode.Q) {
                                t.stop();
                                clock.stop();
                                gg.setText("GameOver\n" + "Your score is " + score);
                                stage.setScene(sceneEnd);
                        }
                        if (event.getCode() == KeyCode.DIGIT1) {
                                time = 30;
                                app=5;
                                stage.setScene(scene1);
                                t = new AnimationTimer() {
                                        @Override
                                        public void handle(long now) {

                                                move();
                                                for (int i = 0; i < size; ++i) {
                                                        snake.get(i).setX(x.elementAt(i));
                                                        snake.get(i).setY(y.elementAt(i));
                                                }
                                                if(increase) {
                                                        root1.getChildren().add(snake.get(size-1));
                                                        increase = false;
                                                }
                                                for (int i = 0; i < app; ++i) {
                                                        apples.get(i).setCenterX(applex.elementAt(i));
                                                        apples.get(i).setCenterY(appley.elementAt(i));

                                                }
                                                s.setText(sco+score);
                                                if (gameover) {
                                                        t.stop();
                                                        clock.stop();
                                                        gg.setText("GameOver\n" + "Your score is " + score);
                                                        stage.setScene(sceneEnd);
                                                }
                                                if (time == 0 && level == 1) {
                                                        time = 30;
                                                        level++;
                                                        app=10;
                                                        root2.getChildren().add(l);
                                                        root2.getChildren().add(s);
                                                        for (int i = 0; i < size; ++i) {
                                                                snake.get(i).setX(x.elementAt(i));
                                                                snake.get(i).setY(y.elementAt(i));
                                                                root2.getChildren().add(snake.get(i));
                                                        }for (int i = 0; i < app; ++i) {
                                                                apples.get(i).setCenterX(applex.elementAt(i));
                                                                apples.get(i).setCenterY(appley.elementAt(i));
                                                                root2.getChildren().add(apples.get(i));
                                                        }

                                                        stage.setScene(scene2);
                                                } else if (time == 0 && level == 2) {
                                                        level++;
                                                        time = -1;
                                                        app=15;
                                                        root3.getChildren().add(s);
                                                        for (int i = 0; i < size; ++i) {
                                                                snake.get(i).setX(x.elementAt(i));
                                                                snake.get(i).setY(y.elementAt(i));
                                                                root3.getChildren().add(snake.get(i));
                                                        }
                                                        for (int i = 0; i < app; ++i) {
                                                                apples.get(i).setCenterX(applex.elementAt(i));
                                                                apples.get(i).setCenterY(appley.elementAt(i));
                                                                root2.getChildren().add(apples.get(i));
                                                        }
                                                        stage.setScene(scene3);
                                                }
                                        }
                                };
                                clock = new Timeline(new KeyFrame(Duration.seconds(1), e -> lab()));
                                clock.setCycleCount(Timeline.INDEFINITE);
                                t.start();
                                clock.play();

                        }
                        if (event.getCode() == KeyCode.DIGIT2) {
                                time = 30;
                                app=10;
                                if (level != 2) {
                                        root2.getChildren().add(l);
                                        root2.getChildren().add(s);
                                }
                                level = 2;
                                t = new AnimationTimer() {
                                        @Override
                                        public void handle(long now) {

                                                move();
                                                for (int i = 0; i < size; ++i) {
                                                        snake.get(i).setX(x.elementAt(i));
                                                        snake.get(i).setY(y.elementAt(i));
                                                }
                                                if(increase) {
                                                        root1.getChildren().add(snake.get(size-1));
                                                        increase = false;
                                                }
                                                for (int i = 0; i < app; ++i) {
                                                        apples.get(i).setCenterX(applex.elementAt(i));
                                                        apples.get(i).setCenterY(appley.elementAt(i));

                                                }
                                                s.setText(sco+score);
                                                if (gameover) {
                                                        t.stop();
                                                        clock.stop();
                                                        gg.setText("GameOver\n" + "Your score is " + score);
                                                        stage.setScene(sceneEnd);
                                                }
                                                if (time == 0 && level == 1) {
                                                        time = 30;
                                                        level++;
                                                        app=10;
                                                        root2.getChildren().add(l);
                                                        root2.getChildren().add(s);
                                                        for (int i = 0; i < size; ++i) {
                                                                snake.get(i).setX(x.elementAt(i));
                                                                snake.get(i).setY(y.elementAt(i));
                                                                root2.getChildren().add(snake.get(i));
                                                        }for (int i = 0; i < app; ++i) {
                                                                apples.get(i).setCenterX(applex.elementAt(i));
                                                                apples.get(i).setCenterY(appley.elementAt(i));
                                                                root2.getChildren().add(apples.get(i));
                                                        }

                                                        stage.setScene(scene2);
                                                } else if (time == 0 && level == 2) {
                                                        level++;
                                                        time = -1;
                                                        app=15;
                                                        root3.getChildren().add(s);
                                                        for (int i = 0; i < size; ++i) {
                                                                snake.get(i).setX(x.elementAt(i));
                                                                snake.get(i).setY(y.elementAt(i));
                                                                root3.getChildren().add(snake.get(i));
                                                        }
                                                        for (int i = 0; i < app; ++i) {
                                                                apples.get(i).setCenterX(applex.elementAt(i));
                                                                apples.get(i).setCenterY(appley.elementAt(i));
                                                                root2.getChildren().add(apples.get(i));
                                                        }
                                                        stage.setScene(scene3);
                                                }
                                        }
                                };
                                clock = new Timeline(new KeyFrame(Duration.seconds(1), e -> lab()));
                                clock.setCycleCount(Timeline.INDEFINITE);
                                t.start();
                                clock.play();
                                for (int i = 0; i < size; ++i) {
                                        snake.get(i).setX(x.elementAt(i));
                                        snake.get(i).setY(y.elementAt(i));
                                        root2.getChildren().add(snake.get(i));
                                }
                                for (int i = 0; i < app; ++i) {
                                        apples.get(i).setCenterX(applex.elementAt(i));
                                        apples.get(i).setCenterY(appley.elementAt(i));
                                        root2.getChildren().add(apples.get(i));
                                }
                                stage.setScene(scene2);
                        }
                        if (event.getCode() == KeyCode.DIGIT3) {
                                time = 30;
                                level = 3;
                                plevel = 1;
                                app=15;
                                root3.getChildren().add(s);
                                for (int i = 0; i < size; ++i) {
                                        snake.get(i).setX(x.elementAt(i));
                                        snake.get(i).setY(y.elementAt(i));
                                        root3.getChildren().add(snake.get(i));
                                }
                                for (int i = 0; i < app; ++i) {
                                        apples.get(i).setCenterX(applex.elementAt(i));
                                        apples.get(i).setCenterY(appley.elementAt(i));
                                        root3.getChildren().add(apples.get(i));
                                }
                                t = new AnimationTimer() {
                                        @Override
                                        public void handle(long now) {

                                                move();
                                                for (int i = 0; i < size; ++i) {
                                                        snake.get(i).setX(x.elementAt(i));
                                                        snake.get(i).setY(y.elementAt(i));
                                                }
                                                if(increase) {
                                                        root1.getChildren().add(snake.get(size-1));
                                                        increase = false;
                                                }
                                                for (int i = 0; i < app; ++i) {
                                                        apples.get(i).setCenterX(applex.elementAt(i));
                                                        apples.get(i).setCenterY(appley.elementAt(i));

                                                }
                                                s.setText(sco+score);
                                                if (gameover) {
                                                        t.stop();
                                                        clock.stop();
                                                        gg.setText("GameOver\n" + "Your score is " + score);
                                                        stage.setScene(sceneEnd);
                                                }
                                                if (time == 0 && level == 1) {
                                                        time = 30;
                                                        level++;
                                                        app=10;
                                                        root2.getChildren().add(l);
                                                        root2.getChildren().add(s);
                                                        for (int i = 0; i < size; ++i) {
                                                                snake.get(i).setX(x.elementAt(i));
                                                                snake.get(i).setY(y.elementAt(i));
                                                                root2.getChildren().add(snake.get(i));
                                                        }for (int i = 0; i < app; ++i) {
                                                                apples.get(i).setCenterX(applex.elementAt(i));
                                                                apples.get(i).setCenterY(appley.elementAt(i));
                                                                root2.getChildren().add(apples.get(i));
                                                        }

                                                        stage.setScene(scene2);
                                                } else if (time == 0 && level == 2) {
                                                        level++;
                                                        time = -1;
                                                        app=15;
                                                        root3.getChildren().add(s);
                                                        for (int i = 0; i < size; ++i) {
                                                                snake.get(i).setX(x.elementAt(i));
                                                                snake.get(i).setY(y.elementAt(i));
                                                                root3.getChildren().add(snake.get(i));
                                                        }
                                                        for (int i = 0; i < app; ++i) {
                                                                apples.get(i).setCenterX(applex.elementAt(i));
                                                                apples.get(i).setCenterY(appley.elementAt(i));
                                                                root2.getChildren().add(apples.get(i));
                                                        }
                                                        stage.setScene(scene3);
                                                }
                                        }
                                };
                                clock = new Timeline(new KeyFrame(Duration.seconds(1), e -> lab()));
                                clock.setCycleCount(Timeline.INDEFINITE);
                                t.start();
                                clock.play();
                                stage.setScene(scene3);
                        }
                });
                scene1.setOnKeyPressed(event -> {
                        if(event.getCode() == KeyCode.LEFT) {
                                if (up) {
                                        up = false;
                                        left = true;
                                } else if (down) {
                                        down = false;
                                        right = true;
                                } else if (left) {
                                        left = false;
                                        down = true;
                                } else {
                                        up = true;
                                        right = false;
                                }
                        } else if(event.getCode() == KeyCode.RIGHT) {
                                if (up) {
                                        up = false;
                                        right = true;
                                } else if (down) {
                                        down = false;
                                        left = true;
                                } else if (left) {
                                        left = false;
                                        up = true;
                                } else {
                                        down = true;
                                        right = false;
                                }
                        }
                        if (event.getCode() == KeyCode.P) {
                                if (pause) {

                                } else {
                                        t.stop();
                                        clock.stop();
                                        stage.setScene(sceneSplash);
                                }
                        }
                        if (event.getCode() == KeyCode.R) {

                                t.stop();
                                clock.stop();

                                stage.setScene(sceneSplash);
                        }
                        if (event.getCode() == KeyCode.Q) {
                                t.stop();
                                clock.stop();
                                gg.setText("GameOver\n" + "Your score is " + score);
                                stage.setScene(sceneEnd);
                        }
                        if (event.getCode() == KeyCode.DIGIT1) {
                                time = 30;
                                app=5;
                                stage.setScene(scene1);

                        }
                        if (event.getCode() == KeyCode.DIGIT2) {
                                time = 30;
                                app=10;
                                if (level != 2) {
                                        root2.getChildren().add(l);
                                        root2.getChildren().add(s);
                                }
                                level = 2;
                                for (int i = 0; i < size; ++i) {
                                        snake.get(i).setX(x.elementAt(i));
                                        snake.get(i).setY(y.elementAt(i));
                                        root2.getChildren().add(snake.get(i));
                                }
                                for (int i = 0; i < app; ++i) {
                                        apples.get(i).setCenterX(applex.elementAt(i));
                                        apples.get(i).setCenterY(appley.elementAt(i));
                                        root2.getChildren().add(apples.get(i));
                                }
                                stage.setScene(scene2);
                        }
                        if (event.getCode() == KeyCode.DIGIT3) {
                                time = 30;
                                level = 3;
                                plevel = 1;
                                app=15;
                                root3.getChildren().add(s);
                                for (int i = 0; i < size; ++i) {
                                        snake.get(i).setX(x.elementAt(i));
                                        snake.get(i).setY(y.elementAt(i));
                                        root3.getChildren().add(snake.get(i));
                                }
                                for (int i = 0; i < app; ++i) {
                                        apples.get(i).setCenterX(applex.elementAt(i));
                                        apples.get(i).setCenterY(appley.elementAt(i));
                                        root3.getChildren().add(apples.get(i));
                                }
                                stage.setScene(scene3);
                        }
                });
                scene2.setOnKeyPressed(event -> {
                        if(event.getCode() == KeyCode.LEFT) {
                                if (up) {
                                        up = false;
                                        left = true;
                                } else if (down) {
                                        down = false;
                                        right = true;
                                } else if (left) {
                                        left = false;
                                        down = true;
                                } else {
                                        up = true;
                                        right = false;
                                }
                        } else if(event.getCode() == KeyCode.RIGHT) {
                                if (up) {
                                        up = false;
                                        right = true;
                                } else if (down) {
                                        down = false;
                                        left = true;
                                } else if (left) {
                                        left = false;
                                        up = true;
                                } else {
                                        down = true;
                                        right = false;
                                }
                        }
                        if (event.getCode() == KeyCode.P) {
                                if (pause) {

                                } else {
                                        t.stop();
                                        clock.stop();
                                        stage.setScene(sceneSplash);
                                }
                        }
                        if (event.getCode() == KeyCode.R) {
                                time = 30;
                                level = 1;
                                size = 5;
                                t.stop();
                                clock.stop();
                                stage.setScene(sceneSplash);
                        }
                        if (event.getCode() == KeyCode.Q) {
                                t.stop();
                                clock.stop();
                                gg.setText("GameOver\n" + "Your score is " + score);
                                stage.setScene(sceneEnd);
                        }
                        if (event.getCode() == KeyCode.DIGIT1) {
                                time = 30;
                                app=5;
                                if (level != 1) {
                                        root1.getChildren().add(l);
                                        root1.getChildren().add(s);
                                }
                                level = 1;
                                for (int i = 0; i < size; ++i) {
                                        snake.get(i).setX(x.elementAt(i));
                                        snake.get(i).setY(y.elementAt(i));
                                        root1.getChildren().add(snake.get(i));
                                }
                                for (int i = 0; i < app; ++i) {
                                        apples.get(i).setCenterX(applex.elementAt(i));
                                        apples.get(i).setCenterY(appley.elementAt(i));
                                        root1.getChildren().add(apples.get(i));
                                }
                                stage.setScene(scene1);

                        }
                        if (event.getCode() == KeyCode.DIGIT2) {
                                time = 30;
                                stage.setScene(scene2);
                        }
                        if (event.getCode() == KeyCode.DIGIT3) {
                                time = -1;
                                level = 3;
                                plevel = 2;
                                app=15;
                                root3.getChildren().add(s);
                                for (int i = 0; i < size; ++i) {
                                        snake.get(i).setX(x.elementAt(i));
                                        snake.get(i).setY(y.elementAt(i));
                                        root3.getChildren().add(snake.get(i));
                                }
                                for (int i = 0; i < app; ++i) {
                                        apples.get(i).setCenterX(applex.elementAt(i));
                                        apples.get(i).setCenterY(appley.elementAt(i));
                                        root3.getChildren().add(apples.get(i));
                                }
                                stage.setScene(scene3);
                        }
                });
                scene3.setOnKeyPressed(event -> {
                        if(event.getCode() == KeyCode.LEFT) {
                                if (up) {
                                        up = false;
                                        left = true;
                                } else if (down) {
                                        down = false;
                                        right = true;
                                } else if (left) {
                                        left = false;
                                        down = true;
                                } else {
                                        up = true;
                                        right = false;
                                }
                        } else if(event.getCode() == KeyCode.RIGHT) {
                                if (up) {
                                        up = false;
                                        right = true;
                                } else if (down) {
                                        down = false;
                                        left = true;
                                } else if (left) {
                                        left = false;
                                        up = true;
                                } else {
                                        down = true;
                                        right = false;
                                }
                        }
                        if (event.getCode() == KeyCode.P) {
                                if (pause) {

                                } else {
                                        t.stop();
                                        clock.stop();
                                        stage.setScene(sceneSplash);
                                }
                        }
                        if (event.getCode() == KeyCode.R) {
                                time = 30;
                                level = 1;
                                t.stop();
                                clock.stop();
                                stage.setScene(sceneSplash);
                        }
                        if (event.getCode() == KeyCode.Q) {
                                t.stop();
                                clock.stop();
                                gg.setText("GameOver\n" + "Your score is " + score);
                                stage.setScene(sceneEnd);
                        }
                        if (event.getCode() == KeyCode.DIGIT1) {
                                time = 30;
                                app=5;
                                if (plevel != 1) {
                                        root1.getChildren().add(l);
                                        root1.getChildren().add(s);
                                }
                                level = 1;
                                plevel = 3;
                                for (int i = 0; i < size; ++i) {
                                        snake.get(i).setX(x.elementAt(i));
                                        snake.get(i).setY(y.elementAt(i));
                                        root1.getChildren().add(snake.get(i));
                                }
                                for (int i = 0; i < app; ++i) {
                                        apples.get(i).setCenterX(applex.elementAt(i));
                                        apples.get(i).setCenterY(appley.elementAt(i));
                                        root1.getChildren().add(apples.get(i));
                                }
                                stage.setScene(scene1);

                        }
                        if (event.getCode() == KeyCode.DIGIT2) {
                                time = 30;
                                app=10;
                                if (plevel != 2) {
                                        root2.getChildren().add(l);
                                        root2.getChildren().add(s);
                                }
                                level = 2;
                                plevel = 3;
                                for (int i = 0; i < size; ++i) {
                                        snake.get(i).setX(x.elementAt(i));
                                        snake.get(i).setY(y.elementAt(i));
                                        root2.getChildren().add(snake.get(i));
                                }
                                for (int i = 0; i < app; ++i) {
                                        apples.get(i).setCenterX(applex.elementAt(i));
                                        apples.get(i).setCenterY(appley.elementAt(i));
                                        root2.getChildren().add(apples.get(i));
                                }
                                stage.setScene(scene2);
                        }
                        if (event.getCode() == KeyCode.DIGIT3) {
                                time = -1;
                                level = 3;
                                stage.setScene(scene3);
                        }
                });
            // show starting scene
            stage.setTitle("Snake Game");
            stage.setScene(sceneSplash);
            stage.show();



    }

}


