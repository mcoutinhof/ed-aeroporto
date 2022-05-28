import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Variáveis para estatísticas
        double avgTakeoffTime    = 0; // b) O empo médio de espera para decolagem
        int    takeoffCount      = 0; // Quantos aviões decolaram
        double avgLandingTime    = 0; // c) O tempo médio de espera para aterrissagem
        int    landingCount      = 0; // Quantos aviões aterrissaram
        int    landedWithoutFuel = 0; // d) O número de aviões que aterrissaram sem reserva de combustível.

        while (true) {
            System.out.println("== CICLO " + Airport.getTime() + " ==");

            // De zero a três aeronaves podem chegar nas filas de decolagem;
            int arrivingToTakeoff = new Random().nextInt(4);
            System.out.println(arrivingToTakeoff + " aviões chegaram ao aeroporto para decolar.");
            for (int i = 0; i < arrivingToTakeoff; i++) {
                Queue.shortest(Airport.takeoffs).enqueue(Plane.createTakeoffPlane());
            }

            Plane plane;

            // De zero a três aeronaves podem chegar nas prateleiras;
            int arrivingToLand = new Random().nextInt(4);
            System.out.println(arrivingToLand + " aviões chegaram ao aeroporto para aterrissar.");
            for (int i = 0; i < arrivingToLand; i++) {
                Queue.shortest(Airport.landings).enqueue(plane = Plane.createLandingPlane());
                System.out.println(" > " + plane);
            }

            int occupiedRunways = 0; // Quantas das 3 pistas já estão ocupadas.

            // Pousa até 2 aviões das maiores filas, se possível
            for (Queue queue; occupiedRunways < 2 && (queue = Queue.longest(Airport.landings)).isNotEmpty(); ) {
                if ((plane = queue.dequeue()).hasFuel()) {
                    avgLandingTime = avgLandingTime * (landingCount / (landingCount + 1.d)) + plane.getAge() / (landingCount + 1.d);
                    occupiedRunways++;
                    landingCount++;
                } else {
                    System.out.println("[!] CAIU: " + plane);
                }
            }

            // Pousa aviões com falta de combustível no restante das pistas, se necessário.
            for (Queue queue : Airport.landings) {
                while (occupiedRunways < 3 && queue.isNotEmpty() && queue.peek().getFuel() == 1) {
                    plane = queue.dequeue();
                    avgLandingTime = avgLandingTime * (landingCount / (landingCount + 1.d)) + plane.getAge() / (landingCount + 1.d);
                    landedWithoutFuel++;
                    occupiedRunways++;
                    landingCount++;
                }
            }

            // Decola os aviões das menores filas nas pistas restantes, se possível.
            for (Queue queue; occupiedRunways < 3 && (queue = Queue.longest(Airport.takeoffs)).isNotEmpty(); ) {
                plane = queue.dequeue();
                avgTakeoffTime = avgTakeoffTime * (takeoffCount / (takeoffCount + 1.d)) + plane.getAge() / (takeoffCount + 1.d);
                occupiedRunways++;
                takeoffCount++;
            }

            // Imprime as estatísticas a cada 5 ciclos.
            if (Airport.getTime() % 5 == 0 && Airport.getTime() > 0) {
                System.out.printf("Tempo médio de espera para decolagem: %.2f ciclos.\n", avgTakeoffTime);
                System.out.printf("Tempo médio de espera para aterrissagem: %.2f ciclos.\n", avgLandingTime);
                System.out.println("Número de aviões que aterrissaram sem reserva de combustível: " + landedWithoutFuel);

                // Imprime o conteúdo das filas.
                for (int i = 0; i < Airport.takeoffs.length; i++) {
                    System.out.println("Fila para decolagem " + (i + 1) + ": " + Airport.takeoffs[i]);
                }
                for (int i = 0; i < Airport.landings.length; i++) {
                    System.out.println("Fila para aterrissagem " + (i + 1) + ": " + Airport.landings[i]);
                }
            }

            Airport.tick(); // Avança o tempo para o próximo ciclo.

            new Scanner(System.in).nextLine(); // Pressione ENTER para continuar.
        }
    }
}