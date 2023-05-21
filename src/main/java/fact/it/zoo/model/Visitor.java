/**
 * Name: Md Raihanul Kabir Rafi
 * R- number: r0913740
 */

package fact.it.zoo.model;

import java.util.ArrayList;

public class Visitor extends Person {

    private String personalCode;
    private int yearOfBirth;
    private ArrayList<String> wishList = new ArrayList<>();

    public Visitor(String firstName, String surName) {
        super(firstName, surName);
        this.personalCode = "undefined";
    }

    public String getPersonalCode() {
        return personalCode;
    }

    public void setPersonalCode(String personalCode) {
        if (this.personalCode.equals("undefined")) this.personalCode = personalCode;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public ArrayList<String> getWishList() {
        return wishList;
    }

    public boolean addToWishList(String animal) {
        int size = wishList.size();
        if (size < 5) {
            wishList.add(animal);
            return true;
        }
        return false;
    }

    public int getNumberOfWishes() {
        return this.wishList.size();
    }

    @Override
    public String toString() {
        return "Visitor " + super.toString() + " with personal code " + this.personalCode;
    }
}
