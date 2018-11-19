import {browser, by, element} from 'protractor';

export class MainPage {
  navigateTo() {
    return browser.get('/');
  }

  openEditModal() {
    element(by.id('openEditDialog')).click();
  }

  fillEditModal(firstName, lastName, about, children) {
    element(by.name('firstName')).clear();
    element(by.name('firstName')).sendKeys(firstName);
    element(by.name('lastName')).clear();
    element(by.name('lastName')).sendKeys(lastName);
    element(by.name('about')).clear();
    element(by.name('about')).sendKeys(about);
    element(by.name('children')).clear();
    element(by.name('children')).sendKeys(children);
  }

  save() {
    element(by.id('saveUserData')).click();
  }

  getDisplayFullName() {
    return element(by.id('fullName')).getText();
  }

  getDisplayAboutText() {
    return element(by.id('about')).getText();
  }

  getDisplayChildrenCount() {
    console.log('log: ' + element(by.id('manyChildren')).getText());
    return element(by.id('manyChildren')).getText();
  }

}
