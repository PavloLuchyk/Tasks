import { Component } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent {

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  menutext = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris eleifend auctor sapien ac lacinia. Morbi nibh ipsum, pretium ultricies interdum sed, venenatis iaculis libero. Maecenas a blandit nulla. Suspendisse faucibus tincidunt nisi, at scelerisque enim eleifend in. Morbi vulputate eros magna, pretium tincidunt lectus dictum quis. Integer dignissim finibus sodales. Sed ultricies ante ac mi pulvinar venenatis. Donec risus ex, pretium nec sodales sed, aliquet ut ipsum. Pellentesque dictum libero felis, in aliquam nibh condimentum vel. Nulla sed mollis elit, vitae ultrices purus. Phasellus at sapien lorem."

  constructor(private breakpointObserver: BreakpointObserver) {}

}
