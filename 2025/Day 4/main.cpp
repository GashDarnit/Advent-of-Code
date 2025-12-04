#include <iostream>
#include <fstream>
#include <unordered_set>
#include <vector>
#include <chrono>

using namespace std;

struct pair_hash {
    size_t operator()(const std::pair<int,int>& p) const {
        size_t h1 = std::hash<int>()(p.first);
        size_t h2 = std::hash<int>()(p.second);
        return h1 * 31 + h2;
    }
};

bool in_range(pair<int, int> pos, int n) {
    return ((pos.first >= 0 && pos.first < n) && (pos.second >= 0 && pos.second < n));
}

long part_one(vector<string> map) {
    long total = 0;
    int size = map.size();

    for(int i = 0; i < size; i++) {
        for(int j = 0; j < size; j++) {
            if(map[i][j] == '.') continue;
            int paper_rolls = 0;
            pair<int, int> n = {i - 1, j}, s = {i + 1, j}, w = {i, j - 1}, e = {i, j + 1}, nw = {i - 1, j - 1}, ne = {i - 1, j + 1}, sw = {i + 1, j - 1}, se = {i + 1, j + 1};

            for(pair<int, int> dir : {n, s, w, e, nw, ne, sw, se}) {
                if(!in_range(dir, size)) continue;
                if(map[dir.first][dir.second] == '@') paper_rolls++;
            }
            if(paper_rolls < 4) total++;
        }
    }
    return total;
}

long part_two(vector<string> map) {
    long total = 0;
    int size = map.size();

    while(true) {
        bool access = false;
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(map[i][j] == '.') continue;
                int paper_rolls = 0;
                pair<int, int> n = {i - 1, j}, s = {i + 1, j}, w = {i, j - 1}, e = {i, j + 1}, nw = {i - 1, j - 1}, ne = {i - 1, j + 1}, sw = {i + 1, j - 1}, se = {i + 1, j + 1};

                for(pair<int, int> dir : {n, s, w, e, nw, ne, sw, se}) {
                    if(!in_range(dir, size)) continue;
                    if(map[dir.first][dir.second] == '@') paper_rolls++;
                }
                if(paper_rolls < 4) {
                    total++;
                    map[i][j] = '.';
                    access = true;
                }
            }
        }
        if(!access) break;
    }
    return total;
}

long optimized_part_two(vector<string> map) {
    long total = 0;
    int size = map.size();
    unordered_set<pair<int, int>, pair_hash> papers;
    for(int i = 0; i < size; i++)
        for(int j = 0; j < size; j++)
            if(map[i][j] == '@') papers.insert({i, j});
    
    while(true) {
        bool access = false;


        if(!access) break;
    }

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
