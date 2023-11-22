import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

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
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss']
})
export class LandingComponent implements OnInit {
  zipCode: string = '';
  private map: any;
  private markers: any[] = [];

  constructor(private http: HttpClient,  private router: Router) {}

  ngOnInit(): void {
    this.initMap();
  }

  private initMap(): void {
    this.map = new google.maps.Map(document.getElementById('map'), {
      zoom: 10,
      center: { lat: 37.0902, lng: -95.7129 } // Center of USA
  });
  }

  searchStations(): void {
    if (!this.zipCode) {
      alert("Please enter a zip code.");
      return;
    }

    this.clearMarkers();
    this.http.get<ApiResponse[]>(`http://127.0.0.1:5001/search?zipcode=${this.zipCode}`).subscribe(
      response => {
        if (response && response.length > 0) {
          // Navigate to the map page with the ZIP code as a query parameter
          this.router.navigate(['/map'], { queryParams: { zipCode: this.zipCode } });
        } else {
          alert("No stations found for the entered ZIP code.");
        }
      },
      error => {
        console.error("Error fetching stations:", error);
        alert("An error occurred while fetching stations.");
      }
    );
  }

  private processResponse(response: ApiResponse[]): void {
    response.forEach(location => {
      const infoContent = this.createInfoContent(location);
      this.addMarker({ lat: location.Latitude, lng: location.Longitude }, infoContent);
    });

    if (response.length > 0) {
      this.map.setCenter({ lat: response[0].Latitude, lng: response[0].Longitude });
    }
  }

  private createInfoContent(location: ApiResponse): string {
    let content = `<div><strong>${location.Station_Name}</strong><br>`;
    content += `City: ${location.City}, ${location.State}<br>`;
    content += `ZIP: ${location.ZIP}<br>`;
    content += `Level 2 Chargers: ${location.Num_Level_2}<br>`;
    content += `Level 1 Chargers: ${location.Num_level_1 ?? 'N/A'}</div>`;
    return content;
  }
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
