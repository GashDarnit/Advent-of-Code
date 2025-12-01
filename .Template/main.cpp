#include <iostream>
#include <fstream>
#include <vector>
#include <chrono>

using namespace std;

long part_one(vector<string> input) {
    return 0;
}

long part_two(vector<string> input) {
    return 0;
}

int main() {
    using std::chrono::high_resolution_clock;
    using std::chrono::duration_cast;
    using std::chrono::duration;
    using std::chrono::milliseconds;

    vector<string> input;

    ifstream f("input.txt");
    string s;
    while (getline(f, s)) 
        if(!s.empty()) input.push_back(s);
    f.close();
    
    auto t1 = high_resolution_clock::now();
    long partOne = part_one(input); // Solve Part One here
    cout << "Part One: " << partOne << endl;

    auto t2 = high_resolution_clock::now();
    duration<double, std::milli> ms_double = t2 - t1;
    cout << "--- Part One: "<< ms_double.count() << "ms ---\n";

    long partTwo = part_two(input); // Solve Part Two here
    cout << "Part Two: " << partTwo << endl;
    auto t3 = high_resolution_clock::now();
    duration<double, std::milli> ms_double_two = t3 - t2;

    cout << "--- Part Two: "<< ms_double_two.count() << "ms ---\n";

}
