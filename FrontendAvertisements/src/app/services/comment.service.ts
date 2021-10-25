import {Injectable} from "@angular/core";
import {AppSettings} from "../constants/AppSettings";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Advertisement} from "../models/advertisement";
import {Comment} from "../models/comment";

@Injectable({
  providedIn:"root"
})
export class CommentService {
  private commentsUrl: string = `${AppSettings.API_ENDPOINT}/comment`;

  advertisement?: Advertisement;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  getComments(): Observable<Comment[]> {
    return this.http.get<Comment[]>(this.commentsUrl);
  }

  getAllByAdvertisementId(id: number): Observable<Comment[]> {
    const url = `${this.commentsUrl}/advertisement/${id}`
    return this.http.get<Comment[]>(url);
  }

  getAdvertisement(id: number): Observable<Comment> {
    const url = `${this.commentsUrl}/${id}`;
    return this.http.get<Comment>(url);
  }

  updateAComment(comment:Comment): Observable<any> {
    const url = `${this.commentsUrl}/${comment.id}`;
    return this.http.put(url, comment, this.httpOptions);
  }

  deleteComment(id: number): Observable<Object> {
    const url = `${this.commentsUrl}/${id}`;
    return this.http.delete(url, this.httpOptions);
  }

  saveComment(comment: Comment): Observable<Comment> {
    const url = `${this.commentsUrl}/add`;
    return this.http.post<Comment>(url, comment, this.httpOptions);
  }

  constructor(private http: HttpClient) { }
}
