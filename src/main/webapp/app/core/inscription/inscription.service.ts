import { Injectable } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AccountService } from '../auth/account.service';
import { AuthServerProvider } from '../auth/auth-jwt.service';
import { Inscription } from './inscription.model';
import { Observable } from 'rxjs';
import { flatMap } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class InscriptionModalService {
  private isOpen = false;

  constructor(private accountService: AccountService, private authServerProvider: AuthServerProvider) {}

  inscription(credentials: Inscription): Observable<Account | null> {
    return this.authServerProvider.inscription(credentials).pipe(flatMap(() => this.accountService.identity(true)));
  }
}
