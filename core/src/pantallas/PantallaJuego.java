package pantallas;

import character.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import manager.R;

public class PantallaJuego implements Screen {

    // Constantes


    // Variables
    private SpriteBatch batch;

    // Camara
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    // Player
    private Player player;

    private Level1 level1;

    @Override
    public void show() {
        batch = new SpriteBatch();

        level1 = new Level1(1);

        // Player
        player = new Player(R.getRegion("idle"), level1);


        // Camara
        camera = new OrthographicCamera();
        camera.setToOrtho(false, level1.fondo.getWidth()/2, level1.fondo.getHeight()/2);
        camera.update();

        // usado para que no se deformen los sprites si se resizea la pantalla
        viewport = new ExtendViewport(level1.fondo.getWidth(), level1.fondo.getHeight(), camera);
    }

    @Override
    public void render(float delta) {
        /////////////////////////
        /////////LOGICA//////////
        /////////////////////////
        moverPlayer();
        comprobarColisiones();

        /*camera.position.x = player.posicion.x;
        camera.position.y = player.posicion.y;*/
        camera.position.x = level1.fondo.getX()+level1.fondo.getWidth()/2;
        camera.position.y = level1.fondo.getY()+level1.fondo.getHeight()/2;
        camera.zoom = 1;
        camera.update();
        viewport.update((int)level1.fondo.getWidth(), (int)level1.fondo.getHeight(), true);

        /////////////////////////
        /////////GRAFICO/////////
        /////////////////////////
        Gdx.gl.glClearColor(0.3f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        level1.draw(batch);
        player.actualizarSprite(delta);
        player.pintar(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
    }

    private void moverPlayer(){
        player.moverPlayer();
        // TODO limitesPlayer()
    }

    private void comprobarColisiones(){
        /*if(player.hb.overlaps(level1.hbs[0])){
            player.posicion.x = level1.hbs[0].getWidth();
        }

        if(player.hb.overlaps(level1.hbs[1])){
            player.posicionSuelo = (int)level1.hbs[1].getHeight();
            player.isStanded = true;
            //player.posicion.y = level1.hbs[1].getHeight();
        }
        else{
            player.posicionSuelo = 0;
            player.isFalling = true;
        }*/

        /*if(player.posicion.x < level1.hbs[0].height)
            player.posicion.y = level1.hbs[0].height;*/
    }

}
