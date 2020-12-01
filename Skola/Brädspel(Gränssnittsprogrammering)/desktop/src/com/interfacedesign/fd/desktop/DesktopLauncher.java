package com.interfacedesign.fd.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.interfacedesign.fd.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Forbidden Desert";
		config.height = Game.GAMEHEIGHT;
		config.width = Game.GAMEWIDTH;

		new LwjglApplication(new Game(), config);
	}
}
