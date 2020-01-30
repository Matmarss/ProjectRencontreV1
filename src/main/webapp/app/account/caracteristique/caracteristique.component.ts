import { Component, OnInit } from '@angular/core';
import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { FormBuilder } from '@angular/forms';
import { Caracteristique } from './caracteristique';

@Component({
  selector: 'jhi-caracteristique',
  templateUrl: './caracteristique.component.html'
})
export class CaracteristiqueComponent implements OnInit {
  account!: Account;
  success = false;
  listC = Object.keys(Caracteristique);

  caractForm = this.fb.group({
    caract1: [undefined],
    caract2: [undefined],
    caract3: [undefined],
    caract4: [undefined],
    caract5: [undefined],
    caract6: [undefined],
    caract7: [undefined],
    caract8: [undefined],
    caract9: [undefined],
    caract10: [undefined]
  });

  constructor(private accountService: AccountService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.caractForm.patchValue({
          caract1: account.caract1,
          caract2: account.caract2,
          caract3: account.caract3,
          caract4: account.caract4,
          caract5: account.caract5,
          caract6: account.caract6,
          caract7: account.caract7,
          caract8: account.caract8,
          caract9: account.caract9,
          caract10: account.caract10
        });
        this.account = account;
      }
    });
  }

  save(): void {
    this.success = false;

    (this.account.caract1 = this.caractForm.get('caract1')!.value),
      (this.account.caract2 = this.caractForm.get('caract2')!.value),
      (this.account.caract3 = this.caractForm.get('caract3')!.value),
      (this.account.caract4 = this.caractForm.get('caract4')!.value),
      (this.account.caract5 = this.caractForm.get('caract5')!.value),
      (this.account.caract6 = this.caractForm.get('caract6')!.value),
      (this.account.caract7 = this.caractForm.get('caract7')!.value),
      (this.account.caract8 = this.caractForm.get('caract8')!.value),
      (this.account.caract9 = this.caractForm.get('caract9')!.value),
      (this.account.caract10 = this.caractForm.get('caract10')!.value),
      this.accountService.save(this.account).subscribe(() => {
        this.success = true;

        this.accountService.authenticate(this.account);
      });
  }
}
