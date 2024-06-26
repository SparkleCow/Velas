import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClientService } from './http-client.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{

  auth:boolean = false;
  username:String = "";
  
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

  login():void{
    this.router.navigate(["/login"]);
  }

  register():void{
    this.router.navigate(["/register"]);
  }

  car() {
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

  information(){
    this.router.navigate(["/information"]);
  }

  contact(){
    this.router.navigate(["/contact"]);
  }
  
  logout(){
    localStorage.clear();
    window.location.reload();
  }
}
