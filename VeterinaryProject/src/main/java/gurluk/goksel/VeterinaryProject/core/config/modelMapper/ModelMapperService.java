package gurluk.goksel.VeterinaryProject.core.config.modelMapper;

import gurluk.goksel.VeterinaryProject.dto.request.Animal.AnimalSaveRequest;
import gurluk.goksel.VeterinaryProject.entity.Animal;
import gurluk.goksel.VeterinaryProject.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModelMapperService implements IModelMapperService {
    private final ModelMapper modelMapper;
    // Constructor to initialize the modelMapper


    public ModelMapper forResponse() {
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.STANDARD);
        return this.modelMapper;
    }

    // Other potential configuration methods
    public ModelMapper forRequest() {
        this.modelMapper.getConfiguration().setAmbiguityIgnored(false).setMatchingStrategy(MatchingStrategies.STRICT);
        return this.modelMapper;
    }
}