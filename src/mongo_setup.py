from pymongo import MongoClient
import json
from flask import Flask

def setup_mongodb():
    # Read the schema definition from the schema.json file
    with open("schemas/schema.json", "r") as schema_file:
        schema = json.load(schema_file)

    # Connect to MongoDB
    client = MongoClient("mongodb://localhost:27017")

    # Create the database if it doesn't exist
    db = client[schema["database"]]

    # Create collections based on the schema
    for collection_info in schema["collections"]:
        collection_name = collection_info["name"]
        db.create_collection(collection_name)
        print(f"Created collection: {collection_name}")

    print("MongoDB setup completed.")

