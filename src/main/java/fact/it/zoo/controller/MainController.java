/**
 * Name: Md Raihanul Kabir Rafi
 * R- number: r0913740
 */

package fact.it.zoo.controller;


import fact.it.zoo.model.AnimalWorld;
import fact.it.zoo.model.Staff;
import fact.it.zoo.model.Visitor;
import fact.it.zoo.model.Zoo;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


@Controller
public class MainController {
    private ArrayList<Staff> staffArrayList;
    private ArrayList<Visitor> visitorArrayList;
    private ArrayList<Zoo> zooArrayList;

    @PostConstruct
    public void fillStaffVisitorsZooLists() {
        this.staffArrayList = fillStaffMembers();
        this.visitorArrayList = fillVisitors();
        this.zooArrayList = fillZoos();
    }


    @RequestMapping("/add/visitor")
    public String addNewVisitor(Model model){
        model.addAttribute("zoos", zooArrayList);
        return "1_addNewVisitor";
    }

    @RequestMapping("/visitor/details")
    public String newVisitorDetails(HttpServletRequest request, Model model){
        String firstName = request.getParameter("firstName");
        String surName = request.getParameter("surName");

        int birthYear = Integer.parseInt(request.getParameter("birthYear"));

        Visitor visitor = new Visitor(firstName, surName);
        visitor.setYearOfBirth(birthYear);
        int zooIndex = Integer.parseInt(request.getParameter("zooIndex"));

        Zoo zoo = zooArrayList.get(zooIndex);
        zoo.registerVisitor(visitor);

        visitorArrayList.add(visitor);

        model.addAttribute("visitor", visitor);
        return "2_visitorDetails";
    }

    @RequestMapping("/add/staff")
    public String addNewStaffMember(Model model){
        Staff staff = new Staff("", "");
        model.addAttribute("staff", staff);
        return "3_addNewStaffMember";
    }

    @RequestMapping("/staff-member/details")
    public String staffMemberDetails(HttpServletRequest request, Model model){
        String firstName = request.getParameter("firstName");
        String surName = request.getParameter("surName");
        String employedSince = request.getParameter("employedSince");
        boolean student = Boolean.parseBoolean(request.getParameter("student"));

        Staff staff = new Staff(firstName, surName);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        staff.setStartDate(LocalDate.parse(employedSince, dtf));
        staff.setStudent(student);

        staffArrayList.add(staff);

        model.addAttribute("staff", staff);
        return "4_showStaffMember";
    }

    @RequestMapping("/staff-members/details")
    public String staffMembersDetails(Model model) {
        model.addAttribute("staffmembers", staffArrayList);
        return "5_staffMembersDetails";
    }

    @RequestMapping("/visitors/details")
    public String visitorsDetails(Model model) {
        model.addAttribute("visitors", visitorArrayList);
        return "6_visitorsDetails";
    }

    @RequestMapping("/add/zoo")
    public String addNewZoo() {
        return "7_addNewZoo";
    }

    @RequestMapping("/zoo/details")
    public String zooDetails(HttpServletRequest request, Model model) {
        String zooName = request.getParameter("zooName");

        if(zooName != null) {
            Zoo zoo = new Zoo(zooName);
            zooArrayList.add(zoo);
        }

        model.addAttribute("zoos", zooArrayList);
        return "8_zoosDetails";
    }

    @RequestMapping("/add/animal-world")
    public String addNewAnimalWorld(Model model) {
        AnimalWorld animalWorld = new AnimalWorld();


        model.addAttribute("animalWorld", animalWorld);
        model.addAttribute("zoos", zooArrayList);
        model.addAttribute("staffMembers", staffArrayList);

        return "9_addNewAnimalWorld";
    }

    @RequestMapping("/animal-worlds/details")
    public String animalWorldsDetals(HttpServletRequest request, Model model) {
        String animalWorldName = request.getParameter("animalWorldName");

        int zooIndex = Integer.parseInt(request.getParameter("zooIndex"));


        if (zooIndex != -1 && StringUtils.isBlank(animalWorldName)) {
            //when zoo is clicked
            Zoo newZoo = zooArrayList.get(zooIndex);
            model.addAttribute("zoo", newZoo);

            return "10_animalWorldsDetails";
        }
        AnimalWorld newAnimalWorld = new AnimalWorld(animalWorldName);

        int numberOfAnimals;
        if(request.getParameter("animalsNumber") != null) {
            numberOfAnimals = Integer.parseInt(request.getParameter("animalsNumber"));
            newAnimalWorld.setNumberOfAnimals(numberOfAnimals);
        }

        if (zooIndex == -1) {
            //that means user has not selected anything from the dropdown of zoo
            model.addAttribute("errorMessage", "You didn't choose a zoo!");
            return "error";
        }

        int staffMemberIndex;

        if(request.getParameter("staffMemberIndex") != null) {
            staffMemberIndex = Integer.parseInt(request.getParameter("staffMemberIndex"));

            if (staffMemberIndex == -1) {
                //that means user has not selected anything from the dropdown of staff members
                model.addAttribute("errorMessage", "You didn't choose a staff member!");
                return "error";
            }

            Staff newStaffMember = staffArrayList.get(staffMemberIndex);
            newAnimalWorld.setResponsible(newStaffMember);
        }


        String photo = request.getParameter("photo");
        newAnimalWorld.setPhoto(photo);

        Zoo newZoo = zooArrayList.get(zooIndex);
        newZoo.addAnimalWorld(newAnimalWorld);

        model.addAttribute("zoo", newZoo);

        return "10_animalWorldsDetails";
    }

