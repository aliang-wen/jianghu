<template>
  <div id="readEssayPageId" >
    <div class="readRightPage">
      <div class="essayTitl">
        <!-- 标题 -->
        <h2>{{essayInfo.conTitle}}</h2>
      </div>
      <div class="essayFTitle">
        <h5>————{{essayInfo.conFTitle}}</h5>
      </div>
      <div class="showInfoEssay">
        <!-- 所有的文章的小信息 -->
        <ul>
          <li>作者:&nbsp; {{(essayInfo.userInfo || {}).userName}}</li>
          <li>创作时间:&nbsp; {{formatDateFun(essayInfo.createdData)}}</li>
          <li>修改时间:&nbsp; {{formatDateFun(essayInfo.updateData)}}</li>
          <li>
            文章类型:&nbsp;
            <!-- {{essayInfo.conType}} -->
            <span v-if="typePCode" @click="gotoEssayList(typePCode)">{{typePChinese}}</span>
            <span v-if="typeCCode">></span>
            <span v-if="typeCCode" @click="gotoEssayList(typeCCode)">{{typeCChinese}}</span>
          </li>
        </ul>
      </div>
      <div class="titlePpicture">
        <!-- 图片标题和文章描述 -->
        <img :src="essayInfo.titlePpicture || picUrlBank" width="308" height="130" align="right">
        <span>{{essayInfo.conGText}}</span>
      </div>
      <div class="pageEssayRead" v-html="essayInfo.conText">
      </div>
    </div>
    <div class="showLeftPage">
      <Card style="width:282px">
        <p slot="title">
          <Icon type="ios-grid"></Icon>
          {{typePChinese}}画板
        </p>
        <draw-type :currentBigType="currentBigType" @getValue = "getValueC"></draw-type>
      </Card>
      <div style="height: 20px;"></div>
      <Card style="width:282px">
        <p slot="title">
          <Icon type="ios-grid"></Icon>
          {{typeCChinese}}类文章精选
        </p>
        <a slot="extra" href="javacript:void(0)" @click="changeEssayList('type')">
          <Icon type="ios-planet-outline"></Icon>
          换一批
        </a>
        <ul>
          <li  v-for="(da, index) in pageShowDatasType" :key="'re' + index">
            <a href="javacript:void(0)" @click="routerTo(da.essayId)" :title="da.conTitle"><span class="jing"><Icon type="ios-thumbs-up" /></span>{{da.conTitle}}</a>
          </li>
        </ul>
      </Card>
      <div style="height: 20px;"></div>
      <Card style="width:282px">
        <p slot="title">
          <Icon type="ios-grid"></Icon>
          {{typeCChinese}}类文章精选
        </p>
        <a slot="extra" href="javacript:void(0)" @click="changeEssayList('series')">
          <Icon type="ios-planet-outline"></Icon>
          换一批
        </a>
        <ul>
          <li  v-for="(da, index) in pageShowDatasSerise" :key="'re' + index">
            <a href="javacript:void(0)" @click="routerTo(da.essayId)" :title="da.conTitle"><span class="jing"><Icon type="ios-thumbs-up" /></span>{{da.conTitle}}</a>
          </li>
        </ul>
      </Card>
    </div>
    <!-- 这里的这个div是专门对待浮动的 -->
    <div class="clear"></div>
  </div>
</template>
<script>
import { readEssayFun } from '../../../network/aliangbok_font/readEssayPage/index';
import { findEssayListPageSer } from '../../../network/aliangbok_font/mainPage/index';
import { formatDate, finDictionaryOptioinAllListCon } from '../../../network/public';
import drawtype from '../common/drawtype';
import { ZY02_AOBPR } from '../../../network/publicOptioCode';
import { picUrlBank } from '../../../api/index';

