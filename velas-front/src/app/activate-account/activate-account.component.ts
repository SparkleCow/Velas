import { Component } from '@angular/core';
import { HttpClientService } from '../http-client.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrl: './activate-account.component.css'
})
export class ActivateAccountComponent {

  code:string = "";

  constructor(private router:Router, private httpClient:HttpClientService){}

  activateAccount(){
    this.httpClient.activateAccount(this.code).subscribe(()=>{
      alert("Cuenta activada con éxito")
      this.router.navigate(["/login"])
    },error => {
      console.log("Token expirado o invalidol. "+error)
    });
  }
}
