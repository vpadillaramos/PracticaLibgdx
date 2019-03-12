package pantallas;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Level {

    // Atributos
    public int numNivel;
    public Sprite fondo;
    public Vector2 posicionInicialPlayer;
    public Vector2 startXY; // inicio de la pantalla
    public Vector2 endXY; // fin de la pantalla
    public Rectangle[] terreno;
    public Vector2[] paredesDch; // en la primera columna se especifica la coordenada, en la segunda a quien pertence en terreno
    public Vector2[] paredesIzq;
    public Vector2[] suelos;
    public Vector2[] techos;

    // Constructor
    public Level(int numNivel){
        this.numNivel = numNivel;

        posicionInicialPlayer = new Vector2();
        startXY = new Vector2();
        endXY = new Vector2();
    }

    public void draw(SpriteBatch batch){
        fondo.draw(batch);
    }

    public Level clonar(){
        Level l = new Level(numNivel);
        l.fondo = fondo;
        l.posicionInicialPlayer.set(posicionInicialPlayer);
        l.terreno = terreno.clone();
        l.startXY.set(startXY);
        l.endXY.set(endXY);
        l.paredesDch = paredesDch.clone();
        l.paredesIzq = paredesIzq.clone();
        l.suelos = suelos.clone();
        l.techos = techos.clone();
        return l;
    }
}
