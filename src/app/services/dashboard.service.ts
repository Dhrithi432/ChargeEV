import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private http: HttpClient) { }

  getPieChartData(): Observable<any> {
    return this.http.get('http://localhost:8080/ev-hub/api/analytics/getChargingStationsPieChartData');
  }

  getBarChartData(): Observable<any> {
    return this.http.get('http://localhost:8080/ev-hub/api/analytics/getTransactionPerformanceData"');
  }
}
