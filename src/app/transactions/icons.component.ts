import { Component, OnInit } from '@angular/core';
import { DataService } from './data.service';

@Component({
  selector: 'app-icons',
  templateUrl: './icons.component.html',
  styleUrls: ['./icons.component.css']
})
export class IconsComponent implements OnInit {
  transactions: any[] = [];

  constructor(private dataService: DataService) {}

  ngOnInit() {
    this.loadTransactions();
  }

  loadTransactions() {
    this.dataService.getTransactions().subscribe(
      (data) => {
        this.transactions = data;
      },
      (error) => {
        console.error('There was an error!', error);
      }
    );
  }
}
