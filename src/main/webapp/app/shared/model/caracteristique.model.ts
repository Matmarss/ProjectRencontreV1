import { IPersonne } from 'app/shared/model/personne.model';
import { Caracteristiques } from 'app/shared/model/enumerations/caracteristiques.model';

export interface ICaracteristique {
  id?: number;
  carac?: Caracteristiques;
  personne?: IPersonne;
}

export class Caracteristique implements ICaracteristique {
  constructor(public id?: number, public carac?: Caracteristiques, public personne?: IPersonne) {}
}
