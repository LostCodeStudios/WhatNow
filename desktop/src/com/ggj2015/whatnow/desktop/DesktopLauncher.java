package com.ggj2015.whatnow.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ggj2015.whatnow.WhatNow;

public class DesktopLauncher {
	public static void main(String[] arg) {
		if (arg.length == 0) {
			LwjglApplicationConfiguration config =
					new LwjglApplicationConfiguration();
			new LwjglApplication(new WhatNow(), config);
		}
		else if (arg[0].startsWith("-E"))
		{
			LwjglApplicationConfiguration config =
					new LwjglApplicationConfiguration();
			new LwjglApplication(new WhatNow(true), config);
		}

	}
}
