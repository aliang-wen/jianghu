/* eslint-disable */
import base from './base';
import crypto from './crypto';
import database from './database';
import { fromttingHtml, pack_js } from './beautifulHtml/index';
const hy = {
  version: '0.8',
  base: base,
  crypto: crypto,
  db: database,
  fromttingHtml: fromttingHtml,
  pack_js: pack_js
}

export default hy