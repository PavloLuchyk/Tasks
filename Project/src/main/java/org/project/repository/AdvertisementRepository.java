package org.project.repository;

import org.project.model.Advertisement;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends CrudRepository<Advertisement>, ParentRepository<Advertisement> {
}
