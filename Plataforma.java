import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Plataformas here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Plataforma extends Actor
{
    //guardan las coordenadas del bloque
    int coordX, coordY;
    
    //constructor debe recibir coordenadas para colocar los bloques
    public Plataforma(int mapaX, int mapaY){
        coordX = mapaX;
        coordY = mapaY;
    }
}
