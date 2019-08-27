<template>
  <div id="selfDataId" class="publicClass">
    <Row>
      <Col span="24">
      <h1>个人资料</h1>
      </Col>
    </Row>
      <Col span="24">
        <div class="publicLine">
        </div>
      </Col>
    </Row>
    <Row>
      <Col span="24">
        <Avatar :src="headerPic.samllPicInfo || 'https://i.loli.net/2017/08/21/599a521472424.jpg'" size="large" />
        <br>
        <span class="changePicture" @click="changePictureFun">更换头像</span>
      </Col>
    </Row>
    <Row>
        <Col span="24">
          <div class="changUserInfoClass" @click="changUserInfoFun">修改当前信息</div>
        </Col>
      </Row>
    <Row class="showUserInfo">
      <Col span="24">
        <Form :model="showuserInfo" label-position="right" :label-width="140">
            <FormItem label="博客名称:">
              {{showuserInfo.bigName}}
            </FormItem>
            <FormItem label="性别:">
              {{changeChinese(showuserInfo.sex)}}
            </FormItem>
            <FormItem label="爱好:">
              {{showuserInfo.hobby}}
            </FormItem>
            <FormItem label="QQ号码:">
              {{showuserInfo.qqNumber}}
            </FormItem>
            <FormItem label="所在城市:">
              {{showuserInfo.userAddressChinese}}
            </FormItem>
            <FormItem label="联系电话:">
              {{showuserInfo.userTel}}
            </FormItem>
            <FormItem label="严肃说:">
              {{showuserInfo.bokFlag}}
            </FormItem>
            <FormItem label="技能展示:">
              {{showuserInfo.bokSkill}}
            </FormItem>
            <FormItem label="自己的简介:">
              {{showuserInfo.aboutMyself}}
            </FormItem>
        </Form>
      </Col>
    </Row>
    <div class="showSkilsClass">
      <Row>
        <Col span="24">
          <Button @click="addSkillFun" type="primary" class="addskillsButtonClass">+新增掌握技能</Button>
        </Col>
      </Row>
      <div style="height: 10px;"></div>
      <Table :columns="columnsSkill" :data="dataSkill" border>
          <template slot-scope="{ row, index }" slot="skScore">
            <Progress :percent="row.skScore" :stroke-color="functionColor(row.skScore)" />
          </template>
        <template slot-scope="{ row, index }" slot="action">
          <Button type="primary" size="small" style="margin-right: 5px" @click="updateSkill(row)">修改</Button>
          <Button type="error" size="small" @click="removeSkill(row)">删除</Button>
        </template>
      </Table>
    </div>
    <!-- 下面是个人信息的修改弹出框 -->
    <Modal class="editUserClass" v-model="changeUserInfoFlag" title="修改用户信息" @on-ok="enterChangeUserInfo" @on-cancel="cancelChangeUserInfo" width="40%">
      <Form :model="userInfo" label-position="right" :label-width="100">
        <FormItem label="博客名称">
          <Input v-model="userInfo.bigName"></Input>
        </FormItem>
        <FormItem label="爱好">
          <Input v-model="userInfo.hobby"></Input>
        </FormItem>
        <FormItem label="性别">
          <Select v-model="userInfo.sex">
            <Option v-for="item in sexS" :value="item.value" :key="item.value">{{ item.label }}</Option>
          </Select>
        </FormItem>
        <FormItem label="QQ号码">
          <Input v-model="userInfo.qqNumber"></Input>
        </FormItem>
        <FormItem label="所在城市">
          <Cascader :data="AreaData" v-model="userInfo.userAddress" change-on-select :render-format="atCityFun"></Cascader>
        </FormItem>
        <FormItem label="联系电话">
          <Input v-model="userInfo.userTel"></Input>
        </FormItem>
        <FormItem label="严肃说">
          <Input v-model="userInfo.bokFlag"></Input>
        </FormItem>
        <FormItem label="技能展示">
          <Input v-model="userInfo.bokSkill"></Input>
        </FormItem>
        <FormItem label="关于自己的简介">
          <Input v-model="userInfo.aboutMyself"  style="resize:none"  type="textarea" :autosize="{minRows: 2,maxRows: 5}"></Input>
        </FormItem>
      </Form>
    </Modal>
    <!-- 上传头像的弹出框 -->
    <Modal v-model="headPortraitFlag" title="头像上传" width="40%">
      <!-- 裁剪 -->
      <uploadHeaderPic :fileCuuid="headerPic.smallPicId" :userId="userInfo.userId" @successFun = "successFun"></uploadHeaderPic>
      <template slot="footer">
        <Button type="error" @click="headPortraitFlag = false">关闭</Button>
      </template>
    </Modal>
    <!-- 添加修改技能的弹出框 -->
    <Modal v-model="skillFlag" :title="skillactionName" width="40%">
      <Form :model="bokSkillObj" label-position="right" :rules="bokSkillObjRule" ref="bokSkillObjRef" :label-width="200">
        <FormItem label="名字" prop="skName">
          <Input v-model="bokSkillObj.skName"></Input>
        </FormItem>
        <FormItem label="掌握百分比" prop="skScore">
          <Input v-model="bokSkillObj.skScore"></Input>
        </FormItem>
      </Form>
      <template slot="footer">
        <Button type="error" ghost @click="skillFlag = false">取消</Button>
        <Button type="primary" ghost @click="enterAddUserBokFun">{{enterButtonName}}</Button>
      </template>
    </Modal>
    <!-- 删除技能 -->
    <Modal v-model="delSkillFlag" :title="delSkillName">
      <template slot="footer">
        <Button type="error" @click="delSkillFlag = false">取消</Button>
        <Button type="primary" @click="enterDeleteSkill">确定</Button>
      </template>
      </Modal>
  </div>
