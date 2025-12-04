#include <algorithm>
#include <iostream>
#include <fstream>
#include <utility>
#include <vector>
#include <chrono>

using namespace std;

pair<int, int> get_max_and_idx(string window) {
    pair<int, int> max_and_idx = {window[0] - '0', 0};

    for(int i = 0; i < window.length(); i++) {
        if(window[i] - '0' > max_and_idx.first) max_and_idx = {window[i] - '0', i};
        else if(window[i] - '0' == max_and_idx.first && i < max_and_idx.second) max_and_idx = {window[i] - '0', i};
    }
    return max_and_idx;
}

long get_max_joltage(string bank, int K) {
    int n = bank.length(), R = n, start = 0;
    long cur = 0;
    while(K > 0) {
        int w_size = R - K + 1;
        string window = bank.substr(start, w_size);
        pair<int, int> max_and_idx = get_max_and_idx(window);

        cur = (cur * 10) + max_and_idx.first;
        R = n - start - max_and_idx.second - 1;
        K--;
        start = start + max_and_idx.second + 1;
    }
    return cur;
}

long part_one(vector<string> input) {
    long total = 0;
    for(string bank : input) total += get_max_joltage(bank, 2);
    return total;
}

long part_two(vector<string> input) {
    long total = 0;
    for(string bank : input) total += get_max_joltage(bank, 12);
    return total;
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
