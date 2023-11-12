import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

declare var google: any;
@Component({
  selector: 'app-zip-code-search',
  templateUrl: './zip-code-search.component.html',
  styleUrls: ['./zip-code-search.component.scss']
})
export class ZipCodeSearchComponent implements OnInit {

  zipCode: string = '';
    private map: any;
    private markers: any[] = [];

    constructor(private http: HttpClient) {}

    ngOnInit(): void {
        this.initMap();
    }

    private initMap(): void {
        this.map = new google.maps.Map(document.getElementById('map'), {
            zoom: 4,
            center: { lat: 37.0902, lng: -95.7129 } // Center of USA
        });
    }

    displayLocation(): void {
        this.clearMarkers();

        if (this.zipCode) {
            this.http.get(`http://127.0.0.1:5000/search?zip=${this.zipCode}`).subscribe(response => {
                this.addMarker(response);
            });
        } else {
            // Handle error: No zip code entered
            console.error("Please enter a zip code.");
        }
    }

    private addMarker(location: any): void {
        const marker = new google.maps.Marker({
            position: location,
            map: this.map
        });
        this.markers.push(marker);
    }

    private clearMarkers(): void {
        this.markers.forEach(marker => marker.setMap(null));
        this.markers = [];
    }
  }