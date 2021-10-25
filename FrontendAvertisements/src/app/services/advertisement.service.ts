import {AppSettings} from "../constants/AppSettings";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Advertisement} from "../models/advertisement";
import {Injectable} from "@angular/core";
import {Category} from "../models/category";

@Injectable({
  providedIn:"root"
})
export class AdvertisementService {
  private advertisementsUrl: string = `${AppSettings.API_ENDPOINT}/advertisement`;

  category?: Category;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  getAdvertisements(): Observable<Advertisement[]> {
    return this.http.get<Advertisement[]>(this.advertisementsUrl);
  }

  getAllByCategoryId(id: number): Observable<Advertisement[]> {
    const url = `${this.advertisementsUrl}/category/${id}`
    return this.http.get<Advertisement[]>(url);
  }

  getAdvertisement(id: number): Observable<Advertisement> {
    const url = `${this.advertisementsUrl}/${id}`;
    return this.http.get<Advertisement>(url);
  }

  updateAdvertisement(advertisement:Advertisement): Observable<any> {
    const url = `${this.advertisementsUrl}/${advertisement.id}`;
    return this.http.put(url, advertisement, this.httpOptions);
  }

  deleteAdvertisement(id: number): Observable<Object> {
    const url = `${this.advertisementsUrl}/${id}`;
    return this.http.delete(url, this.httpOptions);
  }

  saveAdvertisement(advertisement:Advertisement): Observable<Advertisement> {
    const url = `${this.advertisementsUrl}/add`;
    return this.http.post<Advertisement>(url, advertisement, this.httpOptions);
  }

  constructor(private http: HttpClient) { }
}
