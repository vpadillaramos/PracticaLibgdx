package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.vpr.practica.Practica;

public class PantallaMenuPrincipal implements Screen {

    // Atributos
    Stage stage;

    @Override
    public void show() {
        if(!VisUI.isLoaded())
            VisUI.load();

        stage = new Stage();

        VisTable menu = new VisTable();
        menu.setFillParent(true);
        stage.addActor(menu);

        // BOTON JUGAR
        VisTextButton btJugar = new VisTextButton("Jugar");
        btJugar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Practica) Gdx.app.getApplicationListener()).setScreen(new PantallaJuego());
                dispose();
            }
        });

        // BOTON SALIR
        VisTextButton btSalir = new VisTextButton("Salir");
        btSalir.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                System.exit(0);
            }
        });

        // BOTON CONFIGURACION
        VisTextButton btConfiguracion = new VisTextButton("Configuración");
        btConfiguracion.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Practica) Gdx.app.getApplicationListener()).setScreen(new PantallaConfiguracion());
                dispose();
            }
        });

        // TITULO
        VisLabel lbTitulo = new VisLabel("Juego");

        // Creditos
        VisLabel lbCreditos = new VisLabel("Victor Padilla");
        stage.addActor(lbCreditos);


        // MENU
        menu.row();
        menu.add(lbTitulo).center().width(200).height(100).pad(5);
        menu.row();
        menu.add(btJugar).center().width(200).height(80).pad(5);
        menu.row();
        menu.add(btSalir).center().width(200).height(80).pad(5);
        menu.row();
        menu.add(btConfiguracion).center().width(200).height(80).pad(5);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // Pintar
        Gdx.gl.glClearColor(0.3f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            ((Practica) Gdx.app.getApplicationListener()).setScreen(new PantallaJuego());
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
        stage.dispose();
    }
}
