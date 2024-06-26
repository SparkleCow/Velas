import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BannerComponent } from './banner/banner.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProductComponent } from './product/product.component';
import { CarritoComponent } from './carrito/carrito.component';
import { CandleFormComponent } from './candle-form/candle-form.component';
import { ActivateAccountComponent } from './activate-account/activate-account.component';
import { InformationComponent } from './information/information.component';
import { ContactComponent } from './contact/contact.component';

const routes: Routes = [
  { path: '', component: BannerComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'product/:id', component: ProductComponent },
  { path: 'carrito', component: CarritoComponent},
  { path: 'candleForm' , component: CandleFormComponent},
  { path: 'activateAccount', component: ActivateAccountComponent},
  { path: 'information', component: InformationComponent},
  { path: 'contact', component: ContactComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
