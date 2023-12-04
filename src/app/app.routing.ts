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


const routes: Routes =[
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: '',
  redirectTo: 'user-landing',
   component: UserlandingComponent },
  
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full',
  }, 
  { path: 'user-landing', component: UserlandingComponent },
  { path: 'user', component: UserComponent },
  
  { path: 'map', component: MapsComponent },

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
