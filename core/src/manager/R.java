package manager;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.io.File;

public class R {
    // Constantes
    private static final String ATLAS = "characters.atlas";

    public static AssetManager assets = new AssetManager();

    // Metodo para cargar todo
    public static void cargarRecursos(){
        assets.load(ATLAS, TextureAtlas.class);
       // assets.load("sounds" + File.separator + "", Sound.class);
    }

    public static boolean update(){
        return assets.update();
    }

    // Carga animacion
    public static Array<TextureAtlas.AtlasRegion> getAnimacion(String nombre){
        return assets.get(ATLAS, TextureAtlas.class).findRegions(nombre);
    }

    // Carga sonido
    public static Sound getSonido(String nombre){
        return assets.get(nombre, Sound.class);
    }

    // Carga musica
    public static Music getMusica(String nombre){
        return assets.get(nombre, Music.class);
    }

    // Carga textura
    public static TextureRegion getRegion(String nombre){
        return assets.get(ATLAS, TextureAtlas.class).findRegion(nombre);
    }

}
