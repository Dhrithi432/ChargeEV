import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StationService } from '../station.service';


declare const google: any;

interface ApiResponse {
    City: string;
    Latitude: number;
    Longitude: number;
    Num_Level_2: number;
    Num_level_1: number | null; // Assuming it can be null based on NaN value
    State: string;
    Address: string;
    Station_Name: string;
    ZIP: string;
}

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss']
})
export class LandingComponent implements OnInit {
  stations: ApiResponse[] = [];
  displayedStations: ApiResponse[] = [];
  private pageIndex: number = 0;
  private pageSize: number = 4;
  location: string = '';
  selectedStation: any; // Replace with your station type
  showFilters: boolean = false;

  constructor(private router: Router, private stationService: StationService) {}
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  searchLocation(): void {
    if (this.location) {
      // Navigate to the map component and pass the ZIP code
      this.router.navigate(['/maps'], { queryParams: { zip: this.location } });
    } else {
      console.error("Please enter a ZIP code.");
    }
  }

  getStations(zipCode: string): void {
    this.stationService.getStationsByZip(zipCode).subscribe(
      (data) => {
        this.stations = data;
        this.updateDisplayedStations();
      },
      (error) => {
        console.error('Error fetching stations:', error);
      }
    );
  }
  updateDisplayedStations(): void {
    const start = this.pageIndex * this.pageSize;
    const end = start + this.pageSize;
    this.displayedStations = this.stations.slice(start, end);
  }

  nextPage(): void {
    if ((this.pageIndex + 1) * this.pageSize < this.stations.length) {
      this.pageIndex++;
      this.updateDisplayedStations();
    }
  }

  previousPage(): void {
    if (this.pageIndex > 0) {
      this.pageIndex--;
      this.updateDisplayedStations();
    }
  }

  private initMap(): void {
    // Initialize your Google map here
  }
  
  toggleFilter(): void {
    this.showFilters = !this.showFilters;
  }
  
  onFilterChange(event: any): void {
    // Handle filter changes
  }
  
  bookStation(stationId: string): void {
    // Booking logic
  }
  
}
