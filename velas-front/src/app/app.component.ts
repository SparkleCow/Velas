import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClientService } from './http-client.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{

  private token:string|null = ""
  
  constructor(private router:Router, private httpClient:HttpClientService){}

  ngOnInit(): void {
    this.token = localStorage.getItem("token");
  }

  public login():void{
    this.router.navigate(["/login"]);
  }

  public register():void{
    this.router.navigate(["/register"]);
  }

  public carrito() {
    this.httpClient.validateToken().subscribe(
      status => {
        if (status) {
          console.log('Token válido:', status);
          this.router.navigate(['/carrito']);
        } else {
          console.log('Token inválido:', status);
          this.router.navigate(['/login']);
        }
      },
      error => {
        console.error('Error validating token:', error);
        alert('Token validation failed. Please log in again.');
        this.router.navigate(['/login']);
      }
    );
  }
  
}
