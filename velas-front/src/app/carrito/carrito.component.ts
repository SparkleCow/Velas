import { Component, OnInit } from '@angular/core';
import { HttpClientService } from '../http-client.service';
import { Router } from '@angular/router';
import { Carrito } from '../models/carrito';

@Component({
  selector: 'app-carrito',
  templateUrl: './carrito.component.html',
  styleUrl: './carrito.component.css'
})
export class CarritoComponent implements OnInit {
  carrito: Carrito = {
    id: 0,
    candles: [],
    totalPrice: 0
  };

  constructor(private httpClient: HttpClientService, private router: Router) {}

  ngOnInit(): void {
    this.cargarCarrito();
  }

  login():void {
    this.router.navigate(["/login"]);
  }

  cargarCarrito(): void {
    this.httpClient.getCarrito().subscribe(
      carrito => {
        this.carrito = carrito;
      },
      error => {
        console.log(error)
        this.login();
      }
    );
  }

  agregarProducto(id:number):void{
    this.httpClient.addProduct(id).subscribe(
      () => {
        console.log(`Unidad del producto con id ${id} agregada`);
        this.cargarCarrito();
      },
      error => {
        console.error(`Error al agregar el producto con id ${id}: `, error);
      }
    );
  }

  eliminarProducto(id: number): void {
    this.httpClient.deleteProduct(id).subscribe(
      () => {
        console.log(`Unidad del producto con id ${id} eliminado`);
        this.cargarCarrito();
      },
      error => {
        console.error(`Error al eliminar el producto con id ${id}: `, error);
      }
    );
  }

  eliminarProductos(id: number): void {
    this.httpClient.deleteProducts(id).subscribe(
      () => {
        console.log(`Producto con id ${id} eliminado`);
        this.cargarCarrito();
      },
      error => {
        console.error(`Error al eliminar el producto con id ${id}: `, error);
      }
    );
  }

  eliminarTodo(id: number): void {
    this.httpClient.deleteAllProducts().subscribe(
      () => {
        this.cargarCarrito();
      },
      error => {
        console.error(`Error al eliminar los productos: `, error);
      }
    );
  }

  home(){
    this.router.navigate(["/"]);
  }
}

