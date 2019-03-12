package pantallas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Level1 extends Level implements Disposable {

    // Atributos

    // Constructor
    public Level1(int numNivel){
        super(numNivel);
        fondo = new Sprite(new Texture("levels/level1.png"));
        posicionInicialPlayer = new Vector2(590, 200); // 190, 200

        // Limites de la pantalla
        startXY = new Vector2(fondo.getX(), fondo.getY());
        endXY = new Vector2(fondo.getWidth(), fondo.getHeight());

        // Terreno
        terreno = new Rectangle[6];
        terreno[0] = new Rectangle(startXY.x, startXY.y, 80, 675);
        terreno[1] = new Rectangle(startXY.x, startXY.y, 458, 77);
        terreno[2] = new Rectangle(endXY.x - 990, 0, 960, 120);
        terreno[3] = new Rectangle(endXY.x - 312, 0, 312, 237);
        terreno[4] = new Rectangle(endXY.x - 78, 0, 78, 711);
        terreno[5] = new Rectangle(500, 259, 327, 97);

        // ParedesDch
        paredesDch = new Vector2[]{
                new Vector2(terreno[0].width, 0),
                new Vector2(terreno[5].width, 5)
        };

        // ParedesIzq
        paredesIzq = new Vector2[]{
                new Vector2(terreno[2].x, 2),
                new Vector2(terreno[3].x, 3),
                new Vector2(terreno[4].x, 4),
                new Vector2(terreno[5].x, 5)
        };

        // Suelos
        suelos = new Vector2[]{
                new Vector2(terreno[0].height, 0),
                new Vector2(terreno[1].height, 1),
                new Vector2(terreno[2].height, 2),
                new Vector2(terreno[3].height, 3),
                new Vector2(terreno[4].height, 4),
                new Vector2(terreno[5].height, 5)
        };

        // Techos
        techos = new Vector2[]{
                new Vector2(terreno[5].y, 5)
        };
    }

    @Override
    public void dispose() {

    }

}
