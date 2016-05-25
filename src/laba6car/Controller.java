/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laba6car;

import java.awt.Color;
import java.util.ArrayList;
import javafx.scene.effect.Light;
import laba6car.Model.Lights;
/**
 *
 * @author Дмитрий
 */
public class Controller implements IControl{

  private UpravView controlView=null;

    public boolean isOnHandControl() {
       if(controlView!=null)
           return true;
       else 
           return false;
    }
    
    private final double safeGap=3;//метров
   
    ArrayList<IView> views = new ArrayList<>();
       public void addListener(IView view) {
       views.add(view);
    }
    public void removeListener(IView view) {
        views.remove(view);
        if(views.isEmpty())isRunning = false;
    }
    private volatile boolean isRunning = true;
    private Thread thread;
    private Model model;
    

    public Controller(Model model) {
        this.model=model;
        thread = new Thread(this::mainControlMethod);
        thread.start();
    }
    private void mainControlMethod(){
    final double sleep = 1.0 / 60; //сек
        while (isRunning) {
            ///модификация трассу(меняем цвета светофоров, текущие времена и т.д.)
            model.increaseLightsTimes(sleep);//увеличили время на светофорах
            //(если прошли полный цикл, его время вычитается)
            //отсюда берется и изменение цвета светофора и все ост. значения.
            Car car = model.getCar();
            //////////////////////////
          
          
            double speed = car.getSpeed();
            double StopWay = speed*speed/(2*car.getComfortdeceleration());
            double distanceToLight= this.getDistanceToLight();
            Lights nextLight = this.getNextLightColor(car.getX());
            int LightIdPrev=this.getNextLightId(car.getX());
            boolean goOnOrange = (nextLight==Lights.ORANGE && StopWay > distanceToLight);
            boolean getUnderWay = (nextLight==Lights.GREEN&&(car.getAccel()<0||car.getSpeed()==0));//трогаемся
            boolean goo = (distanceToLight<safeGap&&car.getAccel()>0);
              if(!isOnHandControl()){
            if(nextLight==Lights.GREEN || goOnOrange || distanceToLight>StopWay+safeGap||getUnderWay|| goo ) // едем дальше
            {
                boolean canAcceleration = distanceToLight>StopWay+safeGap+car.getMaxacceleration()*sleep*sleep/2;
                if((speed<model.getMaxSpeed()&&(goOnOrange||canAcceleration))||getUnderWay ||goo)
                {
                    double accel=car.getMaxacceleration()*sleep;
                    if(speed+accel>model.getMaxSpeed())
                        accel= model.getMaxSpeed()-speed;
                    car.setSpeed(speed+accel);
                    car.setX(car.getX()+speed*sleep + accel*sleep*sleep/2);
                    car.setAcceleration( accel/sleep);
                }
                else
                {
                    car.setX(car.getX()+speed*sleep);
                    car.setAcceleration(0);
                }
            }
            else //тормозим
            {
                double accel=-car.getComfortdeceleration()*sleep;
                if(speed+accel<0)
                    accel= 0-speed;
                car.setSpeed(speed+accel);
                car.setX(car.getX()+speed*sleep + accel*sleep*sleep/2);
                car.setAcceleration( accel/sleep);
            }
            }
            else{
                 double accel=controlView.getZnach();
                 if(accel > 3.0) accel =3.0;
                 if(accel <-7.0)accel =-7.0;
                 car.setAcceleration(accel);
                 double newspeed= speed + accel*sleep;
                 if(newspeed>car.getMaxspeed())accel = (car.getMaxspeed()-speed)/sleep;
                 if(newspeed<0)accel = (0-speed)/sleep;
                 car.setX(car.getX()+speed*sleep+accel*sleep*sleep);
                 car.setSpeed(speed+sleep*accel);
              }
            ///////////
            if(car.getX()>=this.getLength())//проехали всю дистанцию
                car.setX(0);
            if(nextLight==Lights.RED&&LightIdPrev!=this.getNextLightId(car.getX()))
                views.forEach(IView::goOnRed);
            
            views.forEach(IView::onUpdate);
            try {
                Thread.sleep(Math.round(1000 * sleep));
            } catch (InterruptedException e) {
                return;
            }
        }
    }  
    private Lights getNextLightColor(double x){
          double carX=x;
          double dist=0;
          int i=0;
          ArrayList<Double> distanse =this.model.getDistancebetweenLights();
          ArrayList<Lights> lights = this.model.getLights();
          
          while(dist<carX){
              dist+=distanse.get(i++);    
          }
          if(i>0)
          return lights.get(i-1);
          else return Lights.GREEN;//костыль в ситуации, если светофора нет
      }
    public int getNextLightId(double x){
          double carX=x;
          double dist=0;
          int i=0;
          ArrayList<Double> distanse =this.model.getDistancebetweenLights();
          ArrayList<Lights> lights = this.model.getLights();
          
          while(dist<carX){
              dist+=distanse.get(i++);    
          }
          if(i>0)
          return i-1;
          else return -1;//костыль в ситуации, если светофора нет
      }
    
    
    
    
     public double getLength() {
         double distance=0;
         for(int i=0;i<model.getNumberOfLights();i++)
            distance+=model.getDistancebetweenLights().get(i);
        return distance;   
     }
     public ArrayList<Model.Lights> getLights() {
        return model.getLights();
    }
     public ArrayList<Double> getDistancebetweenLights() {
        return model.getDistancebetweenLights();
    }
     public ArrayList<Integer> getTimings() {
        return model.getTimings();
    }
    public double getX(){
        return model.getCar().getX();
    }
     public double getSpeed(){
        return model.getCar().getSpeed();
    }
     public double getAccel(){
        return model.getCar().getAccel();
    }
     public double getGasLeveel(){
        double r=model.getCar().getAccel()/model.getCar().getMaxacceleration();
        if(r>0)
        return r;
        else return 0;
    } 
      public double getTormLevel(){
       double r=model.getCar().getAccel()/model.getCar().getMaxdeceleration();
        if(r<0)
        return -r;
        else return 0;
    } 
      public double getDistanceToLight(){
          Car c = model.getCar();
          double carX=c.getX();
          double dist=0;
          int i=0;
          ArrayList<Double> distanse =this.model.getDistancebetweenLights();
          dist+=distanse.get(i++);
          while(dist<carX){
              dist+=distanse.get(i++);
          }
          return dist-carX;
      }

    @Override
    public boolean setUpravl(UpravView view) {
        if(controlView==null){
            controlView=view;
            return true;
        }
        return false;
    }

    @Override
    public void unsetUpravl(UpravView view) {
        if(controlView!=null&&controlView==view)
            controlView=null;
    }
}
