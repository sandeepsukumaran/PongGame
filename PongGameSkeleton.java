package pong;

import com.googlecode.javacv.CanvasFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class PongGameSkeleton extends javax.swing.JPanel{

/**
*	@author sandeepsukumaran
*/
	
    public PongGameSkeleton(){
	try{
            //COG=new CanvasFrame("COG");
            //COG.setLocationRelativeTo(null);
            initComponents();
            initImages();
            Thread.sleep(1000);
	}catch(java.io.IOException ioe){System.err.println("initimages() pani kitti");} catch (InterruptedException ex) {
            System.err.println("sleep pani kitti");
        }
        new javax.swing.Timer(1000/80,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                System.out.println("calling criticalActions()");
                try {
                    criticalActions();
                } catch (InterruptedException ie) {
                    System.out.println("Interrupted...endammacchi!!!!");
                    System.exit(0);
                }
            }
	}).start();
    }

    public void criticalActions()throws InterruptedException{
	if(ballX<=0||ballX>=480){System.err.println("reflection from boundary");
            ballDeltaX*=-1;}
	else{}
	if(ballY<=playerpaddle[0].Y()+playerpaddle[0].getHeight()+100){  //BALL NEAR TOP PADDLE
            if( (ballX+ballDiameter>=playerpaddle[0].X()) && (ballX<=playerpaddle[0].X()+playerpaddle[0].getWidth()) )  //CONTACT WITH PADDLE CASE
		ballDeltaY*=-1;
            else
                playerTwoScored();
            System.err.println("BALLNEAR TOP PADDLE");
	}
	else if(ballY+ballDiameter>=playerpaddle[1].Y()+100){   //BALL NEAR BOTTOM PADDLE
            if( (ballX+ballDiameter>=playerpaddle[1].X()) && (ballX<=playerpaddle[1].X()+playerpaddle[1].getWidth()) )  //CONTACT WITH PADDLE CASE
                ballDeltaY*=-1;
            else
                playerOneScored();
            System.out.println("BALL NEAR BOTTOM PADDLE");
            //ballDeltaY*=-1;
        }
        System.out.println("ballDeltaX:"+ballDeltaX+"\tballDeltaY:"+ballDeltaY);
	ballX+=ballDeltaX;
	ballY+=ballDeltaY;
        System.err.println("Ball Position updated X:"+ballX+"\tY:"+ballY+".");
	repaint();
    }

    @Override
    public void paint(Graphics g){
        System.out.println("paint called");
	Graphics2D g2=(Graphics2D)g;
	if(TitleScreen){
	}else if(PlayingScreen){
            System.err.println("PlayingScreen painted");
            g2.drawImage(P1Image,0,0,null);
            //g.drawString(Integer.toString(1)/*player1Score)*/, 125+player1Score, 20);
            g2.drawImage(P1ScoreImage,125,0,null);
            g2.drawImage(P2Image,250,0,null);
            //g.drawString(Integer.toString(1)/*player2Score)*/,375+player2Score,20);
            g2.drawImage(P2ScoreImage,375,0,null);
            g2.drawImage(bgImage,0,100,null);
            g2.drawImage(playerpaddleOneImage,playerpaddle[0].X(),playerpaddle[0].Y()+100,null);
            g2.drawImage(playerpaddleTwoImage,playerpaddle[1].X(),playerpaddle[1].Y()+100,null);
            g2.drawImage(ballImage,ballX,ballY,null);
            //COG.showImage(MTRef1.md.markCOG());
	}else if(GameOverScreen){
            player1Score=player2Score=0;//we need to add a button here O.O
	}
    }

    public void playerOneScored()throws InterruptedException{
	player1Score++;
        System.err.println("playerOneScored()");
	if(player1Score>=10){
            PlayingScreen=false;
            GameOverScreen=true;
            ballX=240;
            ballY=315;
            ballDeltaX=1;
            ballDeltaY=2;
            try{Thread.sleep(3000);}catch(InterruptedException ie){}
            System.out.println("GAME OVER:::::::::::");
            System.exit(0);
	}
	else{
            P1ScoreImage=P1PossibleScores[player1Score];
            ballX=240;
            ballY=315;
            ballDeltaY*=-1;
            Thread.sleep(800);
        }
    }

    public void playerTwoScored()throws InterruptedException{
        player2Score++;
        System.err.println("playerTwoScored()");
	if(player2Score>=10){
            PlayingScreen=false;
            GameOverScreen=true;
            ballX=240;
            ballY=315;
            ballDeltaX=1;
            ballDeltaY=2;
            try{Thread.sleep(3000);}catch(InterruptedException ie){}
            System.out.println("GAME OVER:::::::::::");
            System.exit(0);
	}
	else{
            P2ScoreImage=P2PossibleScores[player2Score];
            //P1ScoreImage=P1PossibleScores[player2Score];
            ballX=240;
            ballY=315;
            ballDeltaY*=-1;
            Thread.sleep(800);
        }
    }

    public static void main(String[] args)throws InterruptedException {
	java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            @SuppressWarnings("empty-statement")
            public void run(){
		javax.swing.JFrame baseFrame=new javax.swing.JFrame("Crazy PONG!");
                baseFrame.add(new PongGameSkeleton());
		baseFrame.setSize(new java.awt.Dimension(515,755));
		baseFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		baseFrame.setLocationRelativeTo(null);
                //baseFrame.setResizable(false);
		baseFrame.setVisible(true);
                System.out.println("Frame created");
		
            }
	});
    }

	public void initComponents(){
		TitleScreen=false;
		PlayingScreen=true;GameOverScreen=false;
		ballDeltaX=-6;
		ballDeltaY=6;
                ballX=240;
		ballY=315;//540
		ballDiameter=20;
		player1Score=player2Score=0;
                for(int i=0;i<2;playerpaddle[i++]=new PlayerPaddle()){}
                playerpaddle[0].x=200;
                playerpaddle[1].x=200;
                playerpaddle[0].y=2;
                playerpaddle[1].y=600;
                MTRef1=new MotionTracker(0,playerpaddle[0],this);
                MTRef2=new MotionTracker(1,playerpaddle[1],this);
	}

	public void initImages()throws java.io.IOException{
                System.out.println("Entered initImages successfully");
                //bgImage=new java.awt.image.BufferedImage(getClass().getResource("/pong/gameoverp1wins.jpg"));
		bgImage=ImageIO.read(getClass().getResource("/pong/images/bgImage.jpg"));
		playerpaddleOneImage=ImageIO.read(getClass().getResource("/pong/images/playerpaddle1lar.jpg"));
		playerpaddleTwoImage=ImageIO.read(getClass().getResource("/pong/images/playerpaddle2lar.jpg"));
		ballImage=ImageIO.read(getClass().getResource("/pong/images/ball.jpg"));
		P1Image=ImageIO.read(getClass().getResource("/pong/images/P1.jpg"));
		P2Image=ImageIO.read(getClass().getResource("/pong/images/P2.jpg"));
                StringBuilder tempbuf=new StringBuilder("/pong/P1scores/0.jpg");
		for(int i=0;i<10;++i){
                    tempbuf.setCharAt(7,Integer.toString(1).charAt(0));
                    tempbuf.setCharAt(15,Integer.toString(i).charAt(0));
                    P1PossibleScores[i]=ImageIO.read(getClass().getResource(tempbuf.toString()));
                    tempbuf.setCharAt(7,Integer.toString(2).charAt(0));
                    tempbuf.setCharAt(15,Integer.toString(i).charAt(0));
                    P2PossibleScores[i]=ImageIO.read(getClass().getResource(tempbuf.toString()));
		}
                /*P1PossibleScores[0]=ImageIO.read(getClass().getResource("cetexpong/images/P1S0.jpg"));
                P1PossibleScores[1]=ImageIO.read(getClass().getResource("cetexpong/images/P1S1.jpg"));
                P1PossibleScores[2]=ImageIO.read(getClass().getResource("cetexpong/images/P1S2.jpg"));
                P1PossibleScores[3]=ImageIO.read(getClass().getResource("cetexpong/images/P1S3.jpg"));
                P1PossibleScores[4]=ImageIO.read(getClass().getResource("cetexpong/images/P1S4.jpg"));
                P1PossibleScores[5]=ImageIO.read(getClass().getResource("cetexpong/images/P1S5.jpg"));
                P1PossibleScores[6]=ImageIO.read(getClass().getResource("cetexpong/images/P1S6.jpg"));
                P1PossibleScores[7]=ImageIO.read(getClass().getResource("cetexpong/images/P1S7.jpg"));
                P1PossibleScores[8]=ImageIO.read(getClass().getResource("cetexpong/images/P1S8.jpg"));
                P1PossibleScores[9]=ImageIO.read(getClass().getResource("cetexpong/images/P1S9.jpg"));
                P2PossibleScores[0]=ImageIO.read(getClass().getResource("cetexpong/P2scores/0.jpg"));
                P2PossibleScores[1]=ImageIO.read(getClass().getResource("cetexpong/P2scores/1.jpg"));
                P2PossibleScores[2]=ImageIO.read(getClass().getResource("cetexpong/P2scores/2.jpg"));
                P2PossibleScores[3]=ImageIO.read(getClass().getResource("cetexpong/P2scores/3.jpg"));
                P2PossibleScores[4]=ImageIO.read(getClass().getResource("cetexpong/P2scores/4.jpg"));
                P2PossibleScores[5]=ImageIO.read(getClass().getResource("cetexpong/P2scores/5.jpg"));
                P2PossibleScores[6]=ImageIO.read(getClass().getResource("cetexpong/P2scores/6.jpg"));
                P2PossibleScores[7]=ImageIO.read(getClass().getResource("cetexpong/P2scores/7.jpg"));
                P2PossibleScores[8]=ImageIO.read(getClass().getResource("cetexpong/P2scores/8.jpg"));
                P2PossibleScores[9]=ImageIO.read(getClass().getResource("cetexpong/P2scores/9.jpg"));*/
                System.out.println("Successful");
		P1ScoreImage=P1PossibleScores[0];
		P2ScoreImage=P2PossibleScores[0];
	}

	java.awt.image.BufferedImage bgImage;
	java.awt.image.BufferedImage playerpaddleOneImage;
        java.awt.image.BufferedImage playerpaddleTwoImage;
        java.awt.image.BufferedImage ballImage;
	java.awt.image.BufferedImage P1Image;
	java.awt.image.BufferedImage P2Image;
	java.awt.image.BufferedImage P1ScoreImage;
	java.awt.image.BufferedImage P2ScoreImage;
	java.awt.image.BufferedImage[] P1PossibleScores=new java.awt.image.BufferedImage[10];
	java.awt.image.BufferedImage[] P2PossibleScores=new java.awt.image.BufferedImage[10];
	PlayerPaddle[] playerpaddle=new PlayerPaddle[2];
        CanvasFrame COG;
        MotionTracker MTRef1,MTRef2;
	private volatile boolean TitleScreen;
	private volatile boolean PlayingScreen;
	private volatile boolean GameOverScreen;
	private int ballX;
        private int ballY;
        private int ballDiameter;
        private int ballDeltaX;
        private int ballDeltaY;
        private int player1Score;
        private int player2Score;
}

class PlayerPaddle{
    int x,y,width;
    final int SMALL=50;
    final int MEDIUM=100;
    final int LARGE=150;
    public PlayerPaddle(){
        width=LARGE;
    }
    public int X(){
        return x;
    }
    public int Y(){
        return y;
    }
    public int getWidth(){
        return width;
    }
    int getHeight() {
        return 10;
    }
}