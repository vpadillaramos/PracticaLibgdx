package character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import manager.R;

public class BossFinal extends Character {

    // Constantes
    public final int VELOCIDAD_X = 5;
    public final int VIDAS = 50;

    // Atributos
    public TextureRegion arma;
    public enum Estado{
        IDLE, ATACANDO;
    }
    public Estado estado;


    private Animation<TextureAtlas.AtlasRegion> animacionAtaque;
    private float tiempo;

    public BossFinal(TextureRegion imagen) {
        super(imagen);
        velocidad = new Vector2(VELOCIDAD_X, 0);
        posicion = new Vector2(Gdx.graphics.getWidth() - 250, Gdx.graphics.getHeight() - 150);
        animacionAtaque = new Animation(0.1f, R.getAnimacion("boss"), Animation.PlayMode.LOOP);
        arma = new TextureRegion(R.getRegion("armaBoss"));
        estado = Estado.ATACANDO;
        vidas = VIDAS;
    }

    public void trackPlayer(int sentido){
        switch (sentido) {
            case 1:
                // hacia la derecha
                velocidad.set(VELOCIDAD_X, 0);
                moveRight();
                break;
            case 2:
                //hacia la izquierda
                velocidad.set(VELOCIDAD_X, 0);
                moveLeft();
                break;
        }
    }

    public void actualizaSprite(float delta){
        tiempo += delta;

        switch (estado){
            case IDLE:
                imagen = R.getRegion("idleBoss");
                estado = Estado.IDLE;
                break;
            case ATACANDO:
                imagen = animacionAtaque.getKeyFrame(tiempo, false);
                estado = Estado.ATACANDO;
                break;
        }
    }
}
