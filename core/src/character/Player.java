package character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import manager.R;
import pantallas.Level;

public class Player extends Character implements Disposable, InputProcessor {

    // Constantes
    public final int VELOCIDAD_X = 5;
    public final int VELOCIDAD_Y = 5; // 10
    public final int BOOST_VELOCIDAD_X = 5;
    public final int BOOST_VELOCIDAD_X_SALTO = 10;
    public final int PIXELES_SALTO = 90;
    public final int LIMITE_VUELO = 30;

    // Atributos
    public Level level;
    public enum Estado{
        IDLE, LEFT, RIGHT, JUMPING_RIGHT, JUMPING_LEFT, FALLING_RIGHT, FALLING_LEFT;
    }
    public Estado estado;
    private Animation<TextureAtlas.AtlasRegion> animacionDch;
    private Animation<TextureAtlas.AtlasRegion> animacionIzq;
    private float tiempo;

    // Control corriendo
    private boolean isRunning;
    private boolean isWalking;

    // Control del salto
    public boolean isJumping;
    public boolean isFalling;
    public boolean isStanded;
    private boolean spacePressed;
    private int contadorVolando;
    public float posicionSuelo;

    // Constructor
    public Player(TextureRegion imagen, Level level) {
        super(imagen);
        this.level = level.clonar();
        posicion.set(this.level.posicionInicialPlayer);
        hb.setPosition(posicion);

        velocidad = new Vector2(VELOCIDAD_X, 0);

        // Caraga de animaciones
        animacionDch = new Animation(0.1f, R.getAnimacion("right"), Animation.PlayMode.LOOP);
        animacionIzq = new Animation(0.1f, R.getAnimacion("left"), Animation.PlayMode.LOOP);
        estado = Estado.IDLE;

        // control corriendo
        isRunning = false;
        isWalking = true;

        // control salto
        isJumping = false;
        isFalling = true;
        isStanded = false;
        spacePressed = false;
        contadorVolando = 0;
        Gdx.input.setInputProcessor(this);
    }

    // Metodos
    public void actualizarSprite(float delta){
        tiempo += delta;

        switch (estado){
            case RIGHT:
                if(isStanded)
                    imagen = animacionDch.getKeyFrame(tiempo, false);
                else if(isJumping)
                    imagen = R.getRegion("jumpingRight");
                else if(isFalling)
                    imagen = R.getRegion("fallingRight");

                break;
            case LEFT:
                if(isStanded)
                    imagen = animacionIzq.getKeyFrame(tiempo, false);
                else if(isJumping)
                    imagen = R.getRegion("jumpingLeft");
                else if(isFalling)
                    imagen = R.getRegion("fallingLeft");

                break;
            case IDLE:
                imagen = R.getRegion("idle");
                break;
        }
    }

