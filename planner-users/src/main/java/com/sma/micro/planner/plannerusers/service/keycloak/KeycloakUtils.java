package com.sma.micro.planner.plannerusers.service.keycloak;

import com.sma.micro.planner.plannerusers.dto.UserDto;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakUtils {
    private final UsersResource usersResource;
    private final RealmResource realmResource;
    public Response createKeycloakUser(UserDto user) {
        return usersResource.create(createUserRepresentation(user));
    }

    private UserRepresentation createUserRepresentation(UserDto user) {
        var passwordCredential = createPasswordCredentials(user.getPassword());
        var userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(false);
        userRepresentation.setCredentials(List.of(passwordCredential));
        return userRepresentation;
    }

    private CredentialRepresentation createPasswordCredentials(String password) {
        var passwordCredential = new CredentialRepresentation();
        passwordCredential.setTemporary(false);
        passwordCredential.setType(CredentialRepresentation.PASSWORD);
        passwordCredential.setValue(password);
        return passwordCredential;
    }

    public void addRoles(String userId, List<String> userRoles) {
        var kcRoles =  userRoles.stream()
                .map(role -> realmResource.roles().get(role).toRepresentation())
                .toList();
        var user = usersResource.get(userId);
        user.roles().realmLevel().add(kcRoles);
    }

    public Response deleteUser(String id) {
        return usersResource.delete(id);
    }

    public UserRepresentation findUserById(String id) {
        return usersResource.get(id).toRepresentation();
    }

    public List<UserRepresentation> findUser(String text) {
        return usersResource.searchByAttributes(text);
    }

    public void update(UserDto user) {
        var passwordCredential = createPasswordCredentials(user.getPassword());
        var userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setCredentials(List.of(passwordCredential));

        var userResource = usersResource.get(user.getId());
        userResource.update(userRepresentation);
    }
}
