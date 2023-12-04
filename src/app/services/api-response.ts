// src/app/models/api-response.ts

export interface ApiResponse {
    lng: number;
    lat: number;
    City: string;
    Latitude: number;
    Longitude: number;
    Num_Level_2: number;
    Num_level_1: number | null;
    State: string;
    Station_Name: string;
    Address: string;
    ZIP: string;
    id?: string; // Optional property for station ID
    // Add other properties as needed from your API response
}
