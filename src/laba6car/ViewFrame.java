/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laba6car;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author Дмитрий
 */
public  class ViewFrame{
   
   public static JFrame CreateViewFrame(View view){
        JFrame frmFrame = new JFrame();
        frmFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frmFrame.setTitle("Street");
        frmFrame.setVisible(true);
        frmFrame.getContentPane().setLayout(new BoxLayout(frmFrame.getContentPane(), BoxLayout.Y_AXIS));
        frmFrame.getContentPane().add(view);
        JButton createUprButton = new JButton("Управлять вручную");
        createUprButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              // display/center the jdialog when the button is pressed
               UpravView v = new UpravView(view.getController());
               JFrame UprFrm = ViewFrame.CreateUpravView(v);
            }
        });
        frmFrame.add(createUprButton);
        frmFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                view.getController().removeListener(view);
            }
        });
        frmFrame.pack();//уст. мин. рекомендованный размер
       return frmFrame;
   } 
   public static JFrame CreateLightView(LightView view){
        JFrame frmFrame = new JFrame();
        frmFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frmFrame.setTitle("Street");
        frmFrame.setVisible(true);
        frmFrame.getContentPane().setLayout(new BoxLayout(frmFrame.getContentPane(), BoxLayout.Y_AXIS));
        frmFrame.getContentPane().add(view);
        frmFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                view.getController().removeListener(view);
            }
        });
        frmFrame.pack();//уст. мин. рекомендованный размер
       return frmFrame;
   } 
    public static JFrame CreateUpravView(UpravView view){
        JFrame frmFrame = new JFrame();
        frmFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frmFrame.setTitle("Управление");
        frmFrame.setVisible(true);
        frmFrame.getContentPane().setLayout(new BoxLayout(frmFrame.getContentPane(), BoxLayout.Y_AXIS));
        frmFrame.getContentPane().add(view);
        frmFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                view.getController().removeListener(view);
                view.getController().unsetUpravl(view);
            }
        });
        frmFrame.pack();//уст. мин. рекомендованный размер
       return frmFrame;
   } 
   
    
}
