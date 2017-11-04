/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pong;

import com.googlecode.javacv.OpenCVFrameGrabber;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author vkh
 */
public class MotionTracker {
    PlayerPaddle pobj;
    PongGameSkeleton PGS;
    OpenCVFrameGrabber srcGrabber;
    MotionDetector md;
    public MotionTracker(int srcDeviceNum,PlayerPaddle pprefer1,PongGameSkeleton pgspara)
    {
        pobj=pprefer1;
        PGS=pgspara;
        try{
        srcGrabber=new OpenCVFrameGrabber(srcDeviceNum);
        srcGrabber.start();
        md=new MotionDetector(srcGrabber.grab());
        }catch(Exception e){System.out.println(e);}
        java.awt.EventQueue.invokeLater(new Runnable(){
            public void run(){
                new javax.swing.Timer(1000/20,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                System.out.println("calling getNextXPos()");
                pobj.x=450-getNextXPos();
            }
	}).start();
            }
        }
        );
    }
    public int getNextXPos(){
        try{
        
        int p=md.calcMove(srcGrabber.grab());
        return p;
       }catch(Exception e){System.out.println(e);}
        return -1;
    }
}
