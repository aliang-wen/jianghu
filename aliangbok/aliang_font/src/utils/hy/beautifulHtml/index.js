const { js_beautify } = require('./beautify.js');
const { html_beautify } = require('./beautify-html.js');
require('./base.js'); // 这里相当于全部引进来了

export const fromttingHtml = (data, config) => {
  let dataTemp = '';
  if (looks_like_html(data)) {
    return html_beautify(data, config);
  } else {
    if (config.detectPackers) {
      dataTemp = unpacker_filter(data);
    }
    return js_beautify(dataTemp, config);
  }
}
const looks_like_html = (source) => {
  var trimmed = source.replace(/^[ \t\n\r]+/, '');
  var comment_mark = '<' + '!-' + '-';
  return (trimmed && (trimmed.substring(0, 1) === '<' && trimmed.substring(0, 4) !== comment_mark));
}

const unpacker_filter = (source) => {
  var trailing_comments = '',
      comment = '',
      unpacked = '',
      found = false;

  // cut trailing comments
  do {
      found = false;
      if (/^\s*\/\*/.test(source)) {
          found = true;
          comment = source.substr(0, source.indexOf('*/') + 2);
          source = source.substr(comment.length).replace(/^\s+/, '');
          trailing_comments += comment + "\n";
      } else if (/^\s*\/\//.test(source)) {
          found = true;
          comment = source.match(/^\s*\/\/.*/)[0];
          source = source.substr(comment.length).replace(/^\s+/, '');
          trailing_comments += comment + "\n";
      }
  } while (found);

  var unpackers = [P_A_C_K_E_R, Urlencoded, JavascriptObfuscator/*, MyObfuscate*/];
  for (var i = 0; i < unpackers.length; i++) {
      if (unpackers[i].detect(source)) {
          unpacked = unpackers[i].unpack(source);
          if (unpacked != source) {
              source = unpacker_filter(unpacked);
          }
      }
  }
  return trailing_comments + source;
}
// 将代码进行压缩
export const pack_js = (data, base64 = 0) => {
  // 0是不加密压缩 1是加密压缩
  var packer = new Packer;
  var output = '';
  if (base64) {
    output = packer.pack(data, 1, 0);
  } else {
    output = packer.pack(data, 0, 0);
  }
  return output;
}

