import java.util.Random;

public class Plane {
    private static int landingPlaneCount = 0;
    private static int takeoffPlaneCount = 0;

    private final int id;
    private final int initialFuel; // Quantos ciclos inicialmente o avião poderá ficar no ar.
    private final int arrivedAt;   // Em qual ciclo o avião chegou ao aeroporto.

    private Plane(int id, int initialFuel) {
        this.id = id;
        this.initialFuel = initialFuel;
        this.arrivedAt = Airport.getTime();
    }

    /** Cria um avião para pousar, deve ter ID ímpar e combustível entre 1 e 20. */
    public static Plane createLandingPlane() {
        return new Plane(2 * (landingPlaneCount++) + 1, 1 + new Random().nextInt(20));
    }

    /** Cria um avião para decolar, deve ter ID par e tanque cheio. */
    public static Plane createTakeoffPlane() {
        return new Plane(2 * (takeoffPlaneCount++), 20);
    }

    /** Retorna o ID do avião. */
    public int getId() {
        return id;
    }

    /** Retorna o combustível restante, quantos ciclos o avião ainda pode ficar no ar. */
    public int getFuel() {
        if (id % 2 == 0) { // Aviões com ID par estão no chão e não gastam combustível.
            return Math.max(0, initialFuel);
        } else {
            return Math.max(0, initialFuel - (Airport.getTime() - arrivedAt));
        }
    }

    /** Retorna há quantos ciclos o avião chegou ao aeroporto. */
    public int getAge() {
        return Airport.getTime() - arrivedAt;
    }

    /** Retorna true se o avião ainda tem combustível para ficar no ar. */
    public boolean hasFuel() {
        return getFuel() > 0;
    }

    /** Retorna true se o combustível acabou e o avião caiu. */
    public boolean hasCrashed() {
        return !hasFuel();
    }

    /** Gera uma string que mostra o ID do avião e o combustível restante. */
    public String toString() {
        return "Plane(id=" + getId() + ",fuel=" + getFuel() + ")";
    }
}