package haidnor.jvm.test.demo;


import haidnor.jvm.test.clazz.Human;
import haidnor.jvm.test.clazz.Organism;
import haidnor.jvm.test.clazz.Student;

public class Demo8 {

    public static void main(String[] args) {
        Human organism1 = new Human();
        Organism organism = new Student();
        organism.die();
    }


}
