/*
    =============================
    RANDOM RESTART HILL CLIMBING
    =============================
    Overview:
    RRHC improves RMHC by restarting from a new random solution whenever it gets 
    stuck in a local optimum.

    Step-by-Step Process:
        1. Start RMHC as described above.
        2. If no improvement is made for a certain number of iterations, restart from a new random solution.
        3. Keep track of the best solution found so far.
        4. Repeat until a stopping condition is met (e.g., max restarts or iterations).
    
    🔹 Weakness: Can still waste time on bad restarts.
    🔹 Strength: Increases the chance of finding a better global solution. 
 */

import java.util.ArrayList;

public class RRHC {

    private static HillClimbing HC = new HillClimbing();

    public static void main(String[] args) {

        // Random Restart Hill Climber (RRHC)
        int N = 48;

        String filename = "C:\\Users\\Bruno Estrada\\OneDrive - Brunel University London\\Year 2 - Resources\\CS2004 - Algorithms and their applications\\Lab 21 - TSP Data\\TSP_"+N+".txt";
        double[][] distanceArr = TSP.ReadArrayFile(filename, " "); 

        String fileSolution = "C:\\Users\\Bruno Estrada\\OneDrive - Brunel University London\\Year 2 - Resources\\CS2004 - Algorithms and their applications\\Lab 21 - TSP Data\\TSP_"+N+"_OPT.txt";
        ArrayList<Integer> optimalTour = TSP.ReadIntegerFile(fileSolution);

        // Variables
        int restartThreshold = 2000;  // If no improvement in 2000 iterations, restart
        int maxIterations = 100000;
        int noImprovementCount = 0;
        int count = 0;

        // Generate first random tour and find fitness
        ArrayList<Integer> tour = new ArrayList<>(HC.RandPerm(N));
        double tourFitness = HC.Fitness(N, tour, distanceArr);
        System.out.println("\nIntial tour: " +tour);
        System.out.println("Initial tour fitness: " +tourFitness);

        // Clone tours list to best tour list and fitness to best fitness
        ArrayList<Integer> bestTour = new ArrayList<>(tour);
        double bestFitness = tourFitness;

        // Get optimal tour and its fitness
        double optimalFitness = HC.Fitness(N, optimalTour, distanceArr);
        System.out.println("\nOptimal tour: " + optimalTour);
        System.out.println("Optimal tour fitness: " + optimalFitness);

        // Iterate until optimal value is equal best fitness or count reaches max iterations
        while (optimalFitness != tourFitness && count < maxIterations) {
            // Create new tour by swapping previous tour and get fitness
            ArrayList<Integer> newTour = HC.Swap(tour, distanceArr);
            double newFitness = HC.Fitness(N, newTour, distanceArr); 

            // If new fitness better than previous, accept new tour. Reset improvement count to 0 
            if (newFitness < tourFitness) {
                tour = newTour;
                tourFitness = newFitness;
                noImprovementCount = 0;  // Reset counter on improvement

                // Update the best solution found so far
                if (tourFitness < bestFitness) {
                    bestFitness = tourFitness;
                    bestTour = new ArrayList<>(tour);
                }

            } else {
                noImprovementCount++;
            }

            // Restart if stuck in local optimum
            if (noImprovementCount >= restartThreshold) {
                System.out.println("\nRestarting search... Iteration: " + count);
                tour = new ArrayList<>(HC.RandPerm(N));  // Generate new random tour
                tourFitness = HC.Fitness(N, tour, distanceArr);
                noImprovementCount = 0;  // Reset stagnation counter
            }

            if (count % 10000 == 0) {
                System.out.println("Fitness at " + count + "th iteration: " + tourFitness);
            }

            count++;
        }

        // Print the best solution found across all restarts
        System.out.println("\nBest tour found: " + bestTour);
        System.out.println("Best tour fitness: " + bestFitness);
        System.out.println("\nTotal iterations: " + count);

    }
}
