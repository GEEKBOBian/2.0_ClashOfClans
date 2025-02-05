//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// character moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable {
public int HitCountChar2 = 5;
public int HitCountChar3 = 5;
   //Variable Definition Section
   //Declare the variables used in the program 
   //You can set their initial values too
   
   //Sets the width and height of the program window
	final int WIDTH = 1000;
	final int HEIGHT = 700;

   //Declare the variables needed for the graphics
	public JFrame frame;
	public Canvas canvas;
   public JPanel panel;
   
	public BufferStrategy bufferStrategy;
	public Image characterPic;
	public Image character2Pic;
	public Image character3Pic;
	public Image backgroundPic;

   //Declare the objects used in the program
   //These are things that are made up of more than one variable type
	private Character character;
	private Character character2;
	private Character character3;


   // Main method definition
   // This is the code that runs first and automatically
	public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
		new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method  
	}


   // Constructor Method
   // This has the same name as the class
   // This section is the setup portion of the program
   // Initialize your variables and construct your program objects here.
	public BasicGameApp() {
      
      setUpGraphics();

      //variable and objects
      //create (construct) the objects needed for the game and load up
		characterPic = Toolkit.getDefaultToolkit().getImage("inferno-tower-clash-of-clans.png"); //load the picture
		character2Pic = Toolkit.getDefaultToolkit().getImage("queen-clash-of-clans_jpg_320.jpg");
		character3Pic = Toolkit.getDefaultToolkit().getImage("download (1).jpeg");
		backgroundPic = Toolkit.getDefaultToolkit().getImage("clash-of-clans-bases-2.jpg");
		character = new Character(100, 200);
		character2 = new Character(200, 600);
		character2.dx = 4;
		character2.dy = 7;
		character3 = new Character(10,100);
		character3.dx = 10;
		character3.dy =2;


	}// BasicGameApp()

	public void moveThings() {
		//calls the move( ) code in the objects
//		if(character2.xpos > 500){
//			character2.isAlive = false;
//			System.out.println("oops");
//		}

		collisions();
		character.bounce();
		character2.wrap();
		character3.wrap();

	}

		public void collisions() {
			if (character.rec.intersects(character2.rec) && character.isCrashing == false) {
				System.out.println("explosion");
				character.isCrashing = true;
				//character2.isAlive = false;
				character.dx = -character.dx;
				character.dy = -character.dy;
			//	character.width = character.width + 10;
			//	character.height = character.height + 10;
				character2.dx = -character2.dx;
				character2.dy = -character2.dy;
			//	character2.dx = character2.dx + 20;
			//	character2.dy = character2.dy + 20;
			//	character3.dx = -character3.dx;
			//	character3.dy = -character3.dy;
			//	character3.dx = character3.dx + 20;
			//	character3.dy = character3.dy + 20;
				HitCountChar2 = HitCountChar2-1;
				if(HitCountChar2<1){
					character2.isAlive= false;
				}
			}
			if (!character.rec.intersects(character2.rec)) {
				character.isCrashing = false;
			}
			if (character2.rec.intersects(character3.rec) && character2.isCrashing == false) {
				System.out.println("explosion");
				character2.isCrashing = true;
				//character3.isAlive = false;
				character2.dx = -character2.dx;
				character2.dy = -character2.dy;
//				character.width = character.width + 10;
//				characterer.height = character.height + 10;
			character3.dx = -character3.dx;
				character3.dy = -character3.dy;
//				character3.dx = character3.dx + 20;
//				character3.dy = character3.dy + 20;
				HitCountChar2= HitCountChar2 +1;

			}
			if (!character2.rec.intersects(character3.rec)) {
				character2.isCrashing = false;
			}
			if (character.rec.intersects(character3.rec) && character3.isCrashing == false){
				HitCountChar3 = HitCountChar3 -1;
				character3.isCrashing = true;
				System.out.println("crash");
			}
			if (!character.rec.intersects(character3.rec)) {
				character3.isCrashing = false;
			}

			//make collisions between characters
		//	if character collides character2;
		//	character2 isAlive = false;
		}
//*******************************************************************************
//User Method Section
//
// put your code to do things here.

   // main thread
   // this is the code that plays the game after you set things up
	public void run() {

      //for the moment we will loop things forever.
		while (true) {

         moveThings();  //move all the game objects
         render();  // paint the graphics
         pause(20); // sleep for 10 ms
		}
	}



	
   //Pauses or sleeps the computer for the amount specified in milliseconds
   public void pause(int time ){
   		//sleep
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {

			}
   }

   //Graphics setup method
   private void setUpGraphics() {
      frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.
   
      panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
      panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
      panel.setLayout(null);   //set the layout
   
      // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
      // and trap input events (Mouse and Keyboard events)
      canvas = new Canvas();  
      canvas.setBounds(0, 0, WIDTH, HEIGHT);
      canvas.setIgnoreRepaint(true);
   
      panel.add(canvas);  // adds the canvas to the panel.
   
      // frame operations
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
      frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
      frame.setResizable(false);   //makes it so the frame cannot be resized
      frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!
      
      // sets up things so the screen displays images nicely.
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      canvas.requestFocus();
      System.out.println("DONE graphic setup");
   
   }


	//paints things on the screen using bufferStrategy
	private void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.drawImage(backgroundPic, 0, 0, WIDTH, HEIGHT, null);
		//draw the image of the astronaut
		g.drawImage(characterPic, character.xpos, character.ypos, character.width, character.height, null);
		if(character2.isAlive == true) {
			g.setColor(Color.WHITE);
			g.fillRect(character2.xpos-25, character2.ypos-35, 75, 20);
			g.setColor(Color.BLACK);

			g.drawString("hit points "+HitCountChar2, character2.xpos-20, character2.ypos -20);
			g.drawImage(character2Pic, character2.xpos, character2.ypos, character2.width, character2.height, null);
		}
		if(character3.isAlive == true) {
			g.setColor(Color.WHITE);
			g.fillRect(character3.xpos-25, character3.ypos-35, 75, 20);
			g.setColor(Color.BLACK);

			g.drawString("hit points "+HitCountChar3, character3.xpos-20, character3.ypos -20);
			g.drawImage(character3Pic, character3.xpos, character3.ypos, character3.width, character3.height, null);
		}
		g.drawImage(character3Pic, character3.xpos, character3.ypos, character3.width, character3.height, null);

		g.dispose();
		bufferStrategy.show();
	}
}