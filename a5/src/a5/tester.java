/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class tester extends Application {

    Pane p;
    double orgSceneX, orgSceneY;

    private Ellipse createEllipse(double centerX, double centerY, double radiusX, double radiusY, Color color) {
        Ellipse ellipse = new Ellipse(centerX, centerY, radiusX, radiusY);
        ellipse.setFill(color);
        ellipse.setCursor(Cursor.HAND);

        ellipse.setOnMousePressed((t) -> {
            if (t.isSecondaryButtonDown()) { // check if right mouse button is pressed
                // create a toolbar with sliders and color picker
                HBox toolbar = new HBox();

                Slider xslider = new Slider(10, 100, radiusX); // create a slider for x radius
                xslider.setShowTickMarks(true);
                xslider.setShowTickLabels(true);
                xslider.setMajorTickUnit(10);
                xslider.setBlockIncrement(5);

                Slider yslider = new Slider(10, 100, radiusY); // create a slider for y radius
                yslider.setShowTickMarks(true);
                yslider.setShowTickLabels(true);
                yslider.setMajorTickUnit(10);
                yslider.setBlockIncrement(5);

                Button colorButton = new Button("Color");
                colorButton.setOnAction((event) -> {
                    ColorPicker colorPicker = new ColorPicker((Color) ellipse.getFill());
                    colorPicker.setOnAction((e) -> {
                        ellipse.setFill(colorPicker.getValue());
                    });

                    Stage colorPickerStage = new Stage();
                    colorPickerStage.setScene(new Scene(colorPicker));
                    colorPickerStage.show();
                });

                HBox colorBox = new HBox(colorButton);

                // create delete button
                Button deleteButton = new Button("Delete");
                deleteButton.setOnAction((event) -> {
                    Pane parent = (Pane) ellipse.getParent();
                    parent.getChildren().remove(ellipse);
                });
                toolbar.getChildren().addAll(deleteButton, xslider, yslider, colorBox);

                // create a new scene for the toolbar and show it
                Scene toolbarScene = new Scene(toolbar, 400, 50);
                Stage toolbarStage = new Stage();
                toolbarStage.setScene(toolbarScene);
                toolbarStage.show();

                // add a listener to the slider to update the ellipse's radius
                xslider.valueProperty().addListener((observable, oldValue, newValue) -> {
                    ellipse.setRadiusX(newValue.doubleValue());
                });

                yslider.valueProperty().addListener((observable, oldValue, newValue) -> {
                    ellipse.setRadiusY(newValue.doubleValue());
                });
            } else { // left mouse button is pressed
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();

                Ellipse e = (Ellipse) (t.getSource());
                e.toFront();
            }
        });

        ellipse.setOnMouseDragged((t) -> {
            if (!t.isSecondaryButtonDown()) { // check if left mouse button is not pressed
                double offsetX = t.getSceneX() - orgSceneX;
                double offsetY = t.getSceneY() - orgSceneY;

                Ellipse e = (Ellipse) (t.getSource());
                e.setCenterX(e.getCenterX() + offsetX);
                e.setCenterY(e.getCenterY() + offsetY);

                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
            }
        });

        return ellipse;
    }

    private Rectangle createRectangle(double x, double y, Color color) {
        Rectangle rectangle = new Rectangle(x, y, color);

        rectangle.setCursor(Cursor.HAND);

        rectangle.setOnMousePressed((t) -> {
            if (t.isSecondaryButtonDown()) { // check if right mouse button is pressed
                // create a toolbar with a slider and color picker
                HBox toolbar = new HBox();
                Slider xslider = new Slider(10, 100, x); // create a slider with a range of 10 to 100 and initial value of r
                xslider.setShowTickMarks(true);
                xslider.setShowTickLabels(true);
                xslider.setMajorTickUnit(10);
                xslider.setBlockIncrement(5);

                Slider yslider = new Slider(10, 100, y); // create a slider with a range of 10 to 100 and initial value of r
                yslider.setShowTickMarks(true);
                yslider.setShowTickLabels(true);
                yslider.setMajorTickUnit(10);
                yslider.setBlockIncrement(5);

                Button colorButton = new Button("Color");
                colorButton.setOnAction((event) -> {
                    ColorPicker colorPicker = new ColorPicker((Color) rectangle.getFill());
                    colorPicker.setOnAction((e) -> {
                        rectangle.setFill(colorPicker.getValue());
                    });

                    Stage colorPickerStage = new Stage();
                    colorPickerStage.setScene(new Scene(colorPicker));
                    colorPickerStage.show();
                });

                HBox colorBox = new HBox(colorButton);

                //create delete button
                Button deleteButton = new Button("Delete");
                deleteButton.setOnAction((event) -> {
                    Pane parent = (Pane) rectangle.getParent();
                    parent.getChildren().remove(rectangle);
                });
                toolbar.getChildren().addAll(deleteButton, xslider, yslider, colorBox);

                // create a new scene for the toolbar and show it
                Scene toolbarScene = new Scene(toolbar, 400, 50);
                Stage toolbarStage = new Stage();
                toolbarStage.setScene(toolbarScene);
                toolbarStage.show();

                // add a listener to the slider to update the rectangles width and height
                xslider.valueProperty().addListener((observable, oldValue, newValue) -> {
                    rectangle.setWidth(newValue.doubleValue());
                });
                yslider.valueProperty().addListener((observable, oldValue, newValue) -> {
                    rectangle.setHeight(newValue.doubleValue());
                });
            } else { // left mouse button is pressed
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();

                Rectangle r = (Rectangle) (t.getSource());
                r.toFront();
            }
        });

        rectangle.setOnMouseDragged((t) -> {
            if (!t.isSecondaryButtonDown()) { // check if left mouse button is not pressed
                double offsetX = t.getSceneX() - orgSceneX;
                double offsetY = t.getSceneY() - orgSceneY;

                Rectangle r = (Rectangle) (t.getSource());
                r.setX(r.getX() + offsetX);
                r.setY(r.getY() + offsetY);

                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
            }
        });

        return rectangle;
    }

    private Circle createCircle(double x, double y, double r, Color color) {
        Circle circle = new Circle(x, y, r, color);

        circle.setCursor(Cursor.HAND);

        circle.setOnMousePressed((t) -> {
            if (t.isSecondaryButtonDown()) { // check if right mouse button is pressed
                // create a toolbar with a slider and color picker
                BorderPane toolbar = new BorderPane();
                Slider slider = new Slider(10, 100, r); // create a slider with a range of 10 to 100 and initial value of r
                slider.setShowTickMarks(true);
                slider.setShowTickLabels(true);
                slider.setMajorTickUnit(10);
                slider.setBlockIncrement(5);
                toolbar.setCenter(slider);

                Button colorButton = new Button("Color");
                colorButton.setOnAction((event) -> {
                    ColorPicker colorPicker = new ColorPicker((Color) circle.getFill());
                    colorPicker.setOnAction((e) -> {
                        circle.setFill(colorPicker.getValue());
                    });

                    Stage colorPickerStage = new Stage();
                    colorPickerStage.setScene(new Scene(colorPicker));
                    colorPickerStage.show();
                });

                HBox colorBox = new HBox(colorButton);
                toolbar.setRight(colorBox);

                //create delete button
                Button deleteButton = new Button("Delete");
                deleteButton.setOnAction((event) -> {
                    Pane parent = (Pane) circle.getParent();
                    parent.getChildren().remove(circle);
                });
                toolbar.setLeft(deleteButton);

                // create a new scene for the toolbar and show it
                Scene toolbarScene = new Scene(toolbar, 250, 50);
                Stage toolbarStage = new Stage();
                toolbarStage.setScene(toolbarScene);
                toolbarStage.show();

                // add a listener to the slider to update the circle's radius
                slider.valueProperty().addListener((observable, oldValue, newValue) -> {
                    circle.setRadius(newValue.doubleValue());
                });
            } else { // left mouse button is pressed
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();

                Circle c = (Circle) (t.getSource());
                c.toFront();
            }
        });

        circle.setOnMouseDragged((t) -> {
            if (!t.isSecondaryButtonDown()) { // check if left mouse button is not pressed
                double offsetX = t.getSceneX() - orgSceneX;
                double offsetY = t.getSceneY() - orgSceneY;

                Circle c = (Circle) (t.getSource());

                c.setCenterX(c.getCenterX() + offsetX);
                c.setCenterY(c.getCenterY() + offsetY);

                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
            }
        });

        return circle;
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        p = new Pane();
        root.setCenter(p);
        Polyline polyline = new Polyline();

        // create the menu bar
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu shapeMenu = new Menu("Shape");
        Menu helpMenu = new Menu("Help");

        // create the "New" menu item and set its action
        MenuItem newMenuItem = new MenuItem("New");
        newMenuItem.setOnAction((event) -> {
            p.getChildren().clear();
        });

        // create the "Save" menu item and set its action
        MenuItem saveMenuItem = new MenuItem("Save");
        saveMenuItem.setOnAction((event) -> {
            save();
        });

        // create the "Load" menu item and set its action
        MenuItem loadMenuItem = new MenuItem("Load");
        loadMenuItem.setOnAction((event) -> {
            load();
        });

        // Create "New Circle" menu item
        MenuItem newCircleMenuItem = new MenuItem("New Circle");
        newCircleMenuItem.setOnAction(event -> {
            double x = Math.random() * root.getWidth();
            double y = Math.random() * root.getHeight();
            double r = 50;
            Color color = Color.RED;

            Circle circle = createCircle(x, y, r, color);
            p.getChildren().add(circle);
        });

        // Create "New Rectangle" menu item
        MenuItem newRectangleMenuItem = new MenuItem("New Rectangle");
        newRectangleMenuItem.setOnAction(event -> {
            double x = Math.random() * root.getWidth();
            double y = Math.random() * root.getHeight();

            Color color = Color.BLUE;

            Rectangle rec = createRectangle(x, y, color);
            p.getChildren().add(rec);
        });

// Create "New Ellipse" menu item
        MenuItem newEllipseMenuItem = new MenuItem("New Ellipse");
        newEllipseMenuItem.setOnAction(event -> {
            double centerX = Math.random() * root.getWidth();
            double centerY = Math.random() * root.getHeight();
            double radiusX = 50;
            double radiusY = 30;
            Color color = Color.GREEN;

            Ellipse ellipse = createEllipse(centerX, centerY, radiusX, radiusY, color);
            p.getChildren().add(ellipse);
        });
      // Create the toolbar for the Draw Line option
BorderPane toolbar = new BorderPane();
Slider slider = new Slider(10, 100, lineWidth); // create a slider with a range of 10 to 100 and initial value of lineWidth
slider.setShowTickMarks(true);
slider.setShowTickLabels(true);
slider.setMajorTickUnit(10);
slider.setBlockIncrement(5);
toolbar.setCenter(slider);

Button colorButton = new Button("Color");
colorButton.setOnAction((event) -> {
    ColorPicker colorPicker = new ColorPicker(lineColor);
    colorPicker.setOnAction((e) -> {
        lineColor = colorPicker.getValue();
        polyline.setStroke(lineColor);
    });

    Stage colorPickerStage = new Stage();
    colorPickerStage.setScene(new Scene(colorPicker));
    colorPickerStage.show();
});

HBox colorBox = new HBox(colorButton);
toolbar.setRight(colorBox);

//create delete button
Button deleteButton = new Button("Delete");
deleteButton.setOnAction((event) -> {
    Pane parent = (Pane) polyline.getParent();
    parent.getChildren().remove(polyline);
});
toolbar.setLeft(deleteButton);

// create a new scene for the toolbar
Scene toolbarScene = new Scene(toolbar, 250, 50);

// Create the menu item for Draw Line
MenuItem drawLineMenuItem = new MenuItem("Draw Line");

drawLineMenuItem.setOnAction(event -> {
    // Create a new polyline
    Polyline polyline = new Polyline();

    // Set a mouse event handler to add points to the polyline on left click and drag
    p.setOnMousePressed(mouseEvent -> {
        if (mouseEvent.isPrimaryButtonDown()) {
            polyline.getPoints().addAll(mouseEvent.getX(), mouseEvent.getY());
            p.getChildren().add(polyline);
            toolbar.setVisible(true);
        }
    });

    p.setOnMouseDragged(mouseEvent -> {
        if (mouseEvent.isPrimaryButtonDown()) {
            polyline.getPoints().addAll(mouseEvent.getX(), mouseEvent.getY());
        }
    });

    // Set a mouse event handler to allow the user to select the polyline with a left click
    p.setOnMouseClicked(mouseEvent -> {
        // Check if left mouse button is pressed
        if (mouseEvent.isPrimaryButtonDown()) {
            polyline.toFront();
        }
        // Check if right mouse button is pressed
        else if (mouseEvent.isSecondaryButtonDown()) {
            // show the toolbar
            toolbarStage.setScene(toolbarScene);
            toolbarStage.show();
        }
    });

    // add a listener to the slider to update the polyline's width
    slider.valueProperty().addListener((observable, oldValue, newValue) -> {
        lineWidth = newValue.doubleValue();
        polyline.setStrokeWidth(lineWidth);
    });

    // Set the initial color and width of the polyline
    polyline.setStroke(lineColor);
    polyline.setStrokeWidth(lineWidth);
});

// Create a context menu with the Draw Line menu item
ContextMenu contextMenu = new ContextMenu(drawLineMenuItem);

// Set the context menu for the pane
p.setContextMenu(contextMenu);

// Create a new stage for the toolbar
Stage toolbarStage = new Stage();
toolbarStage.initOwner(p.getScene().getWindow());
toolbarStage.initModality(Modality.WINDOW_MODAL);
toolbarStage.setScene(toolbarScene);
toolbarStage.setTitle("Line Properties");
toolbarStage.setOnCloseRequest(event -> toolbar.setVisible(false));


        // add the menu items to the file menu
        fileMenu.getItems().addAll(newMenuItem, saveMenuItem, loadMenuItem);
        shapeMenu.getItems().addAll(newCircleMenuItem, newRectangleMenuItem, newEllipseMenuItem, drawLineMenuItem);
        menuBar.getMenus().addAll(fileMenu, shapeMenu, helpMenu);

        // Create a VBox to hold the menu bar and other nodes
        VBox menuBox = new VBox();
        menuBox.getChildren().add(menuBar);

        root.setTop(menuBox);

        //create shapes
        Circle redCircle = createCircle(100, 50, 30, Color.RED);
        Rectangle blueRectangle = createRectangle(20, 50, Color.BLUE);
        Ellipse greenEllipse = createEllipse(40, 100, 40, 20, Color.GREEN);

        //add shapes
        p.getChildren().add(redCircle);
        p.getChildren().add(blueRectangle);
        p.getChildren().add(greenEllipse);

        Scene scene = new Scene(root, 640, 480);

        primaryStage.setTitle("Save and load");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void save() {
        try {
            try (PrintStream fw = new PrintStream(new FileOutputStream("myDrawing.txt"))) {
                List<Node> shapes = p.getChildren();
                fw.println(shapes.size());
                for (int i = 0; i < shapes.size(); i++) {
                    Node n = shapes.get(i);
                    if (n instanceof Circle) {
                        fw.println("Circle");
                        Circle c = (Circle) n;
                        fw.println("" + c.getCenterX());
                        fw.println("" + c.getCenterY());
                        fw.println("" + c.getRadius());
                        fw.println(c.getFill().toString());
                    }
                    if (n instanceof Rectangle) {
                        fw.println("Rectangle");
                        Rectangle c = (Rectangle) n;
                        fw.println("" + c.getX());
                        fw.println("" + c.getY());
                        fw.println("" + c.getWidth());
                        fw.println("" + c.getHeight());
                        fw.println(c.getFill().toString());
                    } //Handle the other types of Shapes also
                    else if (n instanceof Ellipse) {
                        fw.println("Ellipse");
                        Ellipse e = (Ellipse) n;
                        fw.println("" + e.getCenterX());
                        fw.println("" + e.getCenterY());
                        fw.println("" + e.getRadiusX());
                        fw.println("" + e.getRadiusY());
                        fw.println(e.getFill().toString());
                    }
                }
                fw.flush();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Something terrible happened");
        }

    }

    private void load() {
        try {
            Scanner s = new Scanner(new File("myDrawing.txt"));
            int nShapes = Integer.parseInt(s.nextLine());
            System.out.println(nShapes);
            for (int i = 0; i < nShapes; i++) {
                String shapeType = s.nextLine();
                if (shapeType.equals("Circle")) {
                    double x = Double.parseDouble(s.nextLine());
                    double y = Double.parseDouble(s.nextLine());
                    double r = Double.parseDouble(s.nextLine());
                    String f = s.nextLine();
                    f = f.substring(0, 8);// get the RGB only
                    p.getChildren().add(new Circle(x, y, r, Color.web(f, 1.0)));
                }
                if (shapeType.equals("Rectangle")) {
                    double x = Double.parseDouble(s.nextLine());
                    double y = Double.parseDouble(s.nextLine());
                    double w = Double.parseDouble(s.nextLine());
                    double h = Double.parseDouble(s.nextLine());
                    String f = s.nextLine();
                    f = f.substring(0, 8); //get the RGB only
                    Rectangle r;
                    p.getChildren().add(r = new Rectangle(x, y, w, h));
                    r.setFill(Color.web(f, 1.0));
                }
                //Handle the other types of shapes as well
                if (shapeType.equals("Ellipse")) {
                    double cx = Double.parseDouble(s.nextLine());
                    double cy = Double.parseDouble(s.nextLine());
                    double rx = Double.parseDouble(s.nextLine());
                    double ry = Double.parseDouble(s.nextLine());
                    String f = s.nextLine();
                    f = f.substring(0, 8); //get the RGB only
                    Ellipse e = new Ellipse(cx, cy, rx, ry);
                    e.setFill(Color.web(f, 1.0));
                    p.getChildren().add(e);
                }
            }
        } catch (FileNotFoundException | NumberFormatException e) {
            System.out.println("Something REALLY terrible happened");
        }
    }

}
