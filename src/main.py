import pandas as pd
from pymongo import MongoClient
from geojson import Point
from flask import Flask

# Connect to MongoDB
conn = MongoClient('mongodb://localhost:27017/')
db = conn['ev_station_db']
collection = db['ev_stations']

# Read CSV data
csv_file_path = 'data/ev_stations.csv'
df = pd.read_csv(csv_file_path)

# Loop through each record in the CSV
for index, row in df.iterrows():
    # Extract latitude and longitude from your CSV columns 
    latitude = float(row['Latitude'])
    longitude = float(row['Longitude'])
    
    # Create a GeoJSON Point with the coordinates
    location = Point((longitude, latitude))
    
    # Modify the record to include the 'location' field
    record = row.to_dict()
    record['location'] = location
    
    # Insert the modified record into MongoDB
    collection.insert_one(record)

# Close the MongoDB connection
conn.close()
