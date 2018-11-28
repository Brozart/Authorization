package brozart.authorization.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void testFindByClientId() {
        final String client_id = "client_id";

        final Client client = new Client();
        client.setClientId(client_id);
        client.setClientSecret("secret");
        client.setScope("test_scope");
        final Client created = testEntityManager.merge(client);
        testEntityManager.flush();

        final Client result = clientRepository.findByClientId(client_id);
        assertEquals(created.getId(), result.getId());
        assertEquals(client_id, result.getClientId());
        assertEquals(created.getClientSecret(), result.getClientSecret());
        assertEquals(created.getScope(), result.getScope());
    }
}
