export interface Advertisement {
  id: number;
  title: string;
  description: string;
  createDate: string;
  authorId: number;
  fullName: string;
  categoryId?: number;
  categoryName?: string;
}
