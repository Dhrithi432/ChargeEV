import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild, ElementRef, NgZone, Input  } from '@angular/core';
import { ApiService } from '../services/api.service';
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
    id?: string;
}

@Component({
  selector: 'app-maps',
  templateUrl: './maps.component.html',
  styleUrls: ['./maps.component.css']
})
export class MapsComponent implements OnInit {
    location: string = '';
    showFilters: boolean = false; // Used for toggling the filter dropdown
    private map: any;
    private markers: any[] = [];
    private autocomplete: any;
    stations: ApiResponse[] = []; 
    selectedStation: ApiResponse | null = null;

    
    @Input() mapClass: string; 
    @ViewChild('autocompleteInput') autocompleteInput: ElementRef;
    @ViewChild('mapContainer', { static: true }) mapContainer: ElementRef; // Add this line to reference the map container div
    private currentInfoWindow: google.maps.InfoWindow | null = null;

    /// <reference types="@types/googlemaps" />

    constructor(private http: HttpClient, private ngZone: NgZone) {}

    ngOnInit() {
      this.initMap();
    }

    ngAfterViewInit() {
      this.initAutocomplete();
    }

    private initAutocomplete(): void {
      this.autocomplete = new google.maps.places.Autocomplete(
        this.autocompleteInput.nativeElement,
        { types: ['(regions)'] }
      );

      this.autocomplete.addListener('place_changed', () => {
        this.ngZone.run(() => {
          const place = this.autocomplete.getPlace();
          if (place.geometry) {
            this.location = place.formatted_address;
            // you could also do this.displayLocation() if you want to search immediately
          } else {
            console.error("Autocomplete place not found");
          }
        });
      });
    }

    private initMap(): void {
        this.map = new google.maps.Map(document.getElementById('map'), {
            zoom: 10,
            center: { lat: 37.0902, lng: -95.7129 } // Center of USA
        });
    }

    displayLocation(): void {
        this.clearMarkers();
        if (this.location) {
          this.http.get<ApiResponse[]>(`http://127.0.0.1:5001/search?location=${this.location}`).subscribe(response => {
            response.forEach(location => {
              let infoContent = `<div><strong>${location.Station_Name}</strong><br>`;
              infoContent += `City: ${location.City}, ${location.State}<br>`;
              infoContent += `ZIP: ${location.ZIP}<br>`;
              infoContent += `Address: ${location.Address}<br>`;
              infoContent += `Level 2 Chargers: ${location.Num_Level_2}<br>`;
              infoContent += `Level 1 Chargers: ${location.Num_level_1 ?? 'N/A'}</div>`;

              this.addMarker(location);
            });
      
            if (response.length > 0) {
              this.map.setCenter({ lat: response[0].Latitude, lng: response[0].Longitude });
            }
          });
        } else {
          console.error("Please enter a zip code.");
        }
    }

    // private addMarker(location: { lat: number; lng: number; }, infoContent: string): void {
    //     const marker = new google.maps.Marker({
    //       position: { lat: location.lat, lng: location.lng },
    //         map: this.map
            
    //     });

    //     const infoWindow = new google.maps.InfoWindow({
    //         content: infoContent
    //     });

    //     marker.addListener("click", () => {
    //       if (this.currentInfoWindow) {
    //         this.currentInfoWindow.close();
    //       }
    //     // Open the new InfoWindow
    //     infoWindow.open(this.map, marker);

    //     // Update the current InfoWindow reference
    //     this.currentInfoWindow = infoWindow;
    //     });

    //     this.markers.push(marker);
    // }

    /*
    private getCustomIcon(level: number): any {
  let iconName;
  switch (level) {
    case 1:
      iconName = 'level1-icon.png';
      break;
    case 2:
      iconName = 'level2-icon.png';
      break;
    // Add more cases for other levels or types
    default:
      iconName = 'default-icon.png';
  }

  return {
    url: `path/to/your/icons/${iconName}`,
    scaledSize: new google.maps.Size(40, 40),
    origin: new google.maps.Point(0, 0),
    anchor: new google.maps.Point(20, 40),
  };
}

private addMarker(location: ApiResponse): void {
  const customIcon = this.getCustomIcon(location.Num_Level_2); // Assuming you want to choose the icon based on Level 2 chargers

  // ... rest of your addMarker code ...
}

     */

closePopup(): void {
  this.selectedStation = null;
  // Additional logic if required
}
    private addMarker(location: ApiResponse): void {
      const customIcon = {
        url: 'assets/img/ev_mapIcon.png', // The URL to your marker image
        scaledSize: new google.maps.Size(60, 60), // Size of the icon
        origin: new google.maps.Point(0, 0), // Origin point of the icon
        anchor: new google.maps.Point(30, 30), // Anchor point. Assumes the tip of the icon is at the bottom center
      };
      const marker = new google.maps.Marker({
        position: { lat: location.Latitude, lng: location.Longitude },
        map: this.map,
        title: location.Station_Name,
        icon: customIcon
      });

      marker.addListener('click', () => {
        if (this.currentInfoWindow) {
          this.currentInfoWindow.close();
        }
  
        const infoWindow = new google.maps.InfoWindow({
          content: `<div><strong>${location.Station_Name}</strong><br>`
            + `City: ${location.City}, ${location.State}<br>`
            + `ZIP: ${location.ZIP}<br>`
            + `Address: ${location.Address}<br>`
            + `Level 2 Chargers: ${location.Num_Level_2}<br>`
            + `Level 1 Chargers: ${location.Num_level_1 ?? 'N/A'}</div>`
        });
        infoWindow.open(this.map, marker);
        this.currentInfoWindow = infoWindow;
        this.selectedStation = location; // Update the selected station details
       });

      this.markers.push(marker);
  }
    
    private clearMarkers(): void {
        this.markers.forEach(marker => marker.setMap(null));
        this.markers = [];

        if (this.currentInfoWindow) {
          this.currentInfoWindow.close();
          this.currentInfoWindow = null;
        }
    }

    bookStation(stationId: string): void {
      // Implement your booking logic here
      // For example, navigate to a booking page or open a booking modal
      console.log(`Booking station with ID: ${stationId}`);
  }

    // Function to toggle the visibility of the filter dropdown
    toggleFilter(): void {
        this.showFilters = !this.showFilters;
    }

    // Function to handle changes in filter options
    onFilterChange(event: any): void {
        // Your logic to handle filter changes goes here
        console.log(event.target.id, ':', event.target.checked);
        // You can call a service or apply a filter to your map based on the checked status
    }
}
