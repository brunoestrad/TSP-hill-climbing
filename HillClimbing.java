import java.util.ArrayList;

public class HillClimbing {

    // Random tour generator
    public static ArrayList<Integer> RandPerm(int N) {

        ArrayList<Integer> cities = new ArrayList<Integer>();
        // populate cities array with values 0 to N - 1
        for(int i = 0; i < N; ++i){
            cities.add(i);
        }

        ArrayList<Integer> tour = new ArrayList<Integer>();

        while(cities.size() > 0) {
            // random index from 0 to cities size - 1
            int i = IMP.UI(0, cities.size() - 1);
            // add cities' value to tour list
            tour.add(cities.get(i));
            // remove from cities list
            cities.remove(i);
        }

        return tour;
    }

    // fitness function
    public static double Fitness(int N, ArrayList<Integer> tour, double[][] distanceArr) {

        double s = 0;

        for(int i = 0; i < N - 1; ++i){

            int a = tour.get(i);
            int b = tour.get(i + 1);

            // tour list contains values from 1 to N
            s += distanceArr[a][b];           
        }

        int endCity = tour.get(N - 1);
        int startCity = tour.get(0);

        s += distanceArr[endCity][startCity];

        return s;
    }

    // small change - swap operator
    public static ArrayList<Integer> Swap(ArrayList<Integer> tour, double[][] distanceArr){
        int i = 0, j = 0;
        double bestFitness = Fitness(tour.size(), tour, distanceArr);
        ArrayList<Integer> bestTour = new ArrayList<Integer>(tour);

        while(i == j) {
            i = IMP.UI(0, tour.size() - 1);
            j = IMP.UI(0, tour.size() - 1);
        }

        ArrayList<Integer> newTour = new ArrayList<Integer>(tour);
        int temp = newTour.get(i);
        newTour.set(i, tour.get(j));
        newTour.set(j, temp);

        double newFitness = Fitness(newTour.size(), newTour, distanceArr);

        if (newFitness < bestFitness) {
            return newTour;
        }

        return bestTour;
    }

    // return true if result is the same
    public static boolean CompareSolution(int N, ArrayList<Integer> solution, String filename) {

        ArrayList<Integer> optimalSolution = null;

        try {

            optimalSolution = TSP.ReadIntegerFile(filename);

            for (int i = 0; i < N; ++i){           
                if (!solution.get(i).equals(optimalSolution.get(i))){
                    return false;
                }
            }

        } catch (Exception E) {
            System.out.println("+++CompareSolution: "+E.getMessage());
            return false;
        }
        
        return true;
    }

}
