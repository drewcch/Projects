#Andrew Chien

class Steps:
    def __init__(self, trip: dict):
        self._trip = trip


    def get_directions(self, trip: dict):
        #prints the maneuvers needed along the path from the first to the last location
        print('DIRECTIONS')
        for directions in trip['route']['legs']:
            for steps in directions['maneuvers']:
                print(steps['narrative'])

        print()
    


class Total_Distance:
    def __init__(self, trip: dict):
        self._trip = trip

        
    def get_distance(self, trip: dict):
        #prints total distance of the trip from the first to the last location 
        total_distance = round(trip['route']['distance'])
        print('TOTAL DISTANCE: ' + str(total_distance) + ' miles')
        print()
        


class Total_Time:
    def __init__(self, trip: dict):
        self._trip = trip
        

    def get_time(self, trip: dict):
        #prints total time of the trip from the first to the last location
        total_time = round(trip['route']['time']/60)
        print('TOTAL TIME: ' + str(total_time) + ' minutes')
        print()
        


class Lat_Long:
    def __init__(self, trip: dict):
        self._trip = trip


    def get_latitude_longitude(self, trip: dict):
        #prints latitude and longitude for each location in the trip
        print('LATLONGS')
        for location in trip['route']['locations']:
            latitude = round(location['displayLatLng']['lat'], 2)
            longitude = round(location['displayLatLng']['lng'], 2)

            if latitude < 0:
                lat = str(abs(latitude))
                print(lat + 'S', end = ' ')

            else:
                print(str(latitude) + 'N', end = ' ')
                
            if longitude < 0:
                long = str(abs(longitude))
                print(long + 'W')

            else:
                print(str(longitude) + 'E')
        print()
                


class Elevation:
    def __init__(self, elevation: dict):
        self._elevation = elevation


    def get_elevation(self, lat_long: dict):
        #prints elevation for a single location in the trip
        for location in lat_long['elevationProfile']:
            elevation = round(location['height'])
            print(elevation)


        
            
            

        
    
