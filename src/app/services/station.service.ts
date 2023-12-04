import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { ApiResponse } from './api-response';

@Injectable({
  providedIn: 'root'
})
export class StationService {
  location: string = '';
  constructor(private http: HttpClient, private router: Router) {}

  getStations(location: string) {
    return this.http.get<ApiResponse[]>(`http://127.0.0.1:5001/search?location=${this.location}`);
  }

  goToUserMaps(locationQuery: string): void {
    this.router.navigate(['/user-maps'], { queryParams: { location: locationQuery } });
  }
}
