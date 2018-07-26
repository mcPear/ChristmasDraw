import {Injectable} from "@angular/core";
import * as SecureLS from "../../../../node_modules/secure-ls/dist/secure-ls";
import {TranslateService} from "@ngx-translate/core";

@Injectable()

export class AppLanguageStorage {
  langKey = 'draw_app_lang';
  defaultLang = 'en';
  currentLang;
  secureLS = new SecureLS({encodingType: 'aes', isCompression: false});

  constructor(private translate: TranslateService) {
    this.translate.setDefaultLang(this.defaultLang);
    this.setCurrentLanguage();
  }

  setCurrentLanguage() {
    let cachedLang = this.secureLS.get(this.langKey);
    this.currentLang = cachedLang ? cachedLang : this.defaultLang;
    this.translate.use(this.currentLang);
  }

  switchLanguage() {
    if (this.translate.currentLang === 'pl') {
      this.currentLang = 'en';
    } else {
      this.currentLang = 'pl';
    }
    this.secureLS.set(this.langKey, this.currentLang as string);
    this.translate.use(this.currentLang);
  }

}
