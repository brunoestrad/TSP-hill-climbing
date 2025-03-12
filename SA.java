/*
    ====================
    SIMULATED ANNEALING
    ====================
    Overview:
    SA extends SHC by introducing a temperature-based acceptance probability that 
    decreases over time.

    Step-by-Step Process:
        1. Initialize:
            Start with a high temperature (T0) to allow exploration.
            Compute a cooling rate (λ) to gradually reduce temperature.
        2. Generate an Initial Random Solution and evaluate fitness.
        3. Perform a Small Change (swap two cities).
        4. Compare Fitness Values:
            If the new solution is better, accept it.
            If it’s worse, accept it with probability: Pr(accept) = exp(-Δf / T)
            where Δf = |f' - f| (the fitness difference).
        5. Decrease Temperature: 𝑇 = 𝜆𝑇.
        6. Repeat until the temperature is very low or max iterations are reached.

    🔹 Weakness: Requires careful tuning of temperature and cooling rate.
    🔹 Strength: Most effective at escaping local optima and finding high-quality solutions.
 */

import java.util.ArrayList;
import java.util.Random;

public class SA {

    private static HillClimbing HC = new HillClimbing();
    
    public static void main(String[] args) {

        int N = 105;
        // Read distance array
        String filename = "C:\\Users\\Bruno Estrada\\OneDrive - Brunel University London\\Year 2 - Resources\\CS2004 - Algorithms and their applications\\Lab 21 - TSP Data\\TSP_" + N + ".txt";
        double[][] distanceArr = TSP.ReadArrayFile(filename, " ");   

        // Read optimal solution
        String fileSolution = "C:\\Users\\Bruno Estrada\\OneDrive - Brunel University London\\Year 2 - Resources\\CS2004 - Algorithms and their applications\\Lab 21 - TSP Data\\TSP_"+N+"_OPT.txt";
        ArrayList<Integer> optimalTour = TSP.ReadIntegerFile(fileSolution);
        // Find optimal fitness
        double optimalFitness = HC.Fitness(N, optimalTour, distanceArr);
        System.out.println("\nOptimal tour: " + optimalTour);
        System.out.println("Optimal fitness: " + optimalFitness);                             

        // Create first random tour
        ArrayList<Integer> tour = new ArrayList<>(HC.RandPerm(N));
        double tourFitness = HC.Fitness(N, tour, distanceArr);
        System.out.println("\nIntial tour: " + tour);
        System.out.println("Initial tour fitness: " + tourFitness);

        // Simulated Annealing Parameters
        double T0 = 1000.0; // Initial temperature
        double T_ITER = 0.001; // Final temperature
        int maxIterations = 100000; // Number of iterations

        // Compute cooling rate λ
        double lambda = Math.pow(T_ITER / T0, 1.0 / maxIterations);
        double T = T0; // Set initial temperature

        Random rand = new Random();
        int count = 0;
        // Iterate until count reaches max iterations
        while (count < maxIterations && T > T_ITER) {
            // Generate new solution
            ArrayList<Integer> newTour = HC.Swap(tour, distanceArr);
            double newFitness = HC.Fitness(N, newTour, distanceArr);
            double deltaF = Math.abs(newFitness - tourFitness);

            // Acceptance criteria
            if (newFitness < tourFitness || Math.exp(-deltaF / T) > rand.nextDouble()) {
                tour = newTour;
                tourFitness = newFitness;
            }

            // Cool down the temperature
            T *= lambda;
            count++;

            if (count % 10000 == 0) {
                System.out.println("Iteration " + count + " - Fitness: " + tourFitness + " - Temperature: " + T);
            }
        }

        System.out.println("\nFinal tour: " + tour);
        System.out.println("Final tour fitness: " + tourFitness);
        System.out.println("\nIterations: " + count);
    }
    
}
