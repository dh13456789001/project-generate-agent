<template>
  <a-layout-header class="header">
    <div class="header-content">
      <div class="header-left">
        <div class="logo-container">
          <!-- 这里暂时使用简单的图标替代 logo.png，实际项目中需要替换为真实的 logo 图片 -->
          <a-icon type="code" class="logo-icon" />
          <span class="site-title">编程导航</span>
        </div>
        
        <a-menu 
          mode="horizontal"
          :items="menuItems"
          @click="handleMenuClick"
          class="header-menu"
          :selectedKeys="[selectedMenuKey]"
        />
      </div>
      
      <div class="header-right">
        <!-- 暂时使用登录按钮替代用户头像和昵称 -->
        <a-button type="primary" @click="handleLogin">
          <a-icon type="user" /> 登录
        </a-button>
      </div>
    </div>
  </a-layout-header>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import type { MenuProps } from 'ant-design-vue';
import { Button, Menu, Layout } from 'ant-design-vue';
import { CodeOutlined, UserOutlined } from '@ant-design/icons-vue';

// 定义菜单项类型
interface MenuItem {
  key: string;
  label: string;
  path: string;
  icon?: any;
}

// 菜单项数据
const menuItems: MenuItem[] = [
  {
    key: 'home',
    label: '首页',
    path: '/',
  },
  {
    key: 'about',
    label: '关于',
    path: '/about',
  },
  {
    key: 'features',
    label: '功能',
    path: '/features',
  },
  {
    key: 'docs',
    label: '文档',
    path: '/docs',
  },
];

// 当前选中的菜单项
const selectedMenuKey = ref('home');

// 处理菜单点击事件
const handleMenuClick = (e: any) => {
  selectedMenuKey.value = e.key;
  // 实际项目中可以在这里处理路由跳转
};

// 处理登录点击事件
const handleLogin = () => {
  console.log('用户点击了登录按钮');
  // 实际项目中可以跳转到登录页面
};
</script>

<style scoped>
.header {
  background-color: #fff;
  padding: 0 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  position: fixed;
  width: 100%;
  top: 0;
  z-index: 1000;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 64px;
  max-width: 1440px;
  margin: 0 auto;
}

.header-left {
  display: flex;
  align-items: center;
}

.logo-container {
  display: flex;
  align-items: center;
  margin-right: 48px;
}

.logo-icon {
  font-size: 24px;
  color: #1890ff;
  margin-right: 8px;
}

.site-title {
  font-size: 18px;
  font-weight: 600;
  color: #1890ff;
}

.header-menu {
  border-bottom: none;
}

.header-right {
  display: flex;
  align-items: center;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .header-content {
    padding: 0 16px;
  }
  
  .header-menu {
    display: none;
  }
  
  .logo-container {
    margin-right: 0;
  }
}
</style>