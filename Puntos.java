import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * Clase que guarda la puntuacion del jugador
 * 
 * @author: Carlos Antonio Aguiñaga Camacho 
 * @version: 120520160035
 */
public class Puntos extends Actor
{   
    //Inicializa los puntos en 0 por defecto
    int puntos = 0;
    
    //boolean lento = false;
    
    //Muestra los puntos en pantalla
    public void act() 
    {
         setImage( new GreenfootImage("Puntos: " + puntos, 24, Color.WHITE, Color.BLACK) );
         
         //llama al metodo que aumenta la puntuacion
         masPuntos();
    }
    
    /**
     * Metodo que aumenta la puntuacion con el tiempo
     */
    public void masPuntos(){
        //if( lento != true )
            puntos++;
    }
    
    /**
     * Metodo que realiza las operaciones necesarias con los puntos. Recibe una cadena con
     * la operacion a realizar y un porcentaje deseado de la puntuacion
     */
    public void operacionPC(String operador, int porcentaje){
        
        //calcula el porcentaje
        int dif = (puntos/100) * porcentaje;
        
        //realiza las operaciones
        if( operador == "suma" ){
            puntos += dif;
        }
        
        if( operador == "resta" ){
            puntos -= dif;
        }
        
    }
}
