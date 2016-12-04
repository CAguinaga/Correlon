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
 * @version: 031220161957
 */
public class ScrollWorld extends World
{
    //////////////////////////////////////////////////////////////
    //crea un objeto de mapa para obtener su medida
    MundoMapa mapa= new MundoMapa();
    
    //obtiene la imagen del mapa
    GreenfootImage imgMapa = mapa.getImage();
    
    //guarda las medidas de la imagen
    final int MAPAANCHO = imgMapa.getWidth();
    final int MAPAALTO = imgMapa.getHeight();
    
    //crea un objeto plataforma para obtener su medida
    Plataforma base = new Plataforma(0,0);
    
    //obtiene la imagen del bloque
    GreenfootImage imgBase = base.getImage();
    
    final int BASEANCHO = imgBase.getWidth();
    final int BASEALTO = imgBase.getHeight();
    
    //definir tamaño de mapa en funcion de las plataformas
    final int MUNDOANCHO = MAPAANCHO * BASEANCHO;
    final int MUNDOALTO = MAPAALTO * BASEALTO;
    
    //guarda todas las plataformas aun si estan fuera de pantalla
    private List<Plataforma> listaP = new ArrayList<Plataforma>();
    
    //define la posicion de la pantalla respecto al mapa (izquierda, derecha, arriba, abajo)
    int pIzq = 0;
    int pDer = getWidth(); //obtiene el ancho de pantalla
    int pArr = MAPAALTO - getHeight();//0; //MAPAALTO - getHeight();
    int pAb = MAPAALTO; //getHeight(); //MAPAALTO;
    
    //Inicializa al jugador
    Personaje jugador= new Personaje();
    //////////////////////////////////////////////////////////////////
    /**
     * Constructor para objectos de la clase ScrollWorld.
     * 
     */
    public ScrollWorld()
    {    
        //Crea un escenario de alto y ancho, con numero de celdas
        //el false permite a los actores existir fuera de la pantalla
        super(600, 400, 1, false);
        
        /*
        //genera la imagen del escenario a medida
        GreenfootImage img = new GreenfootImage(600,400);
        setBackground(img);
        */
       
       creaMapa();
       dibujaObjetos();
       
       //coloca al jugador
       addObject( jugador, 50, 190 );
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
                    listaP.add( new Plataforma(platX, platY) );
                }
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
    }
}
