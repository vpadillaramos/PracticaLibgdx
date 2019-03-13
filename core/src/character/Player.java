package character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import manager.R;

public class Player extends Character implements Disposable {

    // Constantes
    public final int VELOCIDAD_X = 5;
    public final int VELOCIDAD_Y = 5;
    public final int VIDAS = 5;

    // Atributos
    public enum Estado{
        IDLE, LEFT, RIGHT, UP, DOWN;
    }
    public Estado estado;
    private Animation<TextureAtlas.AtlasRegion> animacionRight;
    private Animation<TextureAtlas.AtlasRegion> animacionLeft;
    private Animation<TextureAtlas.AtlasRegion> animacionUp;
    private Animation<TextureAtlas.AtlasRegion> animacionDown;
    private float tiempo;
    public int salaActual;


    // Constructor
    public Player(TextureRegion imagen) {
        super(imagen);

        posicion.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        hb.setPosition(posicion);

        velocidad = new Vector2(VELOCIDAD_X, 0);

        // Caraga de animaciones
        animacionRight = new Animation(0.1f, R.getAnimacion("walkRight"), Animation.PlayMode.LOOP);
        animacionLeft = new Animation(0.1f, R.getAnimacion("walkLeft"), Animation.PlayMode.LOOP);
        animacionUp = new Animation(0.1f, R.getAnimacion("walkBack"), Animation.PlayMode.LOOP);
        animacionDown = new Animation(0.1f, R.getAnimacion("walkFront"), Animation.PlayMode.LOOP);
        estado = Estado.IDLE;


        salaActual = 1;
        vidas = VIDAS;
    }

    // Metodos
    public void actualizarSprite(float delta){
        tiempo += delta;

        switch (estado){
            case RIGHT:
                imagen = animacionRight.getKeyFrame(tiempo, false);
                break;
            case LEFT:
                imagen = animacionLeft.getKeyFrame(tiempo, false);
                break;
            case UP:
                imagen = animacionUp.getKeyFrame(tiempo, false);
                break;
            case DOWN:
                imagen = animacionDown.getKeyFrame(tiempo, false);
                break;
            case IDLE:
                imagen = R.getRegion("idle");
                break;
        }
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }

    public void moverPlayer(){
        actualizarHb();
        movimiento();

    }

    private void actualizarHb(){
        hb.setPosition(posicion);
    }

    private void movimiento(){

        controlDiagonales();
        controlRectas();
        if(!controlDiagonales() && !controlRectas())
            estado = Estado.IDLE;
    }

    public boolean controlRectas(){
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            velocidad.set(VELOCIDAD_X, 0);
            moveRight();
            estado = Estado.RIGHT;
            return true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            velocidad.set(VELOCIDAD_X, 0);
            moveLeft();
            estado = Estado.LEFT;
            return true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            velocidad.set(0, VELOCIDAD_Y);
            moveUp();
            estado = Estado.UP;
            return true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            velocidad.set(0, VELOCIDAD_Y);
            moveDown();
            estado = Estado.DOWN;
            return true;
        }

        return false;
    }

    private boolean controlDiagonales(){
        if(Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)){
            velocidad.set(1, 3);
            diagonalUpRight();
            estado = Estado.UP;
            return true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)){
            velocidad.set(1, 3);
            diagonalUpLeft();
            estado = Estado.UP;
            return true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)){
            velocidad.set(1, 3);
            diagonalDownRight();
            estado = Estado.DOWN;
            return true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)){
            velocidad.set(1, 3);
            diagonalDownLeft();
            estado = Estado.DOWN;
            return true;
        }

        return false;
    }

    public Disparo disparar(){
        Disparo disparo = new Disparo(R.getRegion("tear"));
        disparo.setPosicionInicial(posicion.x + (tamano.x/2) - (disparo.tamano.x/2), posicion.y + (tamano.y/3));
        return disparo;
    }
}
