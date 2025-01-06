package io.github.game;

class HealthPickUp extends PickUp {
    private int healthBonus;
    

    public HealthPickUp(float x, float y, float radius, int healthBonus) {
        super(x, y, radius);
        this.healthBonus = healthBonus;
        
    }

    @Override
    public void applyEffect(Player player) {
    	if(player.getBounds().contains(position))
        player.increaseLife(healthBonus);
    	System.out.println("Vida aumentada");
    }

}