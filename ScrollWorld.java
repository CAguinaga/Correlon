import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

//para obtener posiciones de un pixel 
import java.awt.Color;

//usadas para guardar las posiciones de los elementos dentro del mundo
import java.util.List;
import java.util.ArrayList;

/**
 * Clase que genera el mundo del juego y que guarda los metodos necesarios para realizar scrolling.
 * Esta clase inicializa las variables necesarias para el efecto de scroll usando una clase donde
 * existe un mapa ya dibujado (clase MundoMapa) y la clase de los bloques que conforman las plataformas.
 * 
 * @author: Carlos Antonio Aguiñaga Camacho 
 * @version: 120520160035
 */
public class ScrollWorld extends World
{
    //////////////////////////////////////////////////////////////

    //crea un objeto de mapa para obtener su medida
    MundoMapa mapa = new MundoMapa("platformsf.gif");

    //obtiene la imagen del mapa
    GreenfootImage imgMapa = mapa.getImage();

    //guarda las medidas de la imagen
    final int MAPAANCHO = imgMapa.getWidth();
    final int MAPAALTO = imgMapa.getHeight();

    //crea un objeto plataforma para obtener su medida
    Plataforma base = new Plataforma(0,0,0);

    //obtiene la imagen del bloque
    GreenfootImage imgBase = base.getImage();

    final int BASEANCHO = imgBase.getWidth();
    final int BASEALTO = imgBase.getHeight();

    //definir tamaño de mapa en funcion de las plataformas
    final int MUNDOANCHO = MAPAANCHO * BASEANCHO;
    final int MUNDOALTO = MAPAALTO * BASEALTO;

    //guarda todas las plataformas aun si estan fuera de pantalla
    private List<Plataforma> listaP = new ArrayList<Plataforma>();

    //guarda todos los items
    private List<Item> listaI = new ArrayList<Item>();

    //guarda todos los enemigos
    private List<Enemigo> listaE = new ArrayList<Enemigo>();

    //define la posicion de la pantalla respecto al mapa (izquierda, derecha, arriba, abajo)
    int pIzq = 0;
    int pDer = getWidth(); //obtiene el ancho de pantalla
    int pArr = MAPAALTO - getHeight();//0; //MAPAALTO - getHeight();
    int pAb = MAPAALTO; //getHeight(); //MAPAALTO;

    /////////////////////////////////////////////////////////////////////

    //Inicializa al jugador
    Personaje jugador= new Personaje();

    //////////////////////////////////////////////////////////////////

    //inicializa el contador
    Puntos score = new Puntos();

    /**
     * Metodo que regresa el contador para uso de otras cases que lo llamen
     * 
     * @return Puntos score
     */
    public Puntos getPuntos(){
        return score;
    }

    //////////////////////////////////////////////////////////////////

    //inicializa salud
    Salud vida = new Salud();

    /**
     * Metodo que regresa el contador para uso de otras cases que lo llamen
     * 
     * @return Salud vida
     */
    public Salud getSalud(){
        return vida;
    }

    /////////////////////////////////////////////////////////////////

    //cadena que guarda el nombre de musica de fondo
    String musica="bg1.wav";

    //variable que controlara la musica de fondo
    GreenfootSound sonido = new GreenfootSound(musica);
    
    int corre=0;

    ////////////////////////////////////////////////////////////////

    /**
     * Constructor para objectos de la clase ScrollWorld.
     * 
     */
    public ScrollWorld()
    {    
        //Crea un escenario de alto y ancho, con numero de celdas
        //el false permite a los actores existir fuera de la pantalla
        super(600, 400, 1, false);

        //addObject();
        
        /*
        //genera la imagen del escenario a medida
        GreenfootImage img = new GreenfootImage(600,400);
        setBackground(img);
         */

        creaMapa();
        dibujaObjetos();

        //reproduce el sonido en repeticion
        sonido.playLoop();

        //controla el volume del sonido
        sonido.setVolume(65);

        //coloca al jugador
        addObject( jugador, 50, 190 );

        //coloca la puntuacion
        addObject( score, 520, 20 );

        //coloca las vidas
        addObject( vida, 80, 20 );

        //addObject( jugador, getWidth()/2, getHeight()/2 - jugador.getImage().getHeight()/2 );
    }

