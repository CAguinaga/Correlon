CORRELON
PROYECTO DE PROGRAMACION ORIENTADA A OBJETOS
VERSION 120320161700

==================================================

ACTUALIZACIONES:

03/12/16: Se creo la clase ScrollWorld con scroll funcional. El scroll se ha guardado en una funcion para probarlo manualmente. Se continuara con las clases de personaje y objetos en ese orden a fin de crear la base para otras entidades.

==================================================

DESCRIPCION:

El jugador es un corredor especial que no puede detenerse. Por esto, se le han puesto obstaculos para probar si es cierto que no se podra detener...

El jugador solo se mueve hacia adelante, sin embargo puede saltar y bajar de algunas plataformas. El jugador cuenta con tres puntos de golpe, si el jugador se queda sin puntos de golpe el juego termina. El juego registra la distancia maxima alcanzada y el tiempo que tomo en llegar.

Existen tres clases de obstaculos: manchas de goma que bajan la velocidad del jugador, muros puestos en el camino y posibles transeuntes o vehiculos que se cruzen en el camino. Solo los dos ultimos tipos de enemigo pueden quitar puntos de golpe.

El jugador a su vez, puede recoger tres objetos que le proporcionan beneficios: un objeto que le otorga invencibilidad haciendo un poco mas fAciles los segmentos (el jugador aun puede morir si llega a atorarse con una pared), un objeto que le da un punto de golpe extra (este objeto es escaso), y un objeto que aumenta la puntuaciOn del jugador con una bonificaciOn igual al 15% de la puntuaciOn total.

El jugador avanza automaticamente a traves de los escenarios, pero a medida que avance la velocidad se hara poco a poco mas rapida hasta cierto limite. Existen bonificaciones por alcanzar y mantener la velocidad tope hasta el final del segmento. 

El primer nivel es un nivel en un prado, sencillo con pocos obstAculos que atravesar. Cuando avance 10km cambia el escenario

El segundo nivel es una playa y poco a poco introduce mas obstAculos y retos para el jugador, como las manchas de aceite. El escenario cambia despuEs de 25 km

El tercer nivel introduce a los transeuntes, ya que es un nivel de ciudad. Es un escenario truculento. Termina despuEs de 50 km. 
