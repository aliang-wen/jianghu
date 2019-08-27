<template>
  <div id="TimelineId">
    <div class="readRightPage">
      <Row>
        <Col span="1">&nbsp;</Col>
        <Col span="22">
        <Timeline pending>
          <TimelineItem v-for="(da, index) in datas" :key="'ti' + index">
            <p class="time">{{formatDateFun(da.createdData)}}</p>
            <a href="javascript:void(0)" class="content" @click="routerTo(da.essayId)">{{da.conTitle}}</a>
          </TimelineItem>
          <TimelineItem><a href="javascript:void(0)" @click="getMoreData">查看更多</a></TimelineItem>
        </Timeline>
        </Col>
        <Col span="1">&nbsp;</Col>
      </Row>

    </div>
    <div class="showLeftPage">
      <Card style="width:282px">
        <p slot="title">
          <Icon type="ios-film-outline"></Icon>
          推荐
        </p>
        <a href="javascript:void(0)" slot="extra">
          <Icon type="ios-loop-strong"></Icon>
          改变
        </a>
      </Card>
    </div>
    <!-- 这里的这个div是专门对待浮动的 -->
    <div class="clear"></div>
  </div>
</template>
<script>
import { getEssayServiceTimeLineListCon } from '../../../network/aliangbok_font/mainPage/index';
import { formatDate } from '../../../network/public';

export default {
  data() {
    return {
      essayCurrentPage: 1,
      essaySizePage: 10,
      clickTime: 1,
      total: 0,
      datas: []
    };
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      this.getEssayServiceTimeLineListConFun();
    },
    getEssayServiceTimeLineListConFun() {
      let params = {
        dynamicParams: this.$store.state.appId,
        essayCurrentPage: this.essayCurrentPage,
        essaySizePage: this.essaySizePage
      };
      getEssayServiceTimeLineListCon(params).then(data => {
        console.log(data, '获取的数据是什么');
        if (data.result.flag === 'success') {
          this.datas = data.result.essayList || [];
          this.total = data.result.total || [];
        } else {
          this.$message.error('获取数据失败');
        }
      }).catch(() => {
        this.$message.error('获取数据失败');
      });
    },
    getMoreData() {
      if (this.total > this.essaySizePage) {
        this.clickTime = this.clickTime + 1;
        this.essaySizePage = this.clickTime * this.essaySizePage;
      }
      this.getEssayServiceTimeLineListConFun();
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
    formatDateFun(val) {
      return formatDate(val);
    }
  }
};
</script>
<style lang="scss">
@import './style.scss';
</style>
