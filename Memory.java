import java.util.TreeSet;
import java.util.LinkedList;
import java.util.Random;

public class Memory {
    private static volatile int volatileLoopVariable;

    public static long getRunningTime(int size, int experiments) {
        long runningTime = 0;
        for (int loopVariable = 0; loopVariable < size; loopVariable++) {
            if (loopVariable % 2 == 0) {
                runningTime += loopVariable;
            }
            else {
                runningTime -= loopVariable;
            }
        }
        return runningTime;
    }

    public static long getRunningVolatileTime(int size, int experiments) {
        long volatileRunningTime = 0;

        for (volatileLoopVariable = 0; volatileLoopVariable < size; volatileLoopVariable++) {
            if (volatileLoopVariable % 2 == 0) {
                volatileRunningTime += volatileLoopVariable;
        }
            else {
                volatileRunningTime -= volatileLoopVariable;
            }
        }

        return volatileRunningTime;
}

    public static double calculateAverageRunTimes(int size, int experiments) {
        long totalRunTime = 0;

        for (int i = 0; i < experiments; i++ ) {
            long startTime = System.nanoTime();
            getRunningTime(size, experiments);
            long endTime = System.nanoTime();
            totalRunTime += (endTime - startTime);
        }
        
        return (totalRunTime /(double) experiments) / 1_000_000_000.0;
    }

    public static double calculateVolatileAverageRunTimes(int size, int experiments) {
        long totalRunTime = 0;

        for (int i = 0; i < experiments; i++ ) {
            long startTime = System.nanoTime();
            getRunningVolatileTime(size, experiments);
            long endTime = System.nanoTime();
            totalRunTime += (endTime - startTime);
        }
        
        return (totalRunTime /(double) experiments) / 1_000_000_000.0;
    }

    public static void arrayExperiments(int size, int experiments, int seed) {
        Integer [] array = new Integer[size];
        Random rand = new Random(seed);

        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt();
        }
  
        int indexFirstTenPercent = (int) Math.floor(size * 0.10);
        int indexLastTenPercent = (int) Math.floor(size * 0.90);

        long firstTenPercentTime = 0;
        long lastTenPercentTime = 0;
        long arrayElementSum = 0;

        for (int i = 0; i < experiments; i++) {
            for (int j = 0; j < indexFirstTenPercent; j++) {
                long firstStart = System.nanoTime();
                arrayElementSum += array[j];
                long firstEnd = System.nanoTime();
                firstTenPercentTime += (firstEnd - firstStart);
            }

            int randomIndex = rand.nextInt(size - indexLastTenPercent) + indexLastTenPercent;
            long startLast = System.nanoTime();
            arrayElementSum += array[randomIndex];
            long endLast = System.nanoTime();
            lastTenPercentTime += (endLast - startLast);
        }

            double avgTimeFirstTen = firstTenPercentTime / (double) (experiments * indexFirstTenPercent);
            double avgTimeLastTen = lastTenPercentTime / (double) (experiments);

            System.out.println("Avg time to access known element: " + String.format("%.2f", avgTimeFirstTen) + " nanoseconds");
            System.out.println("Avg time to access random element: " + String.format("%.2f", avgTimeLastTen) + " nanoseconds");
            System.out.println("Sum: " + arrayElementSum);
            
        }

        public static double treeSetExperiment(int size, int experiments) {
            TreeSet<Integer> treeSet = new TreeSet<>();
            Random rand = new Random();
            long totalRunTime = 0;

            for (int i = 0; i < size; i++) {
                treeSet.add(rand.nextInt());
            }

            for (int i = 0; i < experiments; i++) {
                long startTime = System.nanoTime();
                int randomInt = rand.nextInt();
                treeSet.contains(randomInt);
                long endTime = System.nanoTime();
                totalRunTime += (endTime - startTime);
            }

            return (totalRunTime /(double) experiments);

        }

        public static double linkedListExperiment(int size, int experiments) {
            LinkedList<Integer> linkedList = new LinkedList<>();
            Random rand = new Random();
            long totalRunTime = 0;

            for (int i = 0; i < size; i++) {
                linkedList.add(rand.nextInt());
            }

            for (int i = 0; i < experiments; i++) {
                long startTime = System.nanoTime();
                int randomInt = rand.nextInt();
                linkedList.contains(randomInt);
                long endTime = System.nanoTime();
                totalRunTime += (endTime - startTime);
            }

            return (totalRunTime /(double) experiments);
        }

    
    public static void main(String[] args) {

        if (args.length != 3) {
            System.err.print("Error: 3 arguments expected: size, experiments, seed");
        }
        int size = Integer.parseInt(args[0]);
        int experiments = Integer.parseInt(args[1]);
        int seed = Integer.parseInt(args[2]);

        System.out.println("Task 1");

        double averageRegularTime = calculateAverageRunTimes(size, experiments);
        double averageVolatileRunningTime = calculateVolatileAverageRunTimes(size, experiments);

        System.out.println("Regular: " + String.format("%.5f" , averageRegularTime) + " seconds");
        System.out.println("Volatile: " + String.format("%.5f" , averageVolatileRunningTime) + " seconds");

        double regularSum = getRunningTime(size, experiments);
        double volatileSum = getRunningVolatileTime(size, experiments);

        System.out.println("Avg regular sum: " + String.format("%.2f",regularSum));
        System.out.println("Avg volatile sum: " + String.format("%.2f",volatileSum)+ "\n");

        System.out.println("Task 2");
        
        arrayExperiments(size, experiments, seed);
        System.out.print("\n");

        System.out.println("Task 3");
        double treeSetTime = treeSetExperiment(size, experiments);
        System.out.println("Avg time to find in set: " + String.format("%.2f", treeSetTime) + " nanoseconds");
        double linkedListTime = linkedListExperiment(size, experiments);
        System.out.println("Avg time to find in list: " + String.format("%.2f", linkedListTime) + " nanoseconds");
        
    }

}