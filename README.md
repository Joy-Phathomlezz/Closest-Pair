# Closest Pair Algorithm 

## Overview
The **Closest Pair Algorithm** finds the two points in a given set that are closest to each other. It is widely used in computational geometry, geographical mapping, and clustering applications.

This implementation follows a **divide and conquer** approach to optimize performance compared to the brute force method.

---

## **Logical Procedure**

### **1️⃣ Representation of Points**
- Each point consists of two coordinates: `(x, y)`.
- A collection of points is stored in a **list or array**.

### **2️⃣ Input Handling**
- The user provides the **number of points**.
- If the points are generated randomly, their values are constrained within a fixed range.
- The generated points are stored in a file or memory for processing.

### **3️⃣ Sorting the Points**
- Two sorted lists of points are created:
  - One sorted by **X-coordinates** (`P_X`)
  - One sorted by **Y-coordinates** (`P_Y`)
- **Merge Sort** is used for sorting to ensure an efficient time complexity of `O(n log n)`.

### **4️⃣ Closest Pair Calculation (Divide & Conquer Approach)**
#### **Base Cases:**
- If the number of points is **1**, return an infinite distance (no pair exists).
- If there are **2 points**, return their Euclidean distance.
- If there are **3 points**, compute all distances using brute force and return the minimum.

#### **Recursive Division:**
1. **Find the Median** of `P_X` to divide the points into two halves:
   - Left set: `P_L_X, P_L_Y`
   - Right set: `P_R_X, P_R_Y`
2. Recursively compute the **minimum distance** in both halves.
3. Let **`delta`** be the minimum of the two distances.
4. Consider a **strip** around the median line of width `2 * delta`.
5. Find the closest pair **across the two halves** within this strip using the **Sliding Window approach**:
   - **Filter points** into two subsets (`slab Left` and `slab Right`), ensuring that only points within `delta` of the median are included.
   - **Use a two-pointer sliding window** to efficiently scan and compare points from `slab Left` and `slab Right`, reducing redundant comparisons.
   - The sliding window ensures that only relevant points in a limited vertical range are considered, improving efficiency.
6. The **final answer** is the minimum of:
   - The closest pair distance in the left half.
   - The closest pair distance in the right half.
   - The closest pair across the median strip.

### **5️⃣ Brute Force Closest Pair (Fallback Method)**
If the dataset is small (`<= 3 points`), the algorithm uses brute force:
- Compute **all possible pairwise distances**.
- Select the **minimum** distance.
- Time complexity: `O(n^2)`.

### **6️⃣ Sorting & Output Storage**
- The sorted points are written to separate files/lists (`SortX`, `SortY`).
- The **minimum closest pair distance** is printed as output.
- The brute force result is also calculated for comparison.

---

## **Complexity Analysis**
| Approach | Time Complexity |
|----------|----------------|
| **Brute Force** | `O(n^2)` |
| **Divide & Conquer** | `O(n log n)` |
| **Sorted Points with Divide & Conquer** | `O(n)` |

---
## Use Casses
- **finding nearest cities, stores, etc**
- **image recognition, clustering**
- **shortest route selection**


