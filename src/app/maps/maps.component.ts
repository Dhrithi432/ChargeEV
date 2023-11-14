import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

declare const google: any;

interface ApiResponse {
    City: string;
    Latitude: number;
    Longitude: number;
    Num_Level_2: number;
    Num_level_1: number | null; // Assuming it can be null based on NaN value
    State: string;
    Station_Name: string;
    ZIP: string;
}


@Component({
  selector: 'app-maps',
  templateUrl: './maps.component.html',
  styleUrls: ['./maps.component.css']
})
export class MapsComponent implements OnInit {
    zipCode: string = '';
    private map: any;
    private markers: any[] = [];

    constructor(private http: HttpClient) {}

    ngOnInit() {
      this.initMap();
    }

    private initMap(): void {
        this.map = new google.maps.Map(document.getElementById('map'), {
            zoom: 10,
            center: { lat: 37.0902, lng: -95.7129 } // Center of USA
        });
    }

    // displayLocation(): void {
    //     this.clearMarkers();
      
    //     if (this.zipCode) {
    //       this.http.get<ApiResponse[]>(`http://127.0.0.1:5000/search?zipcode=${this.zipCode}`).subscribe(response => {
    //         // Assuming response is an array of objects with Latitude and Longitude
    //         response.forEach(location => {
    //           this.addMarker({ lat: location.Latitude, lng: location.Longitude });
    //         });
      
    //         // Optionally, center the map on the first location
    //         if (response.length > 0) {
    //           this.map.setCenter({ lat: response[0].Latitude, lng: response[0].Longitude });
    //         }
    //       });
    //     } else {
    //       console.error("Please enter a zip code.");
    //     }
    // }

    displayLocation(): void {
        this.clearMarkers();
      
        if (this.zipCode) {
          this.http.get<ApiResponse[]>(`http://127.0.0.1:5000/search?zipcode=${this.zipCode}`).subscribe(response => {
            response.forEach(location => {
              let infoContent = `<div><strong>${location.Station_Name}</strong><br>`;
              infoContent += `City: ${location.City}, ${location.State}<br>`;
              infoContent += `ZIP: ${location.ZIP}<br>`;
              infoContent += `Level 2 Chargers: ${location.Num_Level_2}<br>`;
              infoContent += `Level 1 Chargers: ${location.Num_level_1 ?? 'N/A'}</div>`;
    
              this.addMarker({ lat: location.Latitude, lng: location.Longitude }, infoContent);
            });
      
            if (response.length > 0) {
              this.map.setCenter({ lat: response[0].Latitude, lng: response[0].Longitude });
            }
          });
        } else {
          console.error("Please enter a zip code.");
        }
    }
    

    // private addMarker(location: { lat: number; lng: number; }): void {
    //     const marker = new google.maps.Marker({
    //         position: location,
    //         map: this.map
    //     });
    //     this.markers.push(marker);
    // }

    private addMarker(location: { lat: number; lng: number; }, infoContent: string): void {
        const marker = new google.maps.Marker({
            position: location,
            map: this.map
        });
    
        const infoWindow = new google.maps.InfoWindow({
            content: infoContent
        });
    
        marker.addListener("click", () => {
            infoWindow.open(this.map, marker);
        });
    
        this.markers.push(marker);
    }
    
    private clearMarkers(): void {
        this.markers.forEach(marker => marker.setMap(null));
        this.markers = [];
    }
}
