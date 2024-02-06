import { ActivatedRouteSnapshot, CanActivateFn, RouterStateSnapshot } from '@angular/router';

export const validUuidGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot,
) => {
  const eventId = route.paramMap.get('eventId');
  if (isValidUuid(eventId)) {
    return true;
  } else {
    return false;
  }
};

function isValidUuid(uuid: string | null): boolean {
  const regex = /^[0-9a-f]{8}-[0-9a-f]{4}-[4][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/i;
  return regex.test(uuid ?? '');
}
