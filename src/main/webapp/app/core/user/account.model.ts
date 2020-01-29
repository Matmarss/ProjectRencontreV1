import { Caracteristiques } from 'app/shared/model/enumerations/caracteristiques.model';
export class Account {
  constructor(
    public activated: boolean,
    public authorities: string[],
    public email: string,
    public firstName: string,
    public langKey: string,
    public lastName: string,
    public login: string,
    public imageUrl: string,
    public genre: string,
    public naissance: Date,
    public caracteristiques: string[]
  ) {}
}
