// ************************************************************************* //
// 
// Authors:              Dominic Aquilina
// Title:                Sprite Class
// File Name:            Sprite.java
// 
// Date Started:         2010.04.26
// Date Completed:       2010.##.##
// 
// ************************************************************************* //
// 
// All code and ideas presented in this program belong to the respective
// owners. Any modification or reproduction of the code or ideas presented
// here is prohibited without express permission of the author. This program
// is entirely the work of the author, with the following exceptions:
//
// - Peter Samsonov, for ideas as to build logic and code examples
// 
// ************************************************************************* //
// 
// Program Description:
// 
//   A custom Sprite object to handle sprite building, animation, storage,
// presentation, etc.
// 
// ************************************************************************* //
// 
// Notes:
// 
// - Line breaks at ~80 characters for visibility
// 
// ************************************************************************* //
// 
// Changelog:
// 
// 2010/04/26 - Project Start
//            - Created enum Animations.Java
//            - Created template for basic fill functionality for Sprite
// 2010/04/27 - Included int [] [] delay to handle the framerate
//            - Implemented fill functionality for the setAnimation method
//            - Included descriptive Javadocs wherever relevant
//            - Included template for draw method (awt and swing)
// 2010/04/28 - Changed animations from Image [] [] to BufferedImage [] []
//              - Optimized logic for setAnimation for use with BufferedImage
//            - Removed draw methods in favour of returning the correct frame
//              dynamically to the Paint method of the game window.
//            - Included the prevType flag to ensure that the pointer is reset
//              if the animation changes suddenly before the previous animation
//              has finished
//            - Refined getFrame logic to account for various issues
// 2013/05/25 - Package name changed and project added to GitHub
// 
// ************************************************************************* //

//Package
package DAquilina;

//Imported Packages
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

/**
 * Use this object to handle Sprites. Each sprite contains slots for each type
 * of animation contained in the Animations enum, and can be set individually
 * via the setAnimation method.
 * 
 * <br /> <br />
 * 
 * ie. mySprite.setAnimation (Animations.ATTACK, myAttackTemplate, 2, 2, 4);
 * 
 * <br /> <br />
 * 
 * The Image templates for animations are essentially grids that contain each
 * individually frame of the animation.
 * 
 * <br /> <br />
 * 
 * ie.          
 *   __ __      <br /> &nbsp;
 * 1|._|'_|2    <br /> &nbsp;
 * 3|_'|_.|4    <br /> &nbsp;
 * 
 * <br /> <br />
 * 
 * Each animation has a corresponding delay array, which determines the delay
 * between each frame of the animation. If, for example, the above animation 
 * runs overall at 12 frames per second (default), except for the first frame,
 * which is extended, the corresponding delay array might be something like the
 * following:
 * 
 * <br /> <br />
 * 
 * int [] animationDelay = {200, 59, 59, 59};
 * 
 * <br /> <br />
 * 
 * The delay would then be applied via the setDelay method.
 * 
 * <br /> <br />
 * 
 * ie. mySprite.setDelay (Animations.ATTACK, animationDelay);
 * 
 * <br /> <br />
 * 
 * Since only one frame deviates from the default, however, a better solution
 * might be to set the delay frame manually.
 * 
 * <br /> <br />
 * 
 * ie. mySprite.setDelay (Animations.ATTACK, 0, 200);
 * 
 * <br /> <br />
 * 
 * --------------------------------
 * 
 * @author Dominic Aquilina
 * @version 0.0.0.1 ALPHA
 */
public class Sprite
{
  //Constants
  /**
   * Constant: 12 Frames per second
   */
  public static final int FR_12FPS = 83;
  
  /**
   * Constant: 100 Frames per second
   */
  public static final int FR_100FPS = 10;
  
