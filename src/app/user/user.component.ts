import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router'; // Import Router

declare const google: any;

interface ApiResponse {
  City: string;
  Latitude: number;
  Longitude: number;
  Num_Level_2: number;
  Num_level_1: number | null; // Assuming it can be null based on NaN value
  State: string;
  Station_Name: string;
  Address: string;
  ZIP: string;
}

@Component({
  selector: 'app-user',
  template: `<div #mapContainer style="height: 500px; width: 100%;"></div>`,
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit, AfterViewInit {
  @ViewChild('mapContainer') mapContainerRef: ElementRef;
  zipCode: string = '';
  showFilters: boolean = false; // Used for toggling the filter dropdown
  private map: any;
  private markers: any[] = [];

  constructor(private http: HttpClient, private router: Router) {} // Inject Router

  ngOnInit() {
    // Component initialization
  }

  ngAfterViewInit() {
    // Map initialization is done after the view has been initialized
    this.initMap();
  }

  private initMap(): void {
    // Ensuring the element is present
    if (this.mapContainerRef.nativeElement) {
      this.map = new google.maps.Map(this.mapContainerRef.nativeElement, {
        zoom: 10,
        center: { lat: 37.0902, lng: -95.7129 } // Center of USA
      });
    }
  }

  displayLocation(): void {
    this.clearMarkers();
    if (this.zipCode) {
      this.http.get<ApiResponse[]>(`http://127.0.0.1:5001/search?zipcode=${this.zipCode}`).subscribe(response => {
        response.forEach(location => {
          // Add markers as before

          // Redirect to MapsComponent after fetching data with zip code as a parameter
          this.router.navigate(['/map', { zipcode: this.zipCode }]);
        });
      });
    }
  }

  private addMarker(location: { lat: number; lng: number; }, infoContent: string): void {
    // ... method body unchanged
  }

  private clearMarkers(): void {
    // ... method body unchanged
  }

  // Other methods like toggleFilter and onFilterChange can remain unchanged
  // ...
}
