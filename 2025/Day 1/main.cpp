#include <cstdlib>
#include <iostream>
#include <fstream>
#include <vector>
#include <chrono>
#include <utility>

using namespace std;

long part_one(vector<pair<char, int>> input) {
    long total = 0;
    int cur = 50, n = 100;
    for(pair<char, int> dir : input) {
        cur += dir.second;
        while(cur < 0 || cur > 99) {
            if(cur < 0) cur = n + cur;
            else if(cur > 99) cur = cur - n;
        }
        if(cur == 0) total++;
    }

    return total;
}

// Brute force because nothing sticks for some reason aaaaaaa
long part_two(vector<pair<char, int>> input) {
    long total = 0;
    int cur = 50;
    for(pair<char, int> dir : input) {
        bool pos = (dir.first == 'R') ? true : false;
        for(int i = 0; i < abs(dir.second); i++) {
            if(cur == 0) total++;

            if(pos) cur++;
            else cur--;

            if(cur < 0) cur = 99;
            else if(cur > 99) cur = 0;
        }
    }

    return total;
}

int main() {
    using std::chrono::high_resolution_clock;
    using std::chrono::duration_cast;
    using std::chrono::duration;
    using std::chrono::milliseconds;

    vector<string> raw;

    ifstream f("input.txt");
    string s;
    while (getline(f, s)) 
        if(!s.empty()) raw.push_back(s);
    f.close();

    vector<pair<char, int>> input;
    for(string i : raw) {
        char dir = i[0];
        int val = stoi(i.substr(1, i.length() - 1));
        if(dir == 'L') val *= -1;
        input.push_back({dir, val});
    }

    
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
