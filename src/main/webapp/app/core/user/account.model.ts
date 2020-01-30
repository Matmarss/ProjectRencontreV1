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
    public caract1: string,
    public caract2: string,
    public caract3: string,
    public caract4: string,
    public caract5: string,
    public caract6: string,
    public caract7: string,
    public caract8: string,
    public caract9: string,
    public caract10: string
  ) {}
}
