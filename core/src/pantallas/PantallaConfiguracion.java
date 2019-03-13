package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.vpr.practica.Practica;
import manager.Configuracion;

public class PantallaConfiguracion implements Screen {

    Stage stage;
    VisTextButton btSonido;
    VisTextButton btMusica;

    @Override
    public void show() {

        if(!VisUI.isLoaded())
            VisUI.load();

        stage = new Stage();

        VisTable menu = new VisTable();
        menu.setFillParent(true);
        stage.addActor(menu);


        btMusica = new VisTextButton("");
        btSonido = new VisTextButton("");
        actualizarTextoBotones();



        VisTextButton btVolver = new VisTextButton("Volver");

        // Colocacion
        menu.row();
        menu.add(btSonido).center().width(200).height(80).pad(5);
        menu.row();
        menu.add(btMusica).center().width(200).height(80).pad(5);
        menu.row();
        menu.add(btVolver).center().width(200).height(80).pad(5);

        // Listeners
        btMusica.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Configuracion.setMusicEnabled(!Configuracion.isMusicEnabled());
                actualizarTextoBotones();
            }
        });

        btSonido.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Configuracion.setSoundEnabled(!Configuracion.isSoundEnabled());
                actualizarTextoBotones();
            }
        });

        btVolver.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Practica) Gdx.app.getApplicationListener()).setScreen(new PantallaMenuPrincipal());
                dispose();
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
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
        stage.dispose();
    }

    public void actualizarTextoBotones(){
        String aux  = Configuracion.isMusicEnabled()?"Desactivar música":"Activar música";
        btMusica.setText(aux);
        aux = Configuracion.isSoundEnabled()?"Desactivar sonido":"Activar sonido";
        btSonido.setText(aux);
    }
}
