package pantallas;

import android.graphics.Rect;
import character.BossFinal;
import character.Ghost;
import character.Instructor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import manager.R;

import java.util.Random;

public class Sala {

    // Constantes
    public final int MIN_GHOSTS = 2;
    public final int MAX_GHOSTS = 7;

    // Atributos
    public int numSala;
    public Sprite fondo;
    public static int LIMITE_PARED_IZQUIERDA;
    public static int LIMITE_PARED_DERECHA;
    public static int LIMITE_PARED_SUPERIOR;
    public static int LIMITE_PARED_INFERIOR;
    public Array<Ghost> ghosts;
    public Instructor instructor;
    public Rectangle salida;
    public BossFinal bossFinal;

    // Constructor
    public Sala(int numSala){
        this.numSala = numSala;

        fondo = new Sprite(new Texture("levels/sala" + numSala + ".png"));
        fondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        LIMITE_PARED_IZQUIERDA = 115;
        LIMITE_PARED_DERECHA = (int) (fondo.getWidth()-200);
        LIMITE_PARED_SUPERIOR = (int) (fondo.getHeight()-145);
        LIMITE_PARED_INFERIOR = 115;

        ghosts = new Array<Ghost>();
        for(int i = 1; i <= MIN_GHOSTS + numSala; i++){
            Ghost ghost = new Ghost(R.getRegion("idleGhost"));
            ghost.posicion.set(intRandom(160, Gdx.graphics.getWidth()), intRandom(160, Gdx.graphics.getHeight()));
            ghosts.add(ghost);
        }

        salida = new Rectangle(Gdx.graphics.getWidth()-163, 340, 100, 60);
        instructor = new Instructor(R.getRegion("instructor"));
        bossFinal = new BossFinal(R.getRegion("idleBoss"));
    }

    public void pintar(SpriteBatch batch){
        fondo.draw(batch);
    }

    private int intRandom(float minimo, float maximo) {
        Random num = new Random();
        int min = Math.round(minimo);
        int max = Math.round(maximo);
        return (min + num.nextInt(max-min+1));
    }

    public void dispose(){
        instructor.dispose();
        for(Ghost g : ghosts){
            g.dispose();
        }
        bossFinal.dispose();
    }
}
