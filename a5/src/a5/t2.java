/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a5;

import java.io.File;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

class myCircle extends Circle{
        double orgSceneX, orgSceneY;

public myCircle() {
 
    
}



public class t2 extends Application {
    Pane p;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        p = new Pane();
        root.setCenter(p);
        FlowPane bottom = new FlowPane();
        
        Button clear = new Button("New Drawing");
        clear.setOnAction(e->p.getChildren().clear());
        Button save = new Button("Save File");
        save.setOnAction(e->save());
        Button load = new Button("Load File");
        load.setOnAction(e->load());
        
        bottom.getChildren().addAll(save,load,clear);
        root.setBottom(bottom);
    
        
        //create shapes
    Circle redCircle = createCircle(100, 50, 30, Color.RED);
    Circle blueCircle = createCircle(20, 150, 20, Color.BLUE);
    Circle greenCircle = createCircle(40, 100, 40, Color.GREEN);
    
    //add shapes
    p.getChildren().add(redCircle);
    p.getChildren().add(blueCircle);
    p.getChildren().add(greenCircle);

        
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
            PrintStream fw = new PrintStream(new FileOutputStream("myDrawing.txt"));

            List<Node> shapes = p.getChildren();
            fw.println(shapes.size());
            for(int i = 0; i < shapes.size(); i++){
                Node n = shapes.get(i);
                if(n instanceof Circle){
                    fw.println("Circle");
                    Circle c = (Circle)n;
                    fw.println(""+c.getCenterX());
                    fw.println(""+c.getCenterY());
                    fw.println(""+c.getRadius());
                    fw.println(c.getFill().toString());
                }
                if(n instanceof Rectangle){
                    fw.println("Rectangle");
                    Rectangle c = (Rectangle)n;
                    fw.println(""+c.getX());
                    fw.println(""+c.getY());
                    fw.println(""+c.getWidth());
                    fw.println(""+c.getHeight());
                    fw.println(c.getFill().toString());
                }
                //Handle the other types of Shapes also
            }
            fw.flush();
            fw.close();
        } catch (Exception ex) {
             System.out.println("Something terrible happened");
        }
        
    }

    private void load() {
        try {
            Scanner s = new Scanner(new File("myDrawing.txt"));
            int nShapes = Integer.parseInt(s.nextLine());
            System.out.println(nShapes);
            for(int i = 0; i < nShapes; i++){
                String shapeType = s.nextLine();
                if(shapeType.equals("Circle")){
                    double x = Double.parseDouble(s.nextLine());
                    double y = Double.parseDouble(s.nextLine());;
                    double r = Double.parseDouble(s.nextLine());
                    String f = s.nextLine();
                    f = f.substring(0,8);// get the RGB only
                    p.getChildren().add(new Circle(x,y,r,Color.web(f,1.0)));
                }
                if(shapeType.equals("Rectangle")){
                    double x = Double.parseDouble(s.nextLine());
                    double y = Double.parseDouble(s.nextLine());
                    double w = Double.parseDouble(s.nextLine());
                    double h = Double.parseDouble(s.nextLine());
                    String f = s.nextLine();
                    f = f.substring(0,8); //get the RGB only
                    Rectangle r;
                    p.getChildren().add(r = new Rectangle(x,y,w,h));
                    r.setFill(Color.web(f,1.0));
                }
                //Handle the other types of shapes as well
            }   
        }
        catch(Exception e){
            System.out.println("Something REALLY terrible happened");
        }
    }
    
    
    
}
