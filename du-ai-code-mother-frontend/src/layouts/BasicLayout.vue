<template>
  <a-layout class="layout">
    <!-- 上方导航栏 -->
    <GlobalHeader />
    
    <!-- 中间内容区域 -->
    <a-layout-content class="content">
      <div class="content-container">
        <router-view />
      </div>
    </a-layout-content>
    
    <!-- 下方版权信息 -->
    <GlobalFooter />
  </a-layout>
</template>

<script setup lang="ts">
import { Layout } from 'ant-design-vue';
import GlobalHeader from '../components/GlobalHeader.vue';
import GlobalFooter from '../components/GlobalFooter.vue';
</script>

<style scoped>
.layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.content {
  flex: 1;
  padding: 80px 24px 80px;
  /* 顶部和底部留出空间，避免内容被固定的 header 和 footer 遮挡 */
  background-color: #fff;
}

.content-container {
  max-width: 1440px;
  margin: 0 auto;
  width: 100%;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .content {
    padding: 80px 16px 60px;
  }
}

@media (max-width: 480px) {
  .content {
    padding: 80px 12px 60px;
  }
}
</style>