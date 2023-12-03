import { Component, OnInit } from '@angular/core';

interface Station {
  id: string;
  name: string;
  location: string;
  city: string;
  zipCode: string;
  capacity: number;
}

@Component({
  selector: 'app-table-list',
  templateUrl: './table-list.component.html',
  styleUrls: ['./table-list.component.css']
})
export class TableListComponent implements OnInit {
  stations: Station[] = []; // This property must exist to use "let station of stations"
  currentStation: Station = this.getNewStation(); // Initialize currentStation
  editing: boolean = false; // This property must exist for "[readonly]="editing""

  constructor() { }

  ngOnInit(): void {
    // Perform any initialization for the component here
  }

  getNewStation(): Station {
    // Initialize a new station object
    return {
      id: '',
      name: '',
      location: '',
      city: '',
      zipCode: '',
      capacity: 0
    };
  }

  submitStation(station: Station): void {
    // Implement submission logic for adding/updating a station
    if (this.editing) {
      // Find and update the existing station
      // ...
    } else {
      // Add the new station
      this.stations.push(station);
    }
    this.editing = false;
    this.currentStation = this.getNewStation();
  }

  startUpdate(station: Station): void {
    // Set the currentStation to the station to be edited
    this.currentStation = { ...station };
    this.editing = true;
  }

  deleteStation(stationId: string): void {
    // Remove the station from the stations array
    this.stations = this.stations.filter(s => s.id !== stationId);
  }
}
