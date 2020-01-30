import { ICaracteristique } from 'app/shared/model/caracteristique.model';
import { Genre } from 'app/shared/model/enumerations/genre.model';

export interface IPersonne {
  id?: number;
  nom?: string;
  prenom?: string;
  mail?: string;
  genre?: Genre;
  motDePasse?: string;
  naissance?: string;
  listCarac?: string;
  listCaracs?: ICaracteristique[];
}

export class Personne implements IPersonne {
  constructor(
    public id?: number,
    public nom?: string,
    public prenom?: string,
    public mail?: string,
    public genre?: Genre,
    public motDePasse?: string,
    public naissance?: string,
    public listCarac?: string,
    public listCaracs?: ICaracteristique[]
  ) {}
}
