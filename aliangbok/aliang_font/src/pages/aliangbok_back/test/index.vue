<template>
  <div id="headerPictureId">
    <!-- <label class="btn btn-orange" for="uploads" style="display:inline-block;width: 70px;padding: 0;text-align: center;line-height: 28px;">选择图片</label> -->
    <!-- <input type="file" id="uploads" :value="imgFile" style="position:absolute; clip:rect(0 0 0 0);" accept="image/png, image/jpeg, image/gif, image/jpg"
      @change="uploadImg($event, 1)"> -->
    <div>
      <div class="cropper-content">
        <div class="cropper">
          <VueCropper ref="cropper" :img="option.img" :outputSize="option.size" :outputType="option.outputType" :info="true"
            :full="option.full" :canMove="option.canMove" :canMoveBox="option.canMoveBox" :original="option.original"
            :autoCrop="option.autoCrop" :autoCropWidth="option.autoCropWidth" :autoCropHeight="option.autoCropHeight"
            :fixedBox="option.fixedBox" @realTime="realTime" @imgLoad="imgLoad">
          </VueCropper>
        </div>
        <div style="margin-left:20px;">
          <div class="show-preview" :style="{'width': '155px', 'height':'155px',  'overflow': 'hidden', 'margin': '10px'}">
            <div :style="previews.div" class="preview">
              <img :src="previews.url" :style="previews.img">
            </div>
          </div>
        </div>
      </div>
    </div>
    <Row>
      <Col span="6">
        <ButtonGroup>
          <Button type="primary" title="放大" @click="changeScale(1)">&nbsp;放大&nbsp;</Button>
          <Button type="primary" title="缩小" @click="changeScale(-1)">&nbsp;缩小&nbsp;</Button>
        </ButtonGroup>
      </Col>
      <Col span="4">
        <Upload :before-upload="handleUpload" action="">
          <Button type="info">选择图片</Button>
        </Upload>
      </Col>
      <Col span="7">
        <ButtonGroup>
          <Button type="primary" ghost title="左旋转" @click="rotateLeft">左旋转</Button>
          <Button type="primary" ghost title="右旋转" @click="rotateRight">右旋转</Button>
        </ButtonGroup>
      </Col>
    </Row>
    <Row>
      <Col span="4">&nbsp;</Col>
      <Col>
        <Button title="下载图片" @click="down('base64')" icon="ios-download-outline" type="success">下载图片</Button>
        <Button title="上传头像" @click="finish('base64')" icon="ios-cloud-upload-outline" type="warning">上传头像</Button>
      </Col>
    </Row>
  </div>
</template>
<script>
import { testShow } from '../../../network/aliangbok_back/login/index';

