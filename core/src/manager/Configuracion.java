package manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import util.Constantes;

public class Configuracion {

    private static Preferences preferences = Gdx.app.getPreferences(Constantes.CONFIGURACION);

    public static boolean isMusicEnabled(){
        return preferences.getBoolean(Constantes.MUSICA);
    }

    public static void setMusicEnabled(boolean b){
        preferences.putBoolean(Constantes.MUSICA, b);
        preferences.flush();
    }

    public static boolean isSoundEnabled(){
        return preferences.getBoolean(Constantes.SONIDO);
    }

    public static void setSoundEnabled(boolean b){
        preferences.putBoolean(Constantes.SONIDO, b);
        preferences.flush();
        System.out.println(isSoundEnabled());
    }

}
