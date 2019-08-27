<template>
  <div id="addAriticleId">
    <Row>
      <Col span="24">
        <h2>文章管理</h2>
      </Col>
    </Row>
    <Row>
      <Col span="24">
        <div class="findClass">
          <Form ref="formInline" inline :label-width="65">
            <FormItem>
              <Button type="primary"  @click="search">搜索</Button>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <Button type="warning" @click="restting">重置</Button>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <Button type="success" @click="goToWriteAriticle">写文章</Button>
            </FormItem>
            <FormItem label="文章编码:">
              <Input type="text" v-model="essayNum" placeholder="输入编码搜索">
              </Input>
            </FormItem>
            <FormItem label="文章标题:">
              <Input type="text" v-model="essayTitle" placeholder="输入名称搜索">
              </Input>
            </FormItem>
          </Form>
        </div>
      </Col>
    </Row>
    <div class="essayTableClass">
      <Table border ref="currentRowTable" :columns="ariticleColumns" :data="ariticleData">
        <template slot-scope="{ row, index }" slot="conType">
         {{getConTypeChinese(row.conType)}}
        </template>
        <template slot-scope="{ row, index }" slot="carouselShow">
          <i-switch :value="switchTrueFalse(row.carouselShow)" @on-change="changeSwitch($event, row, 'carouselShow')">
            <span slot="open">开</span>
            <span slot="close">关</span>
          </i-switch>
        </template>
        <template slot-scope="{ row, index }" slot="showBox">
          <i-switch :value="switchTrueFalse(row.showBox)" @on-change="changeSwitch($event, row, 'showBox')">
            <span slot="open">开</span>
            <span slot="close">关</span>
          </i-switch>
        </template>
        <template slot-scope="{ row, index }" slot="ariticleState">
          <i-switch :value="switchTrueFalse(row.ariticleState)" @on-change="changeSwitch($event, row, 'ariticleState')">
            <span slot="open">开</span>
            <span slot="close">关</span>
          </i-switch>
        </template>
        <template slot-scope="{ row, index }" slot="ariticlePrivate">
          <i-switch :value="switchTrueFalse(row.ariticlePrivate)" @on-change="changeSwitch($event, row, 'ariticlePrivate')">
            <span slot="open">开</span>
            <span slot="close">关</span>
          </i-switch>
        </template>
        <template slot-scope="{ row, index }" slot="action">
          <Button type="error" size="small" @click="delCurrentRow(row)">删除</Button>
          <Button type="warning" size="small" @click="updateOption(row)">修改</Button>
        </template>
      </Table>
    </div>
    <div class="essayPageClass">
      <Page :total="allTotal" show-sizer show-total @on-change="gotoOnePage" :pageSize="optionPageSize" :pageSizeOpts="optionPageArr" @on-page-size-change="changeSize"/>
    </div>
    <Spin size="large" fix v-if="spinShow"></Spin>
    <Modal v-model="delEssayFlag" :title="delEssayTitle">
      请确认是否删除文章:
      <h3>{{delEssayName}}</h3>
      <template slot="footer">
        <Button type="error" @click="cancelDeleteEssay">取消</Button>
        <Button type="primary" @click="enterDeleteEssay">确定</Button>
      </template>
    </Modal>
  </div>
</template>
<script>
import { findEssayTableList, updateEssayByIdChangeSwitch, delEssayById } from '../../../../network/aliangbok_back/aliangbokCo/ariticle/index';
import { RC01_AOBPR_AL_SWITCH_ON_OFF } from '../../../../network/publicOptioCode';
import { findDictionaryOptionTableList } from '../../../../network/aliangbok_back/aliangbokCo/dictionaryOption/index';

