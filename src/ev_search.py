import pandas as pd
from pymongo import MongoClient
from geojson import Point
from flask import Flask


# Connect to MongoDB
search_client = MongoClient('mongodb://localhost:27017/')
db = search_client['ev_station_db']
collection = db['ev_stations']

# Define the search parameters
longitude = -122.4085  # Replace with the desired longitude
latitude = 37.7909    # Replace with the desired latitude
radius_meters = 15000  # Specify the radius in meters for your search

longitude=-118.271387
latitude=34.040539
radius_meters = 15000

# Create a GeoJSON Point with the coordinates
location = Point((longitude, latitude))

# Ensure that a 2dsphere index is created for the 'location' field
collection.create_index([("location", "2dsphere")])

# Perform the geo-spatial query to find stations within the specified radius
result = collection.find({
    "location": {
        "$nearSphere": {
            "$geometry": location,
            "$maxDistance": radius_meters
        }
    }
})

# Loop through the result and print the matching records
for record in result:
    print(record)

# Close the MongoDB connection
search_client.close()
