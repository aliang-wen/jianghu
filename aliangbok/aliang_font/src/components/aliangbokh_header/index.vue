<template>
  <div id="alianghHeader" class="publicClass">
    <div class="showTimeClass">
      <Poptip trigger="hover" title="温情提示" placement="bottom-end">
        <span class="showTitle">{{userName}}，{{timeDayNow}}好</span>
        <div class="showWen" slot="content">
          {{popcontent}}
        </div>
      </Poptip>
      <br/>
      <span class="showTimeNow" v-html = "nowTime"></span>
    </div>
    <div class="showWeatherClass">
      <div class="showcurrty textOverHidden">
        <span class="currtCity" title="点击修改城市" @click="changeCity(currentyWeather.city)">{{currentyWeather.city}}</span>
        <span class="weatherFont">&nbsp;&nbsp;当前温度是:{{currentyWeather.wendu}}℃</span>
        <br>
        <span class="weatherFont tishi donTwoLine" :title="currentyWeather.ganmao">{{currentyWeather.ganmao}}</span>
      </div>
      <div class="showAllDay">
        <Carousel v-model="dataWeatherChoose" loop dots="none">
          <CarouselItem v-for="p in dateWeacher" :key="p.key">
            <div class="showInfo">
              {{p.date}}
              <br>
              {{p.high}}
              {{p.low}}
              <br>
              {{p.type}}
              {{p.fengxiang? p.fengxiang : p.fx}}
              {{funThrowCsata(p.fengli? p.fengli : p.fl)}}
            </div>
          </CarouselItem>
        </Carousel>
      </div>
    </div>
    <div class="loginOutClass" @click="loginOutFun">
      <Icon type="md-exit" />
    </div>
    <div class="showPeoplePicClass">
      <Poptip title="操作" placement="bottom-end" trigger="hover">
        <Avatar :src="headerPicUrl || 'https://i.loli.net/2017/08/21/599a521472424.jpg'" />
        <div class="showWen" slot="content">
          <CellGroup>
            <Cell title="主页" to="/back/welcome" />
            <Cell title="设置页面" />
            <span @click="loginOutFun">
              <Cell title="退出" />
            </span>
          </CellGroup>
        </div>
      </Poptip>
    </div>
    <!-- 模态框弹出 -->
    <Modal v-model="modal1" title="修改当前天气显示的城市" @on-ok="enterChangeCity" @on-cancel="cancelChangeCity">
      <div style="height: 240px;">
        请选择当前地区:
        <Cascader :data="AreaData" v-model="userAddress" change-on-select :render-format="formatChose">
        </Cascader>
      </div>
    </Modal>
  </div>
</template>
<script>
import { oclock, getWeatherFromUrl, findAllChianArea } from '../../network/public';
import { findAllUserInfoCon } from '../../network/aliangbok_back/aliangbokCo/welcome/index';

