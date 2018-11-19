import {by, element} from "protractor";

export module E2EUtils {

  export function login() {
    element(by.id('username')).sendKeys('user1');
    element(by.id('password')).sendKeys('user1');
    element(by.id('kc-login')).click();
  }

}