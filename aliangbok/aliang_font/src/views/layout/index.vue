<template>
  <div id="mainLayout">
    <div class="layout">
      <Layout>
        <Header>
          <Inav :navNameVal="navName"></Inav>
        </Header>
        <Content>
          <Breadcrumb :style="{ margin: '0 auto 5px', width: '1200px', height: '20px' }">
            <BreadcrumbItem v-for="(item, index) in titleShwo" :key="index">{{item}}</BreadcrumbItem>
            <!-- <BreadcrumbItem>Components</BreadcrumbItem> -->
            <!-- <BreadcrumbItem>Layout</BreadcrumbItem> -->
          </Breadcrumb>
          <Card>
            <div style="min-height: 200px;">
              <router-view></router-view>
            </div>
          </Card>
        </Content>
        <Footer class="layout-footer-center">
          <IbokFooter></IbokFooter>
        </Footer>
      </Layout>
      <BackTop :height="100" :bottom="80"></BackTop>
    </div>
  </div>
</template>
<script>
import nav from '../../components/aliangbok_navList/header';
import bokFooter from '../../components/aliangbok_footer/index';

export default {
  components: {
    'Inav': nav,
    'IbokFooter': bokFooter
  },
  data() {
    return {
      navName: '',
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
  },
  methods: {
  }
};
</script>
<style lang="scss">
  @import './style.scss';
</style>
