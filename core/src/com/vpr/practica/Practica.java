package com.vpr.practica;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import pantallas.PantallaSplash;


public class Practica extends Game {

	
	@Override
	public void create () {
		Gdx.graphics.setWindowedMode(1280, 720);
		setScreen(new PantallaSplash());
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
