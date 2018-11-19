import {MainPage} from './main-page.po';
import {browser} from 'protractor';
import {E2EUtils} from './e2e-utils';

describe('main page', () => {
  let page: MainPage;

  beforeEach(() => {
    page = new MainPage();
  });

  it('should edit and save user data successfully', () => {
    browser.waitForAngularEnabled(false);
    page.navigateTo();
    E2EUtils.login();
    browser.driver.sleep(3000);
    page.openEditModal();
    page.fillEditModal('Jan', 'Kowalski', 'Mam 42 lata i mieszkam w Warszawie.', 2);
    page.save();
    expect(page.getDisplayFullName()).toEqual('Jan Kowalski');
    expect(page.getDisplayAboutText()).toEqual('Mam 42 lata i mieszkam w Warszawie.');
    expect(page.getDisplayChildrenCount()).toEqual('2 children');
  });
});
