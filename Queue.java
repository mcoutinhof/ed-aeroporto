public class Queue {
    private final Plane[] items = new Plane[4096];
    private int start = 0; // Índice do primeiro item da fila.
    private int size = 0;  // Quantidade de item na fila.

    /** Adiciona o item ao fim da fila. */
    public void enqueue(Plane item) {
        if (isFull()) throw new RuntimeException("A fila está cheia");
        items[(start + size++) % items.length] = item;
    }

    /** Retorna o primeiro item da fila, sem o remover. */
    public Plane peek() {
        if (isEmpty()) throw new RuntimeException("A fila está vazia");
        return items[start];
    }

    /** Remove e retorna o primeiro item da fila. */
    public Plane dequeue() {
        if (isEmpty()) throw new RuntimeException("A fila está vazia");
        Plane item = items[start];
        items[start] = null;
        start = (start + 1) % items.length;
        size--;
        return item;
    }

    /** Retorna a quantidade de itens na fila. */
    public int getSize() {
        return size;
    }

    /** Retorna true se a fila está vazia. */
    public boolean isEmpty() {
        return getSize() <= 0;
    }

    /** Retorna true se a fila não está vazia. */
    public boolean isNotEmpty() {
        return !isEmpty();
    }

    /** Retorna true se a fila está cheia. */
    public boolean isFull() {
        return getSize() >= items.length;
    }

    /** Retorna true se a fila não está cheia. */
    public boolean isNotFull() {
        return !isFull();
    }

    /** Gera uma string com a representação de todos os itens da fila. */
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        for (int i = 0; i < size; i++) {
            str.append(items[(start + i) % items.length]);
            if (i < size - 1) {
                str.append(", ");
            }
        }
        return str.append("}").toString();
    }

    /** Retorna a menor fila do vetor de filas. */
    public static Queue shortest(Queue[] queues) {
        Queue shortest = queues[0];
        for (Queue queue : queues) {
            shortest = queue.getSize() < shortest.getSize() ? queue : shortest;
        }
        return shortest;
    }

    /** Retorna a maior fila do vetor de filas. */
    public static Queue longest(Queue[] queues) {
        Queue longest = queues[0];
        for (Queue queue : queues) {
            longest = queue.getSize() > longest.getSize() ? queue : longest;
        }
        return longest;
    }
}