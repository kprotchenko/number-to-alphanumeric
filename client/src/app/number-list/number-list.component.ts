import { Component, OnInit } from '@angular/core';
import { NumberService } from '../shared/number/number.service';
import { GiphyService } from '../shared/giphy/giphy.service';

@Component({
  selector: 'app-number-list',
  templateUrl: './number-list.component.html',
  styleUrls: ['./number-list.component.css']
})
export class NumberListComponent implements OnInit {
  numbers: Array<any>;

  constructor(private numberService: NumberService, private giphyService: GiphyService) { }

  ngOnInit() {
    this.numberService.getAll().subscribe(data => {
      this.numbers = data;
      for (const number of this.numbers) {
        this.giphyService.get(number.name).subscribe(url => number.giphyUrl = url);
      }
    });
  }
}
