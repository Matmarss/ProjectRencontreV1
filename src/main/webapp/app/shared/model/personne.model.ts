import { Caracteristiques } from 'app/shared/model/enumerations/caracteristiques.model';

export interface IPersonne {
  id?: number;
  nom?: string;
  prenom?: string;
  mail?: string;
  genre?: string;
  motDePasse?: string;
  naissance?: string;
  listCarac?: Caracteristiques;
}

export class Personne implements IPersonne {
  constructor(
    public id?: number,
    public nom?: string,
    public prenom?: string,
    public mail?: string,
    public genre?: string,
    public motDePasse?: string,
    public naissance?: string,
    public listCarac?: Caracteristiques
  ) {}
}
