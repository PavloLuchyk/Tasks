import {AuthorView} from "./author-view";
import {Category} from "./category";

export interface Advertisement {
  id: number;
  title: string;
  description: string;
  createDate: string;
  author: AuthorView;
  category?: Category;
}
