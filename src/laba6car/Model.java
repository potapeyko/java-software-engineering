/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laba6car;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Дмитрий
 */
public class Model {
    private final double CarMaxSpeed = 150.0*1000/3600;
    /**
     *
     * @param maxSpeed - в м/с
     */
    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
   public void setCarMaxSpeed(double maxSpeed) {
        if(this.myCar!=null)
            this.myCar.setMaxspeed(maxSpeed);
    }
    public void setNumberOfLights(int numberOfLights) {
        this.numberOfLights = numberOfLights;
        this.LightsTimes=new ArrayList<Double>();
        this.distancebetweenLights = new ArrayList<>();
        this.trafficLightCycles=new int [numberOfLights][4];
        for(int i=0;i<numberOfLights;i++){
            this.LightsTimes.add(0.0);
            this.distancebetweenLights.add(100.0);//м
            this.trafficLightCycles[i][0]=8;//с
            this.trafficLightCycles[i][1]=3;
            this.trafficLightCycles[i][2]=8; 
            this.trafficLightCycles[i][3]=2;
        }
        //this.distancebetweenLights.set(3, 500.0);//для тестов
    }
    public boolean setDistancebetweenLights() {
        if(this.numberOfLights==0)return false;
        ArrayList<Double> distance = new ArrayList<Double>();
        for(int i=0;i<this.numberOfLights;i++)
            distance.add(100.0);//m
        return this.setDistancebetweenLights(distance);
    }
    public boolean setDistancebetweenLights(ArrayList<Double> distancebetweenLights) {
        if(this.numberOfLights!=distancebetweenLights.size())return false;
        this.distancebetweenLights = distancebetweenLights;
        return true;
    }

    /**
     *
     * @param trafficLightCycles [this.numberOfLights][4] c временами циклов зеленый
     * желтый красный желтый+красный
     * @return  false - если условия не соблюдены
     */
    public boolean setTrafficLightCycles(int[][] trafficLightCycles) {
        if(trafficLightCycles.length!=this.numberOfLights)return false;
        if(trafficLightCycles[0].length!=4)return false;
        this.trafficLightCycles = trafficLightCycles;
        return true;
    }
    public boolean setLightsTimes(ArrayList<Double> LightsTimes) {
        if(LightsTimes.size()!=this.numberOfLights)return false;
        this.LightsTimes = LightsTimes;
        return true;
    }

    /**
     *
     * @param delta - значение в секундах
     */
    public void increaseLightsTimes(Double delta){
        int i=0;
        while(i<this.LightsTimes.size())
        {
           
            int lightCycle=this.trafficLightCycles[i][0]+this.trafficLightCycles[i][1]+
                    this.trafficLightCycles[i][2]+this.trafficLightCycles[i][3];
            if(this.LightsTimes.get(i)+delta>lightCycle)//с
            this.LightsTimes.set(i, this.LightsTimes.get(i++)+delta-lightCycle);
            else 
                this.LightsTimes.set(i, this.LightsTimes.get(i++)+delta);
        }
    }
  

    public int getNumberOfLights() {
        return numberOfLights;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public ArrayList<Double> getDistancebetweenLights() {
        return distancebetweenLights;
    }

    public int[][] getTrafficLightCycles() {
        return trafficLightCycles;
    }

    public ArrayList<Double> getLightsTimes() {
        return LightsTimes;
    }

    public Car getCar() {
        return myCar;
    }

    public double getDistanceTonextLight() {
        return distanceTonextLight;
    }
    private int numberOfLights=7;
    private double maxSpeed = 60.0*1000/3600;//м/с
    
    private ArrayList<Double> distancebetweenLights;//100
    private int[][] trafficLightCycles;//время на цвета зеленый, желтый 
    //мигающий, крассный, крассный с желтым в сек
    private ArrayList<Double> LightsTimes;//время на данном световфоре в сек
    private Car myCar;
    private double distanceTonextLight = 0;
    public Model(int numberOfLights){
        myCar=new Car(this.CarMaxSpeed);
        if(numberOfLights>=0)this.setNumberOfLights(numberOfLights);
        else this.setNumberOfLights(7);
    }
    public Model (){
        this(7);
    }
     public enum Lights {
        GREEN, ORANGE, RED;

        public static Color getColor(Lights light) {
            switch (light) {
                case GREEN:
                    return Color.GREEN;
                case ORANGE:
                    return Color.ORANGE;
                case RED:
                    return Color.RED;
                default:
                    return Color.black;
            }
        }
    }

    
   
    /**
     * 
     * @return Возвращает список текущих цветов всех светофоров
     */
    public ArrayList<Lights> getLights() {
        ArrayList<Lights> arr = new ArrayList<>(this.numberOfLights);
        int i=-1;
        for (Double time : this.LightsTimes) {//time в сек
            int code = 0;
            i++;
            for (int lightCycle : this.trafficLightCycles[i]) {
                if (time > lightCycle) {
                    time -= lightCycle;
                    code++;
                } else {
                    break;
                }
            }
            Lights l;
            switch (code) {
                case 0:
                    l = Lights.GREEN;
                    break;
                case 1:
                case 3:
                    l = Lights.ORANGE;
                    break;
                case 2:
                default:
                    l = Lights.RED;
                    break;
            }
            arr.add(l);
        }
        return arr;
    }
    
    /**
     *
     * @return остатки времени до смены цвета свветофора в сек
     */
    public ArrayList<Integer> getTimings() {
        ArrayList<Integer> arr = new ArrayList<>();
       int i=-1;
        for (Double time : this.LightsTimes) {
            int code = 0;
            i++;
                for (int lightCycle : this.trafficLightCycles[i]) {
                if (time > lightCycle) {
                    time -= lightCycle;
                    code++;
                } else {
                    break;
                }
                }
            arr.add(trafficLightCycles[i][code] - time.intValue());
        }
        return arr;
    }
}