export default {
  data() {
    return {
      headImg: '',
      // 剪切图片上传
      crap: false,
      previews: {},
      option: {
        img: '',
        outputSize: 1, // 剪切后的图片质量（0.1-1）
        full: false, // 输出原图比例截图 props名full
        outputType: 'png',
        canMove: true,
        original: false,
        canMoveBox: true,
        autoCrop: true,
        autoCropWidth: 150,
        autoCropHeight: 150,
        fixedBox: true
      },
      fileName: '', // 本机文件地址
      downImg: '#',
      imgFile: '',
      uploadImgRelaPath: '' // 上传后的图片的地址（不带服务器域名）
    };
  },
  methods: {
    test() {
      var getJson = {
        'username': 'aliangbok',
        'password': 'aliangbok'
      };
      testShow(getJson).then(data => {
        console.log(data);
      });
    },
    // 放大/缩小
    changeScale(num) {
      console.log('changeScale');
      this.$refs.cropper.changeScale(num || 1);
    },
    // 坐旋转
    rotateLeft() {
      console.log('rotateLeft');
      this.$refs.cropper.rotateLeft();
    },
    // 右旋转
    rotateRight() {
      console.log('rotateRight');
      this.$refs.cropper.rotateRight();
    },
    // 上传图片（点击上传按钮）
    finish(type) {
      console.log('finish');
      let formData = new FormData();
      // 输出
      if (type === 'blob') {
        this.$refs.cropper.getCropBlob((data) => {
          let img = window.URL.createObjectURL(data);
          this.model = true;
          this.modelSrc = img;
          console.log(data, 'blob的数据');
          formData.append('file', data, this.fileName);
          hyNetWork.post(
            'url',
            formData,
            { contentType: false, processData: false, headers: { 'Content-Type': 'application/x-www-form-urlencoded' }}
          ).then((response) => {
            console.log('这里是上传完成返回的数据', response);
          });
        });
      } else {
        this.$refs.cropper.getCropData((data) => {
          this.model = true;
          this.modelSrc = data;
          console.log(data, 'base64的数据');
          formData.append('file', data, this.fileName);
          hyNetWork.post(
            'url',
            formData,
            { contentType: false, processData: false, headers: { 'Content-Type': 'application/x-www-form-urlencoded' }}
          ).then((response) => {
            console.log('这里是上传完成返回的数据', response);
          });
        });
      }
    },
    // 实时预览函数
    realTime(data) {
      console.log('realTime', data);
      this.previews = data;
    },
    // 下载图片
    down(type) {
      console.log('down');
      var aLink = document.createElement('a');
      aLink.download = 'author-img';
      if (type === 'blob') {
        this.$refs.cropper.getCropBlob((data) => {
          this.downImg = window.URL.createObjectURL(data);
          aLink.href = window.URL.createObjectURL(data);
          aLink.click();
        });
      } else {
        this.$refs.cropper.getCropData((data) => {
          this.downImg = data;
          aLink.href = data;
          aLink.click();
        });
      }
    },
    // 选择本地图片
    uploadImg(e, num) {
      console.log(e, num, '这里的上传数据');
      console.log('uploadImg');
      var _this = this;
      // 上传图片
      var file = e.target.files[0];
      _this.fileName = file.name;
      if (!/\.(gif|jpg|jpeg|png|bmp|GIF|JPG|PNG)$/.test(e.target.value)) {
        alert('图片类型必须是.gif,jpeg,jpg,png,bmp中的一种');
        return false;
      }
      var reader = new FileReader();
      reader.onload = (e) => {
        let data;
        if (typeof e.target.result === 'object') {
          // 把Array Buffer转化为blob 如果是base64不需要
          data = window.URL.createObjectURL(new Blob([e.target.result]));
        } else {
          data = e.target.result;
        }
        if (num === 1) {
          _this.option.img = data;
        } else if (num === 2) {
          _this.example2.img = data;
        }
      };
      // 转化为base64 就使用上面这个
      // reader.readAsDataURL(file)
      // 转化为blob 就使用下面这个
      reader.readAsArrayBuffer(file);
    },
    imgLoad(msg) {
      console.log('imgLoad');
      console.log(msg);
    },
    handleUpload(file) {
      var _this = this;
      _this.fileName = file.name;
      var reader = new FileReader();
      reader.onload = (e) => {
        let data;
        if (typeof e.target.result === 'object') {
          // 把Array Buffer转化为blob 如果是base64不需要
          // data = window.URL.createObjectURL(new Blob([e.target.result]));
          data = window.URL.createObjectURL([e.target.result]);
        } else {
          data = e.target.result;
        }
        // if (num === 1) {
        //   _this.option.img = data;
        // } else if (num === 2) {
        //   _this.example2.img = data;
        // }
        _this.option.img = data;
      };
      // 转化为base64 使用这个方法
      reader.readAsDataURL(file);
      // 转化为blob 使用这个方法
      /* FileReader 接口提供的 readAsArrayBuffer() 方法用于启动读取指定的 Blob 或 File 内容。
      当读取操作完成时，readyState 变成 DONE（已完成），并触发 loadend 事件，同时 result 属性中将包含一个 ArrayBuffer 对象以表示所读取文件的数据。*/
      // reader.readAsArrayBuffer(file);
      return false;
    }
  }
};
</script>

<style lang="scss">
#headerPictureId {
  width: 500px;
  .cropper-content {
    height: 310px;
    .cropper {
      margin-left: 10px;
      margin-right: 10px;
      float: left;
      width: 300px;
      height: 300px;
    }
    .show-preview {
      float: left;
      .preview {
        overflow: hidden;
        border-radius: 50%;
        border: 1px solid #cccccc;
        background: #cccccc;
        border: 1px
      }
    }
  }
}
</style>
