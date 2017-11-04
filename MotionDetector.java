/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this actualCOGlate file, choose Tools | Templates
 * and open the actualCOGlate in the editor.
 */

package pong;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BLUR;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_THRESH_BINARY;
import com.googlecode.javacv.cpp.opencv_imgproc.CvMoments;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvEqualizeHist;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvGetSpatialMoment;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvMoments;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvSmooth;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvThreshold;
/**
 *
 * @author vkh
 */
class MotionDetector {
     IplImage prevImage,currImage,diffImage;
    private static final int LOW_THRESHOLD=64;
    private static final int MIN_PIXELS=350;
    private int cogX;
    public MotionDetector(IplImage firstFrame){
        prevImage=convertFrame(firstFrame);
        currImage=null;
        diffImage=IplImage.create(prevImage.width(),prevImage.height(),IPL_DEPTH_8U,1);
        cogX=0;
       }
    private IplImage convertFrame(IplImage img)
    {
        cvSmooth(img,img,CV_BLUR,3);
        IplImage grayImg=IplImage.create(img.width(),img.height(),IPL_DEPTH_8U,1);
        cvCvtColor(img,grayImg,CV_BGR2GRAY);
        cvEqualizeHist(grayImg,grayImg);
        return grayImg;
    }
    public int calcMove(IplImage currFrame)
    {
        int temp;
        if(currImage!=null) prevImage=currImage;
        currImage=convertFrame(currFrame);
        cvAbsDiff(currImage,prevImage,diffImage);
        cvThreshold(diffImage,diffImage,LOW_THRESHOLD,255,CV_THRESH_BINARY);
         temp=findCOGX(diffImage);
       /* if(Math.abs(actualCOG-cogX)>ERROR_THRESHOLD&&Math.abs(actualCOG-cogX)<600) cogX=actualCOG;
        return cogX;*/
         if(temp!=-1) actualCOG=temp;
        if(actualCOG<cogX) direction=DIRECTION.LEFT;
        else direction=DIRECTION.RIGHT;
        if(Math.abs(cogX-actualCOG)>INERTIA)
        {
            switch(direction)
            {
                case LEFT:cogX-=VELOCITY;
                            break;
                case RIGHT:cogX+=VELOCITY;
                            break;
            }
        }
        return cogX;
    }
    private int findCOGX(IplImage img)
    {
        int numPixels=cvCountNonZero(img);
        int res=-1;
        if(numPixels>MIN_PIXELS)
        {
            CvMoments m=new CvMoments();
            cvMoments(diffImage,m,1);
            double m00=cvGetSpatialMoment(m,0,0);
            double m10=cvGetSpatialMoment(m,1,0);           
            res=(int)Math.round(m10/m00);
            
        }
        return res;
    }
    IplImage markCOG(){IplImage t;
        if(currImage!=null) t=currImage.clone();else t=null;
        cvLine(t,cvPoint(actualCOG,0),cvPoint(actualCOG,t.height()-10),CV_RGB(255,0,0),1,8,0);
        System.out.println("ASDSGDHRHTR"+actualCOG);
        return t;
    }
   //final int ERROR_THRESHOLD=0;
    int actualCOG;
    enum DIRECTION{LEFT,RIGHT};
    DIRECTION direction;
    static final int INERTIA=20;
    static final int VELOCITY=22;
}
