package brozart.authorization.client;

import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {

    Client findByClientId(String clientId);
}
