import { Component } from '@angular/core';

@Component({
  selector: 'my-app',
  template: `<child-comp></child-comp>
                <p>Привет {{name}}</p>`,
  styles: [`h2, p {color:#333;}`]
})
export class AppComponent {
  name = 'Петр';
}
