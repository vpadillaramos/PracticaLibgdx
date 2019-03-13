package character;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import manager.R;

import java.util.Random;

public class Ghost extends Character{

    // Constantes
    public final int VELOCIDAD_X = intRandom(1, 4);
    public final int VELOCIDAD_Y = intRandom(1, 4);
    public final int VIDAS = 2;

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

    public Ghost(TextureRegion imagen) {
        super(imagen);

        // Caraga de animaciones
        animacionRight = new Animation(0.1f, R.getAnimacion("ghostRight"), Animation.PlayMode.LOOP);
        animacionLeft = new Animation(0.1f, R.getAnimacion("ghostLeft"), Animation.PlayMode.LOOP);
        animacionUp = new Animation(0.1f, R.getAnimacion("ghostBack"), Animation.PlayMode.LOOP);
        animacionDown = new Animation(0.1f, R.getAnimacion("ghostFront"), Animation.PlayMode.LOOP);
        estado = Estado.IDLE;
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
                imagen = R.getRegion("idleGhost");
                break;
        }
    }

    public void trackPlayer(int sentido){
        switch (sentido){
            case 1:
                // hacia arriba
                velocidad.set(0, VELOCIDAD_Y);
                moveUp();
                estado = Estado.UP;
                break;
            case 2:
                //hacia la derecha
                velocidad.set(VELOCIDAD_X, 0);
                moveRight();
                estado = Estado.RIGHT;
                break;
            case 3:
                // hacia abajo
                velocidad.set(0, VELOCIDAD_Y);
                moveDown();
                estado = Estado.DOWN;
                break;
            case 4:
                // hacia la izquierda
                velocidad.set(VELOCIDAD_X, 0);
                moveLeft();
                estado = Estado.LEFT;

                break;
        }
    }

    private int intRandom(float minimo, float maximo) {
        Random num = new Random();
        int min = Math.round(minimo);
        int max = Math.round(maximo);
        return (min + num.nextInt(max-min+1));
    }
}
