#Andrew Chien



import mapquest_api
import mapquest_output



def locations_input() -> list:
    '''User types in the number of input locations desired along with the
    locations desired; function returns the locations in a list of str'''
    valid_num = False

    while not valid_num:
        
        try:
            num = int(input())
            if num >= 2:

                valid_num = True

            #user inputs less than 2 locations
            else:
                print('Please enter 2 or more for the number of locations')
                
        #user does not input a number               
        except ValueError:
            print('Please enter a number')
            
    #initializes a list of locations
    locations = []
    for loc in range(num):
        locations.append(input())

    return locations





def commands_output() -> list:
    '''User types in the number of output commands along with the output
    commands desired; function returns them in a list of str'''
    valid_num = False

    while not valid_num:

        try:
            num = int(input())

            if num < 1 or num > 5:
                print('Please enter a number between 1 and 5')

            else:
                valid_num = True
                
        except ValueError:
            print('Please enter a number')
            
    #initializes list of output commands
    commands = []
    for command in range(num):
        commands.append(input())

    return commands






def construct_route(locations: 'list of locations') -> dict:
    '''Takes in a list of locations inputed previously by the user and
    creates a valid URL that will be parsed to return a python dictionary.'''

    #creates a route URL using the list of locations
    url = mapquest_api.construct_route_url(locations)

    try:
        ##turns route URL into a python dictionary
        ##directions_info will equal None if MapQuest Error occurs
        directions_info = mapquest_api.json_to_python(url)
        
        ##checks if the statuscode is an error code (according to the Genocoding API)
        ##if it is, the route is not valid
        if directions_info['info']['statuscode'] in [400, 403, 500]:
                print()
                print('NO ROUTE FOUND')
                directions_info = None

    finally:
        ##value of directions_info is returned whether it is a valid dictionary
        ##or equal to None
        return directions_info
                





def latitude_longitude_list(directions: dict) -> list:
    '''Takes in a dictionary of python route directions and
    returns a list containing latitude and longitude tuples'''
    lat_long_list = []

    #creates a list of lat_long tuples with latitude, longtitude in each element
    for latlong in directions['route']['locations']:
        value = (latlong['displayLatLng']['lat'], latlong['displayLatLng']['lng'])
        lat_long_list.append(value)

    return lat_long_list

    




def construct_elevation_dict(latlong: 'tuple') -> dict:
    '''Takes in a tuple containing latitude and longitude recordings
    that will be parsed to return a python dictionary of elevation.'''
    
    url = mapquest_api.construct_elevation_url(latlong)
    elevation_info = mapquest_api.json_to_python(url)

    #elevation_info equals None if a MapQuest Error is brought up
    return elevation_info

    




def elevation_information(lat_long_list: list) -> None:
    '''Takes in a list of latitude and longitude tuples and prints out the
    elevation information for each location on the list'''

    print('ELEVATIONS')

    for location in lat_long_list:
        #constructs python dictonary of elevation information for every location
        elevation_dict = construct_elevation_dict(location)
        
        #verifies if elevation_dict is a valid python dictionary (no MapQuest Error)
        if elevation_dict != None:
            
            elevation_info = mapquest_output.Elevation(elevation_dict)
            elevation_info.get_elevation(elevation_dict)
            
        #break out of for loop when MapQuest Error is brought up
        else:
            break
        
    print()
    





def construct_directions(commands: list, directions: dict, lat_long_list: list) -> None:
    '''Takes in a list of output commands desired by the user, the directions
    dictionary, and list of latitude and longitude tuples and prints out
    the corresponding results according to the output commmands'''

    for com in commands:
        
        if com == 'STEPS':
            direction_info = mapquest_output.Steps(directions)
            direction_info.get_directions(directions)

        elif com == 'TOTALDISTANCE':
            distance_info = mapquest_output.Total_Distance(directions)
            distance_info.get_distance(directions)

        elif com == 'TOTALTIME':
            time_info = mapquest_output.Total_Time(directions)
            time_info.get_time(directions)

        elif com == 'LATLONG':
            latlong_info = mapquest_output.Lat_Long(directions)
            latlong_info.get_latitude_longitude(directions)

        elif com == 'ELEVATION':
            elevation_information(lat_long_list)

        #command is ignored if it is not equal to any of the commands above
        else:
            pass






def user_interface() -> None:
    '''Runs the user interface of MapQuest'''
    user_input = locations_input()
    user_output = commands_output()

    directions = construct_route(user_input)

    #checks if directions is a valid python dictionary
    if directions != None:
        lat_long_list = latitude_longitude_list(directions)
        construct_directions(user_output, directions, lat_long_list)

    print('Directions Courtesy of MapQuest; Map Data Copyright OpenStreetMap Contributors')               
        





if __name__ == '__main__':
    user_interface()
    


       
    
