import { Injectable } from '@angular/core';
import { AccountService } from '../auth/account.service';
import { AuthServerProvider } from '../auth/auth-jwt.service';
import { Inscription } from './inscription.model';
import { Observable } from 'rxjs';

import { Account } from 'app/core/user/account.model';
import { SERVER_API_URL } from 'app/app.constants';

@Injectable({ providedIn: 'root' })
export class InscriptionService {
  post: any;
  constructor(private accountService: AccountService, private authServerProvider: AuthServerProvider) {}

  inscription(credentials: Inscription): Observable<Account | null> {
    return this.post(SERVER_API_URL + 'api/register');
  }
}
