<template>
  <div id="drawTypeId">
    <Tag v-for="(da, index) in currentBigType" :key="'t' + index" :color="colorArr[Math.floor(Math.random()*(colorArr.length))]" @click.native="chooseTypeEssay(da)">{{da.label}}</Tag>
  </div>
</template>
<script>
export default {
  props: {
    currentBigType: {
      default: []
    }
  },
  data() {
    return {
      colorArr: [
        'default',
        'primary',
        'success',
        'error',
        'warning',
        'magenta',
        'red',
        'volcano',
        'orange',
        'gold',
        'yellow',
        'lime',
        'green',
        'cyan',
        'blue',
        'geekblue',
        '#FFA2D3'
      ]
    };
  },
  methods: {
    chooseTypeEssay(val) {
      this.$emit('getValue', val);
    }
  }
};
</script>
<style lang="scss">
@import './style.scss';
</style>
