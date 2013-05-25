//Package
package DAquilina;

/**
 * Enumeration for generic sprite animations, specifying the different types of
 * animations a single sprite may contain. The commented numbers are the values
 * associated with each animation type via this.value ().
 * 
 * @author Dominic Aquilina
 */
public enum Animations
{
  IDLE1,           //0
  IDLE2,           //1
  IDLE3,           //2
  MOVE,            //3
  JUMP1,           //4
  JUMP2,           //5
  CROUCH,          //6
  ATTACK1,         //7
  ATTACK2,         //8
  ATTACK3,         //9
  TAKE_DAMAGE,     //10
  DEATH1,          //11
  DEATH2,          //12
  MISC1,           //13
  MISC2,           //14
  MISC3,           //15
  MISC4,           //16
  MISC5,           //17
  
  //May be specified:
  MOVE_UP,         //18
  MOVE_DOWN,       //19
  MOVE_LEFT,       //20
  MOVE_RIGHT,      //21
  
  ATTACK_MELEE,    //22
  ATTACK_RANGED,   //23
  ATTACK_STRONG,   //24
  ATTACK_WEAK;     //25
  
  /**
   * Returns the Integer value associated with each animation type. Useful for
   * array index access, etc.
   */
  //Note: Remember to update switch-case each time an enum filed is added or modified.
  int value ()
  {
    switch (this)
    {
      case IDLE1:
        return 0;
      case IDLE2:
        return 1;
      case IDLE3:
        return 2;
      case MOVE:
        return 3;
      case JUMP1:
        return 4;
      case JUMP2:
        return 5;
      case CROUCH:
        return 6;
      case ATTACK1:
        return 7;
      case ATTACK2:
        return 8;
      case ATTACK3:
        return 9;
      case TAKE_DAMAGE:
        return 10;
      case DEATH1:
        return 11;
      case DEATH2:
        return 12;
      case MISC1:
        return 13;
      case MISC2:
        return 14;
      case MISC3:
        return 15;
      case MISC4:
        return 16;
      case MISC5:
        return 17;
      case MOVE_UP:
        return 18;
      case MOVE_DOWN:
        return 19;
      case MOVE_LEFT:
        return 20;
      case MOVE_RIGHT:
        return 21;
      case ATTACK_MELEE:
        return 22;
      case ATTACK_RANGED:
        return 23;
      case ATTACK_STRONG:
        return 24;
      case ATTACK_WEAK:
        return 25;
      default:
        return -1;
    }
  }
}
