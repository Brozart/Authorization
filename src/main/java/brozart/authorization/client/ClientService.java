package brozart.authorization.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService implements ClientDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class.getName());

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(final ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDetails loadClientByClientId(final String clientId) throws ClientRegistrationException {
        final Client clientById = clientRepository.findByClientId(clientId);
        return new CustomClientDetails(clientById);
    }
}
