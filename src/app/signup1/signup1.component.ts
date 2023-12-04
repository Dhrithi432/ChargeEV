import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-signup1',
  templateUrl: './signup1.component.html',
  styleUrls: ['./signup1.component.scss']
})
export class Signup1Component {
  fullName: string;
  mobileNumber: string;
  email: string;
  password: string;

  // Replace this with the actual URL of your backend API
  apiUrl = 'https://example.com/api/register';

  constructor(private http: HttpClient) {}

  onSubmit() {
    const userData = {
      fullName: this.fullName,
      mobileNumber: this.mobileNumber,
      email: this.email,
      password: this.password
    };

    this.http.post(this.apiUrl, userData).subscribe(
      (response) => {
        // Registration successful, handle the response (e.g., show a success message).
        console.log('Registration successful', response);
        // You can add code here to redirect the user to a login page or perform other actions.
      },
      (error) => {
        // Registration failed, handle the error (e.g., show an error message).
        console.error('Registration failed', error);
        // You can add code here to display an error message to the user.
      }
    );
  }
}
