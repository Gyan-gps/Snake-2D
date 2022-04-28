package SnakeGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public  class GamePanel extends JPanel implements ActionListener , KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int[] snakeXLength = new int[750];
	private int[] snakeYLength = new int[750];
	private int lengthOfSnake = 3;
	
	private Random random = new Random();
	private int enemyX , enemyY;
	
	private boolean GameOver = false;
	
	private int moves = 0,score = 0;
	private boolean right = true , left = false , up = false , down = false;
	
	private int xPos[] = new int[]{ 25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
	private int yPos[] = new int[]{ 75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
	
	
	private ImageIcon snaketitle = new ImageIcon (getClass().getResource("snaketitle.jpg"));
	private ImageIcon righthead = new ImageIcon (getClass().getResource("rightmouth.png"));
	private ImageIcon lefthead = new ImageIcon (getClass().getResource("leftmouth.png"));
	private ImageIcon uphead = new ImageIcon (getClass().getResource("upmouth.png"));
	private ImageIcon downhead = new ImageIcon (getClass().getResource("downmouth.png"));
	private ImageIcon body = new ImageIcon (getClass().getResource("snakeimage.png"));
	private ImageIcon enemy = new ImageIcon (getClass().getResource("enemy.png"));
	
	
	
	private Timer timer;
	private int delay = 150;
	
	GamePanel(){
		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		
		timer = new Timer(delay,this);
		timer.start();
		newEnemy();
		
	}
	
	private void newEnemy() {
		enemyX = xPos[random.nextInt(34)];

		enemyY = yPos[random.nextInt(23)];
		
		for(int i = lengthOfSnake - 1; i >= 0 ; i--){
			if(snakeXLength[i] == enemyX && snakeYLength[i] == enemyY){
				newEnemy();
			}
		}
		
	}

	public void paint(Graphics g){
		g.setColor(Color.WHITE);
		g.drawRect(24, 10, 851, 55);
		g.drawRect(24, 74, 851, 576);
		
		snaketitle.paintIcon(this, g, 25, 11);
		
		g.setColor(Color.BLACK);
		
		g.fillRect(25, 75, 850, 575);
		
		if(moves == 0){
			snakeXLength[0]=100;
			snakeXLength[1]=75;
			snakeXLength[2]=50;
			
			snakeYLength[0]=100;
			snakeYLength[1]=100;
			snakeYLength[2]=100;
			
			
		}
		if(right)righthead.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);
		if(left)lefthead.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);
		if(up)uphead.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);
		if(down)downhead.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);
		
		for(int i=1;i<lengthOfSnake;i++){
			body.paintIcon(this, g, snakeXLength[i], snakeYLength[i]);
		}
		
		enemy.paintIcon(this, g, enemyX, enemyY);
		
		if(GameOver){
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial",Font.BOLD,50));
			g.drawString("Game Over", 300, 300);
			
			g.setFont(new Font("Arial",Font.PLAIN,20));
			g.drawString("Press SPACE to Restart", 300, 350);
		}
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",Font.PLAIN,14));
		g.drawString("Score : "+score, 750, 30);
		g.drawString("Length : "+lengthOfSnake, 750, 50);
		
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		for(int i=lengthOfSnake - 1;i>0;i--){
			snakeXLength[i] = snakeXLength[i-1];
			snakeYLength[i] = snakeYLength[i-1];
		}
		
		if(left){
			snakeXLength[0] = snakeXLength[0] - 25;
		}
		if(right){
			snakeXLength[0] = snakeXLength[0] + 25;
		}
		if(up){
			snakeYLength[0] = snakeYLength[0] - 25;
		}
		if(down){
			snakeYLength[0] = snakeYLength[0] + 25;
		}
		collidesWithEnemy();
		
		if(snakeXLength[0]>850)snakeXLength[0]=25;
		if(snakeXLength[0]<25)snakeXLength[0]=850;
		
		if(snakeYLength[0]>625)snakeYLength[0]=75;
		if(snakeYLength[0]<75)snakeYLength[0]=625;
		
		collidesWithEnemy();
		collidesWithBody();
		
		repaint();
	}

	private void collidesWithBody() {
		
		for(int i = lengthOfSnake - 1; i > 0 ; i--){
			if(snakeXLength[i] == snakeXLength[0] && snakeYLength[i] == snakeYLength[0]){
				timer.stop();
				newEnemy();
				GameOver = true;
			}
		}
	}

	private void collidesWithEnemy() {

		if(snakeXLength[0] == enemyX && snakeYLength[0] == enemyY){
			newEnemy();
			lengthOfSnake++;
			score+=10;
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			restart();
		}
		
		if((right || left) && e.getKeyCode() == KeyEvent.VK_E){
			up = true;
			down = false;
			left = false;
			right = false;
			moves++;
		}
		if((right || left) && e.getKeyCode() == KeyEvent.VK_D){
			up = false;
			down = true;
			left = false;
			right = false;
			moves++;
		}
		if((up || down) && e.getKeyCode() == KeyEvent.VK_R){
			up = false;
			down = false;
			left = false;
			right = true;
			moves++;
		}
		if((up || down) && e.getKeyCode() == KeyEvent.VK_W){
			up = false;
			down = false;
			left = true;
			right = false;
			moves++;
		}
		
	}

	private void restart() {
		GameOver = false;
		moves = 0;
		score = 0;
		lengthOfSnake = 3;
		left = false;
		right = true;
		up = false;
		down = false;
		timer.start();
		repaint();
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
