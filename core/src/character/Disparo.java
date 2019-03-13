package character;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Disparo extends Character {

    // Constantes
    public final int VELOCIDAD_X = 9;
    public final int VELOCIDAD_Y = 9;
    public final int RANGO = 580;

    // Atributos
    public enum Sentido{
        RIGHT, LEFT, UP, DOWN;
    }

    public Vector2 posicionInicial;

    public Sentido sentido;

    public Disparo(TextureRegion imagen) {
        super(imagen);
        velocidad = new Vector2(0, 0);
        posicionInicial = new Vector2(0, 0);
    }

    public Disparo(TextureRegion imagen, float posX, float posY){
        super(imagen);
        velocidad = new Vector2(0, 0);
        posicion = new Vector2(posX, posY);
    }

    public void setPosicionInicial(float x, float y) {
        this.posicionInicial = new Vector2(x, y);
        posicion.set(posicionInicial);
    }

    public void setSentido(Sentido sentido){
        this.sentido = sentido;
    }

    public void mover(){

        switch (sentido){
            case RIGHT:
                velocidad.set(VELOCIDAD_X, 0);
                moveRight();
                break;
            case LEFT:
                velocidad.set(VELOCIDAD_X, 0);
                moveLeft();
                break;
            case UP:
                velocidad.set(0, VELOCIDAD_Y);
                moveUp();
                break;
            case DOWN:
                velocidad.set(0, VELOCIDAD_Y);
                moveDown();
                break;
        }
    }

}
