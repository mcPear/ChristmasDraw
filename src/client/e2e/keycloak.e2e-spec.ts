import {browser} from 'protractor';
import {KeycloakPo} from "./keycloak.po";

describe('main page', () => {
  let po: KeycloakPo;

  beforeEach(() => {
    po = new KeycloakPo();
  });

  it('should log in successfully', () => {
    browser.waitForAngularEnabled(false);
    po.navigateToApp();
    po.enterUsername('user1');
    po.enterPassword('user1');
    po.clickLogin();
    browser.driver.sleep(1000);
    expect(po.getLoggedUserename).toEqual('user1');
  });
});
