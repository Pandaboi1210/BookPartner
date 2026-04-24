import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';


import { HariniComponent } from "./harini/harini";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HariniComponent],
 templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('BookPartner-UI');
}