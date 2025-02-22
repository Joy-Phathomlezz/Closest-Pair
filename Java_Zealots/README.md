README.md
# Documentation for Points.java

## Class: `Point`

### Description
Represents a 2D point with integer coordinates `(x, y)`.

### Constructor
```java
public Point(int x, int y)
```
**Parameters:**
- `x`: X-coordinate of the point.
- `y`: Y-coordinate of the point.

### Methods
#### `toString`
```java
@Override
public String toString()
```
Returns a string representation of the point in the format `"(x, y)"`.

---

## Class: `Points`

### Description
A collection of `Point` objects, extending `ArrayList<Point>`.

### Constructors

#### Default Constructor
```java
public Points()
```
Creates an empty list of points.

#### Constructor with List
```java
public Points(List<Point> list)
```
**Parameters:**
- `list`: A list of `Point` objects to initialize the collection.

#### Constructor with Random Points
```java
public Points(int n)
```
**Parameters:**
- `n`: Number of random points to generate.

Generates `n` random points with x and y values in the range `[-90, 100]` and saves them to `Points.txt`.

### Methods

#### `addPoint`
```java
public void addPoint(int x, int y)
```
**Parameters:**
- `x`: X-coordinate of the new point.
- `y`: Y-coordinate of the new point.

Adds a new `Point` to the list and updates `Points.txt`.

#### `saveToFile`
```java
private void saveToFile(String filename)
```
**Parameters:**
- `filename`: Name of the file to save points to.

Writes all points to the specified file.

#### `sortPoints`
```java
public void sortPoints()
```
Reads points from `Points.txt`, sorts them by `x` and `y` coordinates using merge sort, and saves sorted lists to `SortX.txt` and `SortY.txt`.

#### `saveSortedToFile`
```java
private void saveSortedToFile(String filename, List<Point> sortedList)
```
**Parameters:**
- `filename`: Name of the file to save sorted points to.
- `sortedList`: List of sorted `Point` objects.

#### `mergeSort`
```java
private void mergeSort(List<Point> list, int left, int right, boolean sortByAxis)
```
**Parameters:**
- `list`: List of points to sort.
- `left`: Left index.
- `right`: Right index.
- `sortByAxis`: `true` for sorting by x, `false` for sorting by y.

Sorts points using merge sort algorithm.

#### `merge`
```java
private void merge(List<Point> list, int left, int mid, int right, boolean sortByAxis)
```
**Parameters:**
- `list`: List of points.
- `left`: Left index.
- `mid`: Middle index.
- `right`: Right index.
- `sortByAxis`: `true` for sorting by x, `false` for sorting by y.

Merges two sorted halves of a list.

#### `distance`
```java
public static int distance(Point a, Point b)
```
**Parameters:**
- `a`: First point.
- `b`: Second point.

Returns the Euclidean distance between `a` and `b`.

#### `computeMedian`
```java
public static int computeMedian(Points sortedAxis)
```
**Parameters:**
- `sortedAxis`: A sorted list of points.

Returns the median x-coordinate.

#### `closestPair`
```java
public int closestPair(Points P_X, Points P_Y)
```
**Parameters:**
- `P_X`: List of points sorted by x-coordinate.
- `P_Y`: List of points sorted by y-coordinate.

Uses divide and conquer to find the closest pair of points.

#### `accrossPair`
```java
public int accrossPair(Points P_L_Y, Points P_R_Y, int delta, int median)
```
**Parameters:**
- `P_L_Y`: Left partition sorted by y.
- `P_R_Y`: Right partition sorted by y.
- `delta`: Current closest pair distance.
- `median`: Median x-coordinate.

Finds the closest pair of points across the median line.

#### `bruteForceClosestPair`
```java
public static int bruteForceClosestPair(Points points)
```
**Parameters:**
- `points`: A list of points.

Computes the closest pair distance using brute force.

#### `main`
```java
public static void main(String[] args)
```
Prompts user for number of points, generates them, sorts them, and finds the closest pair using both divide and conquer and brute force methods.

---

## Java Collections Framework Links

- [ArrayList](<https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/ArrayList.html>)

- [List](<https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/List.html>)

- [BufferedWriter](<https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/BufferedWriter.html>)

- [FileWriter](<https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/FileWriter.html>)

- [BufferedReader](<https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/BufferedReader.html>)

- [FileReader](<https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/FileReader.html>)

- [ThreadLocalRandom](<https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/ThreadLocalRandom.html>)

