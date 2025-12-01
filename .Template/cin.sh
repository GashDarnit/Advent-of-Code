#!/usr/bin/env bash

# Exit if any command fails
set -e

if [ $# -ne 2 ]; then
    echo "Usage: $0 <year> <day>"
    exit 1
fi

year="$1"
day="$2"

cookie="brainfarted;"

script_dir="$(cd "$(dirname "$0")" && pwd)"

base_dir="$(cd "$script_dir/.." && pwd)"
day_dir="$base_dir/$year/Day $day"

# Make directories if they don't exist
mkdir -p "$day_dir"

# Fetch AoC input
curl -s -H "Cookie: session=$cookie" "https://adventofcode.com/$year/day/$day/input" -o "$day_dir/input.txt"

# Copy template files
cp "$script_dir/Solution.cpp" "$day_dir/Solution.cpp"
cp "$script_dir/run.sh" "$day_dir/run.sh"

echo "Setup complete: $day_dir"

