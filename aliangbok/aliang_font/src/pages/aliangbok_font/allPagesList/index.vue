<template>
  <div id="allPageListId">
    <div class="readRightPage">
      <div class="headerShow">
        <ul>
          <li>
            分类:
            <Tag v-if="parentsTypeObj.value.length > 0" type="border" closable color="error" @on-close = "closeTab('parents')">{{parentsTypeObj.label}}</Tag>
            <span v-if="childrenTypeObj.value.length > 0">
              >>
            </span>
            <Tag v-if="childrenTypeObj.value.length > 0" type="border" closable color="warning" @on-close = "closeTab('children')">{{childrenTypeObj.label}}</Tag>
          </li>
          <li>
            系列:
            <Tag v-if="seriseTypeObj.value.length > 0" type="border" closable color="primary" @on-close = "closeTab('series')">{{seriseTypeObj.label}}</Tag>
          </li>
        </ul>
      </div>
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
      <div class="pageFen">
        <Page :total="allTotal" show-sizer show-total @on-change="gotoOnePage" :pageSize="optionPageSize" :pageSizeOpts="optionPageArr"
          @on-page-size-change="changeSize" />
      </div>
    </div>
    <div class="showLeftPage">
      <Card style="width:282px">
        <p slot="title">
          <Icon type="ios-grid"></Icon>
          分类画板
        </p>
        <a href="javacript:void(0);" slot="extra" @click.prevent="changeLimit">
          <Icon type="ios-planet-outline"></Icon>
          改变
        </a>
        <draw-type-three :currentBigType="someShowEssayType" @getValue = "getValueF" ></draw-type-three>
      </Card>
      <div style="height: 20px;" v-if="showChildrenEssayType"></div>
      <Card style="width:282px" v-if="showChildrenEssayType">
        <p slot="title">
          <Icon type="ios-grid"></Icon>
          {{parentsName}}分类画板
        </p>
        <draw-type :currentBigType="someShowEssayTypeChildren" @getValue = "getValueC"></draw-type>
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
     <div class="clear"></div>
  </div>
</template>
<script>
import { findEssayListPageSer, getEssaySeriesListCon } from '../../../network/aliangbok_font/mainPage/index';
import { finDictionaryOptioinAllListCon } from '../../../network/public';
import { ZY02_AOBPR } from '../../../network/publicOptioCode';
import drawtype from '../common/drawtype';
import drawtypeTwo from '../common/drawTypeTwo';
import drawtypeThree from '../common/drawTypeThree';
import { picUrlBank } from '../../../api/index';

