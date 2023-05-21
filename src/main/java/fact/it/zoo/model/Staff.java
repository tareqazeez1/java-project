/**
 * Name: Md Raihanul Kabir Rafi
 * R- number: r0913740
 */

package fact.it.zoo.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Staff extends Person{

    private LocalDate startDate;
    private boolean student;

    public Staff(String firstName, String surName) {
        super(firstName, surName);
        this.startDate = LocalDate.now();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if(student) return "Staff member "+super.toString()+" (working student) is employed since "+this.startDate.format(dtf);
        return "Staff member "+super.toString()+" is employed since "+this.startDate.format(dtf);
    }
}