    /**
     * Método que "lee" la imagen del mapa y lo genera. Guarda los bloques en una lista de arreglos
     * 
     */
    public void creaMapa(){

        //recorre altura
        for(int j=0; j < MAPAALTO; j++){

            //recorre ancho
            for(int i=0; i < MAPAANCHO; i++){

                //Obtiene el color de cada pixel para poder colocar cada plataforma en ese punto
                int colorRGB = imgMapa.getColorAt(i,j).getRGB();

                //Comprueba si el perfil de color corresponde al color deseado y coloca las plataformas dentro de la lista
                if( colorRGB == Color.BLACK.getRGB() ){

                    //Coordenadas en el mapa
                    int platX = i * BASEANCHO + BASEANCHO/2;
                    int platY = j * BASEALTO + BASEALTO/2;

                    //Guarda en la lista la plataforma creada
                    listaP.add( new Plataforma(platX, platY,0) );
                }

                if( colorRGB == Color.MAGENTA.getRGB() ){

                    //Coordenadas en el mapa
                    int platX = i * BASEANCHO + BASEANCHO/2;
                    int platY = j * BASEALTO + BASEALTO/2;

                    //Guarda en la lista la plataforma creada
                    listaP.add( new Plataforma(platX, platY,1) );
                }

                ////////////////////////////////////////////////////////////////////

                /*La creacion de los items en el mapa funciona exactamente igual que las
                 *plataformas, con la diferencia que se les asigna un tipo definido por color
                 *
                 *Los colores de los items son BLUE, GREEN y YELLOW para tipos 0, 1 y 2 respectivamente
                 **/

                //asigna tipo de item en funcion del color                
                if( colorRGB == Color.BLUE.getRGB() ){

                    //Coordenadas en el mapa
                    int itemX = i * BASEANCHO + BASEANCHO/2;
                    int itemY = j * BASEALTO + BASEALTO/2;

                    //Guarda en la lista la plataforma creada
                    listaI.add( new Item(itemX, itemY, 0 ) );

                }

                if( colorRGB == Color.GREEN.getRGB() ){

                    //Coordenadas en el mapa
                    int itemX = i * BASEANCHO + BASEANCHO/2;
                    int itemY = j * BASEALTO + BASEALTO/2;

                    //Guarda en la lista la plataforma creada
                    listaI.add( new Item(itemX, itemY, 1 ) );

                }

                if( colorRGB == Color.YELLOW.getRGB() ){

                    //Coordenadas en el mapa
                    int itemX = i * BASEANCHO + BASEANCHO/2;
                    int itemY = j * BASEALTO + BASEALTO/2;

                    //Guarda en la lista la plataforma creada
                    listaI.add( new Item(itemX, itemY, 2 ) );

                }

                ////////////////////////////////////////////////////////////////////

                /*La creacion de los enemigos en el mapa funciona exactamente igual que las
                 *plataformas, con la diferencia que se les asigna un tipo definido por color
                 *
                 *Los colores de los enemigos son RED y ORANGE para tipos 0 Y 1 respectivamente
                 **/

                //Coloca enemigos en el mapa

                if( colorRGB == Color.RED.getRGB() ){

                    //Coordenadas en el mapa
                    int eneX = i * BASEANCHO + BASEANCHO/2;
                    int eneY = j * BASEALTO + BASEALTO/2;

                    //Guarda en la lista la plataforma creada
                    listaE.add( new Enemigo( eneX, eneY, 0 ) );

                }

                Color naranja = new Color( 255,170,0 );
                
                if( colorRGB == naranja.getRGB() ){

                    //Coordenadas en el mapa
                    int eneX = i * BASEANCHO + BASEANCHO/2;
                    int eneY = j * BASEALTO + BASEALTO/2;

                    //Guarda en la lista la plataforma creada
                    listaE.add( new Enemigo( eneX, eneY, 1 ) );
                }

                ////////////////////////////////////////////////////////////////////
            } 

        }
    }

