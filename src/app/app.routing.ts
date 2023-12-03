// src/app/app-routing.module.ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { Routes, RouterModule } from '@angular/router';

// Import the components
import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';
import { MapsComponent } from './maps/maps.component';
import { LandingComponent } from './landing/landing.component';
import { UserComponent } from './user/user.component'; // Assuming this component exists
import { UserlandingComponent } from './userlanding/userlanding.component';
import { TableListComponent } from './table-list/table-list.component';
import { TransactionsComponent } from './transactions/transactions.component';


const routes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full',
  },
  { path: 'landing', component: LandingComponent },
  { path: 'map', component: MapsComponent },
  
  { path: 'user', component: UserComponent },
  { path: 'user-landing', component: UserlandingComponent }, // Use a different path here
  { path: 'table-list', component: TableListComponent },
  { path: 'transactions', component: TransactionsComponent },
  

  
  {
    path: '',
    component: AdminLayoutComponent,
    children: [
      {
        path: '',
        loadChildren: () => import('./layouts/admin-layout/admin-layout.module').then(m => m.AdminLayoutModule)
      }
    ]
  },
  {
    path: '**',
    redirectTo: 'dashboard'
  }
];

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule],
})
export class AppRoutingModule { }
