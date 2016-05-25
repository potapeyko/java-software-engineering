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

public class LightView extends JPanel implements IView{
    private Controller controller;

    public LightView(Controller controller) {
        this.controller = controller;
        controller.addListener(this);
    }
    public Controller getController(){
            return controller;
}

    public Dimension getPreferredSize() {
        return new Dimension(450, 600);
    }
    //ширина дороги 4м
    //длина дороги 300м 
    //размер 1 м = 100 - 0,1167*растояние до предмета 
    //коэффициент подобран
    private final int PADDING = 25;
    private final double CIRCLE_SIZE = 0.2;
    private final int All_DISTANCE = 300;

    
    private void PaintLight(double distance,int lightId,ArrayList<Model.Lights> lights,Graphics2D g2 )
    {
        double metr = 100.0-0.2167*distance;        
        Color col = Model.Lights.getColor(lights.get(lightId));
        g2.fillOval((int)(225+2.0*metr-CIRCLE_SIZE*metr/2),(int)(600-distance*2-CIRCLE_SIZE*metr),(int)(CIRCLE_SIZE*metr),(int)(CIRCLE_SIZE*metr));
        g2.fillOval((int)(225+2.0*metr-CIRCLE_SIZE*metr/2),(int)(600-distance*2-CIRCLE_SIZE*metr*2),(int)(CIRCLE_SIZE*metr),(int)(CIRCLE_SIZE*metr));
        g2.fillOval((int)(225+2.0*metr-CIRCLE_SIZE*metr/2),(int)(600-distance*2-CIRCLE_SIZE*metr*3),(int)(CIRCLE_SIZE*metr),(int)(CIRCLE_SIZE*metr));
       
        if(col==Color.GREEN)
        {
            g2.setColor(col);
            g2.fillOval((int)(225+2.0*metr-CIRCLE_SIZE*metr/2),(int)(600-distance*2-CIRCLE_SIZE*metr),(int)(CIRCLE_SIZE*metr),(int)(CIRCLE_SIZE*metr));
        }
        if(col==Color.ORANGE)
        {
            g2.setColor(col);
            g2.fillOval((int)(225+2.0*metr-CIRCLE_SIZE*metr/2),(int)(600-distance*2-CIRCLE_SIZE*metr*2),(int)(CIRCLE_SIZE*metr),(int)(CIRCLE_SIZE*metr));
        }
        if(col==Color.RED)
        {
            g2.setColor(col);
            g2.fillOval((int)(225+2.0*metr-CIRCLE_SIZE*metr/2),(int)(600-distance*2-CIRCLE_SIZE*metr*3),(int)(CIRCLE_SIZE*metr),(int)(CIRCLE_SIZE*metr));
        }
        g2.setColor(Color.BLACK);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        double scaleFactor = (getWidth()-PADDING*2) / controller.getLength();
        
        ArrayList<Model.Lights> lights = controller.getLights();
        ArrayList<Double> distances = controller.getDistancebetweenLights();
        double metr = 100.0-0.2167*300; 
        g2.drawLine(PADDING, 600, (int) (225.0-2*metr+0.5), 0);
        g2.drawLine(400+PADDING, 600, (int) (225.0+2*metr+0.5), 0);
        
        double nowdist= controller.getDistanceToLight();
        int id = controller.getNextLightId(controller.getX());
        if(id>=0)
        while(nowdist<300)
        {
            PaintLight(nowdist,id,lights,g2);
            if(id>=lights.size()-1)//последний светофор
                id=0;
            else 
                id++;
            nowdist+=distances.get(id);
        }
    }

    @Override
    public void onUpdate() {
        repaint();
    }

    @Override
    public void goOnRed() {
        
    }

    @Override
    public void emergencyDeceleration() {
    }

    @Override
    public void overSpeed() {
    }

    
}