export default {
  data() {
    return {
      ariticleColumns: [
        {
          title: '编号',
          type: 'index',
          width: 80,
          align: 'center'
        },
        {
          title: '标题',
          key: 'conTitle',
          align: 'center',
          tooltip: true
        },
        {
          title: '文章描述',
          key: 'conGText',
          align: 'center',
          tooltip: true
        },
        {
          title: '文章副标题',
          key: 'conFTitle',
          align: 'center'
        },
        {
          title: '文章分类',
          slot: 'conType',
          align: 'center',
          tooltip: true
        },
        {
          title: '轮播展示',
          slot: 'carouselShow',
          align: 'center'
        },
        {
          title: '框内展示',
          slot: 'showBox',
          align: 'center'
        },
        {
          title: '文章点击次数',
          key: 'readCount',
          align: 'center'
        },
        {
          title: '文章推荐',
          key: 'recomStart',
          align: 'center'
        },
        {
          title: '文章发表状态',
          slot: 'ariticleState',
          align: 'center'
        },
        {
          title: '进行隐私发表',
          slot: 'ariticlePrivate',
          align: 'center'
        },
        {
          title: '操作',
          width: 160,
          // fixed: 'right',
          slot: 'action',
          align: 'center'
        }
      ],
      ariticleData: [],
      essayNum: '', // 根据文章编码搜索
      essayTitle: '', // 根据文章标题搜索
      allTotal: 0,
      optionPageArr: [5, 10, 20, 30, 40],
      optionPageSize: 10,
      essayCurrentPage: 1,
      essaySizePage: 10,
      spinShow: false,
      delEssayTitle: '',
      delEssayFlag: false,
      delEssayName: '',
      delEssayIfon: {},
      essayTypeLsit: []
    };
  },
  mounted() {
    this.findDictionaryOptionFun();
  },
  methods: {
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
    findDictionaryOptionFun() {
      var params = {
        dynamicParams: this.$store.state.appId,
        type: 'ZY02_AOBPR_',
        DictionaryOptionCurrent: 1, // 这里是当前页
        DictionaryOptionPage: 100, // 这里是不想接口了就用了分页接口?? 毕竟这里要获取所有的
        optionKey: '',
        optionName: ''
      };
      this.spinShow = true;
      findDictionaryOptionTableList(params).then(data => {
        this.spinShow = false;
        this.essayTypeLsit = [...data.result.allDictionaryOptionTable];
        this.findEssayTableListFun();
      }).catch(() => {
        this.spinShow = false;
        this.$message.error('获取字典项失败');
      });
    },
    search() {
      this.findEssayTableListFun();
    },
    restting() {
      this.essayNum = '';
      this.essayTitle = '';
      this.essayCurrentPage = 1;
      this.essaySizePage = 10;
      this.findEssayTableListFun();
    },
    gotoOnePage(val) {
      // 到那一页的方法
      this.essayCurrentPage = val;
      this.findEssayTableListFun();
    },
    changeSize(val) {
      // 改变页面大小的方法
      this.essayCurrentPage = 1;
      this.essaySizePage = val;
      this.findEssayTableListFun();
    },
    delCurrentRow(row) {
      this.delEssayIfon = JSON.parse(JSON.stringify(row));
      // 删除当前的行
      this.delEssayFlag = true;
      this.delEssayTitle = '是否删除' + row.conTitle;
      this.delEssayName = row.conTitle;
    },
    updateOption(row) {
      // 修改文章跳转
      this.$router.push({
        path: '/back/editarticlepage',
        query: {
          essayId: row.essayId
        }
      });
    },
    findEssayTableListFun() {
      this.spinShow = true;
      let params = {
        dynamicParams: this.$store.state.appId,
        essayNum: this.essayNum,
        essayTitle: this.essayTitle,
        essayCurrentPage: this.essayCurrentPage,
        essaySizePage: this.essaySizePage
      };
      findEssayTableList(params).then(data => {
        this.spinShow = false;
        if (data.result.flag === 'success') {
          this.ariticleData = data.result.essayTableLsit;
          this.allTotal = data.result.total;
        } else {
          this.$message.error(data.result.message);
        }
      }).catch(() => {
        this.spinShow = false;
        this.$message.error('获取文章信息失败');
      });
    },
    changeSwitch(flag, row, val) {
      let params = JSON.parse(JSON.stringify(row));
      params.dynamicParams = this.$store.state.appId;
      if (flag) {
        params[val] = RC01_AOBPR_AL_SWITCH_ON_OFF.SWITCH_ON;
      } else {
        params[val] = RC01_AOBPR_AL_SWITCH_ON_OFF.SWITCH_OFF;
      }
      // 这里走修改方法
      this.updateEssayByIdFun(params);
    },
    switchTrueFalse(val) {
      if (val === RC01_AOBPR_AL_SWITCH_ON_OFF.SWITCH_ON) {
        return true;
      } else {
        return false;
      }
    },
    updateEssayByIdFun(params) {
      this.spinShow = true;
      updateEssayByIdChangeSwitch(params).then(data => {
        console.log(data, '修改后返回的数据');
        this.spinShow = false;
        if (data.result.falg === 'success') {
          // 数据更新成功
        } else {
          this.$message.error('更新数据出错');
        }
        this.findEssayTableListFun();
      }).catch(() => {
        this.spinShow = false;
        this.$message.error('更新数据出错');
        this.findEssayTableListFun();
      });
    },
    delEssayByIdFun(val) {
      let params = {
        dynamicParams: this.$store.state.appId,
        essayId: val
      };
      this.spinShow = true;
      delEssayById(params).then(data => {
        this.spinShow = false;
        if (data.result.falg === 'success') {
          this.$message.success(data.result.message);
          this.delEssayFlag = false;
        } else {
          this.$message.error(data.result.message);
        }
        this.findEssayTableListFun();
      }).catch(() => {
        this.spinShow = false;
        this.$message.error('信息删除失败');
      });
    },
    enterDeleteEssay() {
      if (this.allTotal % this.essaySizePage === 1) {
        this.essayCurrentPage = this.essayCurrentPage - 1;
      }
      this.delEssayByIdFun(this.delEssayIfon.essayId);
    },
    cancelDeleteEssay() {
      this.delEssayFlag = false;
    },
    goToWriteAriticle() {
      // 新建文章跳转
      this.$router.push({
        path: '/back/editarticlepage'
      });
    }
  }
};
</script>
<style lang="scss">
  @import './style.scss';
</style>

