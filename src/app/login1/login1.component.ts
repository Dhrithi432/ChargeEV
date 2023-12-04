import { Component, OnInit } from '@angular/core';
//import { AuthService } from './auth.service';

@Component({
  selector: 'app-login1',
  templateUrl: './login1.component.html',
  styleUrls: ['./login1.component.scss']
})
export class Login1Component implements OnInit {
  username: string;
  password: string;

  //constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  //login1() {
    // Call the AuthService to authenticate the user
   //this.authService.login(this.username, this.password).subscribe(
     // (response) => {
        // Authentication successful, handle redirection or other actions
    //  },
     // (error) => {
        // Authentication failed, handle error messages
     // }
   // );
  //}
}
