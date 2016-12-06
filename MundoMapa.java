import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Clase que recibe una cadena y carga el archivo como imagen para su posterior uso en ScrollWorld
 * 
 * @author: Carlos Antonio Aguiñaga Camacho 
 * @version: 120520160035
 */
public class MundoMapa extends Actor
{
    /**
     * Act - do whatever the MundoMapa wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public MundoMapa(String cad){
        setImage(cad);
    }
}
