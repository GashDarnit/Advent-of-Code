#include <iostream>
#include <fstream>
#include <vector>
#include <chrono>
#include <sstream>
#include <utility>
#include <cmath>

using namespace std;

vector<string> split_string(string str, char delimiter) {
    vector<string> strings;
    stringstream ss(str);
    string t;
    while (getline(ss, t, delimiter)) strings.push_back(t);
    
    return strings;
}

bool valid(string str) {
    int l = 0, r = str.length() / 2;
    if(str[l] == '0') return false;
    while(r < str.length()) {
        if(str[l] != str[r]) return false;
        l++;
        r++;
    }
    return true;
}

bool valid_part_two(string str) {
    int n = str.length();
    for(int step = 1; step <= n / 2; step++) {
        if(n % step != 0) continue;  // must be perfectly divisible

        string pattern = str.substr(0, step);
        bool is_valid = true;

        for(int i = step; i < n; i += step) {
            if (str.compare(i, step, pattern) != 0) {
                is_valid = false;
                break;
            }
        }
        if(is_valid) return true;
    }
    return false;
}


long long part_one(vector<pair<string, string>> input) {
    long total = 0;
    string cur;
    for(pair<string, string> range : input) {
        cur = range.first;
        while(stol(cur) <= stol(range.second)) {
            if(cur.length() % 2 != 0) {
                cur = to_string(stol(cur) + 1);
                continue;
            }
            if(valid(cur)) total += stol(cur);
            cur = to_string(stol(cur) + 1);
        }
    }

    return total;
}

long long part_two(vector<pair<string, string>> input) {
    long total = 0;
    string cur;
    for(pair<string, string> range : input) {
        cur = range.first;
        while(stol(cur) <= stol(range.second)) {
            if(valid_part_two(cur)) total += stol(cur);
            cur = to_string(stol(cur) + 1);
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
    vector<pair<string, string>> input;

    ifstream f("input.txt");
    string s;
    while (getline(f, s)) 
        if(!s.empty()) raw = split_string(s, ',');
    f.close();

    for(string i : raw) {
        if(i.length() > 0) {
            vector<string> data = split_string(i, '-');
            input.push_back({data[0], data[1]});
        }
        
    }

    auto t1 = high_resolution_clock::now();
    long long partOne = part_one(input); // Solve Part One here
    cout << "Part One: " << partOne << endl;

    auto t2 = high_resolution_clock::now();
    duration<double, std::milli> ms_double = t2 - t1;
    cout << "--- Part One: "<< ms_double.count() << "ms ---\n";

    long long partTwo = part_two(input); // Solve Part Two here
    cout << "Part Two: " << partTwo << endl;
    auto t3 = high_resolution_clock::now();
    duration<double, std::milli> ms_double_two = t3 - t2;

    cout << "--- Part Two: "<< ms_double_two.count() << "ms ---\n";

}
