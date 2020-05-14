import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { NumberService } from '../shared/number/number.service';
import { GiphyService } from '../shared/giphy/giphy.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-number-edit',
  templateUrl: './number-edit.component.html',
  styleUrls: ['./number-edit.component.css']
})
export class NumberEditComponent implements OnInit, OnDestroy {
  number: any = {};
  numberDetailed: any = {};
  sub: Subscription;
  panelOpenState1: boolean;
  panelOpenState2: boolean;
  number_id: string;
  constructor(private route: ActivatedRoute,
              private router: Router,
              private numberService: NumberService,
              private giphyService: GiphyService) {
  }
  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      const id = params['id'];
      if (id) {
        this.numberService.get(id).subscribe((number: any) => {
          this.panelOpenState1 = false;
          this.panelOpenState2 = false;
          this.number_id = id;
          if (number) {
            this.number = number;
            this.number.href = number._links.self.href;
            this.numberService.getNumber(id).subscribe((numberDetailed: any) => {
              if (numberDetailed) {
                number.letterCombinationsString = numberDetailed.letterCombinationsString;
                number.letterCombinations = numberDetailed.letterCombinations;
                number.stopWatchReportOnLetterCombination = numberDetailed.stopWatchReportOnLetterCombination;
                number.letterCombinationsSize = numberDetailed.letterCombinationsSize;
                number.number = numberDetailed.number;
                this.giphyService.get(number.name).subscribe(url => number.giphyUrl = url);
              }
            });
          } else {
            console.log(`Number with id '${id}' not found, returning to list`);
            this.gotoList();
          }
        });
      }
    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  gotoList() {
    this.router.navigate(['/number-list']);
  }

  gotoEdit() {
    this.router.navigate(['/number-edit/' + this.number.id]);
  }

  save(form: NgForm) {
    this.numberService.save(form).subscribe(result => {
      this.gotoList();
    }, error => console.error(error));
  }
  saveNumber(form: NgForm) {
    this.numberService.saveNumber(form).subscribe(result => {
      console.log(result);
      this.gotoList();
    }, error => console.error(error));
  }

  remove(href) {
    this.numberService.remove(href).subscribe(result => {
      this.gotoList();
    }, error => console.error(error));
  }
}
