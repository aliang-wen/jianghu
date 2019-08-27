<template>
  <div id="aliangbokId" @keyup.enter="handleSubmit('formInline')">
    <div style="height: 30%;"></div>
    <div class="LoginBox">
      <h1>欢迎</h1>
      <Form ref="formInline" :model="formInline" :rules="ruleInline" inline>
        <FormItem prop="userName">
          <Input type="text" v-model="formInline.userName" placeholder="请输入用户名" size="large">
          <Icon type="ios-person-outline" slot="prepend"></Icon>
          </Input>
        </FormItem>
        <br>
        <FormItem prop="password">
          <Input type="password" v-model="formInline.password" placeholder="请输入密码" size="large">
          <Icon type="ios-lock-outline" slot="prepend"></Icon>
          </Input>
        </FormItem>
        <br>
        <FormItem prop="virCode">
          <div class="virCodeClass">
            <Input type="text" v-model="formInline.virCode" placeholder="请输入验证码" size="large" style="width: 150px; float: left;">
              <Icon type="ios-create-outline" slot="prepend"></Icon>
            </Input>
            <div class="codeImg" @click="getCode">
              <img v-if ="virCodeUrl != ''" :src="virCodeUrl" height="34px" alt="阿良" >
              <span v-else>点击获取验证码</span>
            </div>
          </div>
        </FormItem>
        <br>
        <FormItem>
          <Button type="primary" @click="handleSubmit('formInline')" size="large" :disabled="loginButtonFlag">登录</Button>
        </FormItem>
      </Form>
    </div>
    <Spin size="large" fix v-if="spinShow" absolute></Spin>
  </div>
</template>
<script>
import { LoginBok, giveKeyIv, getSecurityCode } from '../../../network/aliangbok_back/login/index';

export default {
  data() {
    return {
      spinShow: false,
      heightWindow: 0,
      formInline: {
        userName: '',
        password: '',
        virCode: ''
      },
      SecutityCodeToken: '',
      virCodeUrl: '',
      ruleInline: {
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入用户密码', trigger: 'blur' }
        ],
        virCode: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ]
      },
      loginButtonFlag: false
    };
  },
  mounted() {
    console.log('没有彩蛋大家请自便');
    this.heightWindow = $(window).height();
    $('#aliangbokId').height(this.heightWindow);
  },
  methods: {
    loginFun() {
      // 登陆前请求登录出来的密钥
      this.loginButtonFlag = true;
      giveKeyIv({ dynamicParams: this.$store.state.appId }).then(data => {
        // 加密方法
        if (data.result.flag === 'success') {
          // var userna = hy.crypto.Encrypt(this.formInline.userName, data.result.key, data.result.iv);
          var passd = hy.crypto.Encrypt(this.formInline.password, data.result.key, data.result.iv);
          var json = {
            dynamicParams: this.$store.state.appId,
            userName: this.formInline.userName,
            password: passd,
            virCode: this.formInline.virCode,
            SecutityCodeToken: this.SecutityCodeToken
          };
          LoginBok(json).then(data => {
            if (data.result.flag === 'success') {
              // 登录成功之后进行页面跳转 如何将token进行存储
              sessionStorage.setItem('loginToken', data.result.loginToken);
              window.hy.db.cookie.setValue('loginToken', data.result.loginToken);
              this.$router.push({
                path: '/back'
              });
            } else {
              this.loginButtonFlag = false;
              this.$message.error(data.result.message);
            }
          }).catch(() => {
            this.loginButtonFlag = false;
            this.$message.error('登录失败');
          });
        } else {
          this.loginButtonFlag = false;
          this.$message.error(data.result.message);
        }
      }).catch(() => {
        this.loginButtonFlag = false;
        this.$message.error('登录失败');
      });
    },
    handleSubmit(name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.loginFun();
        } else {
          this.$message.error('请填写完整用户名密码');
        }
      });
    },
    getCode() {
      this.spinShow = true;
      getSecurityCode({ dynamicParams: this.$store.state.appId }).then(data => {
        this.spinShow = false;
        if (data.result.flag === 'success') {
          this.virCodeUrl = data.result.SecurityPic;
          this.SecutityCodeToken = data.result.SecutityCodeToken;
        } else {
          this.$message.error(data.result.message);
        }
      }).catch(() => {
        this.spinShow = false;
        this.$message.error('获取验证码失败了');
      });
    }
  },
  watch: {
  }
};
</script>
<style lang="scss">
  @import'./style.scss';
</style>
