<template>
  <div id="fontMainPage">
    <div class="constOne">
      <!-- 轮播 -->
      <div class="drawInfo">
        <Carousel autoplay v-model="pictureValue" :autoplay-speed="5000" >
          <CarouselItem v-for="p in picture" :key="p.key">
            <div class="showInfo" @click="routerTo(p.id)">
              <img :src="p.url || picUrlBank" :alt="p.altName" width="617px" height="260px">
              <div class="titleAltName" :title="p.altName">
                <span>
                  {{p.altName}}
                </span>
              </div>
            </div>
          </CarouselItem>
        </Carousel>
      </div>
      <!-- 上下框 -->
      <div class="doubleK">
        <div class="doubUp" @click="routerTo(pictureShowBox[0].id)">
          <img :src="pictureShowBox[0].url || picUrlBank" alt="" width="270px" height="125px" style="cursor:pointer">
          <div class="titleAltNameShowBox" :title="pictureShowBox[0].altName">
            {{pictureShowBox[0].altName}}
          </div>
        </div>
        <div class="doubDow" @click="routerTo(pictureShowBox[1].id)">
          <img :src="pictureShowBox[1].url || picUrlBank" alt="" width="270px" height="125px" style="cursor:pointer">
          <div class="titleAltNameShowBox" :title="pictureShowBox[1].altName">
            {{pictureShowBox[1].altName}}
          </div>
        </div>
      </div>
      <!-- 个人信息 -->
      <div class="ourInfo">
        <Card :bordered="false">
          <p slot="title">博主信息</p>
          <p class="showInfoClass">网名:&nbsp;{{userInfo.bigName}}</p>
          <p class="showInfoClass">爱好:&nbsp;{{userInfo.hobby}}</p>
          <p class="showInfoClass">现居:&nbsp;{{userInfo.userAddress}}</p>
          <p class="showInfoClass">简介:&nbsp;{{userInfo.bokFlag}}</p>
        </Card>
      </div>
    </div>
    <div class="constTwo">
      <div class="constTwoLeft">
        <div class="SexyToRecommend">
          <Tabs :value="chooseName" :animated="false" @on-click="changeCurrentPage">
            <TabPane label="博主推荐" name="pushYou">
              <Row v-for="(da, index) in pageShowDatas" :key="index">
                <Col span="24">
                  <div class="showTitleNameClass">
                  <span class="bigTitle">题</span>&nbsp;&nbsp;<span class="bigTitleName">{{da.conTitle}}</span>
                  </div>
                  <div class="picAtSexyToRecommend">
                    <img :src="da.titlePpicture || picUrlBank" @click="routerTo(da.essayId)" :alt="da.conTitle" width="270px" height="125px">
                  </div>
                  <div class="contAtSexyToRecommend">
                    <p>副标题: {{da.conFTitle}}</p>
                    <p>分类 : {{getConTypeChinese(da.conType)}}</p>
                    <span>简介: {{da.conGText}}</span>
                  </div>
                </Col>
              </Row>
            </TabPane>
            <TabPane label="最近更新" name="newCurrentWrite">
              <Row v-for="(da, index) in pageShowDatas" :key="index">
                <Col span="24">
                  <div class="showTitleNameClass">
                  <span class="bigTitle">题</span>&nbsp;&nbsp;<span class="bigTitleName">{{da.conTitle}}</span>
                  </div>
                  <div class="picAtSexyToRecommend">
                    <img :src="da.titlePpicture || picUrlBank" @click="routerTo(da.essayId)" :alt="da.conTitle" width="270px" height="125px">
                  </div>
                  <div class="contAtSexyToRecommend">
                    <p>副标题: {{da.conFTitle}}</p>
                    <p>分类 : {{getConTypeChinese(da.conType)}}</p>
                    <span>简介: {{da.conGText}}</span>
                  </div>
                </Col>
              </Row>
            </TabPane>
            <TabPane label="流量文章" name="manyReaders">
              <Row v-for="(da, index) in pageShowDatas" :key="index">
                <Col span="24">
                <div class="showTitleNameClass">
                  <span class="bigTitle">题</span>&nbsp;&nbsp;<span class="bigTitleName">{{da.conTitle}}</span>
                </div>
                <div class="picAtSexyToRecommend">
                  <img :src="da.titlePpicture || picUrlBank" @click="routerTo(da.essayId)" :alt="da.conTitle" width="270px" height="125px">
                </div>
                <div class="contAtSexyToRecommend">
                  <p>副标题: {{da.conFTitle}}</p>
                  <p>分类 : {{getConTypeChinese(da.conType)}}</p>
                  <span>简介: {{da.conGText}}</span>
                </div>
                </Col>
              </Row>
            </TabPane>
          </Tabs>
          <div class="pageFen">
            <Page :total="allTotal" show-sizer show-total @on-change="gotoOnePage" :pageSize="optionPageSize" :pageSizeOpts="optionPageArr"
              @on-page-size-change="changeSize" />
          </div>
        </div>
      </div>
      <div class="constTwoRight">
        <Card style="width:282px">
          <p slot="title">
            <Icon type="ios-grid"></Icon>
            分类画板
          </p>
          <a href="javacript:void(0);" slot="extra" @click.prevent="changeLimit">
            <Icon type="ios-planet-outline"></Icon>
            改变
          </a>
          <draw-type :currentBigType="someShowEssayType" @getValue = "getValueF"></draw-type>
        </Card>
        <div style="height: 20px;"></div>
        <Card style="width:282px">
          <p slot="title">
            <Icon type="ios-grid"></Icon>
            系列推荐
          </p>
          <a href="javacript:void(0);" slot="extra" @click.prevent="changeLimitTwo">
            <Icon type="ios-planet-outline"></Icon>
            改变
          </a>
          <draw-type-two :currentBigType="limitShowEssaySeries" @getValue = "getValueS"></draw-type-two>
        </Card>
      </div>
      <!-- 这里的这个div是专门对待浮动的 -->
      <div class="clear"></div>
    </div>
  </div>
