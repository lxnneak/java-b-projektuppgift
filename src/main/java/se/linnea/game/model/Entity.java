package se.linnea.game.model;

public abstract class Entity {
    private String role;
    private int health;
    private int damage;

    public Entity(String role, int health, int damage) {
        this.role = role;
        this.health = health;
        this.damage = damage;
    }

    private String getRole() {
        return role;
    }

    int getHealth() {
        return health;
    }

    private int getDamage() {
        return damage;
    }

    public boolean isConscious() {
        return health > 0;
    }

    public void addDamage(int damage) {
        this.damage += damage;
    }

    private void takeHit(int damage) {
        this.health -= damage;
    }

    void punch(Entity toPunch) {
        toPunch.takeHit(this.damage);
    }

    public void executePunch(Entity attacker, Entity defender) {
        attacker.punch(defender);
        System.out.println(attacker.getRole() + " attacks " + defender.getRole());
        if (defender.isConscious()) {
            System.out.println(defender.getRole() + " has " + defender.getHealth() + " health left");
        } else {
            System.out.println(defender.getRole() + " is unconscious");
        }
    }

    public void fightOneRound(Entity attacker, Entity defender) {
        executePunch(attacker, defender);
        if (defender.isConscious()) {
            executePunch(defender, attacker);
        }
    }

}