</template>
<script>
import { findAllUserInfoCon } from '../../../../network/aliangbok_back/aliangbokCo/welcome/index';
import { findAllChianArea, findDictionaryOptionList } from '../../../../network/public';
import { Code } from '../../../../network/publicOptioCode';
import { updateSelfData } from '../../../../network/aliangbok_back/aliangbokCo/selfData/index';
import { addBokSkill, delBokSkill, updateBokSkill, findBokSkill } from '../../../../network/aliangbok_back/aliangbokCo/bokSkill/index';
import uploadHeaderPic from '../commons/uploadHeaderPic/index';

export default {
  components: {
    uploadHeaderPic
  },
  data() {
    return {
      userInfo: {
        bigName: '', // 博客名称
        hobby: '', // 爱好
        qqNumber: '', // qq号码，
        userAddress: [], // 所在城市编码
        userTel: '', // 联系电话
        sex: '', // 性别
        aboutMyself: '', // 关于自己
        bokFlag: '', // 座右铭
        bokSkill: '', // 博主的才能
        userId: ''
      },
      showuserInfo: {
        bigName: '', // 博客名称
        hobby: '', // 爱好
        qqNumber: '', // qq号码，
        userAddressChinese: '', // 所在城市
        userTel: '', // 联系电话
        sex: '', // 性别
        aboutMyself: '',
        bokFlag: '', // 座右铭
        bokSkill: '' // 博主的才能
      },
      headerPic: {
        smallPicId: '', // 图片id
        samllPicInfo: '' // 图片
      },
      changeUserInfoFlag: false,
      AreaData: [],
      addressTemp: [],
      sexS: [],
      headPortraitFlag: false,
      dataSkill: [
      ],
      columnsSkill: [
        {
          title: '编号',
          type: 'index',
          width: 60,
          align: 'center'
        },
        {
          title: '技能名字',
          key: 'skName'
        },
        {
          title: '技能熟练度',
          slot: 'skScore'
        },
        {
          title: '操作',
          slot: 'action',
          width: 150,
          align: 'center'
        }
      ],
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
      skillFlag: false,
      skillactionName: '新增掌握技能',
      bokSkillObj: {
        skName: '',
        skScore: 0
      },
      bokSkillObjRule: {
        skName: [
          { required: true, message: '请输入技能名称', trigger: 'blur' }
        ],
        skScore: [
          { required: true, message: '请输入技能分数', trigger: 'blur' }
        ]
      },
      delSkillName: '',
      delSkillFlag: false,
      delSkilId: '',
      enterType: 'add',
      enterButtonName: '确认新增'
    };
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      // 查询省市区的所有地址
      this.findAllChianAreaFun();
      this.findDictionaryOptionListFun();
    },
    // 获取所有的省份的字典项
    findAllChianAreaFun() {
      var selfthis = this;
      findAllChianArea(selfthis.$store.state.appId, data => {
        if (data.length > 0) {
          selfthis.AreaData[0] = JSON.parse(JSON.stringify(data[0]));
          console.log(selfthis.AreaData, '这里打印出来的数据是什么');
        }
      });
    },
    getUserBokInfo() {
      findAllUserInfoCon({ dynamicParams: this.$store.state.appId, loginToken: sessionStorage.getItem('loginToken') }).then(data => {
        console.log(data);
        if (data.result.flag === 'success') {
          // 这里是一些不用显示内容获取
          this.userInfo.userId = data.result.bokuser.userId || '';
          this.findBokSkillFun(); // 查询技能的
          this.headerPic.smallPicId = data.result.bokuser.bokHeaderPic.smallPicId || '';
          this.headerPic.samllPicInfo = data.result.bokuser.bokHeaderPic.samllPicInfo || '';
          // 下面是显示的内容获取
          this.showuserInfo.bigName = data.result.bokuser.bigName || '';
          this.showuserInfo.hobby = data.result.bokuser.hobby || '';
          this.showuserInfo.qqNumber = data.result.bokuser.qqNumber || '';
          this.showuserInfo.userTel = data.result.bokuser.userTel || '';
          this.showuserInfo.sex = data.result.bokuser.sex || '';
          this.showuserInfo.aboutMyself = data.result.bokuser.aboutMyself || '';
          this.showuserInfo.bokFlag = data.result.bokuser.bokFlag || '';
          this.showuserInfo.bokSkill = data.result.bokuser.bokSkill || '';
          var userAddressTemp = (data.result.bokuser.userAddress || '').split('-');
          if (userAddressTemp[0] === '') {
            this.userInfo.userAddress = [];
          } else {
            this.userInfo.userAddress = userAddressTemp;
          }
          this.addressTemp = [];
          this.getAddressChinese(0, this.userInfo.userAddress, this.userInfo.userAddress.length, this.AreaData);
          this.showuserInfo.userAddressChinese = this.addressTemp.join('/');
        } else {
          this.$message.error('获取用户信息失败');
        }
      }).catch(() => {
        this.$message.error('获取用户信息失败');
      });
    },
    // 用户信息修改的方法
    updateSelfDataFun() {
      var jsonTemp = JSON.parse(JSON.stringify(this.userInfo));
      jsonTemp.dynamicParams = this.$store.state.appId;
      jsonTemp.userAddressString = this.userInfo.userAddress.join('-');
      jsonTemp.loginToken = sessionStorage.getItem('loginToken');
      updateSelfData(jsonTemp).then(data => {
        if (data.result.flag === 'success') {
          this.$message.success('个人信息修改成功');
          this.getUserBokInfo();
        } else {
          this.$message.error('个人信息修改失败');
        }
      }).catch(() => {
        this.$message.error('个人信息保存失败');
      });
    },
    // 修改用户信息弹出框
    changUserInfoFun() {
      this.changeUserInfoFlag = true;
      this.userInfo.bigName = this.showuserInfo.bigName;
      this.userInfo.hobby = this.showuserInfo.hobby;
      this.userInfo.qqNumber = this.showuserInfo.qqNumber;
      this.userInfo.userTel = this.showuserInfo.userTel;
      this.userInfo.sex = this.showuserInfo.sex;
      this.userInfo.aboutMyself = this.showuserInfo.aboutMyself;
      this.userInfo.bokFlag = this.showuserInfo.bokFlag;
      this.userInfo.bokSkill = this.showuserInfo.bokSkill;
    },
    // 这里是确认修改信息
    enterChangeUserInfo() {
      this.updateSelfDataFun();
      this.changeUserInfoFlag = false;
    },
    // 这里是取消修改信息
    cancelChangeUserInfo() {
      this.changeUserInfoFlag = false;
    },
    // 更换头像方法
    changePictureFun() {
      this.headPortraitFlag = true;
    },
    // 所在城市的方法
    atCityFun(labels, selectedData) {
      return labels.join('/');
    },
    // 发现所有的方法
    findDictionaryOptionListFun() {
      var params = {
        dynamicParams: this.$store.state.appId,
        DictionaryOptionList: [
          Code.RC01_AOBPR_AL_SEX
        ]
      };
      findDictionaryOptionList(params).then(data => {
        this.sexS = data.result.RC01_AOBPR_AL_SEX;
        // 获取用户信息
        this.getUserBokInfo();
      }).catch(() => {
        this.$message.error('获取字典项失败');
      });
    },
    changeChinese(val) {
      for (let se of this.sexS) {
        if (se.value === val) {
          return se.label;
        }
      }
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
    },
    successFun(val) {
      if (val.result.flag === 'success') {
        this.headPortraitFlag = false;
        this.getUserBokInfo();
        this.$message.success('头像上传成功');
      } else {
        this.$message.error('头像上传失败');
      }
    },
    functionColor(val) {
      let num = val * 1;
      let tempNum = Math.ceil(num / 10) - 1;
      return this.colorArr[tempNum];
    },
    addSkillFun() {
      this.skillFlag = true;
      this.skillactionName = '新增掌握技能';
      this.enterType = 'add';
      this.enterButtonName = '确认新增';
    },
    // skill查询方法
    findBokSkillFun() {
      let params = {
        dynamicParams: this.$store.state.appId,
        userId: this.userInfo.userId
      };
      findBokSkill(params).then(data => {
        if (data.result.falg === 'success') {
          this.dataSkill = data.result.skillList;
        } else {
          this.$message.error(data.result.message);
        }
      }).catch(() => {
        this.$message.error('查询列表出错');
      });
    },
    // skill修改方法
    updateBokSkillFun(val) {
      let params = {
        dynamicParams: this.$store.state.appId,
        userId: this.userInfo.userId,
        skId: this.bokSkillObj.skId,
        skName: this.bokSkillObj.skName,
        skScore: this.bokSkillObj.skScore
      };
      updateBokSkill(params).then(data => {
        if (data.result.falg === 'success') {
          this.$message.success(data.result.message);
          this.skillFlag = false;
          this.findBokSkillFun();
        } else {
          this.$message.error(data.result.message);
        }
      }).catch(() => {
        this.$message.error('查询列表出错');
      });
    },
    // skill删除方法
    delBokSkillFun(val) {
      let params = {
        dynamicParams: this.$store.state.appId,
        userId: this.userInfo.userId,
        skId: val
      };
      delBokSkill(params).then(data => {
        if (data.result.falg === 'success') {
          this.$message.success(data.result.message);
          this.findBokSkillFun();
        } else {
          this.$message.error(data.result.message);
        }
      }).catch(() => {
        this.$message.error('查询列表出错');
      });
    },
    // skill添加方法
    addBokSkillFun() {
      let params = {
        dynamicParams: this.$store.state.appId,
        userId: this.userInfo.userId,
        skName: this.bokSkillObj.skName,
        skScore: this.bokSkillObj.skScore
      };
      addBokSkill(params).then(data => {
        if (data.result.falg === 'success') {
          this.$message.success(data.result.message);
          this.$refs['bokSkillObjRef'].resetFields();
          this.skillFlag = false;
          this.findBokSkillFun();
        } else {
          this.$message.error(data.result.message);
        }
      }).catch(() => {
        this.$message.error('新增失败');
      });
    },
    enterAddUserBokFun() {
      var _this = this;
      this.$refs['bokSkillObjRef'].validate((valid) => {
        if (valid) {
          if (this.enterType === 'add') {
            _this.addBokSkillFun();
          } else {
            _this.updateBokSkillFun();
          }
        }
      });
    },
    updateSkill(val) {
      this.skillFlag = true;
      this.skillactionName = '修改掌握技能';
      this.bokSkillObj.skName = val.skName;
      this.bokSkillObj.skScore = val.skScore;
      this.bokSkillObj.skId = val.skId;
      this.enterType = 'edit';
      this.enterButtonName = '确认修改';
    },
    removeSkill(val) {
      this.delSkillFlag = true;
      this.delSkillName = '确认删除' + val.skName + '?';
      this.delSkilId = val.skId;
    },
    enterDeleteSkill() {
      this.delSkillFlag = false;
      this.delBokSkillFun(this.delSkilId);
    }
  }
};
</script>
<style lang="scss">
  @import '../../../../assets/style/publicStyle.scss';
  @import './style.scss';
</style>
