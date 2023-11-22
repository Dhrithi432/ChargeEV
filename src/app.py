import math
from flask import Flask, request, jsonify, g
from pymongo import MongoClient
from geojson import Point
from flask_cors import CORS 
from flask import Flask
import requests

# Create a Flask application
app = Flask(__name__)
CORS(app)
CORS(app, resources={r"/api/*": {"origins": "http://localhost:4200"}})


# Define the MongoDB client as a global variable
client =  MongoClient('mongodb://localhost:27017/')

def get_Lat_Long(zipcode):
    print('making API call to Google for zipcode', zipcode)
    url = f"https://maps.googleapis.com/maps/api/geocode/json?address={zipcode}&key=AIzaSyASXpcjSKFL5Q50cxYcyNc-xRlHTrFp5EA"
    response = requests.get(url)
    if response.status_code == 200:
        data = response.json()
        if data['results']:
            latitude = data['results'][0]['geometry']['location']['lat']
            longitude = data['results'][0]['geometry']['location']['lng']
            return latitude, longitude
    return None, None

#Google api for location?

# Define the /search endpoint route
@app.route('/search', methods=['GET'])
def search_stations():
    zipcode = request.args.get('zipcode')
    latitude, longitude = get_Lat_Long(zipcode)
    print('searching coordinates for ZIP', zipcode)
    print(latitude, longitude)
    radius_meters = 20000  # Specify the radius in meters for your search
    if latitude is None or longitude is None:
        return jsonify({"error": "Unable to find location for the given zipcode"}), 400
    

    # Use the 'client' global variable to access the MongoDB connection
    db = client['ev_station_db']
    collection = db['ev_stations']

    # Create a GeoJSON Point with the coordinates
    location = Point((longitude, latitude))

    # Ensure that a 2dsphere index is created for the 'location' field
    collection.create_index([("location", "2dsphere")])

    # Perform the geo-spatial query to find stations within the specified radius
    result = collection.find({
        "ZIP": zipcode,
        "location": {
            "$nearSphere": {
                "$geometry": location,
                "$maxDistance": radius_meters
            }
        }
    })

    # Prepare the response data
    stations = []
    for record in result:
        station_info = {
            "City": record["City"],
            "State": record["State"],
            "ZIP": record["ZIP"],
            "Latitude": record["Latitude"],
            "Longitude": record["Longitude"],
            "Station_Name": record["Station Name"],
            "Num_Level_2": None if math.isnan(record["EV Level2 EVSE Num"]) else record["EV Level2 EVSE Num"],
            "Num_level_1": None if math.isnan(record["EV Level1 EVSE Num"]) else record["EV Level1 EVSE Num"]
        }
        stations.append(station_info)

    return jsonify(stations)


# Run the Flask application
if __name__ == '__main__':
    try:
        app.run(debug=True, host='0.0.0.0', port=5001)
    finally:
        client.close()