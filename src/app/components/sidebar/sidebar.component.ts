import { Component, OnInit } from '@angular/core';

declare interface RouteInfo {
    path: string;
    title: string;
    icon: string;
    class: string;
}
export const ROUTES: RouteInfo[] = [
    { path: '/dashboard', title: 'Dashboard',  icon: 'design_app', class: '' },
    { path: '/maps', title: 'Maps',  icon:'location_map-big', class: '' },
    
    
    
    { path: '/table-list', title: 'STATIONS',  icon:'location_pin', class: '' },
    { path: '/icons', title: 'TRANSACTIONS',  icon:'shopping_credit-card', class: '' },
    /*{ path: '/table-list', title: 'Table List',  icon:'design_bullet-list-67', class: '' },
    { path: '/icons', title: 'Icons',  icon:'education_atom', class: '' },
    { path: '/typography', title: 'Typography',  icon:'text_caps-small', class: '' },
    { path: '/icons', title: 'Icons',  icon:'education_atom', class: '' },
     { path: '/user-profile', title: 'User Profile',  icon:'users_single-02', class: '' },*/

];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  menuItems!: any[];
  activeMenuItem: string = '';

setActive(path: string) {
    this.activeMenuItem = path;
}

isActive(path: string) {
    return this.activeMenuItem === path;
}
  constructor() { }

  ngOnInit() {
    this.menuItems = ROUTES.filter(menuItem => menuItem);
  }
  isMobileMenu() {
      if ( window.innerWidth > 991) {
          return false;
      }
      return true;
  };
}
