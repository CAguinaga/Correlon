import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
* Clase que crea la entidad del enemigo en el mundo
 * 
 * @author Carlos Antonio Aguiñaga Camacho
 * @version 120520160035
 */
public class Enemigo extends Actor
{
    //guarda las coordenadas de los enemigos
    int coordX, coordY, tipo, toca = 0;
    
    //asigna las coor denadas y el tipo de enemigo
    public Enemigo(int iX, int iY, int nTipo){
        coordX = iX;
        coordY = iY;
        tipo = nTipo;
    }
    /**
     * Act - do whatever the enemigo wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        cambiaTipo();
    }
    
    /**
     * Metodo para obtener las coordenadas de un enemigo, si se necesitan
     */
    public void getCoord(){
        coordX=getX();
        coordY=getY();
    }

    /**
     * Metodo que asigna la imagen del enemigo dependiendo de su tipo
     */
    public void cambiaTipo(){
        
        //inicializa la cadena que guardara las imagenes a usar
        String cad = "";
        
        //asigna imagen por tipo
        if ( tipo == 0 ){
           cad = "snake.png";
           //setImage(img);
        }
        else if ( tipo == 1 ){
            cad = "hedgehog.png";
           //setImage(img);
        }
        
        //aplica la imagen
        setImage(cad);
    }
}
