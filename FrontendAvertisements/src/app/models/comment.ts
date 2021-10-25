import {AuthorView} from "./author-view";
import {Advertisement} from "./advertisement";

export interface Comment{
  id: number;
  text: number;
  createDate: string;
  author: AuthorView;
  advertisement: Advertisement;
}
