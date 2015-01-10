import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class PongGameSkeleton extends javax.swing.JPanel{

/**
*	@author sandeepsukumaran
*/
	
	public PongGameSkeleton(){
		try{
				initImages();
		}catch(java.io.IOExcpetion ioe){System.exit(0);}

		new javax.swing.Timer(1000/60,new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				criticalActions();
			}
		}).start();
	}

	public void criticalActions(){
		if(ballX<=0||ballX>=500)
			ballDeltaX*=-1;
		else{}
		if(ballY<=playerpaddle[0].Y()+playerpaddle[0].getHeight()+100){  //BALL NEAR TOP PADDLE
			if( (ballX+ballDiameter>=playerpaddle[0].X()) && (ballX<=playerpaddle[0].X()+playerpaddle[0].getWidth()) )  //CONTACT WITH PADDLE CASE
				ballDeltaY*=-1;
			else
				playerTwoScored();
		}
		else if(ballY+ballDiameter>=playerpaddle[1].Y()+100){   //BALL NEAR BOTTOM PADDLE
			if( (ballX+ballDiameter>=playerpaddle[1].X()) && (ballX<=playerpaddle[1].X()+playerpaddle[1].getWidth()) )  //CONTACT WITH PADDLE CASE
				ballDeltaY*=-1;
			else
				playerOneScored();
		}
		ballX+=ballDeltaX;
		ballY+=ballDeltaY;
		repaint();
	}

	public void paint(Graphics g){
		Graphics2D g2=n(Graphics2D)g;
		if(TitleScreen){

		}else if(PlayingScreen){
			g2.drawImage(P1Image,0,0,null);
			g2.drawImage(P1ScoreImage,125,0,null);
			g2.drawImage(P2Image,250,0,null);
			g2.drawImage(P2ScoreImage,375,0,null);
			g2.drawImage(bgImage,0,100,null);
			g2.drawImage(playerpaddleOneImage,playerpaddle[0].X(),playerpaddle[0].Y()+100,null);
			g2.drawImage(playerpaddleTwoImage,playerpaddle[1].X(),playerpaddle[1].Y()+100,null);
			g2.drawImage(ballImage,ballX,ballY,null);
		}else if(GameOverScreen){

			player1Score=player2Score=0;//we need to add a button here O.O
		}
	}

	public void playerOneScored(){
		player1Score++;
		if(player1Score>=10){
			PlayingScreen=false;
			GameOverScreen=true;
			ballX=240;
			ballY=540;
			ballDeltaX=1;
			ballDeltaY=2;
		}
		else{
			P1ScoreImage=P1PossibleScores[player1Score];
			ballX=240;
			ballY=540;
			ballDeltaY*=-1;
		}
	}

	public void playerTwoScored(){
		player2Score++;
		if(player2Score>=10){
			PlayingScreen=false;
			GameOverScreen=true;
			ballX=240;
			ballY=540;
			ballDeltaX=1;
			ballDeltaY=2;
		}
		else{
			P2ScoreImage=P2PossibleScores[player2Score];
			ballX=240;
			ballY=540;
			ballDeltaY*=-1;
		}
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run(){
				javax.swing.JFrame baseFrame=new javax.swing.JFrame("Pong!");
				baseFrame.add(new PongGameSkeleton());
				baseFrame.setSize(new java.awt.Dimension(500,1100));
				baseFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
				baseFrame.setLocationRelativeTo(null);
				baseFrame.setVisible(true);

				for(int i=0;i<2;playerpaddle[i++]=new PlayerPaddle());
			}
		});
	}

	public void initComponents(){
		TitleScreen=true;
		PlayingScreen=GameOverScreen=false;
		ballX=240;
		ballY=540;
		ballDiameter=20;
		ballDeltaX=1;
		ballDeltaY=2;
		player1Score=player2Score=0;
	}

	public void initImages()throws java.io.IOException{
		bgImage=ImageIO.read(new java.io.File("PGBackground.jpg"));
		playerpaddleOneImage=ImageIO.read(new java.io.File("PlayerOnePaddle.jpg"));
		playerpaddleTwoImage=ImageIO.read(new java.io.File("PlayerTwoPaddle.jpg"));
		ballImage=ImageIO.read(new java.io.File("Ball.jpg"));
		P1Image=ImageIO.read(new java.io.File("P1.jpg"));
		P2Image=ImageIO.read(new java.io.File("P2.jpg"));
		for(int i=0;i<10;++i){
			P1PossibleScores[i]=ImageIO.read(new java.io.File("P1Score"+i+".jpg"));
			P2PossibleScores[i]=ImageIO.read(new java.io.File("P2Score"+i+".jpg"));
		}
		P1Score=P1PossibleScores[0];
		P2Score=P2PossibleScores[0];
	}

	java.awt.image.BufferedImage bgImage;
	java.awt.image.BufferedImage playerpaddleOneImage;
	java.awt.image.BufferedImage P1Image;
	java.awt.image.BufferedImage P2Image;
	java.awt.image.BufferedImage P1ScoreImage;
	java.awt.image.BufferedImage P2ScoreImage;
	java.awt.image.BufferedImage[] P1PossibleScores;
	java.awt.image.BufferedImage[] P2PossibleScores;
	PlayerPaddle[] playerpaddle;
	private boolean TitleScreen;
	private boolean PlayingScreen;
	private boolean GameOverScreen;
	private int ballX;
    private int ballY;
    private int ballDiameter;
    private int ballDeltaX;
    private int ballDeltaY;
    private int player1Score;
    private int player2Score;
}
