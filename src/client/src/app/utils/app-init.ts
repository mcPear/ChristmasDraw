/**
 * Created by maciej on 14.03.18.
 */
import {KeycloakService} from "keycloak-angular";

export function initializer(keycloak: KeycloakService): () => Promise<any> {
  return (): Promise<any> => {
    return new Promise(async(resolve, reject) => {
      try {
        await keycloak.init({
          config: {
            url: 'http://localhost:8080/auth',
            realm: 'ChristmasDrawRealm',
            clientId: 'cd-frontend',
            credentials: {
              secret: 'b547064f-123b-41c1-abc4-a80d186c0d12'
            },
          },
          initOptions: {
            onLoad: 'login-required',
            checkLoginIframe: false
          }
        });
        resolve();
      } catch (error) {
        reject(error);
      }
    });
  };
}
