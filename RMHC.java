/*
    ==============================
    RANDOM MUTATION HILL CLIMBING
    ==============================
    Overview:
    RMHC is a basic local search algorithm that iteratively improves a solution by making 
    small random changes. It only accepts changes that improve the solution.

    Step-by-Step Process:
        1. Generate an Initial Random Solution (random tour of cities).
        2. Evaluate the Fitness (total distance of the tour).
        3. Perform a Small Change (swap two cities).
        4. Compare Fitness Values:
            If the new solution is better, accept it.
            If not, discard it.
        5. Repeat until a stopping condition is met (e.g., max iterations).
    
    🔹 Weakness: Can get stuck in local optima.
    🔹 Strength: Simple and fast. 
 */

import java.util.ArrayList;

public class RMHC {

    private static HillClimbing HC = new HillClimbing();
    
    public static void main(String[] args) {

        // Simple Hill Climbing algorithm - Random Mutation Hill Climbing (RMHC)
        int N = 48;

        String filename = "C:\\Users\\Bruno Estrada\\OneDrive - Brunel University London\\Year 2 - Resources\\CS2004 - Algorithms and their applications\\Lab 21 - TSP Data\\TSP_"+N+".txt";
        double[][] distanceArr = TSP.ReadArrayFile(filename, " ");                                

        String fileSolution = "C:\\Users\\Bruno Estrada\\OneDrive - Brunel University London\\Year 2 - Resources\\CS2004 - Algorithms and their applications\\Lab 21 - TSP Data\\TSP_"+N+"_OPT.txt";
        ArrayList<Integer> optimalTour = TSP.ReadIntegerFile(fileSolution);
        
        // Find optimal fitness
        double optimalFitness = HC.Fitness(N, optimalTour, distanceArr);
        System.out.println("\nOptimal tour: " + optimalTour);
        System.out.println("Optimal fitness: " + optimalFitness);

        // Create first random tour and find fitness
        ArrayList<Integer> tour = new ArrayList<>(HC.RandPerm(N));
        double tourFitness = HC.Fitness(N, tour, distanceArr);
        System.out.println("\nIntial tour: " + tour);
        System.out.println("Initial tour fitness: " +tourFitness);

        int count = 0;
        int maxIterations = 100000;

        // Iterate until optimal value is equal tour fitness or count reaches max iterations
        while (optimalFitness != tourFitness && count < maxIterations) {

            // Create new tour by swapping two random cities and find fitness
            ArrayList<Integer> newTour = HC.Swap(tour, distanceArr);
            double newFitness = HC.Fitness(N, newTour, distanceArr); 

            // If new fitness is better than previous, set new tour as current tour
            if (newFitness < tourFitness) {
                tour = newTour;
                tourFitness = newFitness;
            }

            // Display for local optima
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
