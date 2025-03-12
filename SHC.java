/*
    =========================
    STOCHASTIC HILL CLIMBING
    =========================
    Overview:
    SHC enhances RMHC by occasionally accepting worse solutions, allowing it to 
    escape local optima.

    Step-by-Step Process:
        1. Generate an Initial Random Solution and evaluate fitness.
        2. Perform a Small Change (swap two cities).
        3. Compare Fitness Values:
            If the new solution is better, accept it.
            If it’s worse, accept it with some probability, based on how bad the change is.
        4. Repeat until a stopping condition is met (e.g., max iterations).
    
    🔹 Weakness: Might still accept too many bad solutions.
    🔹 Strength: Can escape local optima better than RMHC. 
 */

import java.util.ArrayList;
import java.util.Random;

public class SHC {

    private static HillClimbing HC = new HillClimbing();
    
    public static void main(String[] args) {

        int N = 105;
        // The HIGHER the temperature (T) the more random exploration allowed
        double T = 25;
        Random rand = new Random();

        // Read distance array
        String filename = "C:\\Users\\Bruno Estrada\\OneDrive - Brunel University London\\Year 2 - Resources\\CS2004 - Algorithms and their applications\\Lab 21 - TSP Data\\TSP_" + N + ".txt";
        double[][] distanceArr = TSP.ReadArrayFile(filename, " ");   

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

        int count = 0;
        int maxIterations = 100000;

        // Iterate until count reaches max iterations
        while (count < maxIterations) {
            // Perform swap
            ArrayList<Integer> newTour = HC.Swap(tour, distanceArr);
            double newFitness = HC.Fitness(N, newTour, distanceArr);

            // Probabilistic acceptance function to determine whether a new (potentially
            //worse) solution should be accepted.
            double probability = 1 / (1 + Math.exp((newFitness - tourFitness) / T));

            if (newFitness < tourFitness) {
                // Accept better moves
                tour = newTour;
                tourFitness = newFitness;
            } else {
                // Accept worse solution based on probability formula
                if (rand.nextDouble() < probability) {
                    tour = newTour;
                    tourFitness = newFitness;
                    //System.out.println("Accepted worse move at iteration " + count);
                }
            }

            if (count % 10000 == 0) {
                System.out.println("Fitness at " + count + "th iteration: " + tourFitness);
            }

            count++;
        }

        System.out.println("\nFinal tour: " + tour);
        System.out.println("Final tour fitness: " + tourFitness);
        System.out.println("\nIterations: " + count);
    }
    
}
