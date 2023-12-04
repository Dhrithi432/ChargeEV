import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild, ElementRef, NgZone, Input  } from '@angular/core';
import { ApiService } from '../services/api.service';
import { ActivatedRoute } from '@angular/router';
import PerfectScrollbar from 'perfect-scrollbar';
import { SlideMenu } from 'primeng/slidemenu';

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

interface Station {
  name: string;
  address: string;
  chargers: any;
}

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  isMenuVisible: boolean = false;
  
  menuItems: any[];
  location: string = '';
    showFilters: boolean = false; // Used for toggling the filter dropdown
    private map: any;
    private markers: any[] = [];
    private autocomplete: any;
    stations: ApiResponse[] = []; 
    selectedStation: ApiResponse | null = null;

    private directionsService = new google.maps.DirectionsService();
    private directionsRenderer = new google.maps.DirectionsRenderer();

    
    @Input() mapClass: string; 
    @Input() userType: 'user' | 'vendor';
    @Input() fullScreen: boolean = false; // If true, map takes the full screen
    @Input() showSidebar: boolean = false;
    @ViewChild('autocompleteInput') autocompleteInput: ElementRef;
    @ViewChild('slideMenu') slideMenu: SlideMenu;
    @ViewChild('mapContainer', { static: true }) mapContainer: ElementRef; // Add this line to reference the map container div
    private currentInfoWindow: google.maps.InfoWindow | null = null;

    /// <reference types="@types/googlemaps" />

    constructor(private http: HttpClient, private ngZone: NgZone, private route: ActivatedRoute) {}

    ngOnInit() {
      this.initMap();
      this.route.queryParams.subscribe(params => {
        if (params['location']) {
          this.location = params['location'];
          this.displayLocation();
        }
      });
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
        this.directionsRenderer.setMap(this.map);
    }
    backToList(): void {
      this.selectedStation = null;
      this.directionsRenderer.setDirections({ routes: [] })
      // You may also want to clear the map or reset it to a default state
    }

    selectStation(station: ApiResponse): void {
      this.selectedStation = station;
      
      this.map.setCenter({ lat: station.Latitude, lng: station.Longitude });
      this.map.setZoom(15);

      this.clearMarkers();
      this.addMarker(station);
      this.menuItems = [
        {
          label: station.Station_Name,
          items: [
            {label: 'Address', command: () => { this.backToList(); }},
            {label: station.Address},
            
          ]
        },
        {
          label: 'Back to list',
          command: () => { this.backToList(); }
        }
      ];
      this.isMenuVisible = true;
      this.showDirections(station);
    }

    private showDirections(station: ApiResponse): void {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition((position) => {
          const origin = { lat: position.coords.latitude, lng: position.coords.longitude };
          const destination = { lat: station.Latitude, lng: station.Longitude };
    
          this.directionsService.route(
            {
              origin: origin,
              destination: destination,
              travelMode: google.maps.TravelMode.DRIVING,
            },
            (response: google.maps.DirectionsResult, status: google.maps.DirectionsStatus) => {
              if (status === google.maps.DirectionsStatus.OK) {
                this.directionsRenderer.setDirections(response);
              } else {
                window.alert('Directions request failed due to ' + status);
              }
            }
          );
        });
      } else {
        console.error("Geolocation is not supported by this browser.");
      }
    }
    
  

    displayLocation(): void {
        this.clearMarkers();
        if (this.location) {
          this.http.get<ApiResponse[]>(`http://127.0.0.1:5001/search?location=${this.location}`).subscribe(response => {
            response.forEach(location => {
              this.stations = response;
              
              let infoContent = `<div><strong>${location.Station_Name}</strong><br>`;
              infoContent += `City: ${location.City}, ${location.State}<br>`;
              infoContent += `ZIP: ${location.ZIP}<br>`;
              infoContent += `Address: ${location.Address}<br>`;
              infoContent += `Level 2 Chargers: ${location.Num_Level_2}<br>`;
              infoContent += `Level 1 Chargers: ${location.Num_level_1 ?? 'N/A'}</div>`;

              this.addMarker(location);
              if (response.length > 0) {
                // Assuming you want to zoom into the first result for now
                const firstResult = response[0];
                this.map.setCenter({ lat: firstResult.Latitude, lng: firstResult.Longitude });
                this.map.setZoom(20); // Set a higher zoom level to zoom in closer
              }
            });
      
            if (response.length > 0) {
              this.map.setCenter({ lat: response[0].Latitude, lng: response[0].Longitude });
            }
          });
        } else {
          console.error("Please enter a zip code.");
        }
    }
    runOnRouteChange(): void {
      if (window.matchMedia(`(max-width: 1000px)`).matches && !this.isMac()) {
        const elemMainPanel = <HTMLElement>document.querySelector('.main-bg');
        const ps = new PerfectScrollbar(elemMainPanel);
        ps.update();
        
      }
    }

    isMac(): boolean {
      let bool = false;
      if (navigator.platform.toUpperCase().indexOf('MAC') >= 0 || navigator.platform.toUpperCase().indexOf('IPAD') >= 0) {
          bool = true;
      }
      return bool;
  }
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
          content: `<div style="color: black; width: 300px; min-height: 150px; max-height: 500px; overflow-y: auto; font-size: 1em; font-family: 'Arial', sans-serif; background-color: #f5f5f5; border: 1px solid #ddd; border-radius: 8px; padding: 15px; box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);">`
              + `<h2 style="color: #4a4a4a; margin-top: 0;">${location.Station_Name}</h2>`
              + `<p style="margin: 5px 0;"><strong>City:</strong> ${location.City}, ${location.State}</p>`
              + `<p style="margin: 5px 0;"><strong>ZIP:</strong> ${location.ZIP}</p>`
              + `<p style="margin: 5px 0;"><strong>Address:</strong> ${location.Address}</p>`
              + `<p style="margin: 5px 0;"><strong>Level 2 Chargers:</strong> ${location.Num_Level_2}</p>`
              + `<p style="margin: 5px 0;"><strong>Level 1 Chargers:</strong> ${location.Num_level_1 ?? 'N/A'}</p>`
              + `</div>`
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
