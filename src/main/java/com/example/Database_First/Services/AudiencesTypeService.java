package com.example.Database_First.Services;

import com.example.Database_First.Model.AudiencesTypeEntity;
import com.example.Database_First.Repository.AudiencesTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AudiencesTypeService {
    @Autowired
    private AudiencesTypeRepository audiencesTypeRepository;

    public List<AudiencesTypeEntity> getAllAudiencesTypes() {
        return audiencesTypeRepository.findAll();
    }

    public Optional<AudiencesTypeEntity> getAudienceTypeById(Long id) {
        return audiencesTypeRepository.findById(id);
    }

    public AudiencesTypeEntity saveAudienceType(AudiencesTypeEntity audienceType) {
        return audiencesTypeRepository.save(audienceType);
    }
}
