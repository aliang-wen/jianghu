<template>
  <div id="mainPageHeader">
    <div class="navBackground">
      <div class="colorShow"></div>
      <div class="navzhongjian">
        <!-- 名字 -->
        <div class="bok_nameDiv">
          <div class="bok_nameClass" @click="gotoWelcome">{{bok_name}}的博客</div>
        </div>
        <!-- 导航 -->
        <div class="navLsit">
          <Menu mode="horizontal" :theme="theme1" :active-name="nav_nameValue">
            <MenuItem name="mainPage" to="/mainPage">
              <Icon type="ios-paper" />
              网站首页
            </MenuItem>
            <Submenu name="3">
              <template slot="title">
                <Icon type="ios-snow-outline" />
                梅花香
              </template>
              <!-- 模块就是相当于不同的网页 -->
              <MenuGroup title="文章模块">
                <!-- 写文章模块 -->
                <MenuItem name="allPagesList" to="/allPagesList">文章总览</MenuItem>
                <MenuItem name="outher">其他功能</MenuItem>
              </MenuGroup>
              <MenuGroup title="资源模块">
                <!-- 这里直接就是相当于另一个模块了重新建立表格写功能后期完善 -->
                <MenuItem name="3-4">工具分享</MenuItem>
                <MenuItem name="3-4">其他功能</MenuItem>
              </MenuGroup>
            </Submenu>

            <MenuItem name="Timeline" to="/Timeline">
              <Icon type="ios-ribbon-outline" />
              时间轴
            </MenuItem>

            <MenuItem name="aboutMyselfInfo" to="/aboutMyselfInfo">
              <Icon type="ios-person-add-outline" />
              关于博主
            </MenuItem>

            <MenuItem name="6">
              <Icon type="md-create" />
              路人留言发帖
            </MenuItem>
          </Menu>
        </div>
        <!-- 搜索 -->
        <div class="findClass">
          <Icon type="md-search" class="searchClass" @click="searchFun()" />
        </div>
      </div>
    </div>
    <!-- 搜索框 -->
    <div class="searchwid">
      <div class="searchDiv">
        <div v-if="searchFlag" class="searchDivH">
          <div class="searchTop">
            <Icon type="md-close" class="closeC" @click="closeFun" />
          </div>
          <div class="searchModdle">
            <!-- <Input v-model="searchValue" search enter-button="搜索相关内容" size="large" placeholder="请输入搜索内容..." @on-search="searchConFun" /> -->
            <AutoComplete transfer v-model="searchValue" size="large" :data="findDatas" :filter-method="filterMethod" placeholder="请输入搜索内容...">
            </AutoComplete><Button class="findButton" @click="searchConFun">搜索相关内容</Button>
          </div>
          <div class="searchButton"></div>
        </div>
      </div>
    </div>
    <Modal v-model="spicalAddress" title="特殊技术基地" @on-ok="enterGoFun" @on-cancel="cancelGoFun">
      <p>Content of dialog</p>
      <p>Content of dialog</p>
      <p>Content of dialog</p>
    </Modal>
  </div>
</template>
<script>
import { getUserInfo } from '../../network/aliangbok_font/mainPage/index';

export default {
  props: {
    navNameVal: {
      type: String
    }
  },
  data() {
    return {
      theme1: 'dark',
      bok_name: '',
      nav_nameValue: '',
      searchFlag: false,
      searchValue: '',
      findDatas: [],
      newVal: '',
      timeTempClickThree: 0,
      clickNumber: 0,
      spicalAddress: false
    };
  },
  mounted() {
    this.nav_nameValue = this.navNameVal;
    this.getUserInfoFun();
  },
  watch: {
    searchValue: function(nval, lval) { // 原来这里还有一个搜索提示
      // 这里的这一段代码感觉很有意思 注意学习！！！！！
      this.newVal = nval;
      var tempVal = this.newVal;
      setTimeout(() => {
        console.log(this.newVal, 'lasttime');
        console.log(tempVal, 'tempTime');
        if (this.newVal === tempVal) {
          // 这里执行请求后台方法
          console.log('这里是停止输入0.5秒后进行查询');
        }
      }, 500);
    },
    navNameVal: function(nval, lval) {
      this.nav_nameValue = nval;
    },
    $route(to, from) {
      if (to.path.indexOf('findEssayList') === -1) {
        this.searchFlag = false;
      }
    },
    deep: true
  },
  methods: {
    searchFun() {
      this.searchFlag = !this.searchFlag;
    },
    closeFun() {
      this.searchFlag = false;
    },
    searchConFun() {
      this.timeTempClickThree = (new Date()).getTime();
      var everyTime = this.timeTempClickThree;
      var _this = this;
      if (_this.searchValue.length > 0) {
        setTimeout(function() {
          if (everyTime === _this.timeTempClickThree) {
            console.log('这里是进行跳转的');
            _this.$router.push({
              path: 'findEssayList',
              query: {
                plan: _this.searchValue
              }
            });
          }
        }, 500);
      } else {
        // 只有是空的时候的搜索才会这样子
        _this.clickNumber = _this.clickNumber + 1;
        setTimeout(function() {
          console.log(_this.clickNumber);
          if (everyTime === _this.timeTempClickThree && _this.clickNumber === 5) {
            _this.spicalAddress = true;
          } else {
            _this.$message.error('请输入搜索内容');
          }
        }, 1000);
      }
    },
    filterMethod(value, option) {
      // 这里是没改变一下input就会触发一下这里的方法
      return option.toUpperCase().indexOf(value.toUpperCase()) !== -1;
    },
    enterGoFun() {
      this.spicalAddress = false;
      this.$message.error('玩笑彩蛋，YOoo!');
    },
    cancelGoFun() {
      this.spicalAddress = false;
      this.$message.error('玩笑彩蛋，YOoo!');
    },
    getUserInfoFun() {
      let params = {
        dynamicParams: this.$store.state.appId
      };
      getUserInfo(params).then(data => {
        console.log(data, '这里的数据信息是什么呀');
        if (data.result.flag === 'success') {
          this.bok_name = data.result.userInfo.bigName;
        } else {
          this.$message.error('获取博主信息失败');
        }
      }).catch(() => {
        this.$message.error('获取博主信息失败');
      });
    },
    gotoWelcome() {
      this.$router.push('/mainPage');
    }
  }
};
</script>
<style lang="scss">
  @import './style.scss';
</style>
