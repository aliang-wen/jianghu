<template>
  <div id="dictionaryOptionId">
    <Menu mode="horizontal" :active-name="menuItemOption" @on-select="changTabs">
      <MenuItem name="common">
      <Icon type="ios-paper" />
      日常项
      </MenuItem>
      <MenuItem name="technology">
      <Icon type="ios-people" />
      技术项
      </MenuItem>
      <MenuItem name="areaOption">
      <Icon type="ios-construct" />
      地区项
      </MenuItem>
    </Menu>

    <div class="showCommon tablesClass" v-if="choseTable === 'RC01_AOBPR_'">
      <div class="findClass">
        <Form ref="formInline" inline :label-width="65">
          <FormItem>
            <Button type="primary"  @click="search">搜索</Button>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <Button type="warning" @click="restting">重置</Button>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <Button type="error" @click="addOption('richang', '0')">添加日常字典项</Button>
          </FormItem>
          <FormItem label="编码:">
            <Input type="text" v-model="optionKey" placeholder="输入编码搜索">
            </Input>
          </FormItem>
          <FormItem label="名称:">
            <Input type="text" v-model="optionName" placeholder="输入名称搜索">
            </Input>
          </FormItem>
        </Form>
      </div>
      <Table :columns="TableColumns" :data="optionData" border>
        <template slot-scope="{ row, index}" slot="expansion">
          <span class="tree-ctrl" v-if="iconShow(index, row)" @click="toggleExpanded(row)">
            <i v-if="!row._expandedTest" class="ivu-icon ivu-icon-ios-add"></i>
            <i v-else class="ivu-icon ivu-icon-ios-remove"></i>
          </span>
          <span v-if="!iconShow(index, row)"></span>
        </template>
        <template slot-scope="{ row, index }" slot="action">
          <!-- <Button type="primary" size="small" style="margin-right: 5px">修改</Button> -->
          <Button type="error" size="small" @click="delCurrentRow(row)" :disabled="row.children.length > 0">删除</Button>
          <Button type="warning" size="small" @click="addOption('richang', row.value)" v-if="row.children">添加子项</Button>
        </template>
      </Table>
      <div class="pageChangeClass">
        <Page :total="allTotal" show-sizer show-total @on-change="gotoOnePage" :pageSize="optionPageSize" :pageSizeOpts="optionPageArr" @on-page-size-change="changeSize"/>
      </div>
    </div>

    <div class="showTechonlogy tablesClass" v-if="choseTable === 'ZY02_AOBPR_'">
      <div class="findClass">
        <Form ref="formInline" inline :label-width="65">
          <FormItem>
            <Button type="primary" @click="search">搜索</Button>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <Button type="warning" @click="restting">重置</Button>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <Button type="error" @click="addOption('jishu', '0')">添加技术字典项</Button>
          </FormItem>
          <FormItem label="编码:">
            <Input type="text" v-model="optionKey" placeholder="输入编码搜索">
            </Input>
          </FormItem>
          <FormItem label="名称:">
            <Input type="password" v-model="optionName" placeholder="输入名称搜索">
            </Input>
          </FormItem>
        </Form>
      </div>
      <Table :columns="TableColumns" :data="optionData" border>
        <template slot-scope="{ row, index}" slot="expansion">
          <span class="tree-ctrl" v-if="iconShow(index, row)" @click="toggleExpanded(row)">
            <i v-if="!row._expandedTest" class="ivu-icon ivu-icon-ios-add"></i>
            <i v-else class="ivu-icon ivu-icon-ios-remove"></i>
          </span>
          <span v-if="!iconShow(index, row)"></span>
        </template>
        <template slot-scope="{ row, index }" slot="action">
          <!-- <Button type="primary" size="small" style="margin-right: 5px">修改</Button> -->
          <Button type="error" size="small" @click="delCurrentRow(row)" :disabled="row.children.length > 0">删除</Button>
          <Button type="warning" size="small" @click="addOption('jishu', row.value)" v-if="row.children">添加子项目</Button>
        </template>
      </Table>
      <div class="pageChangeClass">
        <Page :total="allTotal" show-sizer show-total @on-change="gotoOnePage" :pageSize="optionPageSize" :pageSizeOpts="optionPageArr" @on-page-size-change="changeSize"/>
      </div>
    </div>
    <div class="showCommon" v-if="choseTable === 'AREA'">
      <Table :columns="TableColumnsArea" :data="AreaDataTable" border>
        <template slot-scope="{ row, index}" slot="expansion">
          <span class="tree-ctrl" v-if="iconShow(index, row)" @click="toggleExpanded2(row)">
            <i v-if="!row._expandedTest" class="ivu-icon ivu-icon-ios-add"></i>
            <i v-else class="ivu-icon ivu-icon-ios-remove"></i>
          </span>
          <span v-if="!iconShow(index, row)"></span>
        </template>
        <template slot-scope="{ row, index }" slot="action">
          <Button type="primary" size="small" style="margin-right: 5px">修改</Button>
          <!-- <Button type="error" size="small">删除</Button>
          <Button type="error" size="small">添加</Button> -->
        </template>
      </Table>
    </div>
    <Spin size="large" fix v-if="spinShow"></Spin>

    <Modal v-model="modalTitleFlag" :title="modalTitle" width="500px" draggable scrollable>
      <Form :model="optionDataSave" ref="optionDataSaveRef" :rules="optionDataSaveRule" label-position="right" :label-width="100">
        <FormItem label="编码" prop="value">
          <Input v-model="optionDataSave.value" placeholder="请输入字典项编码">
            <span slot="prepend" >
              {{optionCodeLimit}}
            </span>
          </Input>
        </FormItem>
        <FormItem label="名字" prop="label">
          <Input v-model="optionDataSave.label" placeholder="输入字典项名字"></Input>
        </FormItem>
        <FormItem label="描述" prop="description">
          <Input v-model="optionDataSave.description" placeholder="请输入关于字典项的描述"></Input>
        </FormItem>
        <FormItem label="字典项从属" prop="Pid">
          <Input v-model="optionDataSave.Pid" placeholder="自动生成" disabled></Input>
        </FormItem>
      </Form>
      <template slot="footer">
        <Button type="error" @click="cancelChangeUserInfo('optionDataSaveRef')">取消</Button>
        <Button type="primary" @click="enterChangeUserInfo('optionDataSaveRef')">确定</Button>
      </template>
    </Modal>

    <Modal v-model="delOptionFlag" :title="delOptionTitle" width="500px" draggable scrollable>
      <span>
        确认删除<h3>{{delOptinLabel}}</h3>
      </span>
      <template slot="footer">
        <Button type="primary" @click="cancleDelOption">取消</Button>
        <Button type="error" @click="enterDelOption">确定</Button>
      </template>
    </Modal>
  </div>
