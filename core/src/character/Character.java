package character;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Character implements Disposable {
    // Atributos
    public Vector2 posicion;
    public Vector2 velocidad;
    public Vector2 tamano;
    public TextureRegion imagen;
    public Rectangle hb;

    // Constructor
    public Character(TextureRegion imagen){
        this.imagen = imagen;
        tamano = new Vector2(imagen.getRegionWidth(), imagen.getRegionHeight());
        posicion = new Vector2(0,0);
        hb = new Rectangle(posicion.x, posicion.y, tamano.x, tamano.y);
    }

    // Metodos
    public void pintar(SpriteBatch batch){
        batch.draw(imagen, posicion.x, posicion.y);
    }

    public void moverIzq(Vector2 movimiento){
        // movimiento negativo
        posicion.sub(movimiento);
        hb.setPosition(posicion);
    }

    public void moverIzq(){
        posicion.sub(velocidad);
        hb.setPosition(posicion);
    }

    public void moverDch(Vector2 movimiento){
        // movimiento positivo
        posicion.add(movimiento);
        hb.setPosition(posicion);
    }

    public void moverDch(){
        posicion.add(velocidad);
        hb.setPosition(posicion);
    }

    @Override
    public void dispose() {

    }
}
