import {AuthorView} from "./author-view";
import {Category} from "./category";

export interface Advertisement {
  id: number;
  title: string;
  description: string;
  author: AuthorView;
  category: Category;
}
