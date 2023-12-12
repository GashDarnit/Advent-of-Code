local file = io.open("input.txt", "r")
local lines = {}

map = {}


while true do
    local line = file:read()
    
    if not line then break end
    
    lines[#lines + 1] = line
end

function resetMapping(mapping, setValue)
    for k, v in pairs(mapping) do
        mapping[k] = setValue
    end
end


function partOne()
    local total = 0
    
    local targetMapping = {}
    targetMapping["red"] = 12
    targetMapping["green"] = 13
    targetMapping["blue"] = 14
    
    local mapping = {}
    mapping["red"] = -1
    mapping["green"] = -1
    mapping["blue"] = -1
    
    for input = 1, #lines, 1 do
        local pattern = "(.-): (.+)"
        local current = lines[input]
        local info = ""
        
        local game, data = current:match(pattern)
        
        if game and data then
            info = data
        end
        
        local currentInt = 0
        local currentColor = {}
        local flag = true
        
        for char in info:gmatch(".") do
            if(char >= "0" and char <= "9") then
                currentInt = (currentInt * 10) + tonumber(char)
                
            elseif char >= "a" and char <= "z" then
                currentColor[#currentColor + 1] = char
                local color = table.concat(currentColor)
                
                if mapping[color] then
                    if mapping[color] < currentInt then
                        mapping[color] = currentInt
                    end
                    
                    currentInt = 0
                    currentColor = {}
                end
                
            elseif char == ";" then
                currentInt = 0
                currentColor = {}
            end
            
        end
        
        for k, v in pairs(mapping) do
            if targetMapping[k] < v then
                flag = false
            end
        end
        
        resetMapping(mapping, -1)
        currentInt = 0
        currentColor = {}
        
        if flag then
            total = total + input
        end
    end
    
    return total
end


function partTwo()
    local total = 0
    
    local mapping = {}
    mapping["red"] = -1
    mapping["green"] = -1
    mapping["blue"] = -1
    
    for input = 1, #lines, 1 do
        local pattern = "(.-): (.+)"
        local current = lines[input]
        local info = ""
        
        local game, data = current:match(pattern)
        
        if game and data then
            info = data
        end
        
        local currentInt = 0
        local currentColor = {}
        
        for char in info:gmatch(".") do
            if(char >= "0" and char <= "9") then
                currentInt = (currentInt * 10) + tonumber(char)
                
            elseif char >= "a" and char <= "z" then
                currentColor[#currentColor + 1] = char
                local color = table.concat(currentColor)
                
                if mapping[color] then
                    if mapping[color] < currentInt then
                        mapping[color] = currentInt
                    end
                    
                    currentInt = 0
                    currentColor = {}
                end
                
            elseif char == ";" then
                currentInt = 0
                currentColor = {}
            end
            
        end
        
        local power = 1
        for k, v in pairs(mapping) do
            power = power * v
        end
        
        total = total + power
        
        resetMapping(mapping, -1)
        currentInt = 0
        currentColor = {}
        
    end
    
    return total
end

function main()
    print("Part One: " .. partOne())
    print("Part Two: " .. partTwo())
end

main()


