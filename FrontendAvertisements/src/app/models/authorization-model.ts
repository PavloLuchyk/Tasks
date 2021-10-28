import {Role} from "./Role";

export interface AuthorizationModel {
  id: number;
  username:string;
  role:Role;
  token?:string;
}
