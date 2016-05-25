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
public interface IView {
     public void onUpdate();
     public void goOnRed();
     public void emergencyDeceleration();
     public void overSpeed();
}
