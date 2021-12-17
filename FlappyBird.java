import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class FlappyBird implements ActionListener, MouseListener, KeyListener {


	public static FlappyBird fb;
	public final int WIDTH = 800, HEIGHT=700;
	public Renderer rd;
	public Rectangle bird;
	public ArrayList<Rectangle> cols;
	public Random random;
	public int ticks, yPos, score;
	public boolean gameOver , gameStarted;


	public static void main(String[] args) 
	{
		fb= new FlappyBird();
	}

	public FlappyBird() {

		JFrame frame = new JFrame();

		Timer timer = new Timer(20, this);

		random = new Random();

		rd = new Renderer();

		frame.add(rd);

		frame.setTitle("Flappy Bird");
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);

		frame.addMouseListener(this);
		frame.addKeyListener(this);

		frame.setResizable(false);

		frame.setVisible(true);

		bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
		cols = new ArrayList<Rectangle>();

		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);

		timer.start();

	}

	public void addColumn(boolean start)
	{
		int space = 300;
		int width = 100;
		int height = 50 + random.nextInt(300);

		if (start)
		{
			cols.add(new Rectangle(WIDTH + width + cols.size() * 300, HEIGHT - height - 120, width, height));
			cols.add(new Rectangle(WIDTH + width + (cols.size() - 1) * 300, 0, width, HEIGHT - height - space));
		}
		else
		{
			cols.add(new Rectangle(cols.get(cols.size() - 1).x + 600, HEIGHT - height - 120, width, height));
			cols.add(new Rectangle(cols.get(cols.size() - 1).x, 0, width, HEIGHT - height - space));
		}
	}




	public void paintColumn(Graphics g,Rectangle col) {

		g.setColor(Color.green.darker());
		g.fillRect(col.x, col.y, col.width, col.height);
		g.setColor(Color.green.darker());
		g.fillRect(col.x - 20, col.y, col.width + 20, 20);
		g.setColor(Color.green.darker());
		g.fillRect(col.x, col.y, col.width + 20, 20);



	}

	@Override
	public void actionPerformed(ActionEvent e) {


		int speed =10;

		ticks++;

		if(gameStarted) {


			for (int i = 0; i < cols.size(); i++) 
			{
				Rectangle col = cols.get(i);
				col.x -= speed;

			}

			if(ticks % 2 == 0 && yPos < 15) 
			{

				yPos +=2;
			}

			for (int i = 0; i < cols.size(); i++) {

				Rectangle column = cols.get(i);
				if(column.x + column.width < 0 ) 
				{

					cols.remove(column);
					if(column.y == 0) 
					{

						addColumn(false);	
					}
				}

			}



			bird.y += yPos;

			for(Rectangle column : cols) {

				if(column.y == 0 && bird.x + bird.width/2 > column.x + column.width/2 - 5 && bird.x + bird.width /2 < column.x + column.width /2 +10)
				{

					score++;

				}
				if(column.intersects(bird)) 
				{
					gameOver= true;
					bird.x = column.x - bird.width;
				}
			}
			if(bird.y > HEIGHT - 120 || bird.y < 0) 
			{
				gameOver = true;
			}
			if(bird.y + yPos >= HEIGHT -120) {
				bird.y = HEIGHT - 120- bird.height;
			}

		}

		rd.repaint();

	}



	public void repaint(Graphics g) 
	{
		// TODO Auto-generated method stub
		if(score < 10)
		{
			g.setColor(Color.cyan);
			g.fillRect(0, 0, WIDTH, HEIGHT);

		}else if(score >= 10 && score <= 20) {
			
			g.setColor(Color.orange.brighter());
			g.fillRect(0, 0, WIDTH, HEIGHT);

		}
		else if (score > 20  && score <= 30) {
		g.setColor(Color.orange);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		}
		else  {
			g.setColor(Color.black);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			}
		g.setColor(Color.pink);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);

		g.setColor(Color.orange);
		g.fillRect(0, HEIGHT-120, WIDTH, 120);

		g.setColor(Color.green);
		g.fillRect(0, HEIGHT-120, WIDTH, 20);


		for (Rectangle column : cols)
		{
			paintColumn(g, column);
		}


		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 100));


		if(!gameStarted)
		{
			g.drawString("Click to start", 75, HEIGHT/2 - 50);

		}

		if(gameOver)
		{
			g.drawString("GameOver", 100, HEIGHT/2 - 50);

		}
		if(!gameOver && gameStarted)
		{
			g.drawString(String.valueOf(score), WIDTH/2-25, 100);
		} 
		

	}

	public void jump() 
	{
		if(gameOver) 
		{

			bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
			cols.clear();

			yPos = 0;

			score = 0;

			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);

			gameOver = false;
		}

		if(!gameStarted) 
		{
			gameStarted = true;

		}
		else if(!gameOver) 
		{

			if(yPos > 0) 
			{
				yPos = 0;
			}

			yPos -= 10;


		}
	}






	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		jump();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			jump();

		}

	}












}
