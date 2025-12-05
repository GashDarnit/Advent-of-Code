#include <algorithm>
#include <iostream>
#include <fstream>
#include <vector>
#include <chrono>
#include <sstream>
#include <utility>

using namespace std;

vector<string> split_string(string str, char delimiter) {
    vector<string> strings;
    stringstream ss(str);
    string t;
    while (getline(ss, t, delimiter)) strings.push_back(t);
    
    return strings;
}

long part_one(vector<pair<long, long>> ranges, vector<long> ingredients) {
    long total = 0;

    for(long ingredient : ingredients) {
        for(pair<long, long> range : ranges) {
            if(ingredient >= range.first && ingredient <= range.second) {
                total++;
                break;
            }
        }
    }

    return total;
}

long part_two(vector<pair<long, long>> ranges) {
    long total = 0;
    vector<pair<long, long>> modified_ranges;
    sort(ranges.begin(), ranges.end());
    
    long current_start = ranges[0].first, current_end = ranges[0].second;
    for(int i = 1; i < ranges.size(); i++) {
        pair<long, long> check_range = ranges[i];
        long check_start = check_range.first, check_end = check_range.second;

        if(ranges[i].first <= current_end) current_end = max(current_end, ranges[i].second); // Overlap at the end of current_end
        else {
            modified_ranges.push_back({current_start, current_end});
            current_start = ranges[i].first;
            current_end = ranges[i].second;
        }
    }
    modified_ranges.push_back({current_start, current_end});
    for(pair<long, long> range : modified_ranges) total += range.second - range.first + 1;
       
    return total;
}

int main() {
    using std::chrono::high_resolution_clock;
    using std::chrono::duration_cast;
    using std::chrono::duration;
    using std::chrono::milliseconds;

    vector<string> raw;
    vector<pair<long, long>> ranges;
    vector<long> ingredients;

    ifstream f("input.txt");
    string s;
    while (getline(f, s)) 
        if(!s.empty()) raw.push_back(s);
    f.close();

    bool switch_type = false;
    for(string i : raw) {
        if(i.find('-') != string::npos) {
            vector<string> range = split_string(i, '-');
            ranges.push_back({stol(range[0]), stol(range[1])});
        } else ingredients.push_back(stol(i));
    }

    auto t1 = high_resolution_clock::now();
    long partOne = part_one(ranges, ingredients); // Solve Part One here
    cout << "Part One: " << partOne << endl;

    auto t2 = high_resolution_clock::now();
    duration<double, std::milli> ms_double = t2 - t1;
    cout << "--- Part One: "<< ms_double.count() << "ms ---\n";

    long partTwo = part_two(ranges); // Solve Part Two here
    cout << "Part Two: " << partTwo << endl;
    auto t3 = high_resolution_clock::now();
    duration<double, std::milli> ms_double_two = t3 - t2;

    cout << "--- Part Two: "<< ms_double_two.count() << "ms ---\n";
}
