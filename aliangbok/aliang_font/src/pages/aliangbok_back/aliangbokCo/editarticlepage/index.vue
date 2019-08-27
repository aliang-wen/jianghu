<template>
  <div id="editorticleId">
    <div class="editorTitleClass">
      <div class="editorTitleInput">
        <Input placeholder="请输入文章标题" v-model="editorTitle" size="large" @keyup.native="checkNumAboutTitle">
          <div class="showNUmner" slot="suffix">
            {{inputNum}}/100
          </div>
        </Input>
      </div>
      <div class="clickButton">
        <Button size="large" type="warning" @click="saveEditorFun">保存文章</Button>
        <Button size="large" type="error" @click="submitEditorFun">发布文章</Button>
      </div>
      <div class="loginOutClass" @click="loginOutFun">
        <Icon type="md-exit" />
      </div>
      <div class="showPeoplePicClass">
        <Poptip title="操作" placement="bottom-end" trigger="hover">
          <Avatar src="https://i.loli.net/2017/08/21/599a521472424.jpg" />
          <div class="showWen" slot="content">
            <CellGroup>
              <Cell title="主页" to="/back/welcome" />
              <Cell title="设置页面" />
              <Cell title="退出" @click.native="loginOutFun" />
            </CellGroup>
          </div>
        </Poptip>
      </div>
    </div>
    <!-- 编辑器菜单 -->
    <div class="editorListClass" id="editListId">
      <Button @click="goback"><Icon type="ios-arrow-back" />返回</Button>
      <Icon type="md-settings" @click="getShwoDrawerInfo" title="文章属性设置" style="color: #CC0000;" />
    </div>
    <div class="editAtThis" v-show="!showEssayInWindowFlag">
      <!-- 编辑编写 -->
      <div class="writeIn" id="writenInId">
      </div>
      <!-- 编辑器写的结果查看 -->
      <div class="watchAt allWriteHtmlContent" id="watchAtId" @mouseover="mouseOn('right')" @scroll="goOnAllScroll();">
      </div>
    </div>
    <div class="showEssayAllWindowsClass" v-show="showEssayInWindowFlag">
      <div class="showEssayBodyClass" id="showInWindowId"></div>
    </div>
    <div class="editorFooterClass">
    </div>

    <Drawer title="文章属性设置" id="essaySetPropertyId" v-model="showDrawer" width="40" :mask-closable="false" :styles="drawerStyles">
      <div class="bodyFromClass">
        <Form :model="essayInfo" ref="essaySetProperty" :rules="optionDataSaveRule" label-position="right" :label-width="80">
          <FormItem label="文章展示图" prop="titlePpicture">
            <div v-show="uploadTitlePicList.length > 0" class="demo-upload-list" v-for="item in uploadTitlePicList" :key="item.name">
              <template v-if="item.status === 'finished'">
                <img :src="item.url">
                <div class="demo-upload-list-cover">
                  <Icon type="ios-eye-outline" @click.native="essayTitlePicHandleView(item)"></Icon>
                  <Icon type="ios-trash-outline" @click.native="essayTitlePicHandleRemove(item)"></Icon>
                </div>
              </template>
              <template v-else>
                <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
              </template>
            </div>
            <Upload v-show="uploadTitlePicList.length <= 0" ref="essayTitlePicUpload" :show-upload-list="false" :default-file-list="essayTitlePicdefaultList" :on-success="essayTitlePicHandleSuccess"
              :format="['jpg','jpeg','png']" :max-size="2048" :on-format-error="essayTitlePicHandleFormatError" :on-exceeded-size="essayTitlePicHandleMaxSize"
              :before-upload="essayTitlePicHandleBeforeUpload" type="drag" :data="uploadTitleFile" :headers = "headersConfig" :action="titleUploadPathUrl"
              style="display: inline-block;width:308px;">
              <div style="width: 308px;height:130px;line-height: 130px;">
                <Icon type="ios-camera" size="20"></Icon>
              </div>
            </Upload>
            <Input v-model="essayInfo.titlePpicture" :disabled = "uploadDisableFlag" placeholder="请输入文章展示图地址..." @on-change="inputEssayTitlePicUrl"></Input>
          </FormItem>
          <FormItem label="文章副标题" prop="conFTitle">
            <Input v-model="essayInfo.conFTitle" :maxlength="66" placeholder="请输入文章副标题..." @on-change="inputEssayTitlePicUrl"></Input>
          </FormItem>
          <FormItem label="文章描述" prop="conGText">
            <Input v-model="essayInfo.conGText" :maxlength="160" type="textarea" :autosize="{minRows: 3,maxRows: 5}" placeholder="概括描述文章..." @on-change="inputEssayTitlePicUrl"></Input>
          </FormItem>
          <FormItem label="文章分类" prop="conGText">
            <Cascader :data="essayTypeLsit" v-model="essayInfoTypeTemp" @on-change="changeEssayType"></Cascader>
          </FormItem>
          <FormItem label="文章番号" prop="essaySeries">
            <Input v-model="essayInfo.essaySeries" :maxlength="30" placeholder="在写一些列的文章的时候推荐设置成一样的" @on-change="inputEssayTitlePicUrl"></Input>
          </FormItem>
          <FormItem label="轮播展示" prop="CarouselShow">
            <i-switch :value="CarouselShowComp" @on-change="changeSwitch($event, essayInfo, 'CarouselShow')">
              <span slot="open">开</span>
              <span slot="close">关</span>
            </i-switch>
          </FormItem>
          <FormItem label="盒子展示" prop="showBox">
            <i-switch :value="showBoxComp" @on-change="changeSwitch($event, essayInfo, 'showBox')">
              <span slot="open">开</span>
              <span slot="close">关</span>
            </i-switch>
            最多存在两个
          </FormItem>
          <FormItem label="点击次数" prop="readCount">
            <InputNumber :max="99999999" v-model="essayInfo.readCount" :formatter="value => `👍 ${value}`.replace(/B(?=(d{3})+(?!d))/g, ',')"
              :parser="value => value.replace(/$s?|(,*)/g, '')" @on-change="inputEssayTitlePicUrl"></InputNumber>
          </FormItem>
          <FormItem label="推荐星" prop="recomStart">
            <Rate show-text allow-half v-model="essayInfo.recomStart" :count="10" @on-change="inputEssayTitlePicUrl">
              <span style="color: #f5a623">{{ essayInfo.recomStart }}</span>
            </Rate>
          </FormItem>
          <FormItem label="发表状态" prop="ariticleState">
            <i-switch :value="ariticleStateComp" @on-change="changeSwitch($event, essayInfo, 'ariticleState')">
              <span slot="open">开</span>
              <span slot="close">关</span>
            </i-switch>
          </FormItem>
          <FormItem label="隐私发表" prop="ariticlePrivate">
            <i-switch :value="ariticlePrivateComp" @on-change="changeSwitch($event, essayInfo, 'ariticlePrivate')">
              <span slot="open">开</span>
              <span slot="close">关</span>
            </i-switch>
          </FormItem>
        </Form>
      </div>
      <div class="demo-drawer-footer">
        <Button style="margin-right: 8px" @click="showDrawer = false">关闭</Button>
      </div>
    </Drawer>
    <!-- 这里是左侧的控制栏 -->
    <div id="floatLetfoperationId">
      <Card :bordered="false" :class="{moveMouserClass: mouseOnFlag, onMouserClass: !mouseOnFlag}" @mouseover.native="mouseOnFlag = true" @mouseout.native="mouseOnFlag = false">
        <span @click="setEssayCodeFromttingFun" style="cursor:pointer;"><Icon title="源代码格式设置" type="md-settings" size="18" /></span>
        <br/>
        <span @click="editCodeFromEssayFun" style="cursor:pointer;"><Icon title="查看源代码" type="md-aperture" size="18" /></span>
        <br/>
        <span @click="viewEssayFun" style="cursor:pointer;"><Icon title="预览" type="md-eye" size="18" /></span>
        <br/>
        <span @click="writeEssayFun" style="cursor:pointer;"><Icon title="编写文章" type="md-create" size="18" /></span>
      </Card>
    </div>
    <!-- 这里是源代码预览的弹出框 -->
    <Modal v-model="essayCodeFlag" class="essayCodeClass" draggable :title="editorTitle === '' ? '文章' : editorTitle + '的源码'" width="90">
      <Input v-model="essayCodeInput" type="textarea" :autosize="{minRows: 15,maxRows: 20}" placeholder="Enter something..." />
      <template slot="footer">
        <Button type="error" @click="closeEditModalFun">关闭</Button>
        <Button type="primary" @click="saveEssayCodeFun">保存</Button>
      </template>
    </Modal>
    <Modal v-model="setEssayCodeFromttingFlag" draggable title="设置代码格式" class="setEssayCodeFromttingClass">
      <Form :model="setEssayCodeFromttingObj" ref="setEssayCodeFromttingRef" label-position="right" :label-width="120">
        <FormItem label="缩进空格">
          <Select v-model="setEssayCodeFromttingObj.indent_size">
            <Option value="1">使用tab键缩进</Option>
            <Option value="2">使用2个空格键</Option>
            <Option value="3">使用3个空格键</Option>
            <Option value="4">使用4个空格键</Option>
            <Option value="8">使用8个空格键</Option>
          </Select>
        </FormItem>
        <FormItem label="标签间换行">
          <Select v-model="setEssayCodeFromttingObj.max_preserve_newlines">
            <Option value="-1">删除所有多余换行</Option>
            <Option value="1">标记之间允许存在1个换行</Option>
            <Option value="2">标记之间允许存在2个换行</Option>
            <Option value="5">标记之间允许存在5个换行</Option>
            <Option value="10">标记之间允许存在10个换行</Option>
            <Option value="0">标记之间允许存在无限个换行</Option>
          </Select>
        </FormItem>
        <FormItem label="长度换行">
          <Select v-model="setEssayCodeFromttingObj.wrap_line_length">
            <Option value="0">不换行</Option>
            <Option value="40">在40个字符处换行</Option>
            <Option value="70">在70个字符处换行</Option>
            <Option value="80">在80个字符处换行</Option>
            <Option value="110">在110个字符处换行</Option>
            <Option value="120">在120个字符处换行</Option>
            <Option value="160">在160个字符处换行</Option>
          </Select>
        </FormItem>
        <FormItem label="括号语句控制">
          <Select v-model="setEssayCodeFromttingObj.brace_style">
            <Option value="collapse">使用括号控制语句</Option>
            <Option value="collapse-preserve-inline">保持括号不独立换行</Option>
            <Option value="expand">括号在独立的行</Option>
            <Option value="end-expand">结束括号在独立的行</Option>
            <Option value="none">保持格式化之后的括号位置不变</Option>
          </Select>
        </FormItem>
        <FormItem label="HTML <style>, <script> formatting:">
          <Select v-model="setEssayCodeFromttingObj.indent_scripts">
            <Option value="keep">保持标签的缩进级别</Option>
            <Option value="normal">添加一个缩进级别</Option>
            <Option value="separate">单独缩进</Option>
          </Select>
        </FormItem>
        <FormItem label="末尾新行">
          <Checkbox v-model="setEssayCodeFromttingObj.end_with_newline">在脚本或样式末尾添加一个新行</Checkbox>
        </FormItem>
        <FormItem label="e4x/jsx">
          <Checkbox v-model="setEssayCodeFromttingObj.e4x">支持 e4x/jsx 语法</Checkbox>
        </FormItem>
        <FormItem label="模糊化处理">
          <Checkbox v-model="setEssayCodeFromttingObj.detectPackers">检测包并模糊化处理?</Checkbox>
        </FormItem>
        <FormItem label="数组缩进">
          <Checkbox v-model="setEssayCodeFromttingObj.keep_array_indentation">保持数组缩进?</Checkbox>
        </FormItem>
        <FormItem label="链式方法换行">
          <Checkbox v-model="setEssayCodeFromttingObj.break_chained_methods">在链式方法处换行?</Checkbox>
        </FormItem>
        <FormItem label="条件语句前空格">
          <CheckboxGroup>
            <Checkbox v-model="setEssayCodeFromttingObj.space_before_conditional">条件语句之前的空格: if(x) if (x)</Checkbox>
          </CheckboxGroup>
        </FormItem>
        <FormItem label="不转义可打印字符">
          <CheckboxGroup >
            <Checkbox v-model="setEssayCodeFromttingObj.unescape_strings">不转义可打印字符如 \xNN 或 \uNNNN?</Checkbox>
          </CheckboxGroup>
        </FormItem>
        <FormItem label="JSLint-happy">
          <CheckboxGroup>
            <Checkbox v-model="setEssayCodeFromttingObj.jslint_happy">使用 JSLint-happy 格式调整?</Checkbox>
          </CheckboxGroup>
        </FormItem>
        <FormItem label="缩进">
          <CheckboxGroup>
            <Checkbox  v-model="setEssayCodeFromttingObj.indent_inner_html">缩进 head 和 body 部分?</Checkbox>
          </CheckboxGroup>
        </FormItem>
      </Form>
      <template slot="footer">
        <Button type="error" @click="setEssayCodeFromttingFlag = false">关闭</Button>
      </template>
    </Modal>
    <Spin size="large" fix v-if="spinShow" absolute></Spin>
  </div>