</template>
<script>
import { findDictionaryOptionTableList, addDictionaryOptionList, delDictionaryOption } from '../../../../network/aliangbok_back/aliangbokCo/dictionaryOption/index';
import { findAllChianArea } from '../../../../network/public';

export default {
  data() {
    const validateNameList = (rule, value, callback) => {
      if (value.indexOf('ZY02_AOBPR_') > -1 || value.indexOf('RC01_AOBPR_') > -1) {
        callback(new Error('编码不能包含ZY02_AOBPR_、RC01_AOBPR_'));
      } else {
        callback();
      }
    };
    return {
      menuItemOption: '',
      expandAll: false,
      optionData: [],
      AreaDataTable: [],
      allTotal: 100,
      TableColumns: [
        {
          title: ' ',
          slot: 'expansion',
          width: 100,
          align: 'center'
        },
        {
          title: '编码',
          key: 'value'
        },
        {
          title: '编码代表',
          key: 'label'
        },
        {
          title: '字典项描述',
          key: 'description'
        },
        {
          title: '操作',
          slot: 'action',
          width: 200,
          align: 'center'
        }
      ],
      TableColumnsArea: [
        {
          title: ' ',
          slot: 'expansion',
          width: 100,
          align: 'center'
        },
        {
          title: '编码',
          key: 'value'
        },
        {
          title: '编码代表',
          key: 'label'
        },
        {
          title: '字典项描述',
          key: 'areaPid'
        },
        {
          title: '操作',
          slot: 'action',
          width: 200,
          align: 'center'
        }
      ],
      tableTreeData: [],
      choseTable: '', // 这里是选中的那个标签
      DictionaryOptionCurrent: 1, // 这里是当前页
      DictionaryOptionPage: 10, // 这里是一页显示十条
      optionPageSize: 10,
      optionPageArr: [10, 20, 30, 40],
      optionKey: '',
      optionName: '',
      AreaData: [],
      spinShow: false,
      modalTitle: '',
      modalTitleFlag: false,
      optionDataSave: {
        value: '',
        label: '',
        description: '',
        Pid: '0'
      },
      optionCodeLimit: '',
      optionDataSaveRule: {
        value: [
          { required: true, message: '请输入编码', trigger: 'blur' },
          { validator: validateNameList, trigger: 'blur' }
        ],
        label: [
          { required: true, message: '请输入编码名称', trigger: 'blur' }
        ],
        Pid: [
          { required: true, message: '未能够获取到父级元素', trigger: 'blur' }
        ]
      },
      delOptionFlag: false,
      delOptionTitle: '',
      delOptinLabel: '',
      delOptionKey: ''
    };
  },
  mounted() {
    this.menuItemOption = 'common';
    this.choseTable = 'RC01_AOBPR_';
    this.findDictionaryOptionFun();
  },
  methods: {
    iconShow(index, record) {
      return (record.children && record.children.length > 0);
    },
    // 切换下级是否展开
    toggleExpanded: function(row) {
      for (let r = 0; r < this.tableTreeData.length; r++) {
        if (row.value === this.tableTreeData[r].value) {
          const record = this.tableTreeData[r];
          record._expandedTest = !row._expandedTest;
          break;
        }
      }
      this.optionData = this.formatData();
    },
    // 格式化数据源
    formatData() {
      let tmp = [];
      if (!Array.isArray(this.tableTreeData)) {
        tmp = JSON.parse(JSON.stringify([this.tableTreeData]));
      } else {
        tmp = JSON.parse(JSON.stringify(this.tableTreeData));
      }
      let tmpShow = [];
      for (let i = 0; i < tmp.length; i++) {
        tmpShow.push(tmp[i]);
        if (!(tmp[i]._expandedTest === true || tmp[i]._expandedTest === false)) {
          tmp[i]._expandedTest = this.expandAll;
        }
        if (tmp[i]._expandedTest) {
          for (let k = 0; k < tmp[i].children.length; k++) {
            tmpShow.push(tmp[i].children[k]);
          }
        }
      }
      return tmpShow;
    },
    // 切换下级是否展开
    toggleExpanded2: function(row) {
      for (let r = 0; r < this.AreaData.length; r++) {
        if (row.value === this.AreaData[r].value) {
          const record = this.AreaData[r];
          record._expandedTest = !row._expandedTest;
          break;
        }
      }
      this.AreaDataTable = this.formatData2(this.AreaData);
    },
    formatData2(val) {
      let tmp = [];
      if (!Array.isArray(val)) {
        tmp = JSON.parse(JSON.stringify([val]));
      } else {
        tmp = JSON.parse(JSON.stringify(val));
      }
      let tmpShow = [];
      for (let i = 0; i < tmp.length; i++) {
        tmpShow.push(tmp[i]);
        if (!(tmp[i]._expandedTest === true || tmp[i]._expandedTest === false)) {
          tmp[i]._expandedTest = this.expandAll;
        }
        if (tmp[i]._expandedTest) {
          for (let k = 0; k < tmp[i].children.length; k++) {
            tmpShow.push(tmp[i].children[k]);
          }
        }
        // this.formatData2(tmp[i]);
      }
      return tmpShow;
    },
    // 查询字典项
    findDictionaryOptionFun() {
      var params = {
        dynamicParams: this.$store.state.appId,
        type: this.choseTable,
        DictionaryOptionCurrent: this.DictionaryOptionCurrent, // 这里是当前页
        DictionaryOptionPage: this.DictionaryOptionPage,
        optionKey: this.optionKey,
        optionName: this.optionName
      };
      this.spinShow = true;
      findDictionaryOptionTableList(params).then(data => {
        this.spinShow = false;
        this.tableTreeData = data.result.allDictionaryOptionTable;
        this.allTotal = data.result.allDictionaryOptionTableNum;
        this.optionData = this.formatData();
      }).catch(() => {
        this.spinShow = false;
        this.$message.error('获取字典项失败');
      });
    },
    changeSize(val) {
      this.DictionaryOptionCurrent = 1;
      this.DictionaryOptionPage = val;
      this.findDictionaryOptionFun();
    },
    gotoOnePage(val) {
      this.DictionaryOptionCurrent = val;
      this.findDictionaryOptionFun();
    },
    search() {
      this.DictionaryOptionCurrent = 1;
      this.DictionaryOptionPage = 10;
      this.findDictionaryOptionFun();
    },
    restting() {
      this.optionKey = '';
      this.optionName = '';
      this.DictionaryOptionCurrent = 1;
      this.DictionaryOptionPage = 10;
      this.findDictionaryOptionFun();
    },
    // 获取所有的省份的字典项
    findAllChianAreaFun() {
      findAllChianArea(this.$store.state.appId, data => {
        if (data.length > 0) {
          this.AreaData[0] = JSON.parse(JSON.stringify(data[0]));
          this.AreaDataTable = this.formatData2(this.AreaData);
        }
      });
    },
    changTabs(name) {
      if (name === 'common') {
        this.choseTable = 'RC01_AOBPR_';
        this.optionKey = '';
        this.optionName = '';
        this.DictionaryOptionCurrent = 1;
        this.DictionaryOptionPage = 10;
        this.findDictionaryOptionFun();
      } else if (name === 'technology') {
        this.choseTable = 'ZY02_AOBPR_';
        this.optionKey = '';
        this.optionName = '';
        this.DictionaryOptionCurrent = 1;
        this.DictionaryOptionPage = 10;
        this.findDictionaryOptionFun();
      } else if (name === 'areaOption') {
        this.choseTable = 'AREA';
        this.findAllChianAreaFun();
      }
    },
    enterChangeUserInfo(name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.addDictionaryOptionListFun();
        } else {
          this.$message.error('请填写完整的字典项信息');
        }
      });
    },
    cancelChangeUserInfo(name) {
      this.modalTitleFlag = false;
      this.$refs[name].resetFields();
    },
    // 添加字典项的方法
    addDictionaryOptionListFun() {
      let paramsTemp = {
        dynamicParams: this.$store.state.appId,
        optionType: this.optionCodeLimit,
        label: this.optionDataSave.label,
        value: this.optionDataSave.value,
        Pid: this.optionDataSave.Pid,
        description: this.optionDataSave.description
      };
      this.spinShow = true;
      addDictionaryOptionList(paramsTemp).then(data => {
        this.spinShow = false;
        if (data.result.flag === 'success') {
          this.$message.success(data.result.message);
          this.$refs['optionDataSaveRef'].resetFields();
          this.modalTitleFlag = false;
          // this.DictionaryOptionCurrent = 1;
          // this.DictionaryOptionPage = 10;
          // 个人认为添加完成后不用进行重置
          this.findDictionaryOptionFun();
        } else {
          this.$message.error(data.result.message);
          this.modalTitleFlag = true;
        }
      }).catch(() => {
        this.spinShow = false;
        this.modalTitleFlag = true;
        this.$message.error('添加字典项失败');
      });
    },
    // 添加字典项时候的弹出框
    addOption(val, parId) {
      if (val === 'richang') {
        this.modalTitle = '添加日常字典项';
        if (parId !== '0') { // 就是有父类的时候
          this.optionCodeLimit = 'NO';
        } else {
          this.optionCodeLimit = 'RC01_AOBPR_';
        }
      } else if (val === 'jishu') {
        this.modalTitle = '添加技术字典项';
        if (parId !== '0') { // 就是有父类的时候
          this.optionCodeLimit = 'NO';
        } else {
          this.optionCodeLimit = 'ZY02_AOBPR_';
        }
      }
      // 这里是默认的父级别
      this.optionDataSave.Pid = parId;
      this.modalTitleFlag = true;
    },
    delDictionaryOptionFun() {
      let params = {
        dynamicParams: this.$store.state.appId,
        value: this.delOptionKey
      };
      delDictionaryOption(params).then(data => {
        if (data.result.flag === 'success') {
          this.$message.success(data.result.message);
          this.delOptionFlag = false;
          this.DictionaryOptionCurrent = 1;
          this.DictionaryOptionPage = 10;
          this.findDictionaryOptionFun();
        } else {
          this.$message.success(data.result.message);
          this.delOptionFlag = true;
        }
      }).catch(() => {
        this.$message.error('删除字典项失败');
      });
    },
    delCurrentRow(row) {
      this.delOptionTitle = '删除字典项' + row.label;
      this.delOptinLabel = row.label;
      this.delOptionKey = row.value;
      this.delOptionFlag = true;
    },
    cancleDelOption() {
      this.delOptionFlag = false;
    },
    enterDelOption() {
      this.delDictionaryOptionFun();
    }
  },
  computed: {
  },
  watch: {
  }
};
</script>
<style lang="scss">
  @import './style.scss';
</style>
