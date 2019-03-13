package pantallas;

import character.BossFinal;
import character.Disparo;
import character.Ghost;
import character.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.vpr.practica.Practica;
import manager.Configuracion;
import manager.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

public class PantallaJuego implements Screen {

    // Constantes
    private final int MAX_SALAS = 3;
    private final long INTERVALO_DISPARO = 400;
    private final long INTERVALO_INVENCIBILLIDAD = 2000;
    private final long INTERVALO_BOSS_ATACK = 300;

    // Variables
    private SpriteBatch batch;


    // Musica
    Music music;

    // Player
    private Player player;

    // Disparo
    private Array<Disparo> disparos;
    private Array<Disparo> disparosBoss;

    // Tiempos
    private long timeLastDisparo;
    private long timeLastHit;
    private long timeLastBossAtack;

    // Salas
    public Sala[] salas;


    // HUD
    TextureRegion[] vidas;
    BitmapFont vidasBoss;

    @Override
    public void show() {
        batch = new SpriteBatch();

        // Player
        player = new Player(R.getRegion("idle"));

        // Disparo
        disparos = new Array<Disparo>();
        disparosBoss = new Array<Disparo>();

        // Tiempos
        timeLastDisparo = TimeUtils.millis();
        timeLastHit = TimeUtils.millis();
        timeLastBossAtack = TimeUtils.millis();

        salas = new Sala[MAX_SALAS];
        for(int i = 0; i < salas.length; i++){
            salas[i] = new Sala(i+1);
        }

        // HUD
        vidas = new TextureRegion[player.vidas];
        for(int i = 0; i < vidas.length; i++){
            vidas[i] = new TextureRegion(R.getRegion("fullHeart"));
        }
        vidasBoss = new BitmapFont();

        // Musica
        music = R.getMusica("sounds/dungeon.mp3");

        comprobarConfiguracion();
    }

