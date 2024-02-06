import { KeycloakService } from 'keycloak-angular';
import { environment } from '../../environments/environment';
import { SessionService } from '../services/session/session.service';


export function initializeKeycloak(
  keycloak: KeycloakService,
  sessionService: SessionService,
): () => Promise<void> {
  return async () => {
    await keycloak.init({
      config: {
        url: environment.authenticatorUrl,
        realm: environment.realmName,
        clientId: environment.clientId,
      },
      initOptions: {
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri:
          window.location.origin + '/assets/scripts/verify-sso.html',
      },
      shouldAddToken: (request) => {
        const { method, url } = request;

        const isGetRequest = 'GET' === method.toUpperCase();
        const acceptablePaths = ['/assets/scripts'];
        const isAcceptablePathMatch = acceptablePaths.some((path) =>
          url.includes(path),
        );

        return !(isGetRequest && isAcceptablePathMatch);
      },
    });

    await sessionService.initializeService();
  };
}