    /**
     * Metodo que genera los bloques del juego, usando las listas generadas
     * por el metodo creaMapa
     * 
     */
    public void dibujaObjetos(){

        //inicializamos una plataforma
        Plataforma miPlataforma;

        //usan la informacion de la lista
        int miPlataformaX, miPlataformaY, pantallaX, pantallaY;

        //recorre la lista para generar las plataformas
        for(int i=0; i<listaP.size(); i++){

            //guarda las plataformas y las asigna para su posterior colocacion
            miPlataforma = listaP.get(i);

            //obtiene las coordenadas guardadas de la lista
            miPlataformaX = miPlataforma.coordX;
            miPlataformaY = miPlataforma.coordY;

            //comprobamos que las coordenadas esten en pantalla comparando las coordenadas de la plataforma con las coordenadas de la pantalla
            if( miPlataformaX + BASEANCHO >= pIzq && 
            miPlataformaX - BASEANCHO <= pDer && 
            miPlataformaY + BASEALTO >= pArr && 
            miPlataformaY - BASEALTO <= pAb )
            {
                //"mueve" las plataformas
                pantallaX = miPlataformaX - pIzq;
                pantallaY = miPlataformaY - pArr;

                //comprueba si la plataforma existe en el mundo. Si no existe, la genera. Si ya existe, la "mueve". Si sale de la pantalla, la elimina
                if( miPlataforma.getWorld() == null ){

                    //crea el objeto si no existe en el mundo
                    addObject( miPlataforma, pantallaX, pantallaY );
                }
                else{

                    //"mueve" el objeto si ya existe en el mundo
                    miPlataforma.setLocation( pantallaX, pantallaY );
                }
            }else{

                //elimina el objeto si sale de los limites de la pantalla
                if( miPlataforma.getWorld() != null ){
                    removeObject( miPlataforma );
                }
            }
        }

        /////////////////////////////////////////////////////////////////////////

        /*La creacion de los items en la lista funciona exactamente igual que las plataformas
         *
         **/
        //inicializamos un item
        Item miItem;

        //usan la informacion de la lista
        int miItemX, miItemY;

        //recorre la lista para generar las plataformas
        for(int i=0; i<listaI.size(); i++){

            //guarda las plataformas y las asigna para su posterior colocacion
            miItem = listaI.get(i);

            //obtiene las coordenadas guardadas de la lista
            miItemX = miItem.coordX;
            miItemY = miItem.coordY;

            //comprobamos que las coordenadas esten en pantalla comparando las coordenadas de la plataforma con las coordenadas de la pantalla
            if( miItemX >= pIzq && 
            miItemX <= pDer && 
            miItemY >= pArr && 
            miItemY <= pAb )
            {
                //"mueve" los items
                pantallaX = miItemX - pIzq;
                pantallaY = miItemY - pArr;

                //comprueba si el item no ha sido tomado antes 
                if( miItem.toma == 0){
                    //comprueba si el item existe en el mundo. Si no existe, lo genera. Si ya existe, lo "mueve". Si sale de la pantalla, lo elimina
                    if( miItem.getWorld() == null){

                        //crea el objeto si no existe en el mundo
                        addObject( miItem, pantallaX, pantallaY );
                    }
                    else{

                        //"mueve" el objeto si ya existe en el mundo
                        miItem.setLocation( pantallaX, pantallaY );
                    }
                }
            }else{

                //elimina el objeto si sale de los limites de la pantalla
                if( miItem.getWorld() != null ){
                    removeObject( miItem );
                }
            }
        }

        /////////////////////////////////////////////////////////////////////////

        /*La creacion de los enemigos en la lista funciona exactamente igual que las plataformas
         *
         **/
        //inicializamos un item
        Enemigo miEnemigo;

        //usan la informacion de la lista
        int miEnemigoX, miEnemigoY;

        //recorre la lista para generar las plataformas
        for(int i=0; i<listaE.size(); i++){

            //guarda las plataformas y las asigna para su posterior colocacion
            miEnemigo = listaE.get(i);

            //obtiene las coordenadas guardadas de la lista
            miEnemigoX = miEnemigo.coordX;
            miEnemigoY = miEnemigo.coordY;

            //comprobamos que las coordenadas esten en pantalla comparando las coordenadas de la plataforma con las coordenadas de la pantalla
            if( miEnemigoX >= pIzq && 
            miEnemigoX <= pDer && 
            miEnemigoY >= pArr && 
            miEnemigoY <= pAb )
            {
                //"mueve" los items
                pantallaX = miEnemigoX - pIzq;
                pantallaY = miEnemigoY - pArr;

                //comprueba si el item existe en el mundo. Si no existe, lo genera. Si ya existe, lo "mueve". Si sale de la pantalla, lo elimina
                if( miEnemigo.getWorld() == null){

                    //crea el objeto si no existe en el mundo
                    addObject( miEnemigo, pantallaX, pantallaY );
                }
                else{

                    //"mueve" el objeto si ya existe en el mundo
                    miEnemigo.setLocation( pantallaX, pantallaY );
                }
            }else{

                //elimina el objeto si sale de los limites de la pantalla
                if( miEnemigo.getWorld() != null ){
                    removeObject( miEnemigo );
                }
            }
        }

        /////////////////////////////////////////////////////////////////////////

    }

