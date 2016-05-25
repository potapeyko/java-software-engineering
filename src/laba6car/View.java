/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 
    



 */
package laba6car;

/**
 *
 * @author Дмитрий
 */
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;

public class View extends JPanel implements IView{
    private Controller controller;

    public View(Controller controller) {
        this.controller = controller;
        controller.addListener(this);
    }
    public Controller getController(){
            return controller;
}
    public Dimension getPreferredSize() {
        return new Dimension(1200, 500);
    }

    private final int PADDING = 25;
    private final int CIRCLE_SIZE = 15;
    private final int CAR_WIDTH = 15;
    private final int CAR_HEIGHT = 9;
    private String onRedString="";
    private int timeOfMassage=0;
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        

        double scaleFactor = (getWidth()-PADDING*2) / controller.getLength();
        g2.drawLine(PADDING, 200, getWidth()-PADDING, 200);

        ArrayList<Model.Lights> lights = controller.getLights();
        ArrayList<Double> distances = controller.getDistancebetweenLights();
        ArrayList<Integer> timings = controller.getTimings();
        double allDistanse = 0,nowdist=0;
        
        for(Double dist : distances)allDistanse+=dist;
        
        for (int i = 0; i < distances.size(); i++) {
            nowdist+=distances.get(i);
            int x = (int)(PADDING + (getWidth() - PADDING*2)/allDistanse*nowdist+0.5);
            g2.setColor(Color.BLACK);
            g2.drawLine(x, 200, x, 150);
            g2.setColor(Model.Lights.getColor(lights.get(i)));
            g2.fillOval(x-CIRCLE_SIZE/2, 150-CIRCLE_SIZE/2, CIRCLE_SIZE, CIRCLE_SIZE);
            String str = timings.get(i)+"";
            g2.setColor(Color.BLACK);
            g2.drawString(str, x-g.getFontMetrics().stringWidth(str)/2, 220);
            
        }
        g2.setColor(Color.BLACK);
        int x = Double.valueOf(controller.getX() * scaleFactor + PADDING).intValue();
        g2.fillRect(x-CAR_WIDTH, 200-CAR_HEIGHT, CAR_WIDTH, CAR_HEIGHT );
        
        //скорость ускорение уровни газа и тормоза
        String speed = "Speed: "+controller.getSpeed()+"  м/с";
        g2.drawString(speed, PADDING, 300);
        String acceleration = "Acceleration: "+controller.getAccel()+"  м/с^2";
        g2.drawString(acceleration, PADDING, 320);
        String gas = "Уровень газа: "+controller.getGasLeveel()*100+"  %";
        g2.drawString(gas, PADDING, 340);
        String torm = "Уровень тормоза: "+controller.getTormLevel()*100+"  %";
        g2.drawString(torm, PADDING, 360);
        String rast = "Расстояние до ближайшего светофора: "+controller.getDistanceToLight()+"  м";
        g2.drawString(rast, PADDING, 380);
        
        if(!onRedString.isEmpty()){
        g2.drawString(onRedString, PADDING, 20);
        timeOfMassage++;
        }
        if(timeOfMassage==120){
            onRedString="";
            timeOfMassage=0;
        }
    }

    @Override
    public void onUpdate() {
        repaint();
    }

    @Override
    public void goOnRed() {
        onRedString = "Ой, Проехали на крассный";
        timeOfMassage=0;
    }

    @Override
    public void emergencyDeceleration() {
    }

    @Override
    public void overSpeed() {
    }

    
}


