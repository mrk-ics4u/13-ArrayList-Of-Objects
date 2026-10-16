/*
 * Name:        Lesson 13: ArrayList of Objects (ArrayListOfObjectsDemo.java)
 * Description: Stores Enemy objects in an ArrayList, calls their methods,
 *              and runs the standard algorithms using getters.
 * Created by:  Mr Kowalczewski
 * Last edited: 2026-10-13
 */
import java.util.ArrayList;

// The Enemy class is in a separate file, Enemy.java, in the same folder.

public class ArrayListOfObjectsDemo {

    public static void main(String[] args) {

        // create the list, then add new objects to it
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(new Enemy("Goblin", 30, 4));
        enemies.add(new Enemy("Orc", 50, 8));
        enemies.add(new Enemy("Slime", 10, 1));
        enemies.add(new Enemy("Troll", 80, 12));

        // an object already stored in a variable can be added too
        Enemy boss = new Enemy("Dragon", 200, 25);
        enemies.add(boss);

        System.out.println("Enemies: " + enemies.size());

        // printing the list shows Enemy@1b6d3586 style codes: Enemy has no toString
        System.out.println(enemies);

        // call a method on one element: get(i) first, then the dot
        System.out.println(enemies.get(0).getName() + " has " + enemies.get(0).getHealth() + " health");

        // or store the element in a variable first (easier to read)
        Enemy orc = enemies.get(1);
        orc.takeDamage(20);

        // orc and enemies.get(1) are the SAME object, so the list sees the change
        System.out.println("Orc in the list now has " + enemies.get(1).getHealth() + " health");

        // same for boss: it's in the list, not a copy of it
        boss.takeDamage(50);
        System.out.println("Dragon in the list now has " + enemies.get(4).getHealth() + " health");

        // traverse with an index
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            System.out.println(i + ": " + e.getName() + " (" + e.getHealth() + " hp, " + e.getDamage() + " dmg)");
        }

        // enhanced for: calling a mutator on each object works
        for (Enemy e : enemies) {
            e.takeDamage(10);           // every enemy loses 10 health
        }
        System.out.println("After the explosion:");
        for (Enemy e : enemies) {
            System.out.println(e.getName() + ": " + e.getHealth());
        }

        // sum: add up a getter
        int totalDamage = 0;
        for (Enemy e : enemies) {
            totalDamage = totalDamage + e.getDamage();
        }
        System.out.println("Total damage per round: " + totalDamage);

        // max: keep the whole OBJECT, not just the number
        Enemy strongest = enemies.get(0);
        for (Enemy e : enemies) {
            if (e.getDamage() > strongest.getDamage()) {
                strongest = e;
            }
        }
        System.out.println("Hardest hitter: " + strongest.getName());

        // count: how many have a property
        int defeated = 0;
        for (Enemy e : enemies) {
            if (e.isDefeated()) {
                defeated++;
            }
        }
        System.out.println("Defeated: " + defeated);

        // find by name: compare Strings with .equals, never ==
        String target = "Troll";
        Enemy found = null;
        for (Enemy e : enemies) {
            if (e.getName().equals(target)) {
                found = e;
            }
        }
        if (found != null) {
            found.takeDamage(100);
            System.out.println(found.getName() + " defeated? " + found.isDefeated());
        } else {
            System.out.println("No enemy called " + target);
        }

        // remove defeated enemies: index loop, backward (never an enhanced for)
        for (int i = enemies.size() - 1; i >= 0; i--) {
            if (enemies.get(i).isDefeated()) {
                Enemy removed = enemies.remove(i);      // remove returns the object
                System.out.println("Removed " + removed.getName());
            }
        }

        System.out.println("Still standing:");
        for (Enemy e : enemies) {
            System.out.println(e.getName() + ": " + e.getHealth());
        }
    }

}
