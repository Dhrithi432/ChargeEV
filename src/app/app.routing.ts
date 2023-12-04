import { NgModule } from '@angular/core';
import { CommonModule, } from '@angular/common';
import { BrowserModule  } from '@angular/platform-browser';
import { Routes, RouterModule } from '@angular/router';
import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';
import { MapsComponent } from './maps/maps.component';
import { UserComponent } from './user/user.component'; // Assuming this component exists
import { UserlandingComponent } from './userlanding/userlanding.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { Login1Component } from './login1/login1.component';
import { Signup1Component } from './signup1/signup1.component';


const routes: Routes =[
  { path: 'login', component: LoginComponent },
  { path: 'login1', component: Login1Component },
  { path: 'signup', component: SignupComponent },
  { path: 'signup1', component: Signup1Component },
  { path: '',
  redirectTo: 'user-landing',component: UserlandingComponent },
  
  
  { path: 'user-landing', component: UserlandingComponent },
  { path: 'user', component: UserComponent },
  
  { path: 'map', component: MapsComponent },
  { path: 'login/business', component: LoginComponent }, // Business login route
  { path: 'login1/driver', component: Login1Component },
  { path: 'signup/business', component: SignupComponent },
  { path: 'signup1/driver', component: Signup1Component },
   // Business signup route

  
  { path: '', redirectTo: '/login', pathMatch: 'full' },

  {
    path: '',
    component: AdminLayoutComponent,
    children: [
        {
      path: '',
      loadChildren: () => import('./layouts/admin-layout/admin-layout.module').then(m => m.AdminLayoutModule)
  }]},
  {
    path: '**',
    redirectTo: 'dashboard'
  },
  { path: 'user', component: UserComponent },
 
  
];

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule.forRoot(routes)
  ],
  exports: [
  ],
})
export class AppRoutingModule { }