</template>
<script>
import { findEssayById, updateEssayById, delPictureTitle } from '../../../../network/aliangbok_back/aliangbokCo/ariticle/index';
import { RC01_AOBPR_AL_SWITCH_ON_OFF } from '../../../../network/publicOptioCode';
import { findDictionaryOptionTableList } from '../../../../network/aliangbok_back/aliangbokCo/dictionaryOption/index';
import { TitleUploadPathUrl, PageUploadPathUrl } from '../../../../network/public';

export default {
  components: {
  },
  data() {
    return {
      false: false,
      true: true,
      spinShow: false,
      showEditFlag: true,
      editorTitle: '', // 文章的标题
      inputNum: 0,
      editWangL: Object,
      mouseLeft: false,
      mouseRight: false,
      showDrawer: false,
      drawerStyles: {
        height: 'calc(100% - 55px)',
        overflow: 'auto',
        paddingBottom: '53px',
        position: 'static'
      },
      essayInfo: {
        essayId: '', // 文章id
        essayFCuuId: '', // 文章副id
        userId: '', // 用户id
        essayNum: '', // 文章编hao
        conText: '', // 文章内容
        conTitle: '', // 文章标题,
        conType: '', // 文章分类
        essaySeries: '', // 文章一系列的分类
        CarouselShow: '', // 是否轮播
        showBox: '', // 是否展示在盒子上
        readCount: 0, // 点击阅读数
        recomStart: 0, // 推荐星
        conFTitle: '', // 文章副标题
        conGText: '', // 文章简介
        ariticleState: '', // 文章发布情况
        ariticlePrivate: '', // 文章是否私有
        titlePpicture: '' // 文章展示图
      },
      changContentSaveTime: 0,
      optionDataSaveRule: {},
      essayInfoTypeTemp: [],
      essayTypeLsit: [],
      essayTitlePicdefaultList: [ // 这里是默认的
      ],
      uploadTitlePicList: [],
      mouseOnFlag: false,
      essayCodeFlag: false,
      showEssayInWindowFlag: false,
      TempEssay: '',
      essayCodeInput: '',
      setEssayCodeFromttingFlag: false,
      setEssayCodeFromttingObj: {
        indent_size: '4',
        max_preserve_newlines: '5',
        wrap_line_length: '40',
        brace_style: 'collapse-preserve-inline',
        indent_scripts: 'normal',
        end_with_newline: false,
        e4x: false,
        detectPackers: true,
        keep_array_indentation: false,
        break_chained_methods: true,
        space_before_conditional: true,
        unescape_strings: false,
        jslint_happy: false,
        indent_inner_html: false
      },
      headersConfig: {
        verifyToken: '123456789ceshikey',
        loginToken: sessionStorage.getItem('loginToken')
        // Accept: 'application/json, text/plain, */*; charset=utf-8',
        // 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF8'
        // 'Content-Type': 'application/json;charset=UTF8'
      },
      uploadTitleFile: {},
      uploadDisableFlag: false,
      titleUploadPathUrl: '',
      essayUploadPathUrl: ''
    };
  },
  mounted() {
    this.init();
  },
  computed: {
    CarouselShowComp() {
      return this.switchTrueFalse(this.essayInfo.CarouselShow);
    },
    showBoxComp() {
      return this.switchTrueFalse(this.essayInfo.showBox);
    },
    ariticleStateComp() {
      return this.switchTrueFalse(this.essayInfo.ariticleState);
    },
    ariticlePrivateComp() {
      return this.switchTrueFalse(this.essayInfo.ariticlePrivate);
    }
  },
  methods: {
    init() {
      if (this.$route.query.essayId) {
        this.findEssayByIdFun(this.$route.query.essayId);
      } else {
        this.findEssayByIdFun('');
      }
      // 将图片加载后进行赋值
      this.uploadTitlePicList = this.$refs.essayTitlePicUpload.fileList;
      // 查询字典项
      this.findDictionaryOptionFun();
    },
    // 查询字典项
    findDictionaryOptionFun() {
      var params = {
        dynamicParams: this.$store.state.appId,
        type: 'ZY02_AOBPR_',
        DictionaryOptionCurrent: 1, // 这里是当前页
        DictionaryOptionPage: 100,
        optionKey: '',
        optionName: ''
      };
      this.spinShow = true;
      findDictionaryOptionTableList(params).then(data => {
        this.spinShow = false;
        this.essayTypeLsit = [...data.result.allDictionaryOptionTable];
      }).catch(() => {
        this.spinShow = false;
        this.$message.error('获取字典项失败');
      });
    },
    findEssayByIdFun(val) {
      let params = {
        dynamicParams: this.$store.state.appId,
        essayId: val,
        userToken: sessionStorage.getItem('loginToken')
      };
      this.spinShow = true;
      findEssayById(params).then(data => {
        this.spinShow = false;
        this.creatEdit();
        setTimeout(() => {
          this.setWETextFun();
        }, 100);
        if (data.result.falg === 'success') {
          setTimeout(() => {
            // 文章内容赋值
            this.editWangL.txt.html(data.result.essay.conText);
            // 文章内容展示赋值
            $('#watchAtId').html(data.result.essay.conText);
            // 文章标题赋值
            this.editorTitle = data.result.essay.conTitle || '';
            // 文章标题展示赋值
            this.inputNum = this.editorTitle.length || 0;
            // 文章标题赋值
            this.essayInfo.conTitle = this.editorTitle;
            // 文章id
            this.essayInfo.essayId = data.result.essay.essayId || '';
            // 文章的副id
            this.essayInfo.essayFCuuId = data.result.essay.essayFCuuId || '';
            this.uploadTitleFile['essayFCuuId'] = data.result.essay.essayFCuuId || '';
            this.editWangL.customConfig.uploadImgParams['essayFCuuId'] = this.essayInfo.essayFCuuId;
            // 用户的id
            this.essayInfo.userId = data.result.essay.userId || '';
            // 文章的编号 后台根据吧这里分两种情况进行查询
            this.essayInfo.essayNum = data.result.essay.essayNum || '';
            // 文章内容
            this.essayInfo.conText = data.result.essay.conText || '';
            // 文章系列
            this.essayInfo.essaySeries = data.result.essay.essaySeries || '';
            // 文章分类
            this.essayInfo.conType = data.result.essay.conType || '';
            // 是否轮播
            this.essayInfo.CarouselShow = data.result.essay.carouselShow || '';
            // 是否展示在盒子上
            this.essayInfo.showBox = data.result.essay.showBox || '';
            // 点击阅读数
            this.essayInfo.readCount = data.result.essay.readCount * 1 || 0;
            // 推荐按星
            this.essayInfo.recomStart = data.result.essay.recomStart * 1 || 0;
            // 文章副标题
            this.essayInfo.conFTitle = data.result.essay.conFTitle || '';
            // 文章简介
            this.essayInfo.conGText = data.result.essay.conGText || '';
            // 文章发布情况
            this.essayInfo.ariticleState = data.result.essay.ariticleState || '';
            // 文章是否私有
            this.essayInfo.ariticlePrivate = data.result.essay.ariticlePrivate || '';
            // 文章展示图
            this.essayInfo.titlePpicture = data.result.essay.titlePpicture || '';
            // 这里是对图片加载显示
            if (this.essayInfo.titlePpicture === '' || this.essayInfo.titlePpicture === null || this.essayInfo.titlePpicture === undefined) {
              this.essayTitlePicdefaultList = [];
            } else {
              this.essayTitlePicdefaultList = [
                {
                  'name': '',
                  'url': this.essayInfo.titlePpicture
                }
              ];
              if (this.essayInfo.titlePpicture.indexOf('upload') > -1) {
                this.uploadDisableFlag = true;
              } else {
                this.uploadDisableFlag = false;
              }
            }
            setTimeout(() => {
              this.uploadTitlePicList = this.$refs.essayTitlePicUpload.fileList;
            }, 100);
            // 进行级联下拉选赋值
            this.essayInfoTypeTemp = (this.essayInfo.conType || '').split('-');
          }, 100);
        } else {
          this.$message.error('获取文章信息失败');
        }
      }).catch(() => {
        this.spinShow = false;
        this.$message.error('获取文章失败');
      });
    },
    // 创建编辑器
    creatEdit() {
      var this_ = this;
      this_.editWangL = new E('#editListId', '#writenInId'); // 两个参数也可以传入 elem 对象，class 选择器
      // 下面两个配置，使用其中一个即可显示“上传图片”的tab。但是两者不要同时使用！！！
      // this_.editWangL.customConfig.uploadImgShowBase64 = true; // 使用 base64 保存图片
      this_.editWangL.customConfig.uploadImgServer = this.essayUploadPathUrl; // 上传图片到服务器
      // 限制图片大小
      this_.editWangL.customConfig.uploadImgMaxSize = 3 * 1024 * 1024;
      // 限制图片上传数量
      this_.editWangL.customConfig.uploadImgMaxLength = 1;
      // 自定义上传文件名称
      this_.editWangL.customConfig.uploadFileName = 'upFile';
      // 设置上传时候向后台传的参数
      this_.editWangL.customConfig.uploadImgParams = { essayFCuuId: '' };
      // 设置请求头
      this_.editWangL.customConfig.uploadImgHeaders = {
        verifyToken: '123456789ceshikey',
        loginToken: sessionStorage.getItem('loginToken')
      };
      this_.editWangL.customConfig.onchange = function(html) {
        // 监控变化，同步更新到 页面展示
        $('#watchAtId').html(html);
        // document.getElementById('writenInId').scrollTop = document.getElementById('writenInId').scrollHeight;
        // 在这里调用一个实时更新数据库数据的方法
        this_.changContentSave();
      };
      // 这里直接设置z-index的高度
      this_.editWangL.customConfig.zIndex = 100;
      // 设置上传的回调钩子的方法
      this_.editWangL.customConfig.uploadImgHooks = {
        before: function(xhr, editor, files) {
          console.log(xhr, editor, files, '图片上传的时候触发');
          // 图片上传之前触发
          // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，files 是选择的图片文件

          // 如果返回的结果是 {prevent: true, msg: 'xxxx'} 则表示用户放弃上传
          // return {
          //     prevent: true,
          //     msg: '放弃上传'
          // }
        },
        success: function(xhr, editor, result) {
          console.log(xhr, editor, result, '图片上传成功时候触发');
          // 图片上传并返回结果，图片插入成功之后触发
          // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
        },
        fail: function(xhr, editor, result) {
          console.log(xhr, editor, result, '图片上传并返回结果，但图片插入错误时触发');
          // 图片上传并返回结果，但图片插入错误时触发
          // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
        },
        error: function(xhr, editor) {
          console.log(xhr, editor, '图片上传出错时触发');
          // 图片上传出错时触发
          // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
        },
        timeout: function(xhr, editor) {
          console.log(xhr, editor, '图片上传超时时触发');
          // 图片上传超时时触发
          // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
        },

        // 如果服务器端返回的不是 {errno:0, data: [...]} 这种格式，可使用该配置
        // （但是，服务器端返回的必须是一个 JSON 格式字符串！！！否则会报错）
        customInsert: function(insertImg, result, editor) {
          console.log(insertImg, result, editor, '图片上传并返回结果，自定义插入图片的事件（而不是编辑器自动插入图片');
          // 图片上传并返回结果，自定义插入图片的事件（而不是编辑器自动插入图片！！！）
          // insertImg 是插入图片的函数，editor 是编辑器对象，result 是服务器端返回的结果

          // 举例：假如上传图片成功后，服务器端返回的是 {url:'....'} 这种格式，即可这样插入图片：
          var url = result.result.url;
          insertImg(url);
          // result 必须是一个 JSON 格式字符串！！！否则报错
        }
      };
      this_.editWangL.create();
    },
    // 获取文件内容
    getEditorContent(val) {
      if (val === 'text') {
        return this.editWangL.txt.text();
      } else if (val === 'html') {
        return this.editWangL.txt.html();
      } else if (val === 'json') {
        return this.editWangL.txt.getJSON();
      }
      return;
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
    checkNumAboutTitle() {
      var numTemp = this.editorTitle.length;
      if (numTemp > 100) {
        this.editorTitle = this.editorTitle.substring(0, 100);
      }
      this.inputNum = this.editorTitle.length;
      this.essayInfo.conTitle = this.editorTitle;
      this.changContentSave();
    },
    // 文章保存
    saveEditorFun() {
      if (this.essayInfo.conTitle === '') {
        this.$message.error('请输入文章的标题');
        return false;
      }
      this.essayInfo.conText = this.getEditorContent('html');
      this.updateEssayByIdFun();
    },
    submitEditorFun() {
      this.essayInfo.ariticleState = 'SWITCH_ON';
      this.saveEditorFun();
    },
    changContentSave() {
      // 这里设置一个每隔3s保存一下
      this.changContentSaveTime = new Date().getTime();
      var changContentSaveTimeTemp = this.changContentSaveTime;
      setTimeout(() => {
        if (changContentSaveTimeTemp === this.changContentSaveTime) {
          // 打开xss会有一些东西的丢失
          // this.essayInfo.conText = XSS(this.getEditorContent('html'));
          this.essayInfo.conText = this.getEditorContent('html');
          this.updateEssayByIdFunChange();
        }
      }, 3000);
    },
    // 文章的保存方法 也就是一个大的修改方法
    updateEssayByIdFun() {
      this.spinShow = true;
      let params = JSON.parse(JSON.stringify(this.essayInfo));
      params.dynamicParams = this.$store.state.appId;
      params.userToken = sessionStorage.getItem('loginToken');
      params.saveType = 'SAVE';
      updateEssayById(params).then(data => {
        this.spinShow = false;
        if (data.result.falg === 'success') {
          this.$message.success(data.result.message);
          this.$router.push({
            path: '/back/editarticlepage',
            query: {
              essayId: data.result.essay.essayId
            }
          });
          this.init();
        } else {
          this.$message.error(data.result.message);
        }
      }).catch(() => {
        this.spinShow = false;
        this.$message.error('保存数据失败');
      });
    },
    // 这里是修改时候走的后台的方法
    updateEssayByIdFunChange() {
      // this.spinShow = true;
      let params = JSON.parse(JSON.stringify(this.essayInfo));
      params.dynamicParams = this.$store.state.appId;
      params.userToken = sessionStorage.getItem('loginToken');
      params.saveType = 'TEMPSAVE';
      updateEssayById(params).then(data => {
        // this.spinShow = false;
        // if (data.result.falg === 'success') {
        //   this.$message.success('已经帮您记录下来');
        // } else {
        //   this.$message.error(data.result.message);
        // }
      }).catch(() => {
        // this.spinShow = false;
      });
    },
    mouseOn(val) { // 鼠标在上面
      if (val === 'left') {
        this.mouseLeft = true;
      } else {
        this.mouseLeft = false;
      }
    },
    // 滚动条同步
    goOnAllScroll() {
      if (this.mouseLeft) {
        $('#watchAtId').scrollTop($('.w-e-text').scrollTop());
      }
      if (!this.mouseLeft) {
        $('.w-e-text').scrollTop($('#watchAtId').scrollTop());
      }
    },
    // .w-e-text 方法设置
    setWETextFun() {
      $('.w-e-text').mouseover(() => {
        this.mouseOn('left');
      });
      $('.w-e-text').scroll(() => {
        this.goOnAllScroll('left');
      });
    },
    getShwoDrawerInfo() {
      this.showDrawer = true;
    },
    goback() {
      // this.$router.go(-1); // 返回上一层 /back/addArticle
      this.$router.push({
        path: '/back/addArticle'
      });
    },
    // 获取文章分类的数据
    changeEssayType(labels, selectedData) {
      console.log(selectedData, '打印这里选中的数据');
      var tempDa = [];
      for (let i = 0; i < selectedData.length; i++) {
        tempDa.push(selectedData[i].value);
      }
      this.essayInfo.conType = tempDa.join('-');
      this.changContentSave();
      return labels.join('/');
    },
    switchTrueFalse(val) {
      if (val === RC01_AOBPR_AL_SWITCH_ON_OFF.SWITCH_ON) {
        return true;
      } else {
        return false;
      }
    },
    changeSwitch(flag, row, val) {
      if (flag) {
        this.essayInfo[val] = RC01_AOBPR_AL_SWITCH_ON_OFF.SWITCH_ON;
      } else {
        this.essayInfo[val] = RC01_AOBPR_AL_SWITCH_ON_OFF.SWITCH_OFF;
      }
      this.changContentSave();
    },
    inputEssayTitlePicUrl() {
      if (this.essayInfo.titlePpicture === '') {
        this.essayTitlePicdefaultList = [];
      } else {
        this.essayTitlePicdefaultList = [
          {
            'name': '',
            'url': this.essayInfo.titlePpicture
          }
        ];
      }
      setTimeout(() => {
        this.uploadTitlePicList = this.$refs.essayTitlePicUpload.fileList;
      }, 100);
      this.changContentSave();
    },
    // 上传成功
    essayTitlePicHandleSuccess(res, file) {
      this.essayTitlePicdefaultList = [];
      if (res && res.result && res.result.flag === 'success') {
        this.essayInfo.titlePpicture = res.result.url + '?type=upload';
        this.essayTitlePicdefaultList = [
          {
            'name': res.result.name,
            'url': this.essayInfo.titlePpicture
          }
        ];
        setTimeout(() => {
          this.uploadTitlePicList = this.$refs.essayTitlePicUpload.fileList;
        }, 100);
        this.changContentSave();
        this.uploadDisableFlag = true;
      } else {
        this.this.essayInfo.titlePpicture = '';
        this.essayTitlePicdefaultList = [];
        this.uploadDisableFlag = false;
      }
    },
    essayTitlePicHandleFormatError(file) {
      this.$message.error('文件上传失败');
      this.uploadDisableFlag = false;
    },
    essayTitlePicHandleMaxSize(file) {
      console.log(file, '上传的大小进行校验');
    },
    essayTitlePicHandleBeforeUpload() {
      console.log('这里不知道是什么时候走的');
    },
    essayTitlePicHandleView(val) {
      console.log(val, '这里的数据是什么');
    },
    essayTitlePicHandleRemove(val) {
      if (val.url.indexOf('upload') > -1) {
        // 这里是上传的的删除
        let arrTemp = val.url.split('/');
        let arrTempTwo = arrTemp[arrTemp.length - 1] ;
        this.delPictureTitleFun(arrTempTwo.split('?')[0]);
      }
      this.uploadDisableFlag = false;
      this.essayInfo.titlePpicture = '';
      this.inputEssayTitlePicUrl();
    },
    viewEssayFun() {
      if (!this.showEssayInWindowFlag) {
        this.showEssayInWindowFlag = true;
        setTimeout(() => {
          this.TempEssay = this.getEditorContent('html');
          $('#showInWindowId').html(this.TempEssay);
        }, 100);
      }
    },
    writeEssayFun() {
      if (this.showEssayInWindowFlag) {
        this.showEssayInWindowFlag = false;
        setTimeout(() => {
          this.editWangL.txt.html(this.TempEssay);
          $('#watchAtId').html(this.TempEssay);
          this.setWETextFun();
        }, 100);
      }
    },
    // 设置源码的显示格式
    setEssayCodeFromttingFun() {
      this.setEssayCodeFromttingFlag = true;
    },
    // 格式化代码显示方法
    fromttingHtmlShowFun(data, config) {
      this.essayCodeInput = hy.fromttingHtml(data, config);
    },
    // 查看源码
    editCodeFromEssayFun() {
      this.essayCodeFlag = true;
      this.TempEssay = this.getEditorContent('html'); // 临时文章
      this.fromttingHtmlShowFun(this.TempEssay, this.setEssayCodeFromttingObj);
    },
    // 关闭源码查看
    closeEditModalFun() {
      this.essayCodeFlag = false;
    },
    saveEssayCodeFun() {
      // 不加密压缩 1加密压缩
      this.TempEssay = hy.pack_js(this.essayCodeInput, 0);
      this.essayInfo.conText = this.TempEssay;
      if (this.showEssayInWindowFlag) {
        // 这里是预览情况
        $('#showInWindowId').html(this.TempEssay);
      } else {
        // 这里是编辑情况
        setTimeout(() => {
          this.editWangL.txt.html(this.TempEssay);
          $('#watchAtId').html(this.TempEssay);
          this.setWETextFun();
        }, 100);
      }
      this.updateEssayByIdFunChange();
    },
    delPictureTitleFun(val) {
      let params = {
        dynamicParams: this.$store.state.appId,
        picFileCuuid: val
      };
      delPictureTitle(params);
    }
  },
  watch: { // 这里是进行监控滚动条的变化
  },
  created() {
    this.titleUploadPathUrl = TitleUploadPathUrl();
    this.essayUploadPathUrl = PageUploadPathUrl();
  }
};
</script>
<style lang="scss">
  @import './style.scss';
  @import '../../../../assets/style/publicStyle.scss';
</style>
