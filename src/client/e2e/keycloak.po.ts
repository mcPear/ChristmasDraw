import {browser, by, element} from 'protractor';

export class KeycloakPo {
  navigateToApp() {
    return browser.get('/');
  }

  enterUsername(username) {
    element(by.id('username')).sendKeys(username);
  }

  enterPassword(password) {
    element(by.id('password')).sendKeys(password);
  }

  clickLogin() {
    element(by.id('kc-login')).click();
  }

  getLoggedUserename() {
    return element(by.id('logged-username')).getText();
  }

}
