import java.util.LinkedList;

import processing.core.PApplet;
import processing.core.PFont;

public class Main extends PApplet
{
	//public static int numPeople = 2;
	public static int numMales = 1;
	public static int numFemales = 1;

	//static LinkedList<Person> people = new LinkedList<Person>();
	
	static LinkedList<Person> males = new LinkedList<Person>();
	static LinkedList<Person> females = new LinkedList<Person>();
	
	static Person temp;
	
	// public int lastTime = 0;
	public static int bg;
	int c;
	PFont f;

	int speed;
	public static void main(String args[])
	{
		PApplet.main(new String[] { "Main" });
	}

	public void setup()
	{
		size(800, 600);
		bg = color(240, 230, 220);
		ellipseMode(CENTER);
		speed = 60;
		f = createFont("Univers Medium", 24, true);
		textFont(f);

		// initialize people
		for (int i = 0; i < numMales+numFemales; i++)
		{
			temp = new Person(this, (i % 2 == 0) ? true : false);
			if (temp.gender)
			{
				males.add(temp);
			}
			else
			{
				females.add(temp);
			}
			//people.add(new Person(this));
		}
	}

	public void draw()
	{
		background(bg);
		frameRate(speed);
		println(mouseX, mouseY);
		
		//**************LIVE****************//
		for (int i = 0; i < numMales; i++)
		{
			males.get(i).live();
		}
		for (int i = 0; i < numFemales; i++)
		{
			females.get(i).live();
		}
		//END**************LIVE**************//
		
//		//*******************DETECT COLLISIONS********************//
		for (int i = 0; i < numMales; i++)
		{
			for (int j = 0; j < numFemales; j++)
			{
				if (sphereCollide((int) males.get(i).posX, (int) males.get(i).posY, males.get(i).size, (int) females.get(j).posX, (int) females.get(j).posY, females.get(j).size))
				{
					if(males.get(i).reproduce())
						females.get(j).reproduce();
				}
			}
		}
		//END*******************DETECT COLLISIONS********************//
		
		fill(0);
		text("time: " + frameCount, width - 190, 30);
		text("speed: " + Math.round(frameRate * 100) / 100, width - 190, 60);

		// test color grabbing
		c = get(mouseX, mouseY);
		fill(c);
		noStroke();
		rect(25, 25, 50, 50);

	}
	
	public void keyPressed()
	{
		if (key == '+')
		{
			speed += 10;
		}
		if (key == '-' && speed > 10)
		{
			speed -= 10;
		}
			
	}
	
	public void mousePressed()
	{
		if (mouseButton == LEFT)
		{
			temp = new Person(this, mouseX, mouseY);
			if (temp.gender)
			{
				males.add(temp);
				numMales++;
				println("spawned 1 male");
			}
			else
			{
				females.add(temp);
				numFemales++;
				println("spawned 1 female");
			}
		}

		if (mouseButton == RIGHT)
		{
			for (int i = 0; i < numMales; i++)
			{
				if (pointBall(mouseX, mouseY, (int) males.get(i).posX, (int) males.get(i).posY, males.get(i).size))
				{
					println("kill");
					males.get(i).die();
				}
			}
			for (int i = 0; i < numFemales; i++)
			{
				if (pointBall(mouseX, mouseY, (int) females.get(i).posX, (int) females.get(i).posY, females.get(i).size))
				{
					println("kill");
					females.get(i).die();
				}
			}
		}
	}

	/*
	 * BALL/BALL COLLISION FUNCTION Jeff Thompson // v0.9 // November 2011 //
	 * www.jeffreythompson.org
	 * 
	 * Takes 6 arguments: + x,y position of the first ball - in this case "you"
	 * + diameter of first ball - elliptical collision is VERY difficult + x,y
	 * position of the second ball + diameter of second ball
	 */
	boolean sphereCollide(int x1, int y1, int d1, int x2, int y2, int d2)
	{
		
		// find distance between the two objects
		float xDist = x1 - x2; // distance horiz
		float yDist = y1 - y2; // distance vert
		float distance = PApplet.sqrt((xDist * xDist) + (yDist * yDist)); // diagonal
																			// distance

		// test for collision
		if (d1 / 2 + d2 / 2 > distance)
		{
			
			return true; // if a hit, return true
		}
		else
		{ // if not, return false
			
			return false;
		}
	}

	/*
	 * POINT/BALL COLLISION FUNCTION Jeff Thompson // v0.9 // November 2011 //
	 * www.jeffreythompson.org Takes 5 arguments: + x,y position of the point -
	 * in this case "you" + x,y position of the ball + diameter of ball -
	 * elliptical collision is VERY difficult
	 */
	boolean pointBall(int px, int py, int bx, int by, int bSize)
	{
		
		// find distance between the two objects
		float xDist = px - bx; // distance horiz
		float yDist = py - by; // distance vert
		float distance = sqrt((xDist * xDist) + (yDist * yDist)); // diagonal
																	// distance

		// test for collision
		if (bSize / 2 > distance)
		{
			
			return true; // if a hit, return true
		}
		else
		{ // if not, return false
			
			return false;
		}
	}
}