export default {
  components: {
    'draw-type': drawtype,
    'draw-type-two': drawtypeTwo,
    'draw-type-three': drawtypeThree
  },
  data() {
    return {
      pageShowDatas: [],
      allTotal: 0,
      optionPageArr: [5, 10, 20, 30, 40],
      optionPageSize: 10,
      essayCurrentPage: 1,
      essaySizePage: 10,
      picUrlBank: picUrlBank,
      someShowEssayType: [],
      someShowEssayTypeChildren: [],
      showChildrenEssayType: false,
      essayTypeLsit: [],
      clickChangNum: 0,
      essaySeriesList: [],
      limitShowEssaySeries: [],
      clickChangNumTwo: 0,
      parentsName: '',
      showTypeEssay: [],
      showSeriseEssay: [],
      seriesEss: '',
      typeEss: '',
      parentsTypeObj: {
        label: '',
        value: ''
      },
      childrenTypeObj: {
        label: '',
        value: ''
      },
      seriseTypeObj: {
        label: '',
        value: ''
      },
      typeP: '',
      typeC: '',
      serise: ''
    };
  },
  mounted() {
    // 获取文章分类
    this.findDictionaryOptionListFun();
    // 获取文章系列分类
    this.getEssaySeriesListConFun();
  },
  methods: {
    matchChooseDic() {
      if ((this.$route.query.typeP || '').length > 0) {
        this.typeP = this.$route.query.typeP;
        for (let i = 0; i < this.essayTypeLsit.length; i++) {
          if (this.$route.query.typeP === this.essayTypeLsit[i].value) {
            this.showChildrenEssayType = true;
            this.someShowEssayTypeChildren = this.essayTypeLsit[i].children;
            this.parentsName = this.essayTypeLsit[i].label;
            this.parentsTypeObj.value = this.essayTypeLsit[i].value;
            this.parentsTypeObj.label = this.essayTypeLsit[i].label;
            this.typeP = this.essayTypeLsit[i].value;
          }
        }
        if ((this.$route.query.typeC || '').length > 0) {
          this.typeC = this.$route.query.typeC;
          for (let k = 0; k < this.someShowEssayTypeChildren.length; k++) {
            if (this.someShowEssayTypeChildren[k].value === this.$route.query.typeC) {
              this.childrenTypeObj.value = this.someShowEssayTypeChildren[k].value;
              this.childrenTypeObj.label = this.someShowEssayTypeChildren[k].label;
              this.typeC = this.someShowEssayTypeChildren[k].value;
            }
          }
        }
      }
      if ((this.$route.query.serise || '').length > 0) {
        this.serise = this.$route.query.serise;
        this.seriseTypeObj.label = this.$route.query.serise;
        this.seriseTypeObj.value = this.$route.query.serise;
      }
      this.seriesEss = this.serise || '';
      if (this.typeC) {
        this.typeEss = (this.typeP || '') + '-' + (this.typeC || '');
      } else {
        this.typeEss = this.typeP || '';
      }
      this.findEssayListPageSerFun();
    },
    findEssayListPageSerFun() {
      let params = {
        dynamicParams: this.$store.state.appId,
        essayCurrentPage: this.essayCurrentPage,
        essaySizePage: this.essaySizePage,
        showOrder: 'newCurrentWrite', // 这里是按照什么排序
        essaySeries: this.seriesEss, // 这是按照文章系列查询 不传就不进行按照系列查询
        conType: this.typeEss // 这是按照文章类型进行查询 不传值就是没有按照类型进行查询
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
    findDictionaryOptionListFun() {
      let params = {
        dynamicParams: this.$store.state.appId,
        DictionaryOptionList: [],
        dicType: ZY02_AOBPR
      };
      finDictionaryOptioinAllListCon(params).then(data => {
        this.essayTypeLsit = data.result.DictionaryOption || [];
        this.limitShowType();
        // 匹配选项
        this.matchChooseDic();
      }).catch(() => {
        this.$message.error('获取下拉选失败');
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
    changeLimit() {
      this.someShowEssayTypeChildren = [];
      this.limitShowType();
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
    getValueF(val) {
      console.log(val, '我想看看这里的数据');
      if (val.children.length > 0) {
        this.showChildrenEssayType = true;
        this.someShowEssayTypeChildren = val.children;
        this.parentsName = val.label;
        this.parentsTypeObj.value = val.value;
        this.parentsTypeObj.label = val.label;
        this.typeP = val.value;
        this.childrenTypeObj.value = '';
        this.childrenTypeObj.label = '';
        this.typeC = '';
        this.gotoallPagesList();
      } else {
        this.showChildrenEssayType = false;
      }
    },
    getValueC(val) {
      console.log(val, '子类打印');
      this.childrenTypeObj.value = val.value;
      this.childrenTypeObj.label = val.label;
      this.typeC = val.value;
      this.gotoallPagesList();
    },
    getValueS(val) {
      console.log(val, '系类打印');
      this.seriseTypeObj.value = val.value;
      this.seriseTypeObj.label = val.label;
      this.serise = val.value;
      this.gotoallPagesList();
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
    changeLimitTwo() {
      this.limitShowTypeTwo();
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
    },
    closeTab(val) {
      if (val === 'parents') {
        // 删除父亲节点
        this.parentsTypeObj.value = '';
        this.parentsTypeObj.label = '';
        this.typeP = '';
        this.childrenTypeObj.value = '';
        this.childrenTypeObj.label = '';
        this.typeC = '';
        this.gotoallPagesList();
      } else if (val === 'children') {
        // 删除孩子节点
        this.childrenTypeObj.value = '';
        this.childrenTypeObj.label = '';
        this.typeC = '';
        this.gotoallPagesList();
      } else if (val === 'series') {
        // 删除系列
        this.seriseTypeObj.value = '';
        this.seriseTypeObj.label = '';
        this.serise = '';
        this.gotoallPagesList();
      }
    }
  },
  watch: {
    $route(to, from) {
      console.log(to, from, '首次进来这里的路由走不走???');
      this.seriesEss = this.serise || '';
      if (this.typeC) {
        this.typeEss = (this.typeP || '') + '-' + (this.typeC || '');
      } else {
        this.typeEss = this.typeP || '';
      }
      this.findEssayListPageSerFun();
    }
  }
};
</script>
<style lang="scss">
  @import'./style.scss';
</style>

