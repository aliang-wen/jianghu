<template>
  <div id="laygoutHmain">
    <Layout :style="{minHeight: '100vh'}">
      <Sider collapsible :collapsed-width="78" v-model="isCollapsed">
        <aliangbokhleftList :isCollapsedIn="isCollapsed" :currentPageIn="currentPage"></aliangbokhleftList>
      </Sider>
      <Layout>
        <Header :style="{background: '#fff', boxShadow: '0 2px 3px 2px rgba(0,0,0,.1)'}">
          <!-- 这里是头部 -->
          <aliangbokhheader></aliangbokhheader>
        </Header>
        <Content :style="{padding: '0 10px 10px'}">
          <Breadcrumb :style="{ margin: '5px auto', width: '100%', height: '20px' }">
            <BreadcrumbItem v-for="(item, index) in titleShwo" :key="index">{{item}}</BreadcrumbItem>
            <!-- <BreadcrumbItem>Components</BreadcrumbItem> -->
            <!-- <BreadcrumbItem>Layout</BreadcrumbItem> -->
          </Breadcrumb>
          <Card>
            <div class="routerClass"  >
              <!-- 这里是内容 -->
              <router-view></router-view>
            </div>
          </Card>
        </Content>
      </Layout>
    </Layout>
  </div>
</template>
<script>
import aliangbokhheader from '../../components/aliangbokh_header/index';
import aliangbokhleftList from '../../components/aliangbokh_leftList/index';

export default {
  components: {
    aliangbokhheader,
    aliangbokhleftList
  },
  data() {
    return {
      isCollapsed: false,
      currentPage: this.$route.name,
      titleShwo: []
    };
  },
  mounted() {
    this.navName = this.$route.name;
    this.titleShwo = this.$route.meta.title;
  },
  watch: {
    $route(to, from) {
      this.navName = to.name;
      this.titleShwo = to.meta.title;
    }
  }
};
</script>
<style lang="scss">
  @import './style.scss';
</style>
