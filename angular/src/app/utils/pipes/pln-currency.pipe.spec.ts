import { PlnCurrencyPipe } from './pln-currency.pipe';

describe('PlnCurrencyPipe', () => {
  let pipe: PlnCurrencyPipe;

  beforeEach(() => {
    pipe = new PlnCurrencyPipe();
  });

  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('transforms "123" to "123.00 zł"', () => {
    expect(pipe.transform(123)).toBe('123.00 zł');
  });

  it('transforms "123.456" to "123.46 zł"', () => {
    expect(pipe.transform(123.456)).toBe('123.46 zł');
  });

  it('transforms "0" to "0.00 zł"', () => {
    expect(pipe.transform(0)).toBe('0.00 zł');
  });

  it('returns an empty string for null input', () => {
    expect(pipe.transform(null)).toBe('');
  });

  it('returns an empty string for undefined input', () => {
    expect(pipe.transform(undefined)).toBe('');
  });
});