</template>
<script>
import { getUserInfo, getUserInfoCarousel, getUserInfoShowBox, findEssayListPageSer, getEssaySeriesListCon } from '../../../network/aliangbok_font/mainPage/index';
import { finDictionaryOptioinAllListCon } from '../../../network/public';
import drawtype from '../common/drawtype';
import drawtypeTwo from '../common/drawTypeTwo';
import { ZY02_AOBPR } from '../../../network/publicOptioCode';
import { picUrlBank } from '../../../api/index';

export default {
  components: {
    'draw-type': drawtype,
    'draw-type-two': drawtypeTwo
  },
  data() {
    return {
      false: false,
      nav1Val: '1',
      picture: [
        { url: 'http://menhu.yangqq.com/skin/menhu/images/banner2.jpg', altName: '展示样本', key: '1', id: '123' },
        { url: 'http://jxhx2.yangqq.com/skin/jxhx/images/2.jpg', altName: '展示样本', key: '2', id: '123' }
      ],
      pictureShowBox: [
        { url: 'http://menhu.yangqq.com/skin/menhu/images/banner2.jpg', altName: '展示样本', key: '1', id: '123' },
        { url: 'http://jxhx2.yangqq.com/skin/jxhx/images/2.jpg', altName: '展示样本', key: '2', id: '123' }
      ],
      pictureValue: -1,
      doubUpData: {},
      doubDowData: {},
      userInfo: {
        hobby: '',
        bigName: '',
        userAddress: ''
      },
      picUrlBank: picUrlBank,
      pageShowDatas: [],
      allTotal: 0,
      optionPageArr: [5, 10, 20, 30, 40],
      optionPageSize: 10,
      essayCurrentPage: 1,
      essaySizePage: 10,
      chooseName: 'pushYou',
      essayTypeLsit: [],
      someShowEssayType: [],
      clickChangNum: 0,
      Allswitch: [],
      randomMovieList: [],
      showOrderType: 'recomStart',
      essaySeriesList: [],
      limitShowEssaySeries: [],
      clickChangNumTwo: 0,
      typeP: '',
      typeC: '',
      serise: ''
    };
  },
  mounted() {
    this.pictureValue = 0;
    this.init();
  },
  methods: {
    init() {
      // 获取博主信息
      this.getUserInfoFun();
      // 查询出来轮播列表
      this.getUserInfoCarouselFun();
      // 获取展示框的信息
      this.getUserInfoShowBoxFun();
      // 获取所有的文章
      // this.findEssayListPageSerFun();
      // 获取所有文章的分类
      this.findDictionaryOptionListFun();
      // 获取文章系列分类
      this.getEssaySeriesListConFun();
    },
    // 轮播件跳转到相应的界面
    routerTo(id) {
      if (id !== '123') {
        this.$router.push({
          path: 'readEssayPage',
          query: {
            id: id
          }
        });
      }
    },
    getUserInfoFun() {
      let params = {
        dynamicParams: this.$store.state.appId
      };
      getUserInfo(params).then(data => {
        if (data.result.flag === 'success') {
          this.userInfo = data.result.userInfo || {};
        } else {
          this.$message.error('获取博主信息失败');
        }
      }).catch(() => {
        this.$message.error('获取博主信息失败');
      });
    },
    getUserInfoCarouselFun() {
      let params = { dynamicParams: this.$store.state.appId };
      getUserInfoCarousel(params).then(data => {
        if (data.result.listEssay && data.result.listEssay.length > 0) {
          this.picture = [];
          for (let es of data.result.listEssay) {
            var tempObj = {};
            tempObj.url = es.titlePpicture;
            tempObj.altName = es.conTitle;
            tempObj.key = es.essayId;
            tempObj.id = es.essayId;
            this.picture.push(tempObj);
          }
        }
      }).catch(() => {
        this.$message.error('轮播获取信息失败');
      });
    },
    getUserInfoShowBoxFun() {
      let params = { dynamicParams: this.$store.state.appId };
      getUserInfoShowBox(params).then(data => {
        if (data.result.listEssay && data.result.listEssay.length > 0) {
          if (data.result.listEssay.length === 1) {
            var tempObj = {};
            tempObj.url = data.result.listEssay[0].titlePpicture;
            tempObj.altName = data.result.listEssay[0].conTitle;
            tempObj.key = data.result.listEssay[0].essayId;
            tempObj.id = data.result.listEssay[0].essayId;
            this.pictureShowBox[0] = tempObj;
          }
          if (data.result.listEssay.length > 1) {
            var tempObjThree = {};
            tempObjThree.url = data.result.listEssay[0].titlePpicture;
            tempObjThree.altName = data.result.listEssay[0].conTitle;
            tempObjThree.key = data.result.listEssay[0].essayId;
            tempObjThree.id = data.result.listEssay[0].essayId;
            this.pictureShowBox[0] = tempObjThree;
            var tempObjTwo = {};
            tempObjTwo.url = data.result.listEssay[1].titlePpicture;
            tempObjTwo.altName = data.result.listEssay[1].conTitle;
            tempObjTwo.key = data.result.listEssay[1].essayId;
            tempObjTwo.id = data.result.listEssay[1].essayId;
            this.pictureShowBox[1] = tempObjTwo;
          }
        }
      }).catch(() => {
        this.$message.error('展示框取信息失败');
      });
    },
    findEssayListPageSerFun() {
      let params = {
        dynamicParams: this.$store.state.appId,
        essayCurrentPage: this.essayCurrentPage,
        essaySizePage: this.essaySizePage,
        showOrder: this.showOrderType, // 这里是按照什么排序
        essaySeries: '', // 这是按照文章系列查询 不传就不进行按照系列查询
        conType: '' // 这是按照文章类型进行查询 不传值就是没有按照类型进行查询
      };
      findEssayListPageSer(params).then(data => {
        console.log(data, '这里的数据是什么');
        if (data.result.flag === 'success') {
          this.pageShowDatas = data.result.listEssay || [];
          this.allTotal = data.result.total || 0;
        } else {
          this.$message.error('获取文章信息失败');
        }
      }).catch(() => {
        this.$message.error('获取文章信息失败');
      });
    },
    gotoOnePage(val) {
      this.essayCurrentPage = val;
      this.findEssayListPageSerFun();
    },
    changeSize(val) {
      // 改变页面大小的方法
      this.essayCurrentPage = 1;
      this.essaySizePage = val;
      this.findEssayListPageSerFun();
    },
    findDictionaryOptionListFun() {
      let params = {
        dynamicParams: this.$store.state.appId,
        DictionaryOptionList: [],
        dicType: ZY02_AOBPR
      };
      finDictionaryOptioinAllListCon(params).then(data => {
        this.essayTypeLsit = data.result.DictionaryOption || [];
        this.limitShowType();
        this.findEssayListPageSerFun();
      }).catch(() => {
        this.$message.error('获取下拉选失败');
      });
    },
    getConTypeChinese(val) {
      var arrTemp = val.split('-');
      var arrT = [];
      for (let i = 0; i < this.essayTypeLsit.length; i++) {
        if (arrTemp[0] === this.essayTypeLsit[i].value) {
          arrT.push(this.essayTypeLsit[i].label);
          for (let m = 0; m < this.essayTypeLsit[i].children.length; m++) {
            if (arrTemp[1] === this.essayTypeLsit[i].children[m].value) {
              arrT.push(this.essayTypeLsit[i].children[m].label);
              break;
            }
          }
        }
      }
      return arrT.join('/');
    },
    changeCurrentPage(name) {
      this.essayCurrentPage = 1;
      this.essaySizePage = 10;
      if (name === 'pushYou') {
        this.showOrderType = 'recomStart';
        this.findEssayListPageSerFun();
      } else if (name === 'newCurrentWrite') {
        this.showOrderType = '';
        this.findEssayListPageSerFun();
      } else if (name === 'manyReaders') {
        this.showOrderType = 'readCount';
        this.findEssayListPageSerFun();
      }
    },
    limitShowType() {
      this.someShowEssayType = []; // 这是要求显示的数据
      // 设置一次显示二十个
      var tempLast = this.clickChangNum + 1;
      if (this.essayTypeLsit.length > tempLast * 20) {
        for (let i = this.clickChangNum * 20; i < tempLast * 20; i++) {
          this.someShowEssayType.push(this.essayTypeLsit[i]);
        }
        this.clickChangNum = this.clickChangNum + 1;
      } else {
        for (let i = this.clickChangNum * 20; i < this.essayTypeLsit.length; i++) {
          this.someShowEssayType.push(this.essayTypeLsit[i]);
        }
        this.clickChangNum = 0;
      }
    },
    changeLimit() {
      this.limitShowType();
    },
    getEssaySeriesListConFun() {
      let params = {
        dynamicParams: this.$store.state.appId
      };
      getEssaySeriesListCon(params).then(data => {
        if (data.result.flag === 'success') {
          this.essaySeriesList = [];
          for (let i = 0; i < (data.result.list || []).length; i++) {
            var params = { value: data.result.list[i], label: data.result.list[i] };
            this.essaySeriesList.push(params);
          }
          this.limitShowTypeTwo();
        } else {
          this.$message.error('文章系列分类获取失败');
        }
      }).catch(() => {
        this.$message.error('文章分类获取失败');
      });
    },
    limitShowTypeTwo() {
      this.limitShowEssaySeries = []; // 这是要求显示的数据
      // 设置一次显示二十个
      var tempLast = this.clickChangNumTwo + 1;
      if (this.essaySeriesList.length > tempLast * 20) {
        for (let i = this.clickChangNumTwo * 20; i < tempLast * 20; i++) {
          this.limitShowEssaySeries.push(this.essaySeriesList[i]);
        }
        this.clickChangNumTwo = this.clickChangNumTwo + 1;
      } else {
        for (let i = this.clickChangNumTwo * 20; i < this.essaySeriesList.length; i++) {
          this.limitShowEssaySeries.push(this.essaySeriesList[i]);
        }
        this.clickChangNumTwo = 0;
      }
    },
    changeLimitTwo() {
      this.limitShowTypeTwo();
    },
    getValueF(val) {
      this.typeP = val.value;
      this.typeC = '';
      this.serise = '';
      this.gotoallPagesList();
    },
    getValueS(val) {
      this.typeP = '';
      this.typeC = '';
      this.serise = val.value;
      this.gotoallPagesList();
    },
    gotoallPagesList() {
      this.$router.push({
        path: 'allPagesList',
        query: {
          typeP: this.typeP,
          typeC: this.typeC,
          serise: this.serise
        }
      });
    }
  }
};
</script>
<style lang="scss">
  @import'./style.scss';
</style>
