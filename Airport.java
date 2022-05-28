public class Airport {
    private static int time = 0; // Unidade de tempo, ciclo atual da simulação.

    public static final Queue[] landings = new Queue[4]; // Prateleiras de espera para aterrissagem.
    public static final Queue[] takeoffs = new Queue[3]; // Fila de espera para decolagem.

    static {
        for (int i = 0; i < landings.length; i++) { landings[i] = new Queue(); }
        for (int i = 0; i < takeoffs.length; i++) { takeoffs[i] = new Queue(); }
    }

    /** Retorna o ciclo atual. */
    public static int getTime() {
        return time;
    }

    /** Vai para o próximo ciclo. */
    public static void tick() {
        time++;
    }
}