export default {
  data() {
    return {
      true: true,
      nowTime: '',
      userName: '',
      timeDayNow: '',
      popcontent: '',
      dataWeatherChoose: -1,
      dateWeacher: [],
      currentyWeather: {
        city: '',
        ganmao: '',
        wendu: ''
      },
      modal1: false,
      AreaData: [],
      currentCity: '',
      addressTemp: [],
      userAddress: [],
      headerPicUrl: ''
    };
  },
  mounted() {
    // 时间展示
    this.nowTimes();
    this.$nextTick(function() {
      setInterval(this.nowTimes, 1000);
    });
    this.dataWeatherChoose = 1;
    // 获取地区字典项
    this.findAllChianAreaFun();
  },
  methods: {
    nowTimes() {
      let timeTemp = oclock();
      let getTimeArr = timeTemp.split('-');
      this.nowTime = getTimeArr[0];
      this.popcontent = getTimeArr[1];
      let timeDay = this.nowTime.split('&nbsp;&nbsp;');
      this.timeDayNow = timeDay[3];
    },
    loginOutFun() {
      ModalBox.confirm({
        title: '是否退出系统',
        content: '<p>请问您打算退出吗</p>',
        onOk: () => {
          this.$router.push('/login');
        },
        onCancel: () => {
          this.$message.error('没事别瞎点哈');
        }
      });
    },
    getWeatherFromUrlFun(val) {
      getWeatherFromUrl({ city: val }).then(data => {
        console.log(data);
        if (data.desc === 'OK') {
          this.dateWeacher = [];
          this.currentyWeather.city = data.data.city;
          this.currentyWeather.ganmao = data.data.ganmao;
          this.currentyWeather.wendu = data.data.wendu;
          this.dateWeacher[0] = data.data.yesterday;
          for (let i = 0; i < data.data.forecast.length; i++) {
            this.dateWeacher[i + 1] = data.data.forecast[i];
          }
        } else {
          this.$message.error('获取天气信息失败');
        }
      }).catch(() => {
        this.$message.error('获取天气信息失败');
      });
    },
    getUserBokInfo() {
      findAllUserInfoCon({ dynamicParams: this.$store.state.appId, loginToken: sessionStorage.getItem('loginToken') }).then(data => {
        console.log(data);
        if (data.result.flag === 'success') {
          this.userName = data.result.bokuser.bigName || '';
          this.headerPicUrl = data.result.bokuser.bokHeaderPic.samllPicInfo || '';
          // 代码解析方法
          var userAddressTemp = (data.result.bokuser.userAddress || '').split('-');
          if (userAddressTemp[0] === '') {
            this.userAddress = [];
          } else {
            this.userAddress = userAddressTemp;
          }
          this.addressTemp = [];
          this.getAddressChinese(0, this.userAddress, this.userAddress.length, this.AreaData);
          // 获取天气
          let city = this.addressTemp[this.addressTemp.length - 1];
          if (city === undefined || city === null || city === '' || city.indexOf('省') > 0 || city.indexOf('国') > 0 || city.indexOf('区') > 0 || city.indexOf('团') > 0) {
            this.getWeatherFromUrlFun('北京');
          } else {
            this.getWeatherFromUrlFun(city);
          }
        } else {
          this.$message.error('获取名称失败');
        }
      }).catch(() => {
        this.$message.error('获取名称失败');
      });
    },
    // 地区代码解析方法
    // 去掉<![CDATA[<3级]]>
    funThrowCsata(val) {
      let last = val.lastIndexOf('[');
      let first = val.indexOf(']');
      return val.substring(last + 1, first);
    },
    changeCity(val) { // 点击进行天气城市的修改
      this.modal1 = true;
    },
    enterChangeCity() {
      this.modal1 = false;
      if (!(this.city.indexOf('国') > 0 || this.city.indexOf('省') > 0 || this.city.indexOf('区') > 0 || this.city.indexOf('团') > 0)) {
        this.getWeatherFromUrlFun(this.city);
      } else {
        this.$message.error('请选择某个市');
      }
    },
    cancelChangeCity() {
      this.modal1 = false;
    },
    // 获取所有的省份的字典项
    findAllChianAreaFun() {
      var selfthis = this;
      findAllChianArea(selfthis.$store.state.appId, data => {
        if (data.length > 0) {
          selfthis.AreaData[0] = data[0];
          // 获取用户信息
          this.getUserBokInfo();
        }
      });
    },
    formatChose(labels, selectedData) {
      if (labels.length > 0) {
        this.city = labels[labels.length - 1];
        if (!(labels[labels.length - 1].indexOf('国') > 0 || labels[labels.length - 1].indexOf('省') > 0 || labels[labels.length - 1].indexOf('区') > 0 || labels[labels.length - 1].indexOf('团') > 0)) {
          this.city = labels[labels.length - 1];
        }
      }
      return labels.join('/');
    },
    // 这里的遍历将会是公共方法
    getAddressChinese(index, numArr, numArrLength, AllData) {
      if (AllData === null || AllData === 'null' || AllData === undefined || AllData === 'undefined') {
        return;
      }
      for (let i = 0; i < AllData.length; i++) {
        if (numArr[index] === AllData[i].value) {
          this.addressTemp.push(AllData[i].label);
          if (index < numArrLength) {
            this.getAddressChinese(index + 1, numArr, numArrLength, AllData[i].children);
          }
          break;
        }
      }
    }
  },
  computed: {
  }
};
</script>
<style lang="scss">
  @import './style.scss';
  @import '../../assets/style/publicStyle.scss';
</style>
