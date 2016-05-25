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
import java.util.Hashtable;

public class UpravView extends JPanel implements IView{
    private Controller controller;
    private boolean isMyControl=false;
    private  JSlider framesPerSecond = new JSlider(JSlider.CENTER,
                                      -700, 300, 0);
        
    public UpravView(Controller controller) {
        this.controller = controller;
        controller.addListener(this);
        if(!this.controller.isOnHandControl()){
        isMyControl=true;
        this.controller.setUpravl(this);
        }
        //Create the label table
        JPanel topPanel = new JPanel(new BorderLayout());
         topPanel.setPreferredSize(new Dimension(400, 50));
        Hashtable labelTable = new Hashtable();
        labelTable.put( new Integer( -700 ), new JLabel("-7") );
        labelTable.put( new Integer( -400 ), new JLabel("-4") );
        labelTable.put( new Integer( 0 ), new JLabel("0") );
        labelTable.put( new Integer( 300 ), new JLabel("+3") );
        framesPerSecond.setLabelTable( labelTable );      
        framesPerSecond.setPaintLabels(true);
        framesPerSecond.setMajorTickSpacing(100);
        framesPerSecond.setPaintTicks(true);
        
        
        topPanel.add((framesPerSecond));
        this.add(topPanel);
        
    }
    public Controller getController(){
            return controller;
}
    public double getZnach(){
        return (double)(framesPerSecond.getValue())/100;
    }

    public Dimension getPreferredSize() {
        return new Dimension(400, 200);
    }
   
    private String onRedString="";
    private int timeOfMassage=0;
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
       String rast = "Расстояние до светофора "+controller.getDistanceToLight()+"  м";
        g2.drawString(rast, 25, 120);
        String torm = "Скорость"+controller.getSpeed()+"  м";
        g2.drawString(torm, 25, 135);
       
        if(!onRedString.isEmpty()){
        g2.drawString(onRedString, 20, 100);
        timeOfMassage++;
        }
        if(timeOfMassage==120){
            onRedString="";
            timeOfMassage=0;
        }
        
        if(controller.isOnHandControl()&&isMyControl==false){
         String a = "Управление уже захвачено ";
        g2.drawString(a, 25, 150);
        }
        else if(isMyControl==false){
               if(controller.setUpravl(this)) 
               {
                   isMyControl=true;
                    String rast1 = "Только что захватили управление ";
                    g2.drawString(rast, 25, 150);
               }
               else
               {
                     String rast1 = "Управление уже захвачено ";
                     g2.drawString(rast, 25, 150);
               }
        }
        else
        {
        
        String rast1 = "Управление у нас ";
        g2.drawString(rast, 25, 150);
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


