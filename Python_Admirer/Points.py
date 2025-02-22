import random
import math

def generate_points(n):
    points = [(random.randint(-90, 100), random.randint(-90, 100)) for _ in range(n)]
    with open("Points.txt", "w") as file:
        for x, y in points:
            file.write(f"{x}, {y}\n")
    return points

def distance(p1, p2):
    return math.sqrt((p1[0] - p2[0]) ** 2 + (p1[1] - p2[1]) ** 2)

def merge_sort(points, sort_by_x=True):
    if len(points) <= 1:
        return points
    mid = len(points) // 2
    left = merge_sort(points[:mid], sort_by_x)
    right = merge_sort(points[mid:], sort_by_x)
    return merge(left, right, sort_by_x)

def merge(left, right, sort_by_x):
    merged = []
    i, j = 0, 0
    while i < len(left) and j < len(right):
        if (sort_by_x and left[i][0] <= right[j][0]) or (not sort_by_x and left[i][1] <= right[j][1]):
            merged.append(left[i])
            i += 1
        else:
            merged.append(right[j])
            j += 1
    merged.extend(left[i:])
    merged.extend(right[j:])
    return merged

def brute_force_closest_pair(points):
    min_dist = float('inf')
    for i in range(len(points)):
        for j in range(i + 1, len(points)):
            min_dist = min(min_dist, distance(points[i], points[j]))
    return min_dist

def closest_pair(P_X, P_Y):
    if len(P_X) <= 3:
        return brute_force_closest_pair(P_X)
    mid = len(P_X) // 2
    median = P_X[mid][0]
    P_L_X, P_R_X = P_X[:mid], P_X[mid:]
    P_L_Y, P_R_Y = [], []
    for p in P_Y:
        (P_L_Y if p[0] < median else P_R_Y).append(p)
    delta = min(closest_pair(P_L_X, P_L_Y), closest_pair(P_R_X, P_R_Y))
    slab = [p for p in P_Y if abs(p[0] - median) < delta]
    min_dist = delta
    for i in range(len(slab)):
        for j in range(i + 1, min(i + 7, len(slab))):
            min_dist = min(min_dist, distance(slab[i], slab[j]))
    return min_dist

def main():
    n = int(input("Enter number of points: "))
    points = generate_points(n)
    sorted_x = merge_sort(points, True)
    sorted_y = merge_sort(points, False)
    closest_dist = closest_pair(sorted_x, sorted_y)
    print(f"Closest pair distance: {closest_dist}")
    print(f"Closest pair distance Brute Force: {brute_force_closest_pair(points)}")

if __name__ == "__main__":
    main()

