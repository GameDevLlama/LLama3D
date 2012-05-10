package com.llama3d.main.activity;

import com.llama3d.main.settings.GameSettings;

import android.os.Bundle;

public interface BaseInterface {

	public void onGameSave(Bundle instance);

	public void onGameRestore(Bundle instance);

	public void onGameSettings(GameSettings settings);

	public void onGameStart();

	public void onGameFrame();

	public void onGameUpdate();

	public void onGameStartLoading();

	public void onGameLoading();

	public void onGameFinishLoading();

	public void onGamePause();

	public void onGameResume();

	public void onGameDestroy();

}
