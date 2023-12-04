import { Component, OnInit } from '@angular/core';
import Chart, { ChartType } from 'chart.js';
import { ChartDataSets, ChartOptions } from 'chart.js';
import { ChartsModule } from 'ng2-charts';
import { Label } from 'ng2-charts';
import { DashboardService } from '../services/dashboard.service';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  vehicles: any[];
  public myPieChart: Chart;
  public myBarChart: Chart;
 
  public lineBigDashboardChartType: string | undefined;
  public gradientStroke: { addColorStop: (arg0: number, arg1: string) => void; };
  public chartColor: string;
  public canvas : any;
  public ctx: { createLinearGradient: (arg0: number, arg1: number, arg2: number, arg3: number) => { addColorStop: (arg0: number, arg1: string) => void; }; };
  public gradientFill: { addColorStop: any; };
  public lineBigDashboardChartData:Array<any>;
  public lineBigDashboardChartOptions:any;
  public lineBigDashboardChartLabels:Array<any>;
  public lineBigDashboardChartColors:Array<any>

  public gradientChartOptionsConfiguration: any;
  public gradientChartOptionsConfigurationWithNumbersAndGrid: any;

  public lineChartType: string;
  public lineChartData:Array<any>;
  public lineChartOptions:any;
  public lineChartLabels:Array<any>;
  public lineChartColors:Array<any>

  public lineChartWithNumbersAndGridType: string;
  public lineChartWithNumbersAndGridData:Array<any>;
  public lineChartWithNumbersAndGridOptions:any;
  public lineChartWithNumbersAndGridLabels:Array<any>;
  public lineChartWithNumbersAndGridColors:Array<any>

  public lineChartGradientsNumbersType: string;
  public lineChartGradientsNumbersData:Array<any>;
  public lineChartGradientsNumbersOptions:any;
  public lineChartGradientsNumbersLabels:Array<any>;
  public lineChartGradientsNumbersColors:Array<any>
  public barChartOptions: ChartOptions = {
    responsive: true,
    maintainAspectRatio: false, 
    // other options here
  };
  public barChartLabels: Label[] = this.generateLabelsFor24H(); // Generate labels for 24 hours
  public barChartType: ChartType = 'bar';
  public barChartLegend = true;
  public barChartData: ChartDataSets[] = [
    { data: this.generateRandomDataFor24H(), label: 'Performance' } // Generate random data for 24 hours
  ];
  // events
  public chartClicked(e:any):void {
    console.log(e);
  }

  public chartHovered(e:any):void {
    console.log(e);
  }
  public hexToRGB(hex: string, alpha: string | number) {
    var r = parseInt(hex.slice(1, 3), 16),
      g = parseInt(hex.slice(3, 5), 16),
      b = parseInt(hex.slice(5, 7), 16);

    if (alpha) {
      return "rgba(" + r + ", " + g + ", " + b + ", " + alpha + ")";
    } else {
      return "rgb(" + r + ", " + g + ", " + b + ")";
    }
  }
  public legendData = [
    { title: 'Available', imageClass: 'fa fa-circle text-info' },
    { title: 'Out-of-service', imageClass: 'fa fa-circle text-danger' },
    { title: 'Maintenance', imageClass: 'fa fa-circle text-warning' }
  ];
  private generateLabelsFor24H(): Label[] {
    return Array.from({ length: 24 }, (_, i) => `${i}:00`);
  }
  private generateRandomDataFor24H(): number[] {
    return Array.from({ length: 24 }, () => Math.floor(Math.random() * 200));
  }
  
  constructor( private dashboardServc: DashboardService) { 
   this.vehicles = [
      { name: 'Honda Accord', city: 'San Jose', amount: 30 },
      { name: 'Toyota Camry', city: 'Los Angeles', amount: 35 },
      // ... more data
    ];
    
  }

  private initializeCharts() {
    // Initialize Pie Chart
    const pieCanvas = document.getElementById('myPieChart') as HTMLCanvasElement;
  if (pieCanvas) {
    const pieCtx = pieCanvas.getContext('2d');
    if (pieCtx) {
      this.myPieChart = new Chart(pieCtx, {
      type: 'pie',
      data: {
        datasets: [{
          data: [], // Data will be set by the API
          backgroundColor: ['rgba(23, 162, 184, 0.6)', 'rgba(220, 53, 69, 0.6)', 'rgba(255, 193, 7, 0.6)'],
          borderColor: ['rgba(23, 162, 184, 1)', 'rgba(220, 53, 69, 1)', 'rgba(255, 193, 7, 1)'],
          borderWidth: 1,
        }],
        labels: ['Available', 'Out-of-service', 'Maintenance'],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false
        // ... other options ...
      }
    });
  }
}

    // Initialize Bar Chart
    const barCanvas = document.getElementById('myBarChart') as HTMLCanvasElement;
  if (barCanvas) {
    const barCtx = barCanvas.getContext('2d');
    if (barCtx) {
      this.myBarChart = new Chart(barCtx, {
      type: 'bar',
      data: {
        labels: this.generateLabelsFor24H(),
        datasets: [{
          label: 'Performance',
          data: [], // Data will be set by the API
          backgroundColor: 'rgba(75, 192, 192, 0.2)',
          borderColor: 'rgba(75, 192, 192, 1)',
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false
        // ... other options ...
      }
    });
  }
}
  }

  private fetchChartData() {
    // Fetch Pie Chart Data
    this.dashboardServc.getPieChartData().subscribe(data => {
      this.updatePieChart(data);
    });

    // Fetch Bar Chart Data
    this.dashboardServc.getBarChartData().subscribe(data => {
      this.updateBarChart(data);
    });
  }

  private updatePieChart(data: any): void {
    if (this.myPieChart && this.myPieChart.data.datasets) {
      if (this.myPieChart.data.datasets.length > 0) {
        this.myPieChart.data.datasets[0].data = data;
        this.myPieChart.update();
      } else {
        // Handle the case where the datasets array is empty
        console.error('Pie chart datasets array is empty');
      }
    } else {
      console.error('Pie chart or its datasets is undefined');
    }
  }

  private updateBarChart(data: any): void {
    if (this.myBarChart && this.myBarChart.data && this.myBarChart.data.datasets) {
      if (this.myBarChart.data.datasets.length > 0) {
        this.myBarChart.data.datasets[0].data = data;
        this.myBarChart.update();
      } else {
        // Handle the case where the datasets array is empty
        console.error('Bar chart datasets array is empty');
        // Optionally, initialize the datasets array here
      }
    } else {
      console.error('Bar chart, its data, or datasets is undefined');
      // Handle the undefined cases appropriately
    }
  }

  ngOnInit() {
    this.chartColor = "#FFFFFF";
    this.canvas = document.getElementById("bigDashboardChart");
    this.ctx = this.canvas.getContext("2d");

    this.gradientStroke = this.ctx.createLinearGradient(500, 0, 100, 0);
    this.gradientStroke.addColorStop(0, '#80b6f4');
    this.gradientStroke.addColorStop(1, this.chartColor);

    this.gradientFill = this.ctx.createLinearGradient(0, 200, 0, 50);
    this.gradientFill.addColorStop(0, "rgba(128, 182, 244, 0)");
    this.gradientFill.addColorStop(1, "rgba(255, 255, 255, 0.24)");


    this.lineBigDashboardChartData = [
        {
          label: "Data",

          pointBorderWidth: 1,
          pointHoverRadius: 7,
          pointHoverBorderWidth: 2,
          pointRadius: 5,
          fill: true,

          borderWidth: 2,
          data: [50, 150, 100, 190, 130, 90, 150, 160, 120, 140, 190, 95]
        }
      ];
      this.lineBigDashboardChartColors = [
       {
         backgroundColor: this.gradientFill,
         borderColor: this.chartColor,
         pointBorderColor: this.chartColor,
         pointBackgroundColor: "#2c2c2c",
         pointHoverBackgroundColor: "#2c2c2c",
         pointHoverBorderColor: this.chartColor,
       }
     ];
    this.lineBigDashboardChartLabels = ["JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"];
    this.lineBigDashboardChartOptions = {

          layout: {
              padding: {
                  left: 20,
                  right: 20,
                  top: 0,
                  bottom: 0
              }
          },
          maintainAspectRatio: false,
          tooltips: {
            backgroundColor: '#fff',
            titleFontColor: '#333',
            bodyFontColor: '#666',
            bodySpacing: 4,
            xPadding: 12,
            mode: "nearest",
            intersect: 0,
            position: "nearest"
          },
          legend: {
              position: "bottom",
              fillStyle: "#FFF",
              display: false
          },
          scales: {
              yAxes: [{
                  ticks: {
                      fontColor: "rgba(255,255,255,0.4)",
                      fontStyle: "bold",
                      beginAtZero: true,
                      maxTicksLimit: 5,
                      padding: 10
                  },
                  gridLines: {
                      drawTicks: true,
                      drawBorder: false,
                      display: true,
                      color: "rgba(255,255,255,0.1)",
                      zeroLineColor: "transparent"
                  }

              }],
              xAxes: [{
                  gridLines: {
                      zeroLineColor: "transparent",
                      display: false,

                  },
                  ticks: {
                      padding: 10,
                      fontColor: "rgba(255,255,255,0.4)",
                      fontStyle: "bold"
                  }
              }]
          }
    };

    //pie chart
   
    
    const canvas = document.getElementById('myPieChart') as HTMLCanvasElement;
    const ctx = canvas.getContext('2d');
    const pieChartData = {
      datasets: [{
        data: [40, 10, 30], // Replace with your actual data
        backgroundColor: [
          'rgba(23, 162, 184, 0.6)', 
          'rgba(220, 53, 69, 0.6)', 
          'rgba(255, 193, 7, 0.6)' 
        ],
        borderColor: [
          'rgba(23, 162, 184, 1)', 
          'rgba(220, 53, 69, 1)', 
          'rgba(255, 193, 7, 1)' // Bootstrap warning color
        ],
        borderWidth: 1,
      }],
      labels: ['Available', 'Out-of-service', 'Maintenance'],
    };
  
    const pieChartOptions = {
      responsive: true,
      maintainAspectRatio: false,
      legend: {
        display: false // We will create a custom legend
      }
    };
  
    // Create the pie chart
    /*if (ctx !== null) {
      const myPieChart = new Chart(ctx, {
      type: 'pie',
      data: pieChartData,
      options: pieChartOptions,
    });
  
    // Function to generate the legend
    function generateLegend(legendData: any[]) {
      const legendContainer = document.getElementById('myPieChartLegend');
      legendData.forEach(item => {
        const legendItem = document.createElement('div');
        legendItem.innerHTML = `<i class="${item.imageClass} ${item.color}" aria-hidden="true"></i> ${item.title}`;
        legendContainer?.appendChild(legendItem);
      });
    }
  
    // Call the function to generate the legend
    generateLegend(this.legendData);
    this.lineBigDashboardChartType = 'line';
  } else {
    // Handle the error situation, maybe log it or show user-friendly message
    console.error('Could not get context from the canvas element');
  }
*/
if (ctx !== null) {
  const myPieChart = new Chart(ctx, {
    type: 'pie',
    data: pieChartData,
    options: pieChartOptions,
  });

  // Convert function declaration to function expression
  const generateLegend = (legendData: any[]): void => {
    const legendContainer = document.getElementById('myPieChartLegend');
    if (!legendContainer) {
      console.error('Legend container not found');
      return;
    }
    
    legendData.forEach(item => {
      const legendItem = document.createElement('div');
      legendItem.innerHTML = `<i class="${item.imageClass} ${item.color}" aria-hidden="true"></i> ${item.title}`;
      legendContainer.appendChild(legendItem);
    });
  };

  // Call the function to generate the legend
  generateLegend(this.legendData);
  this.lineBigDashboardChartType = 'line';
} else {
  // Handle the error situation, maybe log it or show a user-friendly message
  console.error('Could not get context from the canvas element');
}

//24 hr graph
this.lineChartWithNumbersAndGridLabels = ["12pm,", "3pm", "6pm", "9pm", "12am", "3am", "6am", "9am"];
this.lineChartWithNumbersAndGridOptions = this.gradientChartOptionsConfigurationWithNumbersAndGrid;

this.lineChartWithNumbersAndGridType = 'line';
this.canvas = document.getElementById("barChartSimpleGradientsNumbers");
this.ctx = this.canvas.getContext("2d");

this.gradientFill = this.ctx.createLinearGradient(0, 170, 0, 50);
this.gradientFill.addColorStop(0, "rgba(128, 182, 244, 0)");
this.gradientFill.addColorStop(1, this.hexToRGB('#2CA8FF', 0.6));


this.lineChartGradientsNumbersData = [
    {
      label: "Active Countries",
      pointBorderWidth: 2,
      pointHoverRadius: 4,
      pointHoverBorderWidth: 1,
      pointRadius: 4,
      fill: true,
      borderWidth: 1,
      data: [80, 99, 86, 96, 123, 85, 100, 75, 88, 90, 123, 155]
    }
  ];
this.lineChartGradientsNumbersColors = [
 {
   backgroundColor: this.gradientFill,
   borderColor: "#2CA8FF",
   pointBorderColor: "#FFF",
   pointBackgroundColor: "#2CA8FF",
 }
];
//end
    this.gradientChartOptionsConfiguration = {
      maintainAspectRatio: false,
      legend: {
        display: false
      },
      tooltips: {
        bodySpacing: 4,
        mode: "nearest",
        intersect: 0,
        position: "nearest",
        xPadding: 10,
        yPadding: 10,
        caretPadding: 10
      },
      responsive: 1,
      scales: {
        yAxes: [{
          display: 0,
          ticks: {
            display: false
          },
          gridLines: {
            zeroLineColor: "transparent",
            drawTicks: false,
            display: false,
            drawBorder: false
          }
        }],
        xAxes: [{
          display: 0,
          ticks: {
            display: false
          },
          gridLines: {
            zeroLineColor: "transparent",
            drawTicks: false,
            display: false,
            drawBorder: false
          }
        }]
      },
      layout: {
        padding: {
          left: 0,
          right: 0,
          top: 15,
          bottom: 15
        }
      }
    };

    

    this.canvas = document.getElementById("lineChartExample");
    this.ctx = this.canvas.getContext("2d");

    this.gradientStroke = this.ctx.createLinearGradient(500, 0, 100, 0);
    this.gradientStroke.addColorStop(0, '#80b6f4');
    this.gradientStroke.addColorStop(1, this.chartColor);

    this.gradientFill = this.ctx.createLinearGradient(0, 170, 0, 50);
    this.gradientFill.addColorStop(0, "rgba(128, 182, 244, 0)");
    this.gradientFill.addColorStop(1, "rgba(249, 99, 59, 0.40)");

    this.lineChartData = [
        {
          label: "Active Users",
          pointBorderWidth: 2,
          pointHoverRadius: 4,
          pointHoverBorderWidth: 1,
          pointRadius: 4,
          fill: true,
          borderWidth: 2,
          data: [542, 480, 430, 550, 530, 453, 380, 434, 568, 610, 700, 630]
        }
      ];
      this.lineChartColors = [
       {
         borderColor: "#f96332",
         pointBorderColor: "#FFF",
         pointBackgroundColor: "#f96332",
         backgroundColor: this.gradientFill
       }
     ];
    this.lineChartLabels = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
    this.lineChartOptions = this.gradientChartOptionsConfiguration;

    this.lineChartType = 'line';

    this.canvas = document.getElementById("lineChartExampleWithNumbersAndGrid");
    this.ctx = this.canvas.getContext("2d");

    this.gradientStroke = this.ctx.createLinearGradient(500, 0, 100, 0);
    this.gradientStroke.addColorStop(0, '#18ce0f');
    this.gradientStroke.addColorStop(1, this.chartColor);

    this.gradientFill = this.ctx.createLinearGradient(0, 170, 0, 50);
    this.gradientFill.addColorStop(0, "rgba(128, 182, 244, 0)");
    this.gradientFill.addColorStop(1, this.hexToRGB('#18ce0f', 0.4));

    this.lineChartWithNumbersAndGridData = [
        {
          label: "Email Stats",
           pointBorderWidth: 2,
           pointHoverRadius: 4,
           pointHoverBorderWidth: 1,
           pointRadius: 4,
           fill: true,
           borderWidth: 2,
          data: [40, 500, 650, 700, 1200, 1250, 1300, 1900]
        }
      ];
      this.lineChartWithNumbersAndGridColors = [
       {
         borderColor: "#18ce0f",
         pointBorderColor: "#FFF",
         pointBackgroundColor: "#18ce0f",
         backgroundColor: this.gradientFill
       }
     ];
  
    this.lineChartGradientsNumbersLabels = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    this.lineChartGradientsNumbersOptions = {
        maintainAspectRatio: false,
        legend: {
          display: false
        },
        tooltips: {
          bodySpacing: 4,
          mode: "nearest",
          intersect: 0,
          position: "nearest",
          xPadding: 10,
          yPadding: 10,
          caretPadding: 10
        },
        responsive: 1,
        scales: {
          yAxes: [{
            gridLines: {
              zeroLineColor: "transparent",
              drawBorder: false
            },
            ticks: {
                stepSize: 20
            }
          }],
          xAxes: [{
            display: 0,
            ticks: {
              display: false
            },
            gridLines: {
              zeroLineColor: "transparent",
              drawTicks: false,
              display: false,
              drawBorder: false
            }
          }]
        },
        layout: {
          padding: {
            left: 0,
            right: 0,
            top: 15,
            bottom: 15
          }
        }
      }

    this.lineChartGradientsNumbersType = 'bar';
  }
  
}