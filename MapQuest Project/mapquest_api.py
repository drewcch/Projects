#Andrew Chien



import json
import urllib.parse
import urllib.request
import urllib.error



MAPQUEST_API_KEY = 'pmr4dBer8p3xztnO4EQ0xBF3GGKWPz6x'

BASE_MAPQUEST_URL = 'http://open.mapquestapi.com/directions/v2'

BASE_ELEVATION_URL = 'http://open.mapquestapi.com/elevation/v1/profile'



def json_to_python(url: str) -> dict:
    '''Takes in a URL str and returns a python dictionary that represents
    the parsed json response'''
    response = None

    try:
        response = urllib.request.urlopen(url)
        json_response = response.read().decode(encoding = 'utf-8')

        return json.loads(json_response)

    except (urllib.error.HTTPError, urllib.error.URLError):
        #invalid API Key/URL entered or MapQuest was down
        print()
        print('MAPQUEST ERROR')

    finally:
        if response != None:
            response.close()





def construct_route_url(route_query: list) -> str:
    '''Constructs the route url using a list of locations inputed by the user'''
    query_parameters = []
    starting_location = True

    for location in route_query:

        #checks if the location is the first location in the list
        if starting_location:
            query_parameters.append(('from', location))
            starting_location = False
            
        else:
            query_parameters.append(('to', location))

    return BASE_MAPQUEST_URL + '/route?key=' + MAPQUEST_API_KEY + '&' + urllib.parse.urlencode(query_parameters) 






def construct_elevation_url(location: 'tuple') -> str:
    '''Constructs the elevation url that will be used for MapQuest'''

    #location contains latitude, longitude in a tuple form, representing 1 location
    elevation_parameters = (str(location[0]) + ',' + str(location[1]))
        

    return BASE_ELEVATION_URL + '?key=' + MAPQUEST_API_KEY + '&shapeFormat=raw&latLngCollection=' + elevation_parameters
        
    

    

    
    

