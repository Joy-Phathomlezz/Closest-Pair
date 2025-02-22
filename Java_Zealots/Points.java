import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

class Points extends ArrayList<Point> {
    public Points() {
        super();
    }

    public Points(List<Point> list) {
        super();
        this.addAll(list);
    }

    public Points(int n) {
        super();
        for (int i = 0; i < n; i++) {
            int x = ThreadLocalRandom.current().nextInt(-90000, 100000);
            int y = ThreadLocalRandom.current().nextInt(-90000, 100000);
            this.add(new Point(x, y));
        }
        saveToFile("Points.txt");
    }

    public void addPoint(int x, int y) {
        this.add(new Point(x, y));
        saveToFile("Points.txt");
    }

    private void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Point p : this) {
                writer.write(p.x + ", " + p.y);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void sortPoints() {
        File file = new File("Points.txt");

        List<Point> pointsList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                pointsList.add(new Point(x, y));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        if (pointsList.isEmpty()) {
            System.out.println("Error: No valid points found.");
            return;
        }

        List<Point> sortedByX = new ArrayList<>(pointsList);
        List<Point> sortedByY = new ArrayList<>(pointsList);

        mergeSort(sortedByX, 0, sortedByX.size() - 1, true);
        mergeSort(sortedByY, 0, sortedByY.size() - 1, false);

        saveSortedToFile("SortX.txt", sortedByX);
        saveSortedToFile("SortY.txt", sortedByY);
    }

    private void saveSortedToFile(String filename, List<Point> sortedList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Point p : sortedList) {
                writer.write(p.x + ", " + p.y);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

    }

    private void mergeSort(List<Point> list, int left, int right, boolean sortByAxis) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(list, left, mid, sortByAxis);
            mergeSort(list, mid + 1, right, sortByAxis);
            merge(list, left, mid, right, sortByAxis);
        }
    }

    private void merge(List<Point> list, int left, int mid, int right, boolean sortByAxis) {
        List<Point> leftList = new ArrayList<>(list.subList(left, mid + 1));
        List<Point> rightList = new ArrayList<>(list.subList(mid + 1, right + 1));

        int i = 0, j = 0, k = left;
        while (i < leftList.size() && j < rightList.size()) {
            if ((sortByAxis && leftList.get(i).x <= rightList.get(j).x) ||
                (!sortByAxis && leftList.get(i).y <= rightList.get(j).y)) {
                list.set(k++, leftList.get(i++));
            } else {
                list.set(k++, rightList.get(j++));
            }
        }
        while (i < leftList.size()) {
            list.set(k++, leftList.get(i++));
        }
        while (j < rightList.size()) {
            list.set(k++, rightList.get(j++));
        }
    }

    public static int distance(Point a, Point b) {
        // System.out.println("( "+a.x + ", " + a.y + " ) -- (" + b.x + ", " + b.y+" )");
        // System.err.println();
        return (int) Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    public static int computeMedian(Points sortedAxis) {
        return sortedAxis.get(sortedAxis.size() / 2).x;
    }

    public int closestPair(Points P_X, Points P_Y) {
        if (P_X.size() == 1) {
            return Integer.MAX_VALUE;
        } else if (P_X.size() == 2) {
            return distance(P_X.get(0), P_X.get(1));
        }

        int median = computeMedian(P_X);

        Points P_L_X = new Points(new ArrayList<>(P_X.subList(0, P_X.size() / 2)));
        Points P_R_X = new Points(new ArrayList<>(P_X.subList(P_X.size() / 2, P_X.size())));
        Points P_L_Y = new Points();
        Points P_R_Y = new Points();
        for (Point p : P_Y) {
            if (p.x < median) {
                P_L_Y.add(p);
            } else {
                P_R_Y.add(p);
            }
        }

        // System.out.println("P_L_X: " + P_L_X + "\n" + "P_R_X: " + P_R_X + "\n" + "P_L_Y: " + P_L_Y + "\n" + "P_R_Y: " + P_R_Y);
        int dLeft = closestPair(P_L_X, P_L_Y);
        int dRight = closestPair(P_R_X, P_R_Y);


        int delta = Math.min(dLeft, dRight);
        int dCross = accrossPair( P_L_Y, P_R_Y, delta, median);

        return Math.min(delta, dCross);
    }


    public int accrossPair( Points P_L_Y, Points P_R_Y, int delta, int median) {
        int left_bound = median - delta; 
        int right_bound = median + delta;
    
        Points slabLeft = new Points();
        Points slabRight = new Points();
    
        // Filtering points within the slab range
        for (Point p : P_L_Y) {
            if (p.x >= left_bound && p.x < median) {
                slabLeft.add(p);
            }
        }
    
        for (Point p : P_R_Y) {
            if (p.x >= median && p.x <= right_bound) {
                slabRight.add(p);
            }
        }
    
        int minDist = delta;
    
        // Sliding window approach with two pointers
        int j = 0; // Index for slabRight
        for (int i = 0; i < slabLeft.size(); i++) {
            Point p = slabLeft.get(i);
            int low_bound = p.y - delta;
            int up_bound = p.y + delta;
    
            // Move j to the first point in slabRight within the Y range
            while (j < slabRight.size() && slabRight.get(j).y < low_bound) {
                j++;
            }
    
            int k = j; // Second pointer to check within the Y range
            while (k < slabRight.size() && slabRight.get(k).y <= up_bound) {
                minDist = Math.min(minDist, distance(p, slabRight.get(k)));
                k++;
            }
        }
    
        return minDist;
    }
    
    public static int bruteForceClosestPair(Points points) {
        int minDist = Integer.MAX_VALUE;
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                minDist = Math.min(minDist, distance(points.get(i), points.get(j)));
                // System.out.println( points.get(i) + " and " + points.get(j) + " " +minDist);
            }
        }
        return minDist;
    }

       public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter number of points: ");
            int n = scanner.nextInt();

        Points points = new Points(n);
        // System.out.println(points);

        points.sortPoints();

        Points sortedX = new Points();
        Points sortedY = new Points();
        
        try (BufferedReader readerX = new BufferedReader(new FileReader("SortX.txt"));
             BufferedReader readerY = new BufferedReader(new FileReader("SortY.txt"))) {
            String line;
            while ((line = readerX.readLine()) != null) {
                String[] parts = line.split(", ");
                sortedX.add(new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
            }
            while ((line = readerY.readLine()) != null) {
                String[] parts = line.split(", ");
                sortedY.add(new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
            }
        } catch (IOException e) {
            System.out.println("Error reading sorted files: " + e.getMessage());
        }
        // int median = points.computeMedian(sortedX);
        int closestDistance = points.closestPair(sortedX, sortedY);
        System.out.println("Closest pair distance: " + closestDistance);
        System.err.println();
        int closestDistance1 = Points.bruteForceClosestPair(points);
        System.out.println("Closest pair distance Brute Force: " + closestDistance1);
        scanner.close();
    }

}
