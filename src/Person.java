import processing.core.PApplet;
import processing.core.PVector;

public class Person
{
	PApplet parent; // reference to the window
	int age; // the age of the person in years (1 year = 10 seconds)
	int size; // the diameter of the person in pixels
	int color; // the color of the person (r,g,b)
	float posX, posY; // the persons current position
	int birthday; // the frame number when the person was born
	// int lastTime; //used for frame based time control
	float vectorX, vectorY; // direction and speed that the person is moving
	Mood mood; // contains all the variables to run the mental state of the person
	boolean gender; // the gender of this person. 1 is male, 0 is female
	Person offspring[]; // an array of people that holds this persons kids
	Person parents[]; // an array of people that holds this persons parents
	int lastBaby;
	int vision; // this is the amount of pixels the blob can see from its edge
	
	Person(PApplet p)// , parents if any)
	{
		parent = p;
		gender = (parent.random(-1, 1) >= 0) ? true : false;
		// lastTime = 0; //such programming
		//TODO: warmer colors for female, cooler for male
		color = parent.color(parent.random(200, 255), parent.random(100, 180), parent.random(0, 80)); // or based on parents
		if(gender) color = parent.color(parent.random(0, 80), parent.random(100, 180), parent.random(200, 255));
		size = (int) parent.random(50, 200);
		posX = (parent.random(0 + size, parent.width - size));
		
		posY = (parent.random(0 + size, parent.height - size));
		birthday = parent.frameCount; // this needs to be implemented in the aging system
		vectorX = parent.random(-1, 1);
		vectorY = parent.random(-1, 1);

		mood = new Mood();
		mood.dopamine = .5f;
		mood.norepinephrine = .5f;
		mood.seritonin = .5f;
		mood.horny = parent.random(0, 1);

		
		
		vision = 10;
		
		lastBaby = parent.frameCount;

	}
	
	Person(PApplet p, boolean g)// , parents if any)
	{
		parent = p;
		gender = g;
		// lastTime = 0; //such programming
		//TODO: warmer colors for female, cooler for male
		color = parent.color(parent.random(200, 255), parent.random(100, 180), parent.random(0, 80)); // or based on parents
		if(gender) color = parent.color(parent.random(0, 80), parent.random(100, 180), parent.random(200, 255));
		size = (int) parent.random(50, 200);
		posX = (parent.random(0, parent.width - size));
		
		posY = (parent.random(0, parent.height - size));
		birthday = parent.frameCount; // this needs to be implemented in the aging system
		vectorX = parent.random(-1, 1);
		vectorY = parent.random(-1, 1);

		mood = new Mood();
		mood.dopamine = .5f;
		mood.norepinephrine = .5f;
		mood.seritonin = .5f;
		mood.horny = parent.random(0, 1);

		
		
		vision = 10;
		
		lastBaby = parent.frameCount;

	}

	Person(PApplet p, int x, int y)
	{
		parent = p;
		gender = (parent.random(-1, 1) >= 0) ? true : false;
		// lastTime = 0; //such programming
		//TODO: warmer colors for female, cooler for male
		color = parent.color(parent.random(200, 255), parent.random(100, 180), parent.random(0, 80)); // or based on parents
		if(gender) color = parent.color(parent.random(0, 80), parent.random(100, 180), parent.random(200, 255));
		size = (int) parent.random(50, 200);
		posX = x;
		posY = y;
		birthday = parent.frameCount; // this needs to be implemented in the aging system
		vectorX = parent.random(-1, 1);
		vectorY = parent.random(-1, 1);

		mood = new Mood();
		mood.dopamine = .5f;
		mood.norepinephrine = .5f;
		mood.seritonin = .5f;
		mood.horny = parent.random(0, 1);

		
		
		vision = 10;
		
		lastBaby = parent.frameCount;

	}
	