    @Override
    public void render(float delta) {
        /////////////////////////
        /////////LOGICA//////////
        /////////////////////////

        if(player.salaActual < 3)
            actualizarMovimientos();
        else{
            // Player
            moverPlayer();
            playerDispara();

            // Disparo
            for(Disparo disparo : disparos)
                disparo.mover();

            // Acciones
            comprobarVidaBoss();
            comprobarAtaqueBoss();

            // Si el player esta a la derecha
            if(player.posicion.x > salas[2].bossFinal.posicion.x)
                salas[2].bossFinal.trackPlayer(1);
            else if(player.posicion.x <= salas[2].bossFinal.posicion.x)
                salas[2].bossFinal.trackPlayer(2);

            bossDispara();
            for(Disparo disparo : disparosBoss){
                disparo.moveDown();
            }
        }


        /////////////////////////
        /////////GRAFICO/////////
        /////////////////////////
        Gdx.gl.glClearColor(0.3f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        if(player.salaActual < 3)
            actualizarGrafico(delta);
        else{
            // Sala
            salas[player.salaActual-1].pintar(batch);

            // Disparo
            for(Disparo disparo : disparos){
                disparo.pintar(batch);

                if(Math.abs(disparo.posicion.x - disparo.posicionInicial.x) > disparo.RANGO)
                    disparos.removeValue(disparo, true);
            }


            // Player
            player.actualizarSprite(delta);
            player.pintar(batch);

            // Boss
            salas[2].bossFinal.pintar(batch);
            salas[2].bossFinal.actualizaSprite(delta);


            for(Disparo disparo : disparosBoss){
                disparo.pintar(batch);
            }

            // HUD
            int cont = 0;
            for(TextureRegion tr : vidas){
                batch.draw(tr, 80 + cont, Gdx.graphics.getHeight() - 40);
                cont += 30;
            }
            vidasBoss.getData().setScale(2, 2);
            vidasBoss.draw(batch, "BOSS: "+salas[2].bossFinal.vidas+" vidas", (Gdx.graphics.getWidth()/2) - 50, 120);
        }

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
        for(Sala s : salas){
            s.dispose();
        }
        music.dispose();
    }

    private void comprobarEntradaSala(){
        if(salas[player.salaActual-1].ghosts.size == 0 && player.hb.overlaps(salas[player.salaActual-1].salida)){
            player.salaActual += 1;
            player.posicion.set(150, 360);
            disparos.clear();
        }
    }

    private void actualizarMovimientos(){
        // Player
        moverPlayer();
        playerDispara();

        // Disparo
        for(Disparo disparo : disparos)
            disparo.mover();


        // Ghosts
        ghostTrackPlayer();

        // Acciones
        comprobarVidas();
        comprobarEntradaSala();

        playerHitGhost();

    }

    private void actualizarGrafico(float delta){

        // Sala
        salas[player.salaActual-1].pintar(batch);

        if(player.salaActual == 1){
            salas[0].instructor.pintar(batch);

            if(player.hb.overlaps(salas[0].instructor.hb)){
                salas[0].instructor.decirFrase(batch);
            }
        }

        // Disparo
        for(Disparo disparo : disparos){
            disparo.pintar(batch);

            if(Math.abs(disparo.posicion.x - disparo.posicionInicial.x) > disparo.RANGO)
                disparos.removeValue(disparo, true);
        }

        // Player
        player.actualizarSprite(delta);
        player.pintar(batch);

        // Ghost
        for(Ghost g : salas[player.salaActual-1].ghosts){
            g.actualizarSprite(delta);
            g.pintar(batch);
        }

        // HUD
        int cont = 0;
        for(TextureRegion tr : vidas){
            batch.draw(tr, 80 + cont, Gdx.graphics.getHeight() - 40);
            cont += 30;
        }
    }

    private void moverPlayer(){
        player.moverPlayer();
        comprobarColisiones();
    }

    private void playerDispara(){
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            if(TimeUtils.timeSinceMillis(timeLastDisparo) >= INTERVALO_DISPARO){
                Disparo disparo = player.disparar();
                disparo.setSentido(Disparo.Sentido.RIGHT);
                disparos.add(disparo);
                timeLastDisparo = TimeUtils.millis();
                player.setEstado(Player.Estado.RIGHT);

                if(Configuracion.isSoundEnabled())
                    R.getSonido("sounds/waterDrop.mp3").play();
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            if(TimeUtils.timeSinceMillis(timeLastDisparo) >= INTERVALO_DISPARO){
                Disparo disparo = player.disparar();
                disparo.setSentido(Disparo.Sentido.LEFT);
                disparos.add(disparo);
                timeLastDisparo = TimeUtils.millis();
                player.setEstado(Player.Estado.LEFT);

                if(Configuracion.isSoundEnabled())
                    R.getSonido("sounds/waterDrop.mp3").play();
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            if(TimeUtils.timeSinceMillis(timeLastDisparo) >= INTERVALO_DISPARO){
                Disparo disparo = player.disparar();
                disparo.setSentido(Disparo.Sentido.UP);
                disparos.add(disparo);
                timeLastDisparo = TimeUtils.millis();
                player.setEstado(Player.Estado.UP);

                if(Configuracion.isSoundEnabled())
                    R.getSonido("sounds/waterDrop.mp3").play();
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            if(TimeUtils.timeSinceMillis(timeLastDisparo) >= INTERVALO_DISPARO){
                Disparo disparo = player.disparar();
                disparo.setSentido(Disparo.Sentido.DOWN);
                disparos.add(disparo);
                timeLastDisparo = TimeUtils.millis();
                player.setEstado(Player.Estado.DOWN);

                if(Configuracion.isSoundEnabled())
                    R.getSonido("sounds/waterDrop.mp3").play();
            }
        }
    }

    private void playerHitGhost(){
        for(Ghost ghost : salas[player.salaActual-1].ghosts){
            for(Disparo disparo : disparos){
                if(disparo.hb.overlaps(ghost.hb)){
                    if(ghost.vidas > 0) {
                        ghost.vidas -= 1;
                        if(Configuracion.isSoundEnabled())
                            R.getSonido("sounds/ghost.mp3").play();
                    }
                    else
                        salas[player.salaActual-1].ghosts.removeValue(ghost, true);
                    disparos.removeValue(disparo, true);
                }
            }
        }
    }

    private void ghostTrackPlayer(){

        for(Ghost g : salas[player.salaActual-1].ghosts){

            // Si player esta encima
            if(player.posicion.y > g.posicion.y)
                g.trackPlayer(1);

            // Si player esta a su derecha
            if(player.posicion.x > g.posicion.x)
                g.trackPlayer(2);

            // Si player esta debajo
            if(player.posicion.y < g.posicion.y)
                g.trackPlayer(3);

            // Si player esta a su izquierda
            if(player.posicion.x < g.posicion.x)
                g.trackPlayer(4);

        }
    }

    private void comprobarColisiones(){

        // Pared izquierda
        if(player.posicion.x < Sala.LIMITE_PARED_IZQUIERDA)
            player.posicion.x = Sala.LIMITE_PARED_IZQUIERDA;

        // Pared inferior
        if(player.posicion.y < Sala.LIMITE_PARED_INFERIOR)
            player.posicion.y = Sala.LIMITE_PARED_INFERIOR;

        // Pared derecha
        if(player.posicion.x > Sala.LIMITE_PARED_DERECHA)
            player.posicion.x = Sala.LIMITE_PARED_DERECHA;

        // Pared superior
        if(player.posicion.y > Sala.LIMITE_PARED_SUPERIOR)
            player.posicion.y = Sala.LIMITE_PARED_SUPERIOR;

    }

    private void comprobarVidas(){

        for(Ghost g : salas[player.salaActual-1].ghosts){
            if(TimeUtils.timeSinceMillis(timeLastHit) > INTERVALO_INVENCIBILLIDAD && g.hb.overlaps(player.hb) && player.vidas > 0){
                player.vidas -= 1;
                vidas[player.vidas] = new TextureRegion(R.getRegion("emptyHeart"));
                timeLastHit = TimeUtils.millis();
                if(Configuracion.isSoundEnabled())
                    R.getSonido("sounds/isaacDamage.mp3").play();
            }
            else if(player.vidas <= 0){
                if(Configuracion.isMusicEnabled())
                    music.stop();
                ((Practica) Gdx.app.getApplicationListener()).setScreen(new PantallaMenuFinal("Has perdido"));
            }
        }
    }

    private void comprobarAtaqueBoss(){
        for(Disparo disparo : disparosBoss){
            if(TimeUtils.timeSinceMillis(timeLastHit) > INTERVALO_INVENCIBILLIDAD && disparo.hb.overlaps(player.hb)){
                player.vidas -= 1;
                vidas[player.vidas] = new TextureRegion(R.getRegion("emptyHeart"));
                timeLastHit = TimeUtils.millis();
                if(Configuracion.isSoundEnabled())
                    R.getSonido("sounds/isaacDamage.mp3").play();
            }
            else if(player.vidas <= 0){
                if(Configuracion.isMusicEnabled())
                    music.stop();
                ((Practica) Gdx.app.getApplicationListener()).setScreen(new PantallaMenuFinal("Has perdido"));
            }
        }
    }

    private void comprobarVidaBoss(){
        for(Disparo disparo : disparos){
            if(salas[2].bossFinal.vidas <= 0){
                ((Practica) Gdx.app.getApplicationListener()).setScreen(new PantallaMenuFinal("¡ GANASTE !"));
            }

            if(disparo.hb.overlaps(salas[2].bossFinal.hb)){
                System.out.println(salas[2].bossFinal.vidas);
                salas[2].bossFinal.vidas -= 1;
                disparos.removeValue(disparo, true);
            }
        }

    }


    public void bossDispara(){
        if(TimeUtils.timeSinceMillis(timeLastBossAtack) >= INTERVALO_BOSS_ATACK){
            int x = intRandom(150, Gdx.graphics.getWidth()-150);
            int y = Gdx.graphics.getHeight() + 50;
            Disparo disparo = new Disparo(R.getRegion("armaBoss"), x, y);
            disparo.velocidad.y = 6;
            disparosBoss.add(disparo);
            timeLastBossAtack = TimeUtils.millis();
        }

        for(Disparo disparo : disparosBoss){
            if(disparo.posicion.y <= -60)
                disparosBoss.removeValue(disparo, true);
        }
    }

    private void comprobarConfiguracion(){
        if(Configuracion.isMusicEnabled()){
            music.setLooping(true);
            music.setVolume(1f);
            music.play();
        }
    }

    private int intRandom(float minimo, float maximo) {
        Random num = new Random();
        int min = Math.round(minimo);
        int max = Math.round(maximo);
        return (min + num.nextInt(max-min+1));
    }

}
