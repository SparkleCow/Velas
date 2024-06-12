import { Component } from '@angular/core';
import { HttpClientService } from '../http-client.service';
import { Token } from '../models/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  userLoginDto:any = {
    username: "",
    password: ""
  }

  constructor(private httpClient:HttpClientService, private router:Router){}

  enviar() {
    console.log(this.userLoginDto)
    this.httpClient.login(this.userLoginDto).subscribe(
      (token: Token) => {
        console.log('Token recibido:', token);
        localStorage.setItem('token', token.token);
        this.router.navigate([""])
      },
      (error) => {
        console.error('Error al iniciar sesión:', error);
        alert('Error al iniciar sesión. Por favor, verifique sus credenciales.');
      }
    );
  }
}
