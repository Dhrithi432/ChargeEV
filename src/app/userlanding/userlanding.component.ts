import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild, ElementRef, NgZone, Input  } from '@angular/core';
import { ApiService } from '../services/api.service';
import { ActivatedRoute } from '@angular/router';

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
  selector: 'app-userlanding',
  templateUrl: './userlanding.component.html',
  styleUrls: ['./userlanding.component.scss']
})
export class UserlandingComponent implements OnInit {
  ngOnInit(): void {
  
  }

  zoom = 12;
  center: google.maps.LatLngLiteral = { lat: 24.4539, lng: 54.3773 }; // Set default location
  

  // zipCode: string = '';

  // constructor(private router: Router) {}
  // ngOnInit(): void {
  //   throw new Error('Method not implemented.');
  // }

  // // Accept the event object here
  // findStations(event: Event) {
  //   // Prevent the form from actually submitting
  //   event.preventDefault();

  //   // Use your router to navigate to your map component
  //   // Pass the ZIP code as a query parameter
  //   this.router.navigate(['/map'], { queryParams: { zipcode: this.zipCode } });
  // }
}
