import CryptoJS from 'crypto-js/crypto-js';

/**
 * AES加密 ：字符串 key iv  返回base64
 */

export const Encrypt = (wordIn, keyStr = '1234567890123456', ivStr = '1234567890123456') => {
  let key = CryptoJS.enc.Utf8.parse(keyStr);
  let iv = CryptoJS.enc.Utf8.parse(ivStr);
  let srcs = CryptoJS.enc.Utf8.parse(wordIn);
  var encrypted = CryptoJS.AES.encrypt(srcs, key, {
    iv: iv,
    mode: CryptoJS.mode.CBC,
    padding: CryptoJS.pad.ZeroPadding
  });
  // console.log("-=-=-=-", encrypted.ciphertext)
  return CryptoJS.enc.Base64.stringify(encrypted.ciphertext);
};

/**
 * AES 解密 ：字符串 key iv  返回base64
 *
 */

export const Decrypt = (wordIn, keyStr = '1234567890123456', ivStr = '1234567890123456') => {
  let key = CryptoJS.enc.Utf8.parse(keyStr);
  let iv = CryptoJS.enc.Utf8.parse(ivStr);

  let base64 = CryptoJS.enc.Base64.parse(wordIn);
  let src = CryptoJS.enc.Base64.stringify(base64);
  var decrypt = CryptoJS.AES.decrypt(src, key, {
    iv: iv,
    mode: CryptoJS.mode.CBC,
    padding: CryptoJS.pad.ZeroPadding
  });

  var decryptedStr = decrypt.toString(CryptoJS.enc.Utf8);
  return decryptedStr.toString();
};
export default { Encrypt, Decrypt };
