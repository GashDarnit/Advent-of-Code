#include <iostream>
#include <fstream>
#include <iterator>
#include <queue>
#include <unordered_set>
#include <utility>
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

bool in_range(vector<string> input, pair<int, int> cur) {
    return ((cur.first >= 0 && cur.first <= input.size() - 1) && (cur.second >= 0 && cur.second <= input[0].length() - 1));
}

pair<int, int> get_start_pos(vector<string> input) {
    for(int i = 0; i < input.size(); i++) {
        for(int j = 0; j < input[0].length(); j++) {
            if(input[i][j] == 'S') return {i, j};
        }
    }
    return {-1, -1};
}

long dfs(vector<string> input, vector<vector<long>> &dp, pair<int, int> current) {
    if(current.first == input.size() - 1) return 1;
    if(dp[current.first][current.second] != -1) return dp[current.first][current.second];

    long total = 0;
    pair<int, int> next_pos = {current.first + 1, current.second};

    if(in_range(input, next_pos) && input[next_pos.first][next_pos.second] == '.') total += dfs(input, dp, next_pos);
    else if(in_range(input, next_pos) && input[next_pos.first][next_pos.second] == '^') {
        pair<int, int> left = {next_pos.first, next_pos.second - 1};
        pair<int, int> right = {next_pos.first, next_pos.second + 1};

        if(in_range(input, left)) total += dfs(input, dp, left);
        if(in_range(input, right)) total += dfs(input, dp, right);
    }

    dp[current.first][current.second] = total;
    return total;
}

long part_one(vector<string> input) {
    long total = 0;
    queue<pair<int, int>> queue;
    unordered_set<pair<int, int>, pair_hash> visited;
    pair<int, int> start_pos = get_start_pos(input);
    queue.push(start_pos);

    while(!queue.empty()) {
        bool split = false;
        pair<int, int> current = queue.front();
        queue.pop();

        if(visited.find(current) != visited.end()) continue;
        visited.insert(current);

        pair<int, int> next_pos = {current.first + 1, current.second};
        if(in_range(input, next_pos) && input[next_pos.first][next_pos.second] == '.') {
            queue.push(next_pos);
        } else if(in_range(input, next_pos) && input[next_pos.first][next_pos.second] == '^') {
            pair<int, int> left = {next_pos.first, next_pos.second - 1};
            pair<int, int> right = {next_pos.first, next_pos.second + 1};
            if(in_range(input, left) && input[left.first][left.second] == '.') {
                queue.push(left);
                split = true;
            }
            if(in_range(input, right) && input[right.first][right.second] == '.') {
                queue.push(right);
                split = true;
            }
        }
        if(split) total++;
    }
    return total;
}

long part_two(vector<string> input) {
    vector<vector<long>> dp(input.size(), vector<long>(input[0].size(), 0));
    pair<int, int> start_pos = get_start_pos(input);

    for(int i = 0; i < input.size(); i++) for(int j = 0; j < input[0].length(); j++) dp[i][j] = -1;

    return dfs(input, dp, start_pos);
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
