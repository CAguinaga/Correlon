import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Clase que crea la entidad del jugador en el mundo
 * 
 * @author Carlos Antonio Aguiñaga Camacho
 * @version 120420160021
 */
public class Personaje extends Actor
{
    //indica si esta cayendo o no
    boolean cae = false;
    
    //determina la altura del piso respecto a nuestro personaje
    int medidaPiso = getImage().getHeight()/2;
    
    //determina la distancia de una pared
    int medidaLado = getImage().getWidth()/2;
    
    //valor de fuerza
    int grav = 0;
    int vel = 0;
    
    //variables de mundo para crear un scroll que se activa solo a cierta distancia
    World miMundillo;
    int mundoAlto;
    int mundoAncho;
    
    //asigna los valores del mundo a nuestro personaje cuando se agrega al mundo
    public void addedToWorld(World mundo){
        miMundillo = mundo;
        mundoAlto = miMundillo.getHeight();
        mundoAncho = miMundillo.getWidth();
    }
    
    /**
     * Act - do whatever the Personaje wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        //comprueba si cae o no para aplicar la gravedad
        if( cae ){
            gravedad();
        }
        else{
            /*
            //verifica las teclas que se esten presionando para mover al personaje en esa direccion. El mundo se recorre con el scroll
            if( Greenfoot.isKeyDown("left") ){
                //mueve al personaje
                vel = -8;
            }
            else if( Greenfoot.isKeyDown("right") ){
                //mueve al personaje
                vel = +8;
            }
           
            //si no se presionan las teclas el personaje no se mueve
            else{
                vel = 0; 
            }
            */
           
            //si se presiona espacio el personaje saltara
            if( Greenfoot.isKeyDown("space") ){
                grav += 10;
            }
            
            //movimiento perpetuo hacia adelante
            vel = 8;
        }
        
        //gravedad();
        
        //mueve al personaje en el mundo
        mueve();
    }
    
    /**
     * Metodo para realizar el movimiento del personaje. Realiza chequeos de colision para que el personaje pueda andar en plataformas o chocar con ellas
     */
    public void mueve(){
        //agrega la "fuerza" del movimiento
        int nX = getX() + vel;
        int nY = getY() - grav;
        
        //crea un actor para plataforma abajo
        Actor platAb = getOneObjectAtOffset(0, medidaPiso + 5, Plataforma.class);
        
        //crea un actor para plataforma arriba
        Actor platArr = getOneObjectAtOffset(0, -medidaPiso - 5, Plataforma.class);
        
        //crea un actor para plataforma derecha
        Actor platDer = getOneObjectAtOffset(medidaLado + 5, 0, Plataforma.class);
        
        //crea un actor para plataforma izquierda
        Actor platIzq = getOneObjectAtOffset( -( medidaLado - 5 ), 0, Plataforma.class);
        
        //detecta colision. Si toca la plataforma, deja de caer. Si no es asi, continua cayendo
        if( platAb != null){
           
            if ( grav < 0 ){
                
                //reinicia la gravedad y la bandera de caida
                grav = 0;
                cae = false;
                
                //obtiene la imagen de la plataforma abajo y mantiene al personaje encima de la misma
                GreenfootImage imgPlat = platAb.getImage();
                
                //correccion para simular tocar el suelo
                int encima = platAb.getY() - imgPlat.getHeight()/2;
                nY = encima - medidaPiso;
            }
        }
        
        //se asegura de no pasar del fondo de la pantalla
        else if( getY() >= mundoAlto - medidaPiso ){
            if( grav < 0 ){
                
                //reinicializa las variables
                grav=0;
                cae=false;
                
                //correccion de posicion
                nY = mundoAlto - medidaPiso;
            }
        }
        else{
            cae = true;
        }
        
        //Comprueba si existen plataformas arriba
        if( platArr != null){
           
            //reinicia la gravedad y la bandera de caida
            if (grav > 0 ){
                grav = 0;
                cae = false;
                
                //obtiene la imagen de la plataforma arriba para mantener al personaje debajo
                GreenfootImage imgPlatArr = platArr.getImage();
                
                //correccion para evitar que el personaje traspase la plataforma
                int abajo = platArr.getY() + imgPlatArr.getHeight()/2;
                
                //se usa "lado" por el modo en que funciona Greenfoot
                nY = abajo + medidaLado;
            }
        }
        
        //Evitamos que pase del borde izquierdo
        if( getX() <= medidaLado ){
            vel = Math.abs(vel);
        }
        
        //Evitamos que pase del borde derecho
        if( getX() >= mundoAncho - medidaLado ){
            vel = Math.abs(vel) * -1;
        }
        
        //Comprueba si hay plataformas a la derecha
        if( platDer != null){
            vel = Math.abs(vel) * -1;
        }
                
        //Comprueba si hay plataformas a la izquierda
        if( platIzq != null){
            vel = Math.abs(vel);
        }
        
        //llama al movimiento con scroll con la fuerza aplicada
        mueveScroll(nX, nY);
    }
    
    /**
     * Metodo que crea la aceleracion de caida. Es llamado cuando el personaje esta en el aire.
     */
    public void gravedad(){
        
        //crea la aceleracion
        grav--;
    }
    
    /**
     * Metodo que mueve al personaje en el mundo, el mundo hace scroll cuando el personaje se mueve
     */
    public void mueveScroll(int nuevaX, int nuevaY){
        
        //permite usar los metodos del mundo
        ScrollWorld miMundo = (ScrollWorld)getWorld();
       
        //comprueba las posiciones en y y en x para realizar el scroll
        
        //si se encuentra a cierta distancia en el eje y, hace scroll. De lo contrario, la pantalla se queda estatica
        if( ( nuevaY < 200 && miMundo.pArr > 0 ) || 
            ( nuevaY > mundoAlto - 200 && miMundo.pAb < miMundo.MUNDOALTO) ){
            
        //definen que tanto se mueve la pantalla
        int scrollY = nuevaY - getY();
        //int scrollX = nuevaX - getX();
        
        //hace scroll 
        miMundo.scrolling( 0, -scrollY );
       }
       else{
           setLocation( getX(), nuevaY );
        }

        //si se encuentra a cierta distancia en el eje x, hace scroll. De lo contrario, la pantalla se queda estatica
       if( ( nuevaX < 200 && miMundo.pIzq > 0 ) || 
           ( nuevaX > mundoAncho - 300 && miMundo.pDer < miMundo.MUNDOANCHO ) ){
            
        //definen que tanto se mueve la pantalla
        //int scrollY = nuevaY - getY();
        int scrollX = nuevaX - getX();
        
        //hace scroll 
        miMundo.scrolling( scrollX, 0 );
       }
       else{
           setLocation( nuevaX, getY() );
        }
        
       //hace scroll cuando se mueve
       //miMundo.scrolling(scrollX, -scrollY);
        
    }

}
