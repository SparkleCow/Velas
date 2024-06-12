import { Component } from '@angular/core';
import { UserRequestDto } from '../models/user';
import { HttpClientService } from '../http-client.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  userRequestDto:UserRequestDto={
    firstName: '',
    lastName: '',
    username: '',
    email: '',
    password: ''
  }

  constructor(private httpClient:HttpClientService, private router:Router){}

  enviar() {
    this.httpClient.register(this.userRequestDto).subscribe(
      () => {
        console.log('Registro exitoso');
        this.router.navigate(['/login']);
      },
      (error) => {
        console.error('Error al registrarse:', error);
        alert('Error al registrarse. Por favor, inténtalo de nuevo.');
      }
    );
  }
}
