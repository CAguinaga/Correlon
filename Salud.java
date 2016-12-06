import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * Clase que guarda la salud del jugador
 * 
 * @author: Carlos Antonio Aguiñaga Camacho 
 * @version: 120520160035
 */
public class Salud extends Actor
{
    //Inicializa la salud con un valor predeterminado de 3
    int salud = 3;
    
    //Actualiza el contador en pantalla
    public void act() 
    {
         setImage( new GreenfootImage("Salud: " + salud, 24, Color.WHITE, Color.BLACK) );
    }
    
    /**
     * Metodo que realiza las operaciones con la salud
     */
    public void operacionSal(String operador){

        if( operador == "suma" ){
            salud ++;
        }
        
        if( operador == "resta" ){
            salud --;
        }
        
    }
    
    /**
     * Metodo que envia el valor de la salud
     * 
     * @return int salud
     */
    public int getPuntosSalud(){
        return salud;
    }  
}
