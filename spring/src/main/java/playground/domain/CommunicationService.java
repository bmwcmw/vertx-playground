package playground.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import playground.repository.CommunicationRepository;

@Service
public class CommunicationService {

    @Autowired
    private CommunicationRepository communicationRepository;

    public String getAllDspThings() {
        return communicationRepository.getResult().encode();
    }

}