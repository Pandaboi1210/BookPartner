import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Subasri } from "./subasri/subasri";

@Component({
  selector: 'app-root',
  standalone: true,                 
  imports: [RouterOutlet, Subasri],
  templateUrl: './app.html',
  styleUrls: ['./app.css']        
})
export class App {
  protected readonly title = signal('BookPartner-UI');
}