	void live()
	{
		// draw the person on the screen
		parent.noStroke();
		parent.fill(color);
		parent.ellipse(posX, posY, size, size);

		// age person
		if (parent.frameCount % 600 == 0)// (PApplet.second() % 10 == 0 && // lastTime != PApplet.second())
		{
			age++;
		}
		// if (lastTime != PApplet.second())
		// {
		// lastTime = PApplet.second();
		// }

		// call other abilities
		move();
		updateMood();
		see();
		debug();

	}

	void move()
	{
		// bounce off walls
		
		if (posX - size/2  < 0 || posX + size/2 > parent.width)
		{
			vectorX *= -1;
		}
		if (posY - size/2 < 0 || posY + size/2 > parent.height)
		{
			vectorY *= -1;
		}
		
		// update position
		posX += vectorX;
		posY += vectorY;
	}

	void updateMood()
	{

	}

	/**
	 * looks around as far as the little person can see maybe they find
	 * something interesting maybe they want to whip their dick out and rape
	 * something who knows?
	 */
	void see()
	{
		parent.stroke(0);
		//parent.println(posX, posY);
		//parent.line((int) (posX - vision), (int) (posY - vision), posX + sizeX + vision, posY + sizeY + vision);
		
		//if(ballBall(posX, posY, sizeX, age, age, age))
//		//look around pixels in a certain radius from the blobs edge
//		for (int i = (int) (posX - vision); i < posX + sizeX + vision; i++)
//		{
//			for (int j = (int) (posY - vision); j < posY + sizeY + vision; j++)
//			{
//				//if you see something that isnt the background (it must be another person by deduction 
//				int c = parent.get(i,j);
//				if (c != Main.bg && c != color)
//				{
//					//parent.point(i, j); //draw debug vision zones
//					
//					//flip yo color out
//					parent.text("test", posX, posY);
//				}
//			}
//		}
	}
	//TODO: add boner for males if they are horny (urgency factor of reproduction)
	/*		add gestation periods for females
	 * 		reduce delay from male reproduction
	 * 		people collide when unable to reproduce
	 */
	boolean reproduce()
	{
		if (gender) //male
		{
			if(age < 1) return false;
			
			if (parent.frameCount - lastBaby > 1000)
			{
				lastBaby = parent.frameCount;
				return true;
			}
			else
				return false;
			
		}
		if (!gender) //female
		{
			if(age < 1) return false;
			
			if (parent.frameCount - lastBaby > 1000)
			{
				PApplet.println("baby");
				PApplet.println(parent.frameCount - lastBaby);
				Main.temp = new Person(parent, (int)parent.random(posX*.9f, posX*1.1f), (int)parent.random(posY*.9f, posY*1.1f));
				if (Main.temp.gender)
				{
					Main.males.add(Main.temp);
					Main.numMales++;
				}
				else
				{
					Main.females.add(Main.temp);
					Main.numFemales++;
				}
					
				//Main.people.add(new Person(parent, (int)parent.random(posX*.9f, posX*1.1f), (int)parent.random(posY*.9f, posY*1.1f)));
				//Main.numPeople++;
				lastBaby = parent.frameCount;
				return true;
			}
		}
		return false;	
	}


	void die()
	{
		if (gender)
		{
			Main.numMales--;
			Main.males.remove(this);
		}
		else
		{
			Main.numFemales--;
			Main.females.remove(this);
		}
		//Main.people.remove(this);
	}
	
	void debug()
	{
		parent.fill(0);
		//parent.text("lastBaby: " + (parent.frameCount - lastBaby), posX, posY);
		parent.text((parent.frameCount - lastBaby > 1000 ? "+" : "-"), posX, posY);
		parent.text((parent.frameCount - lastBaby > 750 ? "T" : "_"), posX, posY+20);
		//TODO: print out which LL the person is in
		parent.text((parent.frameCount - lastBaby > 750 ? "T" : "_"), posX, posY+20);
		
	}
	
}