    @RequestMapping("/animal-world/details")
    public String animalWorldDetails(HttpServletRequest request, Model model) {
        String keyword = request.getParameter("search");

        for (Zoo zoo : zooArrayList) {
            AnimalWorld animalWorld = zoo.searchAnimalWorldByName(keyword);

            if (animalWorld != null) {
                model.addAttribute("animalWorld", animalWorld);
                return "11_animalWorldDetails";
            }
        }

        //if we reach here, that means the searched animal world is not present in our zoos
        model.addAttribute("errorMessage", "There is no animal world with the name '" + keyword + "'");
        return "error";
    }




   private ArrayList<Staff> fillStaffMembers() {
        ArrayList<Staff> staffMembers = new ArrayList<>();

        Staff staff1 = new Staff("Johan", "Bertels");
        staff1.setStartDate(LocalDate.of(2002, 5, 1));
        Staff staff2 = new Staff("An", "Van Herck");
        staff2.setStartDate(LocalDate.of(2019, 3, 15));
        staff2.setStudent(true);
        Staff staff3 = new Staff("Bruno", "Coenen");
        staff3.setStartDate(LocalDate.of(1995,1,1));
        Staff staff4 = new Staff("Wout", "Dayaert");
        staff4.setStartDate(LocalDate.of(2002, 12, 15));
        Staff staff5 = new Staff("Louis", "Petit");
        staff5.setStartDate(LocalDate.of(2020, 8, 1));
        staff5.setStudent(true);
        Staff staff6 = new Staff("Jean", "Pinot");
        staff6.setStartDate(LocalDate.of(1999,4,1));
        Staff staff7 = new Staff("Ahmad", "Bezeri");
        staff7.setStartDate(LocalDate.of(2009, 5, 1));
        Staff staff8 = new Staff("Hans", "Volzky");
        staff8.setStartDate(LocalDate.of(2015, 6, 10));
        staff8.setStudent(true);
        Staff staff9 = new Staff("Joachim", "Henau");
        staff9.setStartDate(LocalDate.of(2007,9,18));
        staffMembers.add(staff1);
        staffMembers.add(staff2);
        staffMembers.add(staff3);
        staffMembers.add(staff4);
        staffMembers.add(staff5);
        staffMembers.add(staff6);
        staffMembers.add(staff7);
        staffMembers.add(staff8);
        staffMembers.add(staff9);
        return staffMembers;
    }

    private ArrayList<Visitor> fillVisitors() {
        ArrayList<Visitor> visitors = new ArrayList<>();
        Visitor visitor1 = new Visitor("Dominik", "Mioens");
        visitor1.setYearOfBirth(2001);
        Visitor visitor2 = new Visitor("Zion", "Noops");
        visitor2.setYearOfBirth(1996);
        Visitor visitor3 = new Visitor("Maria", "Bonetta");
        visitor3.setYearOfBirth(1998);
        Visitor visitor4 = new Visitor("Md Tareq", "Aziz");
        visitor4.setYearOfBirth(1996);

        visitors.add(visitor1);
        visitors.add(visitor2);
        visitors.add(visitor3);
        visitors.add(visitor4);

        visitors.get(0).addToWishList("Dolphin");
        visitors.get(0).addToWishList("Snake");
        visitors.get(1).addToWishList("Lion");
        visitors.get(1).addToWishList("Eagle");
        visitors.get(1).addToWishList("Monkey");
        visitors.get(1).addToWishList("Elephant");
        visitors.get(2).addToWishList("Turtle");
        visitors.get(3).addToWishList("Crocodile");
        visitors.get(3).addToWishList("Tortoise");

        return visitors;
    }

    private ArrayList<Zoo> fillZoos() {
        ArrayList<Zoo> zoos = new ArrayList<>();
        Zoo zoo1 = new Zoo("Antwerp Zoo");
        Zoo zoo2 = new Zoo("Bellewaerde");
        Zoo zoo3 = new Zoo("Plankendael Zoo");
        AnimalWorld animalWorld1 = new AnimalWorld("Aquarium");
        AnimalWorld animalWorld2 = new AnimalWorld("Reptiles");
        AnimalWorld animalWorld3 = new AnimalWorld("Monkeys");
        animalWorld1.setNumberOfAnimals(1250);
        animalWorld2.setNumberOfAnimals(500);
        animalWorld3.setNumberOfAnimals(785);
        animalWorld1.setPhoto("/img/aquarium.jpg");
        animalWorld2.setPhoto("/img/reptiles.jpg");
        animalWorld3.setPhoto("/img/monkeys.jpg");
        animalWorld1.setResponsible(staffArrayList.get(0));
        animalWorld2.setResponsible(staffArrayList.get(1));
        animalWorld3.setResponsible(staffArrayList.get(2));
        zoo1.addAnimalWorld(animalWorld1);
        zoo1.addAnimalWorld(animalWorld2);
        zoo1.addAnimalWorld(animalWorld3);
        zoo2.addAnimalWorld(animalWorld1);
        zoo2.addAnimalWorld(animalWorld2);
        zoo3.addAnimalWorld(animalWorld1);
        zoo3.addAnimalWorld(animalWorld3);
        zoos.add(zoo1);
        zoos.add(zoo2);
        zoos.add(zoo3);
        return zoos;
    }

}
