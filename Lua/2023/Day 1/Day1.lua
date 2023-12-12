local file = io.open("input.txt", "r")
local lines = {}

map = {}


while true do
    local line = file:read()
    
    if not line then break end
    
    lines[#lines + 1] = line
end

function partOne()
    total = 0
    
    for i = 1, #lines, 1 do
        local current = lines[i]
        local reversed = string.reverse(current)
        local currentInt = 0
                
        for char in current:gmatch(".") do
            if char >= "0" and char <= "9" then
                currentInt = tonumber(char) * 10
                break
            end
        end
        
        for char in reversed:gmatch(".") do
            if char >= "0" and char <= "9" then
                currentInt = currentInt + tonumber(char)
                break
            end
        end
        
        total = total + currentInt        
    end
    
    return total
end

function partTwo()
    loadMap()
    local total = 0
    
    for i = 1, #lines, 1 do
        local currentInt = 0
        local newString = getModifiedString(lines[i])
        local reverseString = string.reverse(newString)
        
         for char in newString:gmatch(".") do
             if(char >= "0" and char <= "9") then
                 currentInt = tonumber(char) * 10
                 break
             end
         end
         
         for char in reverseString:gmatch(".") do
             if(char >= "0" and char <= "9") then
                 currentInt = currentInt + tonumber(char)
                 break
             end
         end
         
         total = total + currentInt
         
    end
    
    return total
end

function getModifiedString(string)
    local newString = ""
    for key, value in pairs(map) do
        string = string:gsub(key, value)
    end
    
    return string
end


function loadMap()
    map["one"] = "o1e"
    map["two"] = "t2o"
    map["three"] = "t3e"
    map["four"] = "f4r"
    map["five"] = "f5e"
    map["six"] = "s6x"
    map["seven"] = "s7n"
    map["eight"] = "e8t"
    map["nine"] = "n9e"
end

function main() 
    print("Part One: " .. partOne())
    print("Part Two: " .. partTwo())
end

main()