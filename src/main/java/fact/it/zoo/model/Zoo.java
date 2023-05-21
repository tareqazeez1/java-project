/**
 * Name: Md Raihanul Kabir Rafi
 * R- number: r0913740
 */

package fact.it.zoo.model;

import java.util.ArrayList;

public class Zoo {

    private String name;
    private int numberVisitors;
    private ArrayList<AnimalWorld> animalWorlds = new ArrayList<>();

    public Zoo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberVisitors() {
        return numberVisitors;
    }

    public ArrayList<AnimalWorld> getAnimalWorlds() {
        return animalWorlds;
    }

    public int getNumberOfAnimalWorlds() {
        return this.animalWorlds.size();
    }

    public void addAnimalWorld(AnimalWorld animalWorld) {
        this.animalWorlds.add(animalWorld);
    }

    public AnimalWorld searchAnimalWorldByName(String name) {
        for (AnimalWorld animalWorld : animalWorlds) {
            if (animalWorld.getName().equals(name)) return animalWorld;
        }
        return null;
    }

    public void registerVisitor(Visitor visitor) {
        numberVisitors++;
        String personalCode = this.name.substring(0, 2) + numberVisitors;
        visitor.setPersonalCode(personalCode);
    }
}
