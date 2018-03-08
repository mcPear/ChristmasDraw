import { ChristmasDrawClientPage } from './app.po';

describe('christmas-draw-client App', function() {
  let page: ChristmasDrawClientPage;

  beforeEach(() => {
    page = new ChristmasDrawClientPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