    public void moverPlayer(){

        actualizarHb();
        movimientoHorizontal();
        controlVelocidad();
        controlSalto();


        // CONTROL SUELOS
        for(int i = 0; i < level.suelos.length; i++){

            // Si el jugador colisiona con el suelo
            //if(hb.overlaps(terreno[i]) && posicion.y <= terreno[i].height){

            /*if((level.suelos[i].x - posicion.y) >= 10)
                System.out.println("Diferencia: " + (level.suelos[i].x - posicion.y));
            if(!hb.overlaps(level.terreno[(int)level.suelos[i].y]))
                System.out.println("No overlaps");*/

            if((level.suelos[i].x - posicion.y) < 10 && hb.overlaps(level.terreno[(int)level.suelos[i].y])){
                //System.out.printf("He pisado el suelo %d. (%.0f)\n", i, level.suelos[i].x);
                //System.out.printf("PlayerHB (%.0f,%.0f)\n", hb.x, hb.y);
                posicionSuelo = level.suelos[i].x;
                posicion.y = posicionSuelo;
                isStanded = true;
                isJumping = false;
                isFalling = false;

            }
            // Si e jugador no detecta suelo debajo cae hasta el proximo suelo
            else {
                posicionSuelo = 0;
                isFalling = true;
                isJumping = false;
                isStanded = false;
            }
        }



        if(isFalling){
            posicion.y -= VELOCIDAD_Y;
        }

        // CONTROL PAREDES DERECHA
        for(int i = 0; i < level.paredesDch.length; i++){

            if(posicion.x < level.paredesDch[i].x && hb.overlaps(level.terreno[(int)level.paredesDch[i].y])){
                posicion.x = level.paredesDch[i].x;
            }
        }

        // CONTROL PAREDES IZQUIERDA
        // posicion.x > level.paredesIzq[i].x
        for(int i = 0; i < level.paredesIzq.length; i++){
            if((level.paredesIzq[i].x - posicion.x) < 5 && hb.overlaps(level.terreno[(int)level.paredesIzq[i].y])){
                posicion.x = level.paredesIzq[i].x;
            }
        }

        /*if(overlapsWall(terreno[0]) == 2){
            posicion.x = terreno[0].width;
        }*/

        /*// CONTROL DE VELOCIDAD
        controlVelocidad();

        // MOVIMIENTO VERTICAL
        // comienza el salto

        // si se deja de pulsar el espacio, comienza a caer independientemente de si llego al limite o no
        if(isJumping && !spacePressed){
            isFalling = true;
            isStanded = false;
            isJumping = false;
        }

        // Compruebo si el jugador esta o no pisando suelo
        for(int i = 0; i < terreno.length; i++){
            // Si pisa suelo pongo isStanded a true
            if(posicion.y < terreno[i].y){
                posicion.y = terreno[i].height;
                posicionSuelo = terreno[i].height; // actualizo el suelo actual
                isStanded = true;
                isFalling = false;
                isJumping = false;
            }
            // Si no pisa debe caer
            else{
                isFalling = true;
                isStanded = false;
                isJumping = false;
            }
        }

        // isKeyJustPressed para que cuando caiga tenga que volver a pulsar espacio
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && spacePressed && isStanded){
            isJumping = true;
            isStanded = false;
            isFalling = false;
        }
        // mientras salta
        else if(spacePressed && isJumping && posicion.y < LIMITE_SALTO){
            posicion.y += VELOCIDAD_Y;

            // si ha llegado al limite comienza a caer
            if(posicion.y >= LIMITE_SALTO) {
                posicion.y = LIMITE_SALTO;
            }
        }
        // el jugador cae hasta el limite
        else if(isFalling && posicion.y > posicionSuelo){
            contadorVolando = 0;
            posicion.y -= VELOCIDAD_Y;

            // si ha llegado al suelo
            if(posicion.y < posicionSuelo){
                posicion.y = posicionSuelo;
                isStanded = true;
                isFalling = false;
                isJumping = false;
            }
        }

        if(isJumping && spacePressed && contadorVolando < LIMITE_VUELO){

            if(contadorVolando < LIMITE_VUELO/3)
                posicion.y += 3;
            else if(contadorVolando < 2*(LIMITE_VUELO/3))
                posicion.y += 2;
            else
                posicion.y += 1;

            contadorVolando++;
        }
        else if(contadorVolando == LIMITE_VUELO){
            isFalling = true;
            isJumping = false;
            isStanded = false;
            contadorVolando = 0;

        }

        // MOVIMIENTO HORIZONTAL
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            moverDch();
            estado = Estado.RIGHT;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            moverIzq();
            estado = Estado.LEFT;
        }
        else {
            estado = Estado.IDLE;
        }*/
    }

    private void actualizarHb(){
        hb.setPosition(posicion);
    }

    private void movimientoHorizontal(){
        // MOVIMIENTO HORIZONTAL
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            moverDch();
            estado = Estado.RIGHT;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            moverIzq();
            estado = Estado.LEFT;
        }
        else {
            estado = Estado.IDLE;
        }
    }

    private void controlSalto(){
        //System.out.println(spacePressed + " " + isStanded);
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && spacePressed && isStanded){
            isJumping = true;
            isStanded = false;
            isFalling = false;
            System.out.println("Saltando");
        }

        if(isJumping){
            if(posicion.y < (posicionSuelo + PIXELES_SALTO)){
                posicion.y += VELOCIDAD_Y;
            }
        }
    }

    /**
     * Comprueba las colisiones con cada pared de un rectangulo dado
     * @param r
     * @return (int) Devuelve un int dependiendo de la pared con la que haya colisionado. 0(no ha colisionado),
     * 1(colision con la pared superior), 2(colision con la pared derecha), 3(colision con la pared inferior),
     * 4(colision con la pared izquierda)
     */
    public int overlapsWall(Rectangle r){
        // Colisiones con las paredes del Rectangle dado
        if(hb.overlaps(r)){
            // Colision con la pared de arriba
            if(posicion.y < r.height)
                return 1;
            // Colision con la pared derecha
            if(posicion.x < r.width)
                return 2;
            // Colision con la pared de abajo
            if(posicion.y > r.y)
                return 3;
            // Colision con la pared de la izquierda
            if(posicion.x > r.x)
                return 4;
        }

        return 0;
    }

    private void controlVelocidad(){

        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
            velocidad.x = VELOCIDAD_X + BOOST_VELOCIDAD_X;
        }
        else
            velocidad.x = VELOCIDAD_X;

        /*if(isWalking && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            isRunning = true;
            isWalking = false;
            if(isJumping || isFalling)
                velocidad.x = VELOCIDAD_X + BOOST_VELOCIDAD_X + BOOST_VELOCIDAD_X_SALTO;
            else
                velocidad.x = VELOCIDAD_X + BOOST_VELOCIDAD_X;
        }
        else if(isRunning && !Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
            isWalking = true;
            isRunning = false;

            if(isJumping || isFalling)
                velocidad.x = VELOCIDAD_X + BOOST_VELOCIDAD_X_SALTO;
            else
                velocidad.x = VELOCIDAD_X;
        }*/
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.SPACE)
            spacePressed = true;

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.SPACE)
            spacePressed = false;

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
