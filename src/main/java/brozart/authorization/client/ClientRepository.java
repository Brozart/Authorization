package brozart.authorization.client;

import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {

    /**
     * Finds the client by given client id.
     *
     * @param clientId the client id
     * @return the found client
     */
    Client findByClientId(String clientId);
}
