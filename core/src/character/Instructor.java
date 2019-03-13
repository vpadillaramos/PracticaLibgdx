package character;

import android.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Instructor extends Character {

    public String[] dialogos = {
            "Muévete con WASD",
            "Dispara con las flechas del teclado!",
            "Buena suerte!",
            "Yo soy un fantasma bueno!!",
            "Cuidado! No me ves?"
    };

    BitmapFont frase;
    int numeroFrase;
    public boolean isOverlaped;

    public Instructor(TextureRegion imagen) {
        super(imagen);
        posicion = new Vector2(Gdx.graphics.getWidth()-170, Gdx.graphics.getHeight()-170);
        hb.setPosition(posicion);
        frase = new BitmapFont();
        isOverlaped = false;
        numeroFrase = intRandom(0, dialogos.length-1);
    }

    public void decirFrase(SpriteBatch batch){
        frase.setColor(1, 0, 0, 1);
        frase.draw(batch, dialogos[numeroFrase], posicion.x - 50, tamano.y + 30);

    }

    private int intRandom(float minimo, float maximo) {
        Random num = new Random();
        int min = Math.round(minimo);
        int max = Math.round(maximo);
        return (min + num.nextInt(max-min+1));
    }
}
