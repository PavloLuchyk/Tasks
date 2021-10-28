import {Role} from "./Role";

export interface AuthorView {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  createDate: string;
  role: Role;
}