  //Members
  /**
   * Holds the various animations this Sprite can perform.
   * 
   * <br /> <br />
   * 
   * 0  - IDLE1              <br />
   * 1  - IDLE2              <br />
   * 2  - IDLE3,             <br />
   * 3  - MOVE,              <br />
   * 4  - JUMP1,             <br />
   * 5  - JUMP2,             <br />
   * 6  - CROUCH,            <br />
   * 7  - ATTACK1,           <br />
   * 8  - ATTACK2,           <br />
   * 9  - ATTACK3,           <br />
   * 10 - TAKE_DAMAGE,       <br />
   * 11 - DEATH1,            <br />
   * 12 - DEATH2,            <br />
   * 13 - MISC1,             <br />
   * 14 - MISC2,             <br />
   * 15 - MISC3,             <br />
   * 16 - MISC4,             <br />
   * 17 - MISC5,             <br />
   *                         <br />
   * May be specified:       <br />
   * 18 - MOVE_UP,           <br />
   * 19 - MOVE_DOWN,         <br />
   * 20 - MOVE_LEFT,         <br />
   * 21 - MOVE_RIGHT,        <br />
   *                         <br />
   * 22 - ATTACK_MELEE,      <br />
   * 23 - ATTACK_RANGED;     <br />
   * 24 - ATTACK_STRONG;     <br />
   * 25 - ATTACK_WEAK;       <br />
   */
  private BufferedImage [] [] animations = new BufferedImage [26] [];
  
  /**
   * The delay between each frame in the corresponding animations cell.
   * 
   * @see {@link #animations}
   */
  private int [] [] delay = new int [26] [];
  
  /**
   * Constant determining the framerate of the average animation. Newly-
   * filled animations will default to the overall framerate as the
   * corresponding delay. Altered frame delay will have to be manually
   * specified programmatically.
   * 
   *  @see {@link #setDelay (Animations, int [])}
   *  @see {@link #setDelay (Animations, int, int)
   */
  public int framerate = FR_12FPS;
  
  /**
   * The current frame index at which the animation is pointing.
   */
  private int framePointer = 0;
  
  /**
   * The type of animation that the Sprite was last performing. Used to ensure
   * that the framePointer is reset if the animation suddenly changes before
   * the previous animation has finished.
   */
  private Animations prevType;
  
  /**
   * Counts the number of Sprites created by the application.
   */
  private static int numSprites = 0;
  
  //Constructors
  /**
   * Default Constructor.
   */
  public Sprite ()
  {
    numSprites++;
    
    //Debug
    System.out.println ("Sprite number " + numSprites + " created");
  }
  
  //Functions
  /**
   * Counts the number of Sprites that have been created in the current session
   * 
   * @return The number of Sprites created
   */
  public static int Count ()
  {
    return numSprites;
  }
  
  /**
   * Accessor method for the read-only frame pointer.
   * 
   * @return The frame index at which the animation is currently pointer.
   */
  public int FramePointer ()
  {
    return framePointer;
  }
  
  /**
   * Sets up a specific animation (ie. MOVE, ATTACK). In order for this method
   * to function as expected, the frames that contain valid content must be 
   * arranged left to right, top to bottom.
   * 
   * <br /> <br />
   * 
   * ie.
   *   __ __ __    <br /> &nbsp;
   * 1|._|._|._|3  <br /> &nbsp;
   * 4|._|._|._|6  <br /> &nbsp;
   * 7|._| _| _|9  <br /> &nbsp;
   * 
   * <br /> <br />
   * 
   * In the above example, frames 8 and 9 do not contain content. The number
   * of frames is therefore 7. Height and Width would both be 3.
   * 
   * @param type      - The type of animation, based on the Animations enum
   * @param i         - The Image that contains the frames of this animation
   * @param height    - The number of frames that make up the height of the
   *                    animation.
   * @param width     - The number of frames that make up the width of the
   *                    animation
   * @param numFrames - The total number of frames contained in this animation
   */
  public void setAnimation (Animations type, BufferedImage i, int height, 
                            int width, int numFrames)
  {
    int index = type.value ();
    
    //Determine the dimensions of the image
    ImageIcon image = new ImageIcon (i);
    double imgHeight = image.getIconHeight ();
    double imgWidth  = image.getIconWidth  ();
    
    //Determine the dimensions of each cell
    double cellHeight = imgHeight / height;
    double cellWidth  = imgWidth  / width;
    
    //Allocate space for the new animation
    animations [index] = new BufferedImage [numFrames];
    delay [index] = new int [numFrames];
    
    //split the Image into its individual frames, fill animation and frame delay
    int framesCounted = 0;
    
    for (double rowPos = 0; rowPos < imgHeight; rowPos += cellHeight)
    {
      for (double colPos = 0; colPos < imgWidth; colPos += cellWidth)
      { 
        animations [index] [framesCounted] = i.getSubimage (((int) colPos), 
                                                            ((int) rowPos), 
                                                            ((int) cellWidth),
                                                            ((int) cellHeight));
        delay [index] [framesCounted] = framerate;
        
        framesCounted++;
        
        //Ensures that blank/unwanted frames will not be added
        if (framesCounted == numFrames)
        {
          break;
        }
      }
      
      //The unwanted frames should be on the last row of the image anyway,
      //but in the case of a template larger than the intended number of 
      //frames, this block is required to ensure that only the desired frames 
      //are added
      if (framesCounted == numFrames)
      {
        break;
      }
    }
  }
  
