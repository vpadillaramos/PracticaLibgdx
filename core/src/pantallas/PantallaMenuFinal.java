package pantallas;

import com.badlogic.gdx.Gdx;
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

public class PantallaMenuFinal implements Screen {

    // Atributos
    Stage stage;
    private String mensaje;


    public PantallaMenuFinal(String mensaje){
        this.mensaje = mensaje;
    }

    @Override
    public void show() {
        if(!VisUI.isLoaded())
            VisUI.load();

        stage = new Stage();

        VisTable menu = new VisTable();
        menu.setFillParent(true);
        stage.addActor(menu);

        // Fin del juego
        VisLabel lbFin = new VisLabel(mensaje);


        // BOTON VOLVER A JUGAR
        VisTextButton btVolverJugar = new VisTextButton("Volver a jugar");
        btVolverJugar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Practica) Gdx.app.getApplicationListener()).setScreen(new PantallaJuego());
                dispose();
            }
        });

        // BOTON IR AL MENU
        VisTextButton btMenuPrincipal = new VisTextButton("Ir al menú principal");
        btMenuPrincipal.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Practica) Gdx.app.getApplicationListener()).setScreen(new PantallaMenuPrincipal());
                dispose();
            }
        });

        // Colocacion
        menu.row();
        menu.add(lbFin).center().width(200).height(100).pad(5);
        menu.row();
        menu.add(btVolverJugar).center().width(200).height(80).pad(5);
        menu.row();
        menu.add(btMenuPrincipal).center().width(200).height(80).pad(5);

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
}
