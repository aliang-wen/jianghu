// https://eslint.org/docs/user-guide/configuring

module.exports = {
  root: true,
  parserOptions: {
    parser: 'babel-eslint'
  },
  env: { // 要指定配置文件中的环境，请使用该env键并通过将每个环境设置为指定要启用的环境true
    browser: true,
    node: true,
    es6: true
  },
  globals: { // 配置全局变量
    hy: true,
    hyNetWork: true,
    $: true,
    ModalBox: true,
    E: true,
    XSS: true

  },
  // https://github.com/vuejs/eslint-plugin-vue#priority-a-essential-error-prevention
  // consider switching to `plugin:vue/strongly-recommended` or `plugin:vue/recommended` for stricter rules.
  extends: ['plugin:vue/essential', 'airbnb-base', 'eslint:recommended'],
  // required to lint *.vue files
  plugins: [ // 如果要使用插件中的环境，请确保在plugins数组中指定插件名称，然后使用未加前缀的插件名称，后跟斜杠，后跟环境名称。
    'vue'
  ],
  // check if imports actually resolve
  settings: {
    'import/resolver': {
      webpack: {
        config: 'build/webpack.base.conf.js'
      }
    }
  },
  // add your custom rules here
  rules: {
    // don't require .vue extension when importing
    'import/extensions': ['error', 'always', {
      js: 'never',
      vue: 'never'
    }],
    // disallow reassignment of function parameters
    // disallow parameter object manipulation except for specific exclusions
    'no-param-reassign': ['error', {
      props: true,
      ignorePropertyModificationsFor: [ // 这里是忽略检测的属性
        'state', // for vuex state
        'acc', // for reduce accumulators
        'e', // for e.returnvalue
        'Vue', // vue
        'config',
        'parms'
      ]
    }],
    // allow optionalDependencies
    'import/no-extraneous-dependencies': ['error', {
      optionalDependencies: ['test/unit/index.js']
    }],
    // allow debugger during development
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    // 下面是自己添加的规则
    'linebreak-style': [0 ,'error', 'windows'],  // 解决了Expected linebreaks to be 'CRLF' but found 'LF' 就是不同系统和编辑器下面的换行符不一样的问题（文本编辑的时候提示报错）
    'operator-linebreak': [ // 解决了Expected linebreaks to be 'CRLF' but found 'LF' 就是不同系统和编辑器下面的换行符不一样的问题(这里解决的是控制台显示的报错)
      2,
      'after',
      {
        overrides: {
          '?': 'before',
          ':': 'before'
        }
      }
    ],
    'valid-jsdoc': 1, // jsdoc规则 打开规则不符合仅仅是警告而不是报错 
    'accessor-pairs': 2, // 在对象中使用getter/setter 打开规则 检测不通过作为一个错误处理
    'arrow-spacing': [ // =>的前/后括号 要求前后都有
      2,
      {
        before: true,
        after: true
      }
    ],
    'block-spacing': [2, 'always'], // ？？？？？这里的意思不知道 块状空格什么鬼
    'brace-style': [ // 大括号的风格 这个是什么风格（sss() {}）
      2,
      '1tbs',
      {
        allowSingleLine: true
      }
    ],
    camelcase: [ // 强制驼峰的命名打开 不符合报错
      0,
      {
        properties: 'always'
      }
    ],
    'comma-dangle': [2, 'never'], //对象字面量项尾不能有逗号 一个对象中的属性都是以逗号间隔开来的 这个属性使得最后一个不用添加逗号
    'comma-spacing': [ // 逗号前后的空格 前面没有后面有
      2,
      {
        before: false,
        after: true
      }
    ],
    'comma-style': [2, 'last'], //逗号风格，换行时在行首还是行尾 打开 在尾部 不符合报错
    'constructor-super': 2, // 非派生类不能调用super，派生类必须调用super  打开 不符合报错 不明白什么意思
    'curly': [2, 'multi-line'], // 必须使用 if(){} 中的{} 打开 不符合报错 (multi-line all) 这两个是什么属性不知道
    'dot-location': [2, 'property'], // 对象访问符的位置，换行的时候在行首还是行尾 打开不符合报错 什么意思？？
    'eol-last': 2, // 文件以单一的换行符结束 不明白什么意思
    eqeqeq: [2, 'allow-null'], // 必须采取全等于 ===
    'generator-star-spacing': [ // 生成器函数*的前后空格 函数的前后都有空格
      2,
      {
        before: true,
        after: true
      }
    ],
    'handle-callback-err': [2, '^(err|error)$'], // node.js处理错误 什么意思不知道
    indent: [ // 设置代码的缩进风格 这个设置看不懂
      2,
      2,
      {
        SwitchCase: 1
      }
    ],
    'key-spacing': [ // 对象字面量中冒号的前后空格 打开 不符合报错 前没有后有
      2,
      {
        beforeColon: false,
        afterColon: true
      }
    ],
    'keyword-spacing': [ // 关键字前后要有空格
      2,
      {
        before: true,
        after: true
      }
    ],
    'new-cap': [ // 函数名首行大写必须使用new方式调用，首行小写必须用不带new方式调用 不懂
      2,
      {
        newIsCap: true,
        capIsNew: false
      }
    ],
    'new-parens': 2, // new时必须加小括号
    'no-array-constructor': 2, // 禁止使用数组构造器
    'no-caller': 2, // 禁止使用arguments.caller或arguments.callee
    'no-console': 0, // 禁止使用console
    'no-class-assign': 2, // 禁止给类赋值
    'no-cond-assign': 2, // 禁止在条件表达式中使用赋值语句(厉害 严谨)
    'no-const-assign': 2, // 禁止修改const声明的变量
    'no-control-regex': 2, // 禁止在正则表达式中使用控制字符
    'no-delete-var': 2, // 不能对var声明的变量使用delete操作符
    'no-dupe-args': 2,//函数参数不能重复
    'no-dupe-class-members': 2, // 如果类成员中存在相同名称的声明，则最后一个声明将以静默方式覆盖其他声明。它可能会导致意外的行为。
    'no-dupe-keys': 2, // 在创建对象字面量时不允许键重复 {a:1,a:1}
    'no-duplicate-case': 2, // switch中的case标签不能重复
    'no-empty-character-class': 2, // 正则表达式中的[]内容不能为空
    'no-empty-pattern': 2, // 使用解构时，可以创建一个无效的模式。发生这种情况时，将空花括号用于嵌入对象解构模式的右侧，例如：var {a: {}} = foo;
    'no-eval': 2, // 禁止使用eval
    'no-ex-assign': 2, // 禁止给catch语句中的异常参数赋值
    'no-extend-native': 2, // 禁止扩展native对象
    'no-extra-bind': 2, // 禁止不必要的函数绑定
    'no-extra-boolean-cast': 2, // 禁止不必要的bool转换
    'no-extra-parens': [2, 'functions'], // 禁止非必要的括号
    'no-fallthrough': 2, // 禁止switch穿透
    'no-floating-decimal': 2, // 禁止省略浮点数中的0 .5 3.
    'no-func-assign': 2, // 禁止重复的函数声明
    'no-implied-eval': 2, // 禁止使用隐式eval
    'no-inner-declarations': [2, 'functions'], // 禁止在块语句中使用声明（变量或函数）
    'no-invalid-regexp': 2, // 禁止无效的正则表达式
    'no-irregular-whitespace': 2, // 不能有不规则的空格
    'no-iterator': 2, // 禁止使用__iterator__ 属性
    'no-label-var': 2, // label名不能与var声明的变量名相同
    'no-labels': [ // 禁止标签声明
      2,
      {
        allowLoop: false,
        allowSwitch: false
      }
    ],
    'no-lone-blocks': 2, // 禁止不必要的嵌套块
    'no-mixed-spaces-and-tabs': 2, // 禁止使用tab和空格
    'no-multi-spaces': 1, // 不能用多余的空格
    'no-multi-str': 2, // 字符串不能用\换行
    'no-multiple-empty-lines': [1, {'max': 2}], // 空行最多不能超过2行
    'no-native-reassign': 2, // 不能重写native对象
    'no-negated-in-lhs': 2, // in 操作符的左边不能有!
    'no-new-object': 2, // 禁止使用new Object() var a = {} var b = new Object() 这两个没有初始值的话是一样的 但是 {}要快一些
    'no-new-require': 2, // 禁止使用new require
    'no-new-symbol': 2, // Symbol不是要与new操作员一起使用，而是作为一种功能调用。var foo = new Symbol('foo'); 这会引发TypeError异常。
    'no-new-wrappers': 2, // 禁止使用new创建包装实例，new String new Boolean new Number
    'no-obj-calls': 2, // 不能调用内置的全局对象，比如Math() JSON()
    'no-octal': 2, // 禁止使用八进制数字
    'no-octal-escape': 2, // 禁止使用八进制转义序列
    'no-path-concat': 0, // node中不能使用__dirname或__filename做路径拼接
    'no-proto': 2, // 禁止使用__proto__属性
    'no-redeclare': 2, //禁止重复声明变量
    'no-regex-spaces': 2, //禁止在正则表达式字面量中使用多个空格 /foo bar/
    'no-return-assign': [2, 'except-parens'], // return 中不能有赋值
    'no-self-assign': 2, // 禁止自我赋值
    'no-self-compare': 2, // 不能比较自身
    'no-sequences': 2, // 禁止使用逗号运算符
    'no-shadow-restricted-names': 2, // 严格模式中规定的限制标识符不能作为声明时的变量名使用
    'no-spaced-func': 2, // 函数调用时 函数名与()之间不能有空格
    'no-sparse-arrays': 2, // 禁止稀疏数组， [1,,2]
    'no-trailing-spaces': 2, // 一行结束后面不要有空格
    'no-this-before-super': 2, // 在调用super()之前不能使用this或super
    'no-throw-literal': 2, // 禁止抛出字面量错误 throw 'error';
    'no-undef': 2,//不能有未定义的变量
    'no-undef-init': 2, // 变量初始化时不能直接给它赋值为undefined
    // 'no-undefined': 2, //不 能使用undefined
    'no-unexpected-multiline': 2, // 避免多行表达式
    'no-unmodified-loop-condition': 2, // 变量必须在循环条件中进行修改
    'no-unneeded-ternary': [ // 禁止不必要的嵌套 var isYes = answer === 1 ? true : false;
      2,
      {
        defaultAssignment: false
      }
    ],
    'no-unreachable': 2, // 不能有无法执行的代码
    'no-unsafe-finally': 2, // 规定finally 代码块中不要再改变程序执行流程 finally中不要有return
    'no-unused-vars': [ // 不能有声明而没有被使用的变量
      2,
      {
        vars: 'all',
        args: 'none'
      }
    ],
    'no-useless-call': 2,//禁止不必要的call和apply
    'no-useless-computed-key': 2, // 没有必要使用带文字的计算属性 var foo = {['a']: 'b'}; 改 var foo = {'a': 'b'};
    'no-useless-constructor': 2, // 如果没有指定，ES2015 会提供默认的类构造函数
    'no-useless-escape': 0, // 转义字符串，模板文字和正则表达式中的非特殊字符不会产生任何影响
    'no-whitespace-before-property': 2, // JavaScript 允许在对象和属性之间留出空白。但是，不一致的间距会使代码更难以阅读，并可能导致错误
    'no-with': 2, // 禁用with
    'one-var': [ // 连续声明
      2,
      {
        initialized: 'never'
      }
    ],
    'operator-linebreak': [ // 换行时运算符在行尾还是行首
      2,
      'after',
      {
        overrides: {
          '?': 'before',
          ':': 'before'
        }
      }
    ],
    'padded-blocks': 2, // 块语句内行首行尾是否要空行
    quotes: [ //引号类型
      2,
      'single',
      {
        avoidEscape: true,
        allowTemplateLiterals: true
      }
    ],
    'semi': [2, 'always'], // 语句强制分号结尾
    'semi-spacing': [0, {'before': false, 'after': true}], // 分号前后空格
    'space-before-blocks': [2, 'always'], // 不以新行开始的块{前面要不要有空格
    'space-before-function-paren': [2, 'never'],//函数定义时括号前面要不要有空格
    'space-in-parens': [2, 'never'], // 小括号里面要不要有空格
    'space-infix-ops': 2, // 中缀操作符周围要不要有空格
    'space-unary-ops': [ // //一元运算符的前/后要不要加空格
      2,
      {
        words: true,
        nonwords: false
      }
    ],
    'spaced-comment': [ // 注释风格不要有空格
      2,
      'always',
      {
        markers: [
          'global',
          'globals',
          'eslint',
          'eslint-disable',
          '*package',
          '!',
          ','
        ]
      }
    ],
    'template-curly-spacing': [2, 'never'], // 禁止使用一对${和嵌入模板字符串中的表达式}。let hello = `hello, ${people.name}!`;
    'use-isnan': 2, // 禁止比较时使用NaN，只能用isNaN()
    'valid-typeof': 2, // 必须使用合法的typeof的值
    'yield-star-spacing': [2, 'both'], // 立即执行函数表达式的小括号风格
    yoda: [2, 'never'], // 禁止尤达条件
    "prefer-const": 0, // 首选const
    'object-curly-spacing': [ // 大括号内是否允许不必要的空格 
      2,
      'always',
      {
        objectsInObjects: false
      }
    ],
    "array-bracket-spacing": [2, "never"], // 是否允许非空数组里面有多余的空格
    "vue/no-parsing-error": [2, { "x-invalid-end-tag": false }] //  iView将标签渲染为原生html标签时，由于这些标签是自闭合的，所以有end标签会报错。
  }
}
