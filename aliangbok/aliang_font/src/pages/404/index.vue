<template>
  <div id="page404">
      <div style="height: 100px;"></div>
      <div class="drawInfo">
        <h1 v-if="childerns.length==0">404页面失踪</h1>
        <Carousel v-model="value1" loop radius-dot>
          <CarouselItem v-for="ch in childerns" :key="ch.id">
            <div class="demo-carousel">
              <div class="showInfo">
                <div class="header404ShowInfo">
                  <div class="spancon">
                    <p>您访问的页面找不回来了!</p>
                    <p>但我们可以一起寻找失踪宝贝</p>
                  </div>
                  <h1>404&nbsp;</h1>
                </div>
                <div class="body404ShowInfo">
                  <ul>
                    <li>
                      <h3>姓名:</h3>
                      <span>{{ch.name}}</span>
                    </li>
                    <li>
                      <h3>性别:</h3>
                      <span>{{ch.sex}}</span>
                    </li>
                    <li>
                      <h3>出生日期:</h3>
                      <span>{{ch.birth_time}}</span>
                    </li>
                    <li>
                      <h3>失踪日期:</h3>
                      <span>{{ch.lost_time}}</span>
                    </li>
                    <li>
                      <h3>失踪地点:</h3>
                      <span>{{ch.lost_place}}</span>
                    </li>
                    <li>
                      <h3>失踪人特征描述:</h3>
                      <span>{{ch.child_feature}}</span>
                      </li>
                  </ul>
                </div>
                <div class="footer404ShowInfo">
                  <a href="http://e.t.qq.com/Tencent-Volunteers" class="txvolunteer" title="腾讯志愿者">腾讯志愿者</a>
                  <a href="http://bbs.baobeihuijia.com/" class="bbgohome" title="宝贝回家">宝贝回家</a>
                  <a href="mainPage" class="mainP">返回首页</a>
                  <span class="xiangx">|</span>
                  <a :href="ch.url" class="xiangx">详细</a>
                </div>
                <div class="pictureChildren">
                  <img :src="ch.child_pic" width="260px" alt="图片展示">
                </div>

              </div>
            </div>
          </CarouselItem>
        </Carousel>
      </div>
  </div>
</template>
<script>
import { get404Info } from '../../network/404/index';

export default {
  data() {
    return {
      value1: -1,
      childerns: [],
      cons: {}
    };
  },
  mounted() {
    get404Info().then(data => {
      console.log(data, '看看返回的信息');
      this.childerns = data.result.childrens;
      this.cons = data.result.cons;
    }).catch(() => {
      this.$message.error('获取信息失败');
    });
    // 这里是对页面进行计算的
    var heightWindow = $(window).height();
    $('#page404').height(heightWindow);
    this.value1 = 0;
  }
};
</script>
<style scoped lang="scss">
  @import "./style.scss";
</style>
