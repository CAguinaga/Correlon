import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * Clase que crea un pequeño cartel de juego terminado y a su vez detiene todo
 * 
 * @author: Carlos Antonio Aguiñaga Camacho 
 * @version: 120520160035
 */
public class GameOver extends Actor
{       
       public GameOver(){
           setImage( new GreenfootImage("Juego Terminado\n", 48, Color.WHITE, Color.BLACK) );
           
           Greenfoot.stop();
           
        }
}