  /**
   * Sets the specified animation to a previously defined sprite animation.
   * 
   * @see {@link #setAnimation(Animations, Image, int, int, int)}
   * 
   * @param type         - The type of animation, based on the Animations enum
   * @param newAnimation - The new animation
   */
  public void setAnimation (Animations type, BufferedImage [] newAnimation)
  {
    animations [type.value ()] = newAnimation;
  }
  
  /**
   * Sets the frame delay of the given animation to the new set of values
   * provided by the user.
   * 
   * @param type       - The type of animation, based on the Animations enum
   * @param newDelay   - The new set of frame delay values
   * 
   * @throws Exception - If the specified set of values does not have the
   *                     same length as the given animation.
   */
  public void setDelay (Animations type, int [] newDelay) throws Exception
  {
    if (animations [type.value ()].length == newDelay.length)
    {
      delay [type.value ()] = newDelay;
    }
    else
    {
      throw new Exception ("Length of delay array does not match that of " +
                           "the corresponding animation.");
    }
  }
  
  /**
   * Sets the frame delay of the given frame of the given animation to the new
   * value provided by the user.
   * 
   * @param type       - The type of animation, based on the Animations enum
   * @param frame      - The specific frame of the animation
   * @param newDelay   - The new frame delay value
   * 
   * @throws Exception - If the specified frame lies outside the frame delay
   *                     array for the given animation
   */
  public void setDelay (Animations type, int frame, int newDelay) throws Exception
  {
    if (frame < delay [type.value ()].length)
    {
      delay [type.value ()] [frame] = newDelay;
    }
    else
    {
      throw new Exception ("The number of frames contained in that animation " +
                           "is less than the value of the specified frame.");
    }
  }
  
  /**
   * Selects the correct frame of the target animation. If the specified frame
   * is outside the range of the given animation, the frame will reset to the
   * IDLE1 animation. The framePointer is internally incremented. This method
   * should be used recursively with that same pointer. The framePointer will
   * reset to 0 if the method detects that the Animation type has changed
   * before the previous animation has finished.
   * 
   * <br /> <br />
   * 
   * Use:
   * 
   * <br /> <br />
   * 
   * MyImage = MySprite.getFrame (Animations.ATTACK, MySprite.FramePointer ());
   * 
   * <br /> <br />
   * 
   * If the call isn't used in this way (ie. the user wants to skip a frame),
   * the method detects that the desired frame is not the current frame, and
   * will re-adjust the framePointer to match the desired Frame.
   * 
   * @param type  - The type of animation, based on the Animations enum
   * @param frame - The current frame at which the animation is pointing
   * @return The desired frame of the given animation, or the first frame of
   *         the IDLE1 animation if the frame is out of scope.
   */
  public BufferedImage getFrame (Animations type, int frame)
  {
    int index = type.value ();
    
    //Reset pointer if the animation type has changed
    if (prevType != type)
    {
      framePointer = 0;
      
      prevType = type;
    }
    
    //Return IDLE1 animation if the specified frame is outside the frames
    //contained in that animation
    if (frame >= animations [index].length)
    {
      framePointer = 0;
      
      return animations [Animations.IDLE1.value ()] [0];
    }
    
    //Prepare the target frame
    BufferedImage targetFrame = animations [index] [framePointer];
    
    //Accounts for frames not specified by calling FramePointer ()
    //  -- Updates the framePointer to point at the proper object
    if (frame != framePointer)
    {
      framePointer = frame;
    }
    else
    {
      framePointer++;
    }
    
    //resets the counter once the last frame of the animation is reached
    if (framePointer >= animations [index].length)
    {
      framePointer = 0;
      
      //prevType = Animations.IDLE1;
    }
    
    return targetFrame;
  }
}
