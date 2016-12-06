import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Clase que crea plataformas
 * 
 * @author Carlos Antonio Aguiñaga Camacho
 * @version 120520160035
 */
public class Plataforma extends Actor
{
    //guardan las coordenadas y tipo del bloque, y si es un bloque ralentizador
    int coordX, coordY, lento;
    
    //constructor debe recibir coordenadas para colocar los bloques
    public Plataforma(int mapaX, int mapaY, int sino){
        coordX = mapaX;
        coordY = mapaY;
        lento = sino;
    }
}
