package brozart.authorization.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.oauth2.provider.ClientDetails;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    public void testFindByClientId() {
        final String clientID = "clientID";

        final Client client = new Client();
        client.setId(1L);
        client.setClientId(clientID);
        client.setClientSecret("secret");
        client.setScope("test_scope");

        when(clientRepository.findByClientId(clientID)).thenReturn(client);

        final ClientDetails result = clientService.loadClientByClientId(clientID);

        // Verify
        assertEquals(clientID, result.getClientId());
        assertTrue(result.isSecretRequired());
        assertEquals(client.getClientSecret(), result.getClientSecret());
        assertTrue(result.isScoped());
        assertTrue(result.getScope().contains(client.getScope()));
    }
}
