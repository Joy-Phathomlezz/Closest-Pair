#include <iostream>
#include <vector>
#include <cmath>
#include <fstream>
#include <sstream>
#include <algorithm>
#include <limits>
#include <random>

using namespace std;

struct Point {
    int x, y;
    Point(int x, int y) : x(x), y(y) {}
};

typedef vector<Point> Points;

int distance(const Point& a, const Point& b) {
    return sqrt(pow(a.x - b.x, 2) + pow(a.y - b.y, 2));
}

void saveToFile(const string& filename, const Points& points) {
    ofstream file(filename);
    if (file.is_open()) {
        for (const auto& p : points) {
            file << p.x << ", " << p.y << "\n";
        }
        file.close();
    } else {
        cerr << "Error writing to file: " << filename << endl;
    }
}

Points loadFromFile(const string& filename) {
    Points points;
    ifstream file(filename);
    if (file.is_open()) {
        string line;
        while (getline(file, line)) {
            stringstream ss(line);
            int x, y;
            char comma;
            ss >> x >> comma >> y;
            points.emplace_back(x, y);
        }
        file.close();
    } else {
        cerr << "Error reading file: " << filename << endl;
    }
    return points;
}

void mergeSort(Points& points, int left, int right, bool sortByX) {
    if (left >= right) return;
    int mid = left + (right - left) / 2;
    mergeSort(points, left, mid, sortByX);
    mergeSort(points, mid + 1, right, sortByX);
    
    Points temp;
    int i = left, j = mid + 1;
    while (i <= mid && j <= right) {
        if ((sortByX && points[i].x <= points[j].x) || (!sortByX && points[i].y <= points[j].y)) {
            temp.push_back(points[i++]);
        } else {
            temp.push_back(points[j++]);
        }
    }
    while (i <= mid) temp.push_back(points[i++]);
    while (j <= right) temp.push_back(points[j++]);
    
    for (int k = left; k <= right; ++k) {
        points[k] = temp[k - left];
    }
}

int computeMedian(const Points& sortedPoints) {
    return sortedPoints[sortedPoints.size() / 2].x;
}

int closestPair(Points& P_X, Points& P_Y);

int acrossPair(Points& P_L_Y, Points& P_R_Y, int delta, int median) {
    int left_bound = median - delta, right_bound = median + delta;
    Points slabLeft, slabRight;
    
    for (const auto& p : P_L_Y) {
        if (p.x >= left_bound && p.x < median) slabLeft.push_back(p);
    }
    for (const auto& p : P_R_Y) {
        if (p.x >= median && p.x <= right_bound) slabRight.push_back(p);
    }
    
    int minDist = delta;
    int j = 0;
    for (const auto& p : slabLeft) {
        int low_bound = p.y - delta, up_bound = p.y + delta;
        while (j < slabRight.size() && slabRight[j].y < low_bound) j++;
        int k = j;
        while (k < slabRight.size() && slabRight[k].y <= up_bound) {
            minDist = min(minDist, distance(p, slabRight[k]));
            k++;
        }
    }
    return minDist;
}

int closestPair(Points& P_X, Points& P_Y) {
    if (P_X.size() <= 1) return numeric_limits<int>::max();
    if (P_X.size() == 2) return distance(P_X[0], P_X[1]);
    
    int median = computeMedian(P_X);
    Points P_L_X(P_X.begin(), P_X.begin() + P_X.size() / 2);
    Points P_R_X(P_X.begin() + P_X.size() / 2, P_X.end());
    
    Points P_L_Y, P_R_Y;
    for (const auto& p : P_Y) {
        (p.x < median ? P_L_Y : P_R_Y).push_back(p);
    }
    
    int dLeft = closestPair(P_L_X, P_L_Y);
    int dRight = closestPair(P_R_X, P_R_Y);
    int delta = min(dLeft, dRight);
    
    return min(delta, acrossPair(P_L_Y, P_R_Y, delta, median));
}

int bruteForceClosestPair(Points& points) {
    int minDist = numeric_limits<int>::max();
    for (size_t i = 0; i < points.size(); i++) {
        for (size_t j = i + 1; j < points.size(); j++) {
            minDist = min(minDist, distance(points[i], points[j]));
        }
    }
    return minDist;
}

int main() {
    int n;
    cout << "Enter number of points: ";
    cin >> n;
    
    random_device rd;
    mt19937 gen(rd());
    uniform_int_distribution<> dist(-90, 100);
    
    Points points;
    for (int i = 0; i < n; i++) {
        points.emplace_back(dist(gen), dist(gen));
    }
    
    saveToFile("Points.txt", points);
    mergeSort(points, 0, points.size() - 1, true);
    saveToFile("SortX.txt", points);
    mergeSort(points, 0, points.size() - 1, false);
    saveToFile("SortY.txt", points);
    
    Points sortedX = loadFromFile("SortX.txt");
    Points sortedY = loadFromFile("SortY.txt");
    
    int closestDistance = closestPair(sortedX, sortedY);
    cout << "Closest pair distance: " << closestDistance << endl;
    
    int bruteForceDist = bruteForceClosestPair(points);
    cout << "Closest pair distance (Brute Force): " << bruteForceDist << endl;
    
    return 0;
}

