package haidnor.jvm.test.clazz;

/**
 * 生物
 */
public interface Organism {

    default void die() {
        System.out.println("Organism die");
    }

}