    /**
     * Metodo que verifica la posicion general de los objetos en pantalla y mantiene
     * el foco en la misma
     */
    public void scrolling(int cambioX, int cambioY){

        //"mueve" las plataformas y las mantiene en pantalla
        pIzq += cambioX;
        pDer += cambioX;

        //comprueba si se sale de los limites de la pantalla. Si es asi, se reinicializan los parametros de bordes
        if( pIzq < 0 ){

            //reinicializa si se va mucho a la izquierd
            pIzq = 0;
            pDer = getWidth();
        }
        else if( pDer >= MUNDOANCHO ){

            //reinicializa si se va mucho a la derecha
            pDer = MUNDOANCHO;
            pIzq = MUNDOANCHO - getWidth();
        }

        pArr -= cambioY;
        pAb -= cambioY;

        if( pArr < 0 ){

            //reinicializa si se va mucho hacia arriba
            pArr = 0;
            pAb = getHeight();
        }
        else if( pAb >= MUNDOALTO ){

            //reinicializa si se va mucho hacia abajo
            pAb = MUNDOALTO;
            pArr = MUNDOALTO - getHeight();
        }

        //actualiza el mapa y hace "scroll"
        dibujaObjetos();

        //cambia el mundo a determinada distancia
        cargaMundo( pDer );
        termina (pDer);
    }

    /**
     * Metodo que realiza el cambio de ambiente usando la posicion del jugador
     */
    public void cargaMundo( int pos ){

        //cadena que tomara el valor inicial de la musica
        String cad = musica;

        if( pos >= (MUNDOANCHO/3) - 200){

            //cambia el fondo
            setBackground("bg2.jpg");

            //cambia la cadena
            cad="bg2.wav";

            jugador.aumento=2;
        }
        if( pos >= ((MUNDOANCHO/3)*2) - 200){
            //cambia el fondo
            setBackground("bg3.png");

            //cambia la cadena
            cad="bg3.wav";

            jugador.aumento=4;
        }

        //crea el cambio de musica comparando el nombre viejo con el nuevo
        if( cad != musica ){

            //detiene la musica
            sonido.stop();

            //crea la nueva musica
            sonido = new GreenfootSound(cad);

            //reproduce la musica
            sonido.playLoop();
            sonido.setVolume(65);

            //asigna el nombre de la nueva usica a la musica vieja
            musica=cad;
        }
    }

    public void termina( int pos ){
        if ( pos == MUNDOANCHO ){

            //crea un objeto GameOver
            GameOver termina = new GameOver();

            //imprime la pantalla de GameOver en el mundo
            addObject( termina, getWidth()/2, getHeight()/2 );

            //reproduce el sonido al momento de morir
            //Greenfoot.playSound("muere.wav");

            //detiene la musica
            sonido.stop();
        }
    }
}