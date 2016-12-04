import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Clase que crea plataformas
 * 
 * @author Carlos Antonio Aguiñaga Camacho
 * @version 120320161700
 */
public class Plataforma extends Actor
{
    //guardan las coordenadas y tipo del bloque
    int coordX, coordY;
    
    //constructor debe recibir coordenadas para colocar los bloques
    public Plataforma(int mapaX, int mapaY){
        coordX = mapaX;
        coordY = mapaY;
    }
}
