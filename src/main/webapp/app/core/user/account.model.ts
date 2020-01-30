export class Account {
  caract2: any;
  caract3: any;
  caract4: any;
  caract6: any;
  caract5: any;
  caract1: any;
  caract7: any;
  caract8: any;
  caract10: any;
  caract9: any;
  constructor(
    public activated: boolean,
    public authorities: string[],
    public email: string,
    public firstName: string,
    public langKey: string,
    public lastName: string,
    public login: string,
    public imageUrl: string
  ) {}
}
