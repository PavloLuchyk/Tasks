package org.project.service.impl;

import org.project.enums.PageSize;
import org.project.model.Advertisement;
import org.project.repository.AdvertisementRepository;
import org.project.service.AdvertisementService;
import org.project.service.CrudServiceGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class AdvertisementServiceImpl extends CrudServiceGeneral<Advertisement> implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    @Autowired
    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository) {
        super(advertisementRepository);
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    @Transactional
    public Advertisement create(Advertisement element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null!");
        }
        return element.setId(Long.parseLong(advertisementRepository.create(element).toString()));
    }

    @Override
    public List<Advertisement> getAllByParentId(long parentId, String parentName) {
        return advertisementRepository.getAllByParentId(parentId, parentName);
    }

    @Override
    public List<Advertisement> getAllByParentIdInPages(long parentId, String parentName, PageSize pageSize, int pageNumber) {
        return advertisementRepository.getAllByParentIdInPages(parentId, parentName,pageSize,pageNumber);
    }

    @Override
    public Long getTotalCountOfPages(long parentId, String parentName, PageSize pageSize) {
        return advertisementRepository.getTotalCountOfPages(parentId,parentName,pageSize);
    }
}
