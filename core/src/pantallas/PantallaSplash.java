package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.vpr.practica.Practica;
import manager.R;

public class PantallaSplash implements Screen {

    // Atributos
    private Texture splashTexture;
    private Image splashImage;
    private Stage stage;

    private boolean splashDone = false;

    // Constructor
    public PantallaSplash(){
        splashTexture = new Texture(Gdx.files.internal("splash/splash.png"));
        splashImage = new Image(splashTexture);
    }

    @Override
    public void show() {
        stage = new Stage();

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        // Mostrar la imagen con animacion
        splashImage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f),
                Actions.delay(1.5f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        splashDone = true;
                    }
                })));

        table.row().height(splashTexture.getHeight());
        table.add(splashImage).center();
        stage.addActor(table);
        R.cargarRecursos();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_CLEAR_VALUE);

        stage.act(delta);
        stage.draw();

        // Comprueba si han cargado los datos
        if(R.update()){
            // Si la animacion ha terminado se muestra el menu principal
            if(splashDone)
                ((Practica) Gdx.app.getApplicationListener()).setScreen(new PantallaMenuPrincipal());
        }
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
        splashTexture.dispose();
        stage.dispose();
    }
}
