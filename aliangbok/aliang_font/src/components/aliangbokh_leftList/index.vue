<template>
  <div id="aliangbokHleftListId">
    <Menu :active-name="currentPageIn" theme="dark" width="auto" :class="menuitemClasses">
      <!-- 这里是基本信息的页面 -->
      <MenuItem name="welcome" to="/back/welcome">
      <Icon type="ios-navigate"></Icon>
      <span>欢迎</span>
      </MenuItem>
      <!-- 对当前登录人的信息维护 -->
      <MenuItem name="selfData" to="/back/selfData">
      <Icon type="md-information-circle" />
      <span>个人资料</span>
      </MenuItem>
      <!-- 这里添加文章的分离  文章分类为字典项 -->
      <MenuItem name="dictionaryOption" to="/back/dictionaryOption">
      <Icon type="md-attach" />
      <span>字典项管理</span>
      </MenuItem>
      <!-- 这里是对文章的编写 -->
      <MenuItem name="addArticle" to="/back/addArticle">
      <Icon type="ios-settings"></Icon>
      <span>添加文章</span>
      </MenuItem>
    </Menu>
  </div>
</template>
<script>

export default {
  props: {
    isCollapsedIn: {
      type: Boolean,
      default: false
    },
    currentPageIn: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
    };
  },
  computed: {
    menuitemClasses: function() {
      return [
        'menu-item',
        this.isCollapsedIn ? 'collapsed-menu' : ''
      ];
    }
  }
};
</script>