export default {
  components: {
    'draw-type': drawtype
  },
  data() {
    return {
      essayInfo: {},
      typePChinese: '',
      typePCode: '',
      typeCChinese: '',
      typeCCode: '',
      essayTypeLsit: [],
      currentBigType: [],
      picUrlBank: picUrlBank,
      essaySizePage: 5,
      pageShowDatasType: [],
      pageShowDatasSerise: [],
      allTotalType: 0, // 分类的总的页数
      pageCurrentType: 1, // 分裂的当前的页码
      allTotalSeries: 0, // 系列的总的页数
      pageCurrentSeries: 1 // 系列的当前页数
    };
  },
  mounted() {
    this.findDictionaryOptionListFun();
  },
  methods: {
    readEssayFunFun() {
      let params = {
        dynamicParams: this.$store.state.appId,
        essayId: this.$route.query.id
      };
      readEssayFun(params).then(data => {
        console.log(data, '取出的数据是什么');
        if (data && data.result && data.result.flag && data.result.flag === 'sucess') {
          this.essayInfo = data.result.essay;
          // 中文编码转换
          this.analyEssayType(this.essayInfo.conType);
          // 根据编码找相关的类
          this.findEssayListPageSerFunType(this.essayInfo.conType);
          this.findEssayListPageSerFunSeries(this.essayInfo.essaySeries);
          if (this.essayInfo.essaySeries) {
            // 这里是系列相关的文章
          }
        } else {
          this.$message.error('阅读文章失败');
        }
      }).catch(() => {
        this.$message.error('阅读文章失败');
      });
    },
    // 获取字典项
    findDictionaryOptionListFun() {
      let params = {
        dynamicParams: this.$store.state.appId,
        DictionaryOptionList: [],
        dicType: ZY02_AOBPR
      };
      finDictionaryOptioinAllListCon(params).then(data => {
        this.essayTypeLsit = data.result.DictionaryOption || [];
        this.readEssayFunFun();
      }).catch(() => {
        this.$message.error('获取下拉选失败');
      });
    },
    // 将文章类型进行分析
    analyEssayType(val) {
      let arrTem = val.split('-');
      let num = arrTem.length;
      var stringParams = '';
      stringParams = this.getChineseName(this.essayTypeLsit, arrTem, 0, num - 1);
      let arrName = stringParams.split('/');
      if (num === 0) {
        this.typePCode = '';
        this.typeCCode = '';
        this.typePChinese = '';
        this.typeCChinese = '';
      } else if (num === 1) {
        this.typePCode = arrTem[0];
        this.typeCCode = '';
        this.typePChinese = arrName[0];
        this.typeCChinese = '';
      } else if (num === 2) {
        this.typePCode = arrTem[0];
        this.typeCCode = arrTem[1];
        this.typePChinese = arrName[0];
        this.typeCChinese = arrName[1];
      }
    },
    // 递归解析
    getChineseName(listTree, params, m, n) {
      var str = '';
      if (listTree || listTree.length > 0) {
        for (let i = 0; i < listTree.length; i++) {
          if (listTree[i].value === params[m]) {
            str = listTree[i].label;
            if (m === 0) {
              this.currentBigType = listTree[i].children;
            }
            if (m < n) {
              str += '/' + this.getChineseName(listTree[i].children, params, m + 1, n);
            }
          }
        }
      }
      return str;
    },
    // 时间的转换
    formatDateFun(val) {
      return formatDate(val);
    },
    gotoEssayList(val) {
      alert(val);
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
    changeEssayList(val) {
      if (val === 'type') {
        if (this.pageCurrentType * this.essaySizePage >= this.allTotalType) {
          this.pageCurrentType = 1;
        } else {
          this.pageCurrentType = this.pageCurrentType + 1;
        }
        this.findEssayListPageSerFunType(this.essayInfo.conType);
      } else if (val === 'series') {
        if (this.pageCurrentSeries * this.essaySizePage >= this.allTotalSeries) {
          this.pageCurrentSeries = 1;
        } else {
          this.pageCurrentSeries = this.pageCurrentSeries + 1;
        }
        this.findEssayListPageSerFunSeries(this.essayInfo.essaySeries);
      }
    },
    findEssayListPageSerFunType(val) {
      let params = {
        dynamicParams: this.$store.state.appId,
        essayCurrentPage: this.pageCurrentType,
        essaySizePage: this.essaySizePage,
        showOrder: 'readCount', // 这里是按照什么排序
        essaySeries: '', // 这是按照文章系列查询 不传就不进行按照系列查询
        conType: val // 这是按照文章类型进行查询 不传值就是没有按照类型进行查询
      };
      findEssayListPageSer(params).then(data => {
        console.log(data, '这里的数据是什么1');
        if (data.result.flag === 'success') {
          this.pageShowDatasType = data.result.listEssay || [];
          this.allTotalType = data.result.total || 0;
        } else {
          this.$message.error('获取文章信息失败');
        }
      }).catch(() => {
        this.$message.error('获取文章信息失败');
      });
    },
    findEssayListPageSerFunSeries(val) {
      let params = {
        dynamicParams: this.$store.state.appId,
        essayCurrentPage: this.pageCurrentSeries,
        essaySizePage: this.essaySizePage,
        showOrder: 'readCount', // 这里是按照什么排序
        essaySeries: val, // 这是按照文章系列查询 不传就不进行按照系列查询
        conType: '' // 这是按照文章类型进行查询 不传值就是没有按照类型进行查询
      };
      findEssayListPageSer(params).then(data => {
        console.log(data, '这里的数据是什么2');
        if (data.result.flag === 'success') {
          this.pageShowDatasSerise = data.result.listEssay || [];
          this.allTotalSeries = data.result.total || 0;
        } else {
          this.$message.error('获取文章信息失败');
        }
      }).catch(() => {
        this.$message.error('获取文章信息失败');
      });
    },
    getValueC(val) {
      console.log(val, '子类打印');
      let tempArr = this.essayInfo.conType.split('-');
      this.typeP = tempArr[0];
      this.typeC = val.value;
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
  },
  watch: {
    $route(to, from) {
      this.findDictionaryOptionListFun();
    }
  }
};
</script>
<style lang="scss">
  @import'./style.scss';
</style>

