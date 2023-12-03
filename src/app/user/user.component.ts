import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';

declare const google: any;

interface ApiResponse {
  City: string;
  Latitude: number;
  Longitude: number;
  Num_Level_2: number;
  Num_level_1?: number; // Marked as optional if it can be null
  State: string;
  Station_Name: string;
  Address: string;
  ZIP: string;
}

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit, AfterViewInit {
  @ViewChild('mapContainer', { static: true }) mapContainerRef: ElementRef;
  zipCode: string = '';
  private map: any;

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit() {}

  ngAfterViewInit() {
    this.initMap();
  }

  private initMap(): void {
    this.map = new google.maps.Map(this.mapContainerRef.nativeElement, {
      zoom: 10,
      center: { lat: 37.0902, lng: -95.7129 } // Center of USA
    });
  }

  displayLocation(): void {
    if (this.zipCode) {
      this.http.get<ApiResponse[]>(`http://127.0.0.1:5001/search?zipcode=${this.zipCode}`).subscribe(response => {
        response.forEach(station => {
          const markerLocation = { lat: station.Latitude, lng: station.Longitude };
          const infoContent = `
            <div>
              <strong>${station.Station_Name}</strong><br>
              ${station.Address}, ${station.City}, ${station.State} ${station.ZIP}<br>
              Level 2 Chargers: ${station.Num_Level_2}
            </div>
          `;
          this.addMarker(markerLocation, infoContent);
        });

        // If we have stations, center the map on the first one
        if (response.length > 0) {
          this.map.setCenter({ lat: response[0].Latitude, lng: response[0].Longitude });
          this.map.setZoom(13); // Zoom in
        }
      });
    }
  }

  private addMarker(location: { lat: number; lng: number }, infoContent: string): void {
    const marker = new google.maps.Marker({
      position: location,
      map: this.map
    });

    const infoWindow = new google.maps.InfoWindow({
      content: infoContent
    });

    marker.addListener('click', () => {
      infoWindow.open(this.map, marker);
    });
  }
}
