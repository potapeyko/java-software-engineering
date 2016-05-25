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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args){
      final Random random = new Random();
        ArrayList<Double> dist = new ArrayList<>();//,100,200,70,15,35,100]
        dist.add(50.0);dist.add(100.0);dist.add(200.0);dist.add(70.0);dist.add(55.0);dist.add(135.0);dist.add(100.0);
        int[][] trafficLightCycles = new int[7][4];
        for(int i=0;i<7;i++)
        {
            trafficLightCycles[i][0]=random.nextInt(3)+4;
            trafficLightCycles[i][1]=random.nextInt(2)+2;
            trafficLightCycles[i][2]=random.nextInt(2)+4;
            trafficLightCycles[i][3]=random.nextInt(2)+1;
            
        }
        Model model = new Model();
        model.setMaxSpeed(60.0*1000/3600);
        model.setDistancebetweenLights(dist);
        model.setTrafficLightCycles(trafficLightCycles);
        Controller controller = new Controller(model);
        View view = new View(controller);
        LightView v2= new LightView(controller);
        
        JFrame lightFrame = ViewFrame.CreateLightView(v2);
        JFrame frmFrame = ViewFrame.CreateViewFrame(view);
       
    }
}
