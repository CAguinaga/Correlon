import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Clase que crea la entidad del jugador en el mundo
 * 
 * @author Carlos Antonio Aguiñaga Camacho
 * @version 120520160035
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
    int aumento=0;

    //variables de mundo para crear un scroll que se activa solo a cierta distancia
    World miMundillo;
    int mundoAlto;
    int mundoAncho;

    boolean invencible = false;
    int x = 0;

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
        if( invencible == true ){
            tiempoInvencible();
            setImage("brick.png");
        }else{
            setImage("hedgehog.png");
        }

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
            vel = 8 + aumento;
        }

        //gravedad();

        //mueve al personaje en el mundo
        mueve();

        //interaccion con items
        items();

        //interaccion con enemigos
        golpeado();
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
                grav = 0;
                cae = false;

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

    /**
     * Metodo que se ecarga de interactuar con los items cuando el jugador intersecta con estos
     */
    public void items(){

        //incicializa los actores de tipo item que intersectan
        Actor miPoder = getOneIntersectingObject(Item.class);

        //incicializa un item
        Item poder;

        //verifica que exista un actor item intersectando al jugador. Si existe, el item inicializado toma este actor
        if( miPoder != null){

            //se hace cast al actor para usar el metodo getTipo en el item
            poder = (Item) miPoder;

            //guarda el nombre del sonido a reproducir despues de tomar el item
            String cad = "";

            //comprueba que tipo es el item y realiza el effecto deseado
            if(poder.getTipo() == 0){

                //inicializa la variable de vida
                Salud vida = vida();

                //calcula la diferencia de vida
                vida.operacionSal("suma");

                //reproduce un sonido al momento de tomar el item
                cad="item1.wav";
            }
            if(poder.getTipo() == 1){

                //inicializa la variable de puntuacion
                Puntos score1 = puntos();

                //calcula el bonus
                score1.operacionPC("suma", 15);

                //reproduce un sonido al momento de tomar el item
                cad = "item2.wav";

            }
            if(poder.getTipo() == 2){             

                //invencible
                invencible = true;
                tiempoInvencible();
                //tipoInv = 1;

                //reproduce un sonido al momento de tomar el item
                cad="item3.wav";
            }

            //reproduce el sonido al momento de tomar el item
            Greenfoot.playSound(cad);

            //cambia la bandera para indicar que el item ya ha sido tomado
            poder.setTomado();

            //elimina el item del mundo
            removeTouching( Item.class );
        }
    }

    /**
     * Metodo que detecta las colisiones del jugador y baja la salud. Guarda el llamado a Juego Terminado
     */
    public void golpeado(){

        //inicializa un actor de clase Enemigo para verificacion
        Actor enemigo = getOneIntersectingObject(Enemigo.class);

        //hace cast para accesar a los datos del enemigo
        Enemigo miEnemigo = (Enemigo) enemigo;

        //inicializa la variable de salud
        Salud salud = vida();

        if( invencible == false ){
            //verifica si el jugador intersecta a un enemigo
            if( enemigo != null && miEnemigo.toca == 0 ){

                //verifica que tenga al menos 1 punto de salud
                if( salud.getPuntosSalud() > 1 ){

                    //activa los cuadros de invencibilidad
                    invencible = true;
                    tiempoInvencible();

                    //dice al enemigo que ya ha sido tocado
                    miEnemigo.toca = 1;

                    //inicializa la variable de puntuacion
                    Puntos score = puntos();

                    //calcula la diferencia
                    score.operacionPC("resta",12);

                    //calcula la diferencia de vida
                    salud.operacionSal("resta");

                    //reproduce el sonido al momento de ser golpeado
                    Greenfoot.playSound("golpe.wav");

                }//if
                else{

                    //calcula la diferencia de vida
                    salud.operacionSal("resta");
                    
                    //llama al mundo para referencia
                    World miMundo = getWorld();

                    //crea un objeto GameOver
                    GameOver termina = new GameOver();

                    //imprime la pantalla de GameOver en el mundo
                    miMundo.addObject( termina, miMundo.getWidth()/2, miMundo.getHeight()/2 );

                    //reproduce el sonido al momento de morir
                    Greenfoot.playSound("muere.wav");

                    //elimina al jugador
                    miMundo.removeObject(this);

                    //inicializa una variable que tomara el valor de la musica
                    GreenfootSound bg = ( (ScrollWorld) miMundo).sonido;

                    //detiene la musica
                    bg.stop();

                }//else
            }

        }
    }

    /**
     * Metodo que obtiene los puntos para poder usarlos
     * 
     * @return Puntos cont
     */
    public Puntos puntos()
    {
        //inicializa nuestro mundo
        ScrollWorld mundo = (ScrollWorld) getWorld();

        //inicializa la variable llamando a nuestro metodo getPuntos en el mundo
        Puntos cont = mundo.getPuntos();

        //regresa los puntos
        return cont;
    }

    /**
     * Metodo que obtiene los puntos de salud para poder usarlos
     * 
     * @return Salud cont
     */
    public Salud vida()
    {
        //inicializa nuestro mundo
        ScrollWorld mundo = (ScrollWorld) getWorld();

        //inicializa la variable llamando a nuestro metodo getSalud en el mundo
        Salud cont = mundo.getSalud();

        //regresa los puntos
        return cont;
    }

    public void tiempoInvencible(){
        while( x <= 3000 ){
            x++;
        }

        invencible = false;
        x=0;

    }

}
