import { Component, OnInit } from '@angular/core';
import { TransactionService } from './TransactionService'; // Import the service

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.scss']
})
export class TransactionsComponent implements OnInit {
  transactions: any[] = [];

  constructor(private transactionService: TransactionService) {}

  ngOnInit() {
    this.loadTransactions();
  }

  loadTransactions() {
    this.transactionService.getTransactions().subscribe(
      (data: any) => { // Better to use a specific type if possible
        this.transactions = data;
      },
      (error: any) => {
        console.error('There was an error!', error);
      }
    );
    
  }
}
