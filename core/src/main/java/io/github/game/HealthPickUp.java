package io.github.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

class HealthPickUp extends PickUp {
	private int healthBonus;
	private Sound healSound;

	public HealthPickUp(float x, float y, float radius, int healthBonus) {
		super(x, y, radius);
		this.healthBonus = healthBonus;
		this.healSound = Gdx.audio.newSound(Gdx.files.internal("sounds/healing.wav"));
	}

	@Override
	public void applyEffect(Player player) {
		if (player.getBounds().contains(position) && player.getVida() < 100) {
			healSound.play((float) 0.09);
			player.increaseLife(healthBonus);
			System.out.println("Vida aumentada");
		}
	}

}