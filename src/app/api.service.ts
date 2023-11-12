// api.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  
  constructor(private http: HttpClient) {}
  private zipCodeSubject = new BehaviorSubject<string>('');
  zipCode$ = this.zipCodeSubject.asObservable();

  updateZipCode(zipCode: string) {
    this.zipCodeSubject.next(zipCode);
  }
}
