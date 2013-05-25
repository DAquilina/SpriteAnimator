//Package
package DAquilina;

//Imported Packages
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.ImageIO;

@SuppressWarnings ({ "serial" })
public class TEST extends JFrame implements KeyListener
{
  static BufferedImage bg, man, manRight, manLeft, buffer, robot;
  
  static Sprite manSprite = new Sprite ();
  
  static Sprite ballSprite = new Sprite ();
  
  static Animations animType;
  
  static int xPos = 20;
  static int yPos = 20;
  
  static boolean left;
  static boolean right;
  static boolean up;
  static boolean down;
  
  public TEST ()
  {
    buffer = new BufferedImage (400, 400, BufferedImage.TYPE_INT_RGB);
    
    manSprite.setAnimation (Animations.IDLE1, man, 1, 1, 1);
    manSprite.setAnimation (Animations.MOVE_LEFT, manLeft, 5, 6, 30);
    manSprite.setAnimation (Animations.MOVE_RIGHT, manRight, 5, 6, 30);
    
    ballSprite.setAnimation (Animations.IDLE1, robot, 2, 2, 4);
    
    setVisible (true);
    setSize (400, 400);
    addKeyListener (this);
    setResizable (false);
    setDefaultCloseOperation (EXIT_ON_CLOSE);
    
    animType = Animations.IDLE1;
    
    repaint();
  }
  
  public static void main (String [] args)
  {
	  String path = new File ("Resources").getAbsolutePath ();
	  
	  System.out.println (path);
	
    try
    {
      bg = ImageIO.read (new File 
       (path + "\\grass.png"));
      
      System.out.println ("A");
      
      man = ImageIO.read (new File 
       (path + "\\walksequence_idle.png"));
      
      System.out.println ("B");
      
      manLeft = ImageIO.read (new File 
       (path + "\\walksequence_left.png"));
      
      System.out.println ("C");
      
      manRight = ImageIO.read (new File 
       (path + "\\walksequence_right.png"));
      
      System.out.println ("D");
      
      robot = ImageIO.read (new File 
       (path + "\\spinning_ball.gif"));
      
      System.out.println ("E");
    }
    catch (Exception e)
    {
      System.out.println (e);
      
      return;
    }
    
    new TEST ();
  }
  
  public void paint (Graphics g)
  {
    if (left)
    {
      xPos -= 10;
      animType = Animations.MOVE_LEFT;
    }
    
    if (right)
    {
      xPos += 10;
      animType = Animations.MOVE_RIGHT;
    }
    
    if (up)
    {
      yPos -= 10;
      animType = Animations.MOVE_RIGHT;
    }
    
    if (down)
    {
      yPos += 10;
      animType = Animations.MOVE_LEFT;
    }
    
    Graphics gi = buffer.createGraphics ();
    
    gi.drawImage (bg, 0, 0, this);
    
    gi.drawImage (manSprite.getFrame (animType, manSprite.FramePointer ()),
                  xPos, yPos, this);
    
    gi.drawImage (ballSprite.getFrame (Animations.IDLE1, ballSprite.FramePointer ()),
                  50, 50, this);
    
    Graphics2D g2= (Graphics2D) g;
    g2.drawImage (buffer, null, 0, 0);
    
    try
    {
      Thread.sleep (10);
    }
    catch (Exception e)
    {
      
    }
    
    repaint();
  }

  @Override
  public void keyPressed (KeyEvent e)
  {
    if (e.getKeyCode () == KeyEvent.VK_LEFT)
    {
      left = true;
    }
    if (e.getKeyCode () == KeyEvent.VK_RIGHT)
    {
      right = true;
    }
    if (e.getKeyCode () == KeyEvent.VK_UP)
    {
      up = true;
    }
    if (e.getKeyCode () == KeyEvent.VK_DOWN)
    {
      down = true;
    }
  }

  @Override
  public void keyReleased (KeyEvent e)
  {
    if (e.getKeyCode () == KeyEvent.VK_LEFT)
    {
      left = false;
    }
    if (e.getKeyCode () == KeyEvent.VK_RIGHT)
    {
      right = false;
    }
    if (e.getKeyCode () == KeyEvent.VK_UP)
    {
      up = false;
    }
    if (e.getKeyCode () == KeyEvent.VK_DOWN)
    {
      down = false;
    }
    
    if (!left && !right && !up && !down)
    {
      animType = Animations.IDLE1;
    }
  }

  @Override
  public void keyTyped (KeyEvent e)
  {
    
  }
}
