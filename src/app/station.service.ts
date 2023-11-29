import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse } from './services/api-response';

@Injectable({
  providedIn: 'root'
})
export class StationService {
  location: string = '';
  //private apiUrl = 'http://127.0.0.1:5001/search?location=${this.location}'; // Replace with your actual API endpoint

  constructor(private http: HttpClient) {}

  getStationsByZip(zipCode: string): Observable<ApiResponse[]> {
    return this.http.get<ApiResponse[]>(`http://127.0.0.1:5001/search?location=${this.location}`);
  }
}
