import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClientService } from './http-client.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{

  public auth:boolean = false;
  public username:String = "";
  
  constructor(private router:Router, private httpClient:HttpClientService){}

  ngOnInit(): void {
    this.httpClient.validateTokenWithUsername().subscribe(username => {
      this.auth = true;
      this.username = username.username;
    }, error => {
      console.log(error)
      this.auth = false;
    });
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
          this.router.navigate(['/carrito']);
        } else {
          this.router.navigate(['/login']);
        }
      },
      error => {
        console.error('Error validating token:', error);
        this.router.navigate(['/login']);
      }
    );
  }
  
}
