import { Component, OnInit } from '@angular/core';

// Consolidated Station interface
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
  stations: Station[] = [];
  currentStation: Station = this.getNewStation();
  editing = false;

  constructor() { }

  ngOnInit() {}

  getNewStation(): Station {
    // Returns a new station object with all fields initialized
    return {
      id: '',
      name: '',
      location: '',
      city: '',
      zipCode: '',
      capacity: 0
    };
  }

  submitStation(formValue: any) {
    // Check if we're in editing mode
    if (this.editing) {
      // Updating an existing station
      const existingStation = this.stations.find(s => s.id === this.currentStation.id);
      if (existingStation) {
        Object.assign(existingStation, formValue);
      }
      this.editing = false;
    } else {
      // Adding a new station
      if (this.stations.some(s => s.id === formValue.id)) {
        alert('Station ID is already taken.');
        return;
      }
      this.stations.push(formValue as Station);
    }

    // Reset the form and currentStation
    this.currentStation = this.getNewStation();
  }

  startUpdate(station: Station) {
    this.currentStation = {...station};
    this.editing = true;
  }

  deleteStation(stationId: string) {
    this.stations = this.stations.filter(s => s.id !== stationId);
  }
}
