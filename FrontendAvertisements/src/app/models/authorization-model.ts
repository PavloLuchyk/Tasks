import {Role} from "./Role";

export interface AuthorizationModel {
  username:string;
  role:Role;
  token?:string;
}
