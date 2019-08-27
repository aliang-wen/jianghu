<template>
  <div id="aboutMyselfInfoId">
    <div class="showMainPic">
      <div class="showPicClass">
        <div id="showHeaderPicHeight"></div>
        <div id="showHeaderPic" style="display:none;">
          <Avatar :src="headerPicUrl || 'https://i.loli.net/2017/08/21/599a521472424.jpg'" display:none size="large" />
        </div>
        <div style="height: 5vh;"></div>
        <div class="showBaseInfoClass" style="display:none;">
          <ul>
            <li>{{bokName}}</li>
            <li class="colorFontClass">{{bokFlag}}</li>
          </ul>
        </div>
        <div class="nextPageClass" style="display:none;">
          <a @click="scrollPositionFun(1)">
            <Icon type="md-arrow-round-down" />
          </a>
        </div>
      </div>
    </div>
    <div class="showMainPicTwo">
      <div class="showPicClassTwo">
        <Row>
          <Col span="1">
            &nbsp;
          </Col>
          <Col span="6">
            <div class="describeMyselfClass">
              <h3>关于自己的技能介绍</h3>
              <div>
                {{aboutMyselfSkillDic}}
              </div>
            </div>
          </Col>
          <Col span="1">&nbsp;</Col>
          <Col span="15">
            <div class="activeShwoClass">
              <ul>
                <li v-for="(da, index) in skillShowDatas" :key="'T' + index">
                  {{da.skName}}：<Progress :percent="da.skScore" :stroke-color="functionColor(da.skScore)" />
                </li>
              </ul>
              </Form>
            </div>
          </Col>
          <Col span="1">
            &nbsp;
          </Col>
        </Row>
        <div class="nextPageClassTwo" style="display:none;">
          <a @click="scrollPositionFun(2)">
            <Icon type="md-arrow-round-down" />
          </a>
        </div>
      </div>
    </div>
    <div class="showMainPicThree">
      <div class="showPicClassThree">
        <Row>
          <Col span="1">
            &nbsp;
          </Col>
          <Col span="22">
            <div class="showMyselfClass">
              <h1>自我介绍</h1>
              <div>
                {{aboutmyself}}
              </div>
            </div>
          </Col>
          <Col span="1">
            &nbsp;
          </Col>
        </Row>
      </div>
    </div>
  </div>
</template>
<script>
import { findShowUserMainPageAboutMyselfCon } from '../../../network/aliangbok_font/mainPage/index';

export default {
  data() {
    return {
      headerPicUrl: '',
      timeTemp: 0,
      scrollTopValue: 0,
      // 红 橙 黄 绿 青 蓝 紫
      colorArr: [
        'red',
        'orange',
        'yellow',
        'green',
        'Aqua',
        '#0066FF',
        'blue',
        'BlueViolet',
        '#C0C0C0',
        '#000000'
      ],
      skillShowDatas: [
      ],
      bokName: '',
      bokFlag: '',
      aboutMyselfSkillDic: '',
      aboutmyself: ''
    };
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      this.scrollPositionFun(0);
      // 窗口添加滚动时间
      window.addEventListener('scroll', this.handleScroll);
      $('#showHeaderPic').fadeIn(1000);
      setTimeout(() => {
        $('.showBaseInfoClass').fadeIn(500);
      }, 1000);
      setTimeout(() => {
        $('.nextPageClass').fadeIn();
      }, 1500);
      $('#showHeaderPicHeight').animate({ height: '12vh', opacity: '0.4' }, 'slow');
      this.findShowUserMainPageAboutMyselfConFun();
    },
    scrollPositionFun(val) {
      let param = 117 + window.innerHeight * val;
      const sTop = document.documentElement.scrollTop || document.body.scrollTop;
      this.scrollTop(window, sTop, param, 1000);
    },
    // vue滚动到 指定位置
    scrollTop(el, from = 0, to, duration = 500, endCallback) {
      if (!window.requestAnimationFrame) {
        window.requestAnimationFrame = (
          window.webkitRequestAnimationFrame ||
          window.mozRequestAnimationFrame ||
          window.msRequestAnimationFrame ||
          function(callback) {
            return window.setTimeout(callback, 1000 / 60);
          }
        );
      }
      const difference = Math.abs(from - to);
      const step = Math.ceil(difference / duration * 50);
      function scroll(start, end, step) {
        if (start === end) {
          endCallback && endCallback();
          return;
        }
        let d = (start + step > end) ? end : start + step;
        if (start > end) {
          d = (start - step < end) ? end : start - step;
        }
        if (el === window) {
          window.scrollTo(d, d);
        } else {
          el.scrollTo(d, 0);
        }
        window.requestAnimationFrame(() => scroll(d, end, step));
      }
      scroll(from, to, step);
    },
    handleScroll() {
      this.scrollTopValue = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
      this.timeTemp = new Date().getTime();
      var timep = this.timeTemp;
      setTimeout(() => {
        if (this.timeTemp === timep) {
          if (this.scrollTopValue > 117 && this.scrollTopValue < window.innerHeight / 2 + 117) {
            this.scrollPositionFun(0);
          } else if (this.scrollTopValue > window.innerHeight / 2 + 117 && this.scrollTopValue < window.innerHeight / 2 * 3 + 117) {
            setTimeout(() => {
              $('.nextPageClassTwo').fadeIn();
            }, 500);
            this.scrollPositionFun(1);
          } else if (this.scrollTopValue > window.innerHeight / 2 * 3 + 117 && this.scrollTopValue < window.innerHeight * 2 + 117) {
            this.scrollPositionFun(2);
          }
        }
      }, 100);
    },
    functionColor(val) {
      let num = val * 1;
      let tempNum = Math.ceil(num / 10) - 1;
      return this.colorArr[tempNum];
    },
    findShowUserMainPageAboutMyselfConFun() {
      let params = {
        dynamicParams: this.$store.state.appId
      };
      findShowUserMainPageAboutMyselfCon(params).then(data => {
        if (data.result.flag === 'success') {
          this.headerPicUrl = data.result.userInfo.bokHeaderPic.samllPicInfo || '';
          this.bokName = data.result.userInfo.bigName || '';
          this.bokFlag = data.result.userInfo.bokFlag || '';
          this.aboutMyselfSkillDic = data.result.userInfo.bokSkill || '';
          this.skillShowDatas = data.result.userInfo.skillList || [];
          this.aboutmyself = data.result.userInfo.aboutMyself || [];
        } else {
          this.$message.error(data.result.message);
        }
      }).catch(() => {
        this.$message.error('获取博主信息失败');
      });
    }
  },
  destroyed() {
    window.removeEventListener('scroll', this.handleScroll);
  }
};
</script>
<style lang="scss">
@import './style.scss';
</style>
