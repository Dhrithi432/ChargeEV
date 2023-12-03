import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-userlanding',
  templateUrl: './userlanding.component.html',
  styleUrls: ['./userlanding.component.scss']
})
export class UserlandingComponent {
  zipCode: string = '';

  constructor(private router: Router) {}

  // Accept the event object here
  findStations(event: Event) {
    // Prevent the form from actually submitting
    event.preventDefault();

    // Use your router to navigate to your map component
    // Pass the ZIP code as a query parameter
    this.router.navigate(['/map'], { queryParams: { zipcode: this.zipCode } });
  }